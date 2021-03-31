package FATCA.EncryptDecrypt;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.crypto.*;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.*;


/**
 * Created by Dmokshin on 4/22/2016.
 *
 * FATCA File Preparation
 * This class performs encryption and decryption
 *
 * Encryption:
 *
 * 1.Validates FATCA XML file and digitally sign
 * 2.Uses “enveloping” signature and creates SHA2-256 hash
 * 3.Create an RSA_ENCRYPTION digital signature using the 2048-bit private key that corresponds to the sender’s public key
 * 4.Compress XML files with compatible compression utility
 * 5.Encrypt XML file with AES-256 key with the following parameters:
 *   •Cipher mode: CBC
 *   •Salt: No salt
 *   •Initialization Vector (IV): 16 byte IV
 *   •Key size: 256 bits/32 bytes
 *   •Encoding: None
 * 6.Uses Padding: PKCS#5
 * 7.Encrypts AES key and IV (48 bytes total – 32 byte AES key and 16 byte IV) with public key of each recipient Padding: PKCS#1 v1.5 , Key size: 2048 bits
 * 8.Encrypts AES key and IV with public key of an approving HCTA Model 1, Option 2 (only)
 * 9.Creates sender metadata file using metadata schema without encrypting metadata.
 * 10.Creates transmission archive. Compress all files with compatible compression utility.
 *
 * Decryption:
 *
 * 1. Unzip Input DataPacket to get encrypted AES key and encrypted payload
 * 2. Decrypt AES combined with vector IV  with Private Key of receiver
 * 3. Decrypt Payload file with AES and vector IV to get compressed payload packet
 * 4. UnZip payload packet to get signed XML payload file
 * Returns path of payload file.
 *
 *
 */

public class FatcaFilePreparation_v01 {

    private static final String SIGNATURE_ALGORITHM = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256";
    private static final int AES_KEY_SIZE = 256;
    private static final int BUFFER_SIZE = 2048;
    private static final String AES_MODE = "AES";
    private static final String CBC_MODE = "CBC";
    private static final String ECB_MODE = "ECB";
    private static final String PKCS5_PADDING = "PKCS5Padding";
    private static final String PKCS1_PADDING = "PKCS1Padding";
    private static final String PKCS7_PADDING = "PKCS7Padding";
    private static final String RSA_ENCRYPTION = "RSA";

    public static void main(String[] args) throws Exception {

        // testing string obfuscation
        String testString = "password";
        byte[] compressedByteArray = compress(testString.getBytes("UTF-8"));
        byte[] b64comp = base64Encode(compressedByteArray);
        byte[] b64salted = base64Encode((new String(b64comp).substring(0,1)+new String(b64comp)).getBytes("UTF-8"));
        byte[] b64unsalted = new String(base64Decode(b64salted)).substring(1).getBytes("UTF-8");
        byte[] b64decomp = base64Decode(b64unsalted);
        byte[] decompressedString = decompress(b64decomp);
        System.out.println("original string: " + testString);
        System.out.println("after compress: " + new String(compressedByteArray));
        System.out.println("after encode: " + new String(b64comp));
        System.out.println("after salt: " + new String(b64salted));
        System.out.println("after unsalt: " + new String(b64unsalted));
        System.out.println("after decode: " + new String(b64decomp));
        System.out.println("after decompress: " + new String(decompressedString));
//        System.out.println("obfuscate: " + obfuscate("password"));
//        System.out.println("deobfuscate: " + deobfuscate(obfuscate("password")));

        // TEST parameters
        String inputGIIN = "G5ME2G.00007.ME.840";
        String inputReciver = "000000.00000.TA.840";

        // Sender Keystore
        String senderKeyAlias = "myp12key";
        String senderKeyStore = "mykeystore.p12";
        String senderKeyStorePassword = store("password");//"SEg0c0lBQUFBQUFBQUFDdElMQzR1enk5S0FRRFZSc0kxQ0FBQUFBPT0=";//"password";

        // Receiver Keystore
        String receiverKeyStore = "mykeystore.p12";
        // String receiverKeyStore = "delivery_axiomsl_com.p12";

        // String receiverKeyStore = "encryption-service_services_irs_gov.cer";
        String receiverKeyAlias = "myp12key";
        String receiverKeyStorePassword = store("password");//"SEg0c0lBQUFBQUFBQUFDdElMQzR1enk5S0FRRFZSc0kxQ0FBQUFBPT0=";//"password";
        String signedObjectTag = "FATCA";
        String workingDirectory = "/Users/ngelfman/IdeaProjects/fatcafileprep/testing";
        // The path to store all final files which will be compressed and submitted
        String finalDecryptedFolder = "/Users/ngelfman/IdeaProjects/fatcafileprep/testing/DECRYPTED";
        String finalOutputFolder = "/Users/ngelfman/IdeaProjects/fatcafileprep/testing/ENCRYPTED";
        String keyType = "KEYSTORE";

        // Initialize AES and Vector IV size
        int aesKeySize = 32;
        int vectorIVsize = 16;
        String encryptionMode = CBC_MODE;
        // String encryptionMode = ECB_MODE;

        // Set the fileSize limit for the submitted file in Mb
        int fileSizeLimit = 200;

        // Create new date format and UTC time zone to satisfy final compressed file name requirement
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmssSSS'Z'");
        TimeZone utc = TimeZone.getTimeZone("UTC");

        // Get current date time and compose it to UTC format
        Date date = new Date();
        dateFormat.setTimeZone(utc);
        String UTCTime = dateFormat.format(date);

        String signedPayloadPath = workingDirectory + File.separator + inputGIIN + "_Payload.xml";
        String senderKeyStorePath = workingDirectory + File.separator + senderKeyStore;
        String FATCAXMLPath = workingDirectory + File.separator + inputGIIN + ".xml";
        String compressedPayloadPath = workingDirectory + File.separator + inputGIIN + "_Payload.zip";
        String encryptedPayloadPath = finalOutputFolder + File.separator + inputGIIN + "_Payload";
        String encryptedKeyPath = finalOutputFolder + File.separator + inputReciver + "_Key";
        String receiverKeyStorePath = workingDirectory + File.separator + receiverKeyStore;
        String finalOutputPath = workingDirectory + File.separator + UTCTime + "_" + inputGIIN + ".zip";


        // Set a method to use for Canonicalization
        String canonicalizationMethod = "EXCLUSIVE";

        // Add <X509SubjectName> tag to the output file (String)
        String x509SubjectName = "true";

        // Add <Transforms> tag to the output file (String)
        boolean isTransformedTag = true;

        // Add <KeyInfo> tag to the output file (boolean)
        boolean keyInfoTag = false;

        // Generate AES Key
        SecretKey aesKey = createAESKey();

        // Generate IV Vector
        byte[] IVvector = getIVvector();

        // Create a new ArrayList and add in all files need to be compressed, then archive all of them
        List<String> filePathList = new ArrayList<>();

        ArrayList<Integer> aesIVlist = initializeIVandAES(aesKeySize,vectorIVsize);

        // Change ignoreLineBreaks system parameter value to true
        ignoreXMLLineBreaks();

        createDigitalSignature(FATCAXMLPath,
                signedPayloadPath,
                senderKeyStorePath,
                senderKeyStorePassword,
                senderKeyStorePassword,
                senderKeyAlias,
                signedObjectTag,
                canonicalizationMethod,
                x509SubjectName,
                isTransformedTag,
                keyInfoTag);
        archive(signedPayloadPath,
                workingDirectory,
                compressedPayloadPath);

        // Encrypt with AES
        encryptWithAES(compressedPayloadPath,
                encryptedPayloadPath,
                aesKey,
                IVvector,
                encryptionMode);

        // Get public key of recipient
        PublicKey publicKey =  getPublicKey(receiverKeyAlias,
                receiverKeyStorePassword,
                receiverKeyStorePath,
                keyType);

        // Encrypt AES with Public Key of receiver
        encryptWithPublicKey(encryptedKeyPath,
                aesKey.getEncoded(),
                publicKey,
                IVvector,
                encryptionMode);

        filePathList = generateFileList(finalOutputFolder,
                finalOutputFolder,
                filePathList);

        // String finalOutputPath = workingDirectory + File.separator + "/notif/" + "840FNA+s5zrpvRvdUtJbIswl9rKGcZsX" + ".zip";

        archiveFinal(filePathList,
                finalOutputFolder,
                finalOutputPath);

        exceedsAllowedFileSize(finalOutputPath, fileSizeLimit);

        /***************************************************************************************//*
         *//*****************************  Decryption Starts **************************************//*
         *//***************************************************************************************//*

         *//***
         * This method performs 4 steps of Decryption
         * 1. Unzip Input DataPacket to get encypted AES key and encrypted payload
         * 2. Decrypt AES combined with vector IV  with Private Key of receiver
         * 3. Decrypt Payload file with AES and vector IV to get compressed payload packet
         * 4. UnZip payload packet to get signed XML payload file
         *
         * Returns path of payload file.
         */
        // Clean Payload Directory
        cleanPayloadDirectory(new File(finalDecryptedFolder));

        // Unzip and Decrypt data packet to get Payload File
        String unZippedPayloadFilePath = getDecryptedPayload(senderKeyStorePath,
                senderKeyStorePassword,
                senderKeyAlias,
                finalOutputPath,
                finalDecryptedFolder,
                aesIVlist,
                encryptionMode);

        if(unZippedPayloadFilePath != null)
        {
            // Get Public key
            // Validate XML Signature of Payload
            boolean validationResult = validateXmlSignatureWithoutKeyInfo(unZippedPayloadFilePath, publicKey);

            if(validationResult) {
                System.out.println("--> Signature passed core validation <--");
            }

            // Remove signature nodes from the file
            removeSignatureNodes(unZippedPayloadFilePath);
        }
        unZipIt("C:\\Users\\007\\Desktop\\testDecryption\\840FjSl1C4NgtO9VfAvWHlqMxI8RyRsX.zip","C:\\Users\\007\\Desktop\\testDecrypt");
    }
    //Decodes text from base64
    private static byte[] base64Decode(byte[] text)
    {
        return Base64.getDecoder().decode(text);
    }

    //Encodes text to base64
    private static byte[] base64Encode(byte[] text)
    {

        return Base64.getEncoder().encode(text);
    }

    //GZips text
    private static byte[] compress(byte[] str) throws IOException
    {
        ByteArrayOutputStream obj=new ByteArrayOutputStream();
        GZIPOutputStream gio = new GZIPOutputStream(obj);
        gio.write(str);
        gio.close();
        return obj.toByteArray();
    }

    //UnGzips text
    private static byte[] decompress(byte[] bytes) throws IOException
    {
        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(bytes));
        BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
        String outStr = "";
        String line;
        while ((line=bf.readLine())!=null)
        {
            outStr += line;
        }
        return outStr.getBytes("UTF-8");
    }

    //Gzips a string, base64 encodes it, salts it using the first char of the resulting string, and base64 encodes it again
    public static String store(String password) throws IOException
    {
        byte[] compressedByteArray = compress(password.getBytes("UTF-8"));
        byte[] b64comp = base64Encode(compressedByteArray);
        byte[] b64salted = base64Encode((new String(b64comp).substring(0,1)+new String(b64comp)).getBytes("UTF-8"));
        return new String(b64salted);
    }

    //If string is obfuscated, decodes string from base64, unsalts it, decodes from base64 again, unzips the result. Otherwise returns passed string.
    private static String retrieve(String password) throws IOException
    {
        try
        {
            byte[] b64unsalted = new String(base64Decode(password.getBytes("UTF-8"))).substring(1).getBytes("UTF-8");
            byte[] b64decomp = base64Decode(b64unsalted);
            byte[] decompressedString = decompress(b64decomp);
            return new String(decompressedString);
        }
        catch (ZipException ex)
        {
            return password;
        }
        catch (IllegalArgumentException ex)
        {
            return password;
        }
        catch (IOException ex)
        {
            return password;
        }
        catch (StringIndexOutOfBoundsException ex)
        {
            return password;
        }
    }


    private static boolean cleanPayloadDirectory(File dir) throws FileNotFoundException
    {
        System.out.println("--> Start Payload dir cleaning <--");

        boolean isExists;

        if(dir.exists()) {
            System.out.println("--> Cleaning files in Payload dir <--");
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) cleanPayloadDirectory(file);
                file.delete();
            }
            isExists = true;
        }
        else
        {
            throw new FileNotFoundException("The directory " + dir + " does not exist. Please create directory and try again");
        }

        System.out.println("--> End Payload dir cleaning <--");

        return isExists;

    }

    public static PublicKey getPublicKey(String receiverKeyAlias, String receiverKeyStorePassword, String receiverKeyStorePath, String keyType) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {

        //deobfuscate password
        receiverKeyStorePassword = retrieve(receiverKeyStorePassword);

        Certificate cert;
        if(keyType.startsWith("K"))
        {
            System.out.println("--> Key type is " + keyType + " <--");
            // Get public Key from receiver's p12 file
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new FileInputStream(receiverKeyStorePath),receiverKeyStorePassword.toCharArray());
            cert = keyStore.getCertificate(receiverKeyAlias);
            System.out.println("--> Certificate was extracted succesfully <--");

        }
        else
        {
            System.out.println("--> Key type is " + keyType + " <--");
            // Get public Key from receiver's p12 file
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            cert = cf.generateCertificate(new FileInputStream(receiverKeyStorePath));
            System.out.println("--> Certificate was extracted succesfully <--");
        }

        System.out.println("--> Public key is extracted succesfully <--");
        try
        {
            return cert.getPublicKey();
        }
        catch (NullPointerException ex)
        {
            throw new NullPointerException("Key alias is empty or incorrect.");
        }

    }

    public static String nullCheck(String s) {
        if (s == null)
        {
            throw new IllegalArgumentException("The argument " + s + " cannot be null");
        }
        else
        {
            return s;
        }
    }

    public static String getDecryptedPayload(String receiverKeyStorePath, String receiverKeyStorePassword, String receiverKeyAlias,
                                             String zippedDataPacket, String finalOutputFolder, ArrayList<Integer> aesIVlist, String encryptionMode) throws Exception{

        //deobfuscate password
        receiverKeyStorePassword = retrieve(receiverKeyStorePassword);

        String unZippedPayloadFilePath = null;
        HashMap<String, String> unZippedFiles;

        // Unzip Input DataPacket to get encypted AES key and encrypted payload
        unZippedFiles = unZipIt(zippedDataPacket,finalOutputFolder);
        String encrypted_payload_path = unZippedFiles.get("encrypted_payload_path");
        String encrypted_aes_key_path = unZippedFiles.get("encrypted_aes_key_full_path");

        // Decrypt AES Key with Private Key of receiver
        HashMap<String, byte[]> aesIvMap = decryptAESKeyWithPrivateKey(receiverKeyStorePath, receiverKeyStorePassword, receiverKeyAlias, encrypted_aes_key_path, aesIVlist, encryptionMode);

        // Decrypt Payload file with AES key (obtained from above step) to get compressed payload packet
        String decryptedZippedPayloadPath = decryptPayloadFileWithAES(encrypted_payload_path, finalOutputFolder, aesIvMap, encryptionMode);

        // UnZip payload packet (obtained from above step) to get signed XML payload file
        unZippedFiles = unZipIt(decryptedZippedPayloadPath, finalOutputFolder);

        // Get path of unzipped Payload xml file
        unZippedPayloadFilePath = unZippedFiles.get("uncompressed_dencrypted_payload_full_path");

        return unZippedPayloadFilePath;
    }


    // Change ignoreLineBreaks system parameter value to true
    // to extend 76 maximum characters length in one line
    public static void ignoreXMLLineBreaks() {
        Properties props = System.getProperties();
        props.setProperty("com.sun.org.apache.xml.internal.security.ignoreLineBreaks", "true");
    }

    // Step 1, Digital signature and enveloping FACTA XML file
    public static void createDigitalSignature(String inputPath,
                                              String outputPath, String keyStorePath, String keyStorePassword,
                                              String keyPassword, String keyAlias, String signedObjectTag, String canonicalizationMethod, String x509SubjectName, boolean transformedTag, boolean keyInfoTag)
            throws Exception {

        //deobfuscate password
        keyStorePassword = retrieve(keyStorePassword);
        keyPassword = retrieve(keyPassword);

        // First, create the DOM XMLSignatureFactory that will be used to
        // generate the XMLSignature
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

        // Next, create a Reference to a same-document URI that is an Object
        // element and specify the SHA256 digest algorithm

        // Adding Canonicalization method for a generated signature file
        // EXCLUSIVE or INCLUSIVE

        Reference ref;

        // Adding Transformation tag options:
        // true - to add it to a generated file;
        // false - to omit it from a file

        canonicalizationMethod = canonicalizationMethod.startsWith("E") ? CanonicalizationMethod.EXCLUSIVE : CanonicalizationMethod.INCLUSIVE;

        if(transformedTag)
        {
            ref = fac.newReference("#" + signedObjectTag,
                    fac.newDigestMethod(DigestMethod.SHA256, null),
                    Collections.singletonList(fac.newTransform(canonicalizationMethod,
                            (TransformParameterSpec) null)), null, null);
        }
        else
        {
            ref = fac.newReference("#" + signedObjectTag, fac.newDigestMethod(DigestMethod.SHA256, null), null, null, null);
        }

        System.out.println("--> Start to create a digital signature <--");

        Document inputXMLDoc = getDocumentBuilder().parse(new File(inputPath));
        Document outputXMLDoc = getDocumentBuilder().newDocument();
        Node rootNode = inputXMLDoc.getDocumentElement();
        XMLStructure content = new DOMStructure(rootNode);
        XMLObject obj = fac.newXMLObject(Collections.singletonList(content),
                signedObjectTag, null, null);

        // Create the SignedInfo
        SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(
                canonicalizationMethod,
                (C14NMethodParameterSpec) null), fac.newSignatureMethod(
                SIGNATURE_ALGORITHM, null), Collections.singletonList(ref));

        System.out.println("--> Load the KeyStore and get the signing key and certificate <--");
        // Load the KeyStore and get the signing key and certificate.
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(new FileInputStream(keyStorePath),
                keyStorePassword.toCharArray());
        KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(keyAlias,
                new KeyStore.PasswordProtection(keyPassword.toCharArray()));
        X509Certificate cert;
        try
        {
            cert = (X509Certificate) keyEntry.getCertificate();
        }
        catch (NullPointerException ex)
        {
            throw new NullPointerException("Key alias is empty or incorrect.");
        }

        // Create the KeyInfo containing the X509Data.
        KeyInfoFactory kif = fac.getKeyInfoFactory();
        @SuppressWarnings("rawtypes")
        List x509Content = new ArrayList();
        if(x509SubjectName.equals("true"))
        {
            x509Content.add(cert.getSubjectX500Principal().getName());
        }

        x509Content.add(cert);
        X509Data xd = kif.newX509Data(x509Content);
        KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

        // Create the XMLSignature (but don't sign it yet)
        // Check if KeyInfo tag is required. If yes then generate with KeyInfo tag.

        XMLSignature signature;

        if(keyInfoTag)
        {
            signature = fac.newXMLSignature(si, ki,
                    Collections.singletonList(obj), null, null);
        }
        else
        {
            signature = fac.newXMLSignature(si, null,
                    Collections.singletonList(obj), null, null);
        }


        // Create a DOMSignContext and specify the PrivateKey for signing
        // and the document location of the XMLSignature
        DOMSignContext dsc = new DOMSignContext(keyEntry.getPrivateKey(), outputXMLDoc);


        // Lastly, generate the enveloping signature using the PrivateKey
        signature.sign(dsc);

        System.out.println("--> Document is signed <--");

        // output the resulting document
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer trans = tf.newTransformer();
        trans.transform(new DOMSource(outputXMLDoc),
                new StreamResult(new File(outputPath)));

        System.out.println("--> End digital signature signing <--");
    }

    // Generates one time AES 256 key to encrypt compressed file and will be
    // used to encrypt by receiver's public key
    // Since Java support AES 128 as default, to allow 256 bits, user need to replace
    // two new jar files in all dir {java.home}/jre/lib/security
    // with US_export_policy.jara and local_policy.jar
    public static SecretKey createAESKey() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        KeyGenerator kgen = KeyGenerator.getInstance(AES_MODE);
        kgen.init(AES_KEY_SIZE, secureRandom);

        return kgen.generateKey();
    }

    public static void removeSignatureNodes(String filePath) throws ParserConfigurationException, IOException, SAXException, XMLStreamException, TransformerException {

        DocumentBuilder documentBuilder = getDocumentBuilder();

        // Read in FATCA XML file and get the root node
        Document inputXMLDoc = documentBuilder.parse(new File(filePath));
        inputXMLDoc.getDocumentElement().normalize();

        // Get Object node and create a transformer with indent
        NodeList nodeList = inputXMLDoc.getElementsByTagName("Object");
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        // Iterate through the all nodes and write to a source
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node rowNode = nodeList.item(i);
            DOMSource source = new DOMSource(rowNode);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
        }
    }

    public static byte[] getIVvector() throws NoSuchAlgorithmException {
        // Create an 16-byte secure initialization vector
        byte[] ivVector = new byte[16];
        new SecureRandom().nextBytes(ivVector);
        return ivVector;
    }

    public static ArrayList<Integer> initializeIVandAES(int aesKeySize, int ivVectorSize)
    {
        ArrayList<Integer> aesIvList = new ArrayList<>();
        aesIvList.add(aesKeySize);
        aesIvList.add(ivVectorSize);

        return  aesIvList;
    }


    // Step 3, Encrypts and then copies the contents of a given file with AES 256 key
    public static void encryptWithAES(String inputPath, String outputPath,
                                      SecretKey aesKey, byte[] ivVector, String encryptionMode) throws InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IOException, BadPaddingException, IllegalBlockSizeException {


        System.out.println("--> Start encrypting with AES Key <--");
        FileInputStream is = null;
        CipherOutputStream os = null;
        Cipher aesCipher = Cipher.getInstance(AES_MODE + "/" + encryptionMode + "/" + PKCS5_PADDING);

        if(encryptionMode.equals(CBC_MODE)) {
            SecretKeySpec keySpec = new SecretKeySpec(aesKey.getEncoded(), AES_MODE);
            IvParameterSpec IVvector = new IvParameterSpec(ivVector);

            // Adding IV Vector to the cipher
            aesCipher.init(Cipher.ENCRYPT_MODE, keySpec, IVvector);
            System.out.println("--> Init cipher with AES key and IV <--");
        }
        else
        {
            aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
            System.out.println("--> Init cipher AES key <--");
        }

        try {
            is = new FileInputStream(new File(inputPath));
            os = new CipherOutputStream(new FileOutputStream(new File(outputPath)), aesCipher);
            streamCopy(is, os);
            System.out.println("--> AES and IV was copied succesfully <--");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(os);
            closeQuietly(is);
        }
    }



    // Step 4, Encrypts AES 256 key with public from receiver's certificate
    // added String keyStorePassword, String keyAlias as parameters
    // to retrieve public key from p12 file.
    public static void encryptWithPublicKey(String outputPath, byte[] aesKey, PublicKey publicKey, byte[] IVvector, String encryptionMode) throws GeneralSecurityException {

        CipherOutputStream os = null;

        try {
            Cipher pkCipher = Cipher.getInstance(RSA_ENCRYPTION);

            System.out.println("--> Start encrypting with Public Key <--");
            pkCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            os = new CipherOutputStream(new FileOutputStream(new File(outputPath)), pkCipher);
            System.out.println("--> Start AES key writing <--");
            os.write(aesKey);
            System.out.println("--> AES was written succesfully <--");
            if(encryptionMode.equals(CBC_MODE)) {

                System.out.println("--> Encryption mode is " + encryptionMode +" <--");
                os.write(IVvector);
                System.out.println("--> IV was written succesfully <--");
            }
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(os);
        }
    }


    // Step 2, Compress single file
    public static ZipOutputStream archive(String childfilePath, String parentFilePath, String outputPath) {

        ZipOutputStream zos = null;
        FileInputStream in = null;

        try {

            System.out.println("--> Start archiving <--");
            zos = new ZipOutputStream(new FileOutputStream(outputPath));
            System.out.println("--> " + outputPath + " File was received succesfully <--");
            zos.setLevel(Deflater.BEST_COMPRESSION);
            zos.setMethod(ZipOutputStream.DEFLATED);

            File parentNode = new File(parentFilePath);
            File node = new File(childfilePath);
            in = new FileInputStream(node);

            String fileCanonicalPath = node.getCanonicalPath();
            String rootCanonicalPath = parentNode.getCanonicalPath();
            String file = fileCanonicalPath.substring(rootCanonicalPath.length() + 1, fileCanonicalPath.length());

            // Create zip entry
            ZipEntry ze = new ZipEntry(file);
            zos.putNextEntry(ze);

            System.out.println("--> Start stream copy <--");
            streamCopy(in, zos);
            System.out.println("--> Stream copy is completed <--");

            zos.closeEntry();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(zos);
            closeQuietly(in);
        }

        return zos;
    }

    // Step 5, Recursively compress multiple files stored in a ArrayList
    public static ZipOutputStream archiveFinal(List<String> fileList, String parentFilePath,
                                               String outputPath) throws NoSuchAlgorithmException {

        ZipOutputStream zos = null;

        try {
            zos = new ZipOutputStream(new FileOutputStream(outputPath));
            zos.setLevel(Deflater.BEST_COMPRESSION);
            zos.setMethod(ZipOutputStream.DEFLATED);

            System.out.println("--> Output to Zip: " + outputPath + " <--");
            for (String file : fileList) {

                // Create zip entry based on string stored in ArrayList
                System.out.println("--> File Added : " + file + " <--");
                ZipEntry ze = new ZipEntry(file);
                zos.putNextEntry(ze);

                // Read in files with full path
                File parentNode = new File(parentFilePath);
                FileInputStream in = null;
                try {
                    in = new FileInputStream(parentNode + File.separator + file);
                    streamCopy(in, zos);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeQuietly(in);
                }
            }

            zos.closeEntry();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(zos);
        }

        return zos;
    }

    // Traverse a directory and get all files, add them into fileList
    public static List<String> generateFileList(String childfilePath, String parentFilePath,
                                                List<String> filepPathList) throws IOException {

        File node = new File(childfilePath);
        File parentNode = new File(parentFilePath);

        // Add file in ArrayList if node is a file
        if (node.isFile()) {
            String fileCanonicalPath = node.getCanonicalPath();	// Canonical path of current file
            String parentCanonicalPath = parentNode.getCanonicalPath();	// Canonical path of parent directory

            // Get file name only and add into ArrayList, which will be used to create ZipEntry
            filepPathList.add(fileCanonicalPath.substring(parentCanonicalPath.length() + 1, fileCanonicalPath.length()));

        }

        // Recursive progress if node is a dir
        if (node.isDirectory()) {
            String[] subNode = node.list();

            for (String filename : subNode) {

                // Create full file path to be treated as file type
                String childFilePath = childfilePath + File.separator + filename;
                generateFileList(childFilePath, parentFilePath, filepPathList);
            }
        }

        return filepPathList;

    }

    // Copy a stream
    private static void streamCopy(InputStream is, OutputStream os) throws IOException{
        int i;
        byte[] b = new byte[BUFFER_SIZE];
        while ((i = is.read(b)) != -1) {
            os.write(b, 0, i);
        }
    }

    // Close a possibly null stream ignoring IOExceptions
    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // Check if the file size exceeds the limit of 200MB
    public static boolean exceedsAllowedFileSize(String zipFile, int fileSizeLimit)
    {
        File file = new File(zipFile);
        boolean isLimitExceeded = false;

        if(file.exists())
        {
            double bytes = file.length();
            double kilobytes = (bytes/1024);
            double megabytes = (kilobytes / 1024);

            if(bytes == 0)
            {
                isLimitExceeded = true;
                System.out.println("--> Archive size cannot be 0 or empty. Your size is " + fileSizeLimit + "Mb <--");
            }
            else if(megabytes <= fileSizeLimit)
            {
                System.out.println("--> Archive does not exceed the allowed limit of " + fileSizeLimit + "Mb <--");
            }
            else
            {
                isLimitExceeded = true;
                System.out.println("--> Archive exceeds the allowed file limit of " + fileSizeLimit + "Mb <--");

            }
        }
        return isLimitExceeded;
    }

    // Unzips compressed file and returns file's location with name-title in a HashMap
    public static HashMap<String, String> unZipIt(String zipFile, String outputFolder) throws IOException{

        byte[] buffer = new byte[1024];
        HashMap<String, String> unZippedFiles = new HashMap<>();
        ZipInputStream zis = null;

        try{

            //create output directory is not exists
            File folder = new File(outputFolder);
            if(!folder.exists()){
                folder.mkdir();
            }


            //get the zip file content
            zis = new ZipInputStream(new FileInputStream(zipFile));

            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while(ze!=null){

                System.out.println("--> Start process of unzipping <--");
                // Get name of unzipped file and save the location in HashMap (named unzippedFiles) accordingly
                String fileName = ze.getName();
                if (fileName.contains("_Payload.xml")) {
                    unZippedFiles.put("uncompressed_dencrypted_payload_full_path", outputFolder + File.separator + fileName);
                    System.out.println("--> Payload.xml unzipped succesfully <--");
                }else if (fileName.contains("_Payload")) {
                    unZippedFiles.put("encrypted_payload_path", outputFolder + File.separator + fileName);
                    System.out.println("--> Payload unzipped succesfully <--");
                }

                if (fileName.contains("_Key")) {
                    unZippedFiles.put("encrypted_aes_key_full_path", outputFolder + File.separator + fileName);
                    System.out.println("--> Key unzipped succesfully <--");
                }

                // Save unzipped file to specified location.
                File newFile = new File(outputFolder + File.separator + fileName);
                System.out.println("--> File unzipped : " + newFile.getAbsoluteFile() + " <--");

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);

                int len;

                System.out.println("--> Start writing zip <--");
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                // Close streams
                fos.close();
                ze = zis.getNextEntry();
            }
            System.out.println("--> End writing zip <--");
            zis.closeEntry();

        }catch(IOException ex){
            ex.printStackTrace();
        }finally{
            zis.close();
        }

        return unZippedFiles;
    }


    // Decrypt AES key with Private key of receiver
    public static HashMap<String, byte []> decryptAESKeyWithPrivateKey(String keyStoreFullPath,
                                                                       String keyStorePassword,
                                                                       String KeyStoreAlias,
                                                                       String encrypted_aes_key_full_path,
                                                                       ArrayList<Integer> aesIvList, String encryptionMode)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, InvalidKeyException, NoSuchPaddingException, IOException, BadPaddingException, IllegalBlockSizeException {



        FileInputStream fis;
        CipherInputStream cis;
        ByteArrayOutputStream bos;
        HashMap<String, byte[]> aesIvMap = new HashMap<>();
        SecretKey originalKey;

        try{

            System.out.println("--> Start obtaining Private Key for decryption <--");
            // Get KeyStore instance
            KeyStore ks = KeyStore.getInstance("PKCS12");

            // Get receiver's Private key from keystore
            ks.load(new FileInputStream(keyStoreFullPath), keyStorePassword.toCharArray());
            Key privateKey = ks.getKey(KeyStoreAlias, keyStorePassword.toCharArray());
            System.out.println("--> Private key is obtained <--");

            Cipher pkCipher = Cipher.getInstance(RSA_ENCRYPTION);
            try
            {
                pkCipher.init(Cipher.DECRYPT_MODE, privateKey);
            }
            catch (NullPointerException ex)
            {
                throw new NullPointerException("Key alias is empty or incorrect.");
            }

            // Decrypt AES key with Private key
            fis = new FileInputStream(encrypted_aes_key_full_path);
            cis = new CipherInputStream(fis, pkCipher);

            System.out.println("--> Start reading the key <--");

            bos = new ByteArrayOutputStream();
            int i;
            byte[] block = new byte[8];
            while ((i = cis.read(block)) != -1) {
                bos.write(block, 0, i);
            }

            bos.close();
            cis.close();
            fis.close();

            System.out.println("--> End reading the key <--");

            // get Decrypted AES Key
            byte[] decryptedAESKey = bos.toByteArray();

            int aesKeySize = aesIvList.get(0);

            if(encryptionMode.equals(CBC_MODE))
            {
                System.out.println("--> Encryption mode is " + encryptionMode + " <--");

                int ivVectorSize = aesIvList.get(1);

                // Initialize AES and IV vector
                byte[] bytesVectorIV = new byte[ivVectorSize];
                byte[] bytesAES = new byte[aesKeySize];

                // Divide Combined key in AES and vector IV
                System.arraycopy(decryptedAESKey, 0, bytesAES, 0 , aesKeySize);
                System.arraycopy(decryptedAESKey, aesKeySize, bytesVectorIV, 0, ivVectorSize);

                // Define secret key
                originalKey = new SecretKeySpec(bytesAES, 0, bytesAES.length, AES_MODE);

                // Put the AES and Vector IV in array
                aesIvMap.put("AES_KEY", originalKey.getEncoded());
                aesIvMap.put("IV_VECTOR", bytesVectorIV);

                System.out.println("--> Added AES and IV to the map <--");

            }
            else
            {
                System.out.println("--> Encryption mode is " + encryptionMode + " <--");

                originalKey = new SecretKeySpec(decryptedAESKey, 0, decryptedAESKey.length, AES_MODE);
                aesIvMap.put("AES_KEY", originalKey.getEncoded());

                System.out.println("--> Added AES to the map <--");

            }

            System.out.println("--> Map is received <--");
            return aesIvMap;
        }catch(FileNotFoundException e){
            throw new FileNotFoundException("The system cannot find " + encrypted_aes_key_full_path + " file");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return aesIvMap;
    }

    // Decrypts payload file with AES Key
    public static String decryptPayloadFileWithAES(String inputPath, String finalOutputFolder, HashMap<String, byte[]> AESIVMap, String encryptionMode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {

        String outputPath = finalOutputFolder + File.separator + "Decrypted_Payload.zip";
        FileInputStream fis;
        CipherInputStream cis;
        FileOutputStream fos;

        try{
            System.out.println("--> Start decrypting Payload <--");
            // Getting AES key and IV vector
            byte[] AESkey = AESIVMap.get("AES_KEY");
            byte[] IVvector = AESIVMap.get("IV_VECTOR");

            SecretKey aesKey = new SecretKeySpec(AESkey, 0, AESkey.length, AES_MODE);

            // Get cipher instance and initialize it with AES Key
            Cipher aesCipher = Cipher.getInstance(AES_MODE + "/" + encryptionMode + "/" + PKCS5_PADDING);

            if(encryptionMode.equals(CBC_MODE))
            {
                // Add IV vector to the cipher
                IvParameterSpec alg = new IvParameterSpec(IVvector);
                // Initialize decrypting mode with AES Key and vector IV
                aesCipher.init(Cipher.DECRYPT_MODE, aesKey, alg);
                System.out.println("--> Init cipher <--");
            }
            else
            {
                aesCipher.init(Cipher.DECRYPT_MODE, aesKey);
                System.out.println("--> Init cipher <--");

            }

            fis = new FileInputStream(inputPath);
            cis = new CipherInputStream(fis, aesCipher);
            fos = new FileOutputStream(outputPath);

            // Go through  payload and Decrypt it with AES key and store it at specified location(iotputPath)
            int i;
            byte[] block = new byte[8];
            System.out.println("--> Start writing to the file <--");
            while ((i = cis.read(block)) != -1) {
                fos.write(block, 0, i);
            }
            System.out.println("--> End writing to the file <--");

            fos.close();
            cis.close();
            fis.close();

        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        // Return path of decrypted payload packet(a zip file)
        return outputPath;
    }

    public static boolean validateXmlSignatureWithoutKeyInfo(String signedFile, PublicKey publicKey) throws Exception  {
        XMLSignatureFactory xmlSigFactory = XMLSignatureFactory.getInstance();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(signedFile));
        boolean coreValidity = false;
        boolean refValid = false;
        String objectString = "Object";
        String signatureString = "Signature";
        String signatureXML = XMLSignature.XMLNS;
        NodeList objectNode = doc.getElementsByTagNameNS(signatureXML, objectString);
        NodeList signatureNode = doc.getElementsByTagNameNS(signatureXML, signatureString);

        if(publicKey == null)
        {
            throw new Exception("Public key is missing or not valid");
        }
        else {
            if (objectNode.getLength() == 0 && signatureNode.getLength() == 0) {
                throw new Exception("Signature element and Object is not found");
            } else {
                Node sigNode = signatureNode.item(0);
                DOMValidateContext valContext = new DOMValidateContext(publicKey, sigNode);
                XMLSignature signature = xmlSigFactory.unmarshalXMLSignature(valContext);
                coreValidity = signature.validate(valContext);

                // Validate the XMLSignature.
                // Check core validation status.
                System.out.println("--> Start core validity check <--");
                if (coreValidity) {
                    boolean sv = signature.getSignatureValue().validate(valContext);
                    System.out.println("--> Signature validation is " + sv + " <--");
                    if (sv) {
                        // Check the validation status of each Reference.
                        @SuppressWarnings("rawtypes")
                        Iterator i = signature.getSignedInfo().getReferences().iterator();
                        for (int j = 0; i.hasNext(); j++) {
                            refValid = ((Reference) i.next()).validate(valContext);
                        }
                    }
                }
            }
        }
        System.out.println("--> Signature and core validation is " + refValid + " <--");
        return refValid;
    }


    public static boolean validateXmlSignatureWithKeyInfo(String unZippedPayloadFilePath) {

        boolean coreValidity = false;

        try{

            XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            Document doc = dbf.newDocumentBuilder().parse(new FileInputStream(unZippedPayloadFilePath));

            // Find Signature element.
            NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");

            if (nl.getLength() == 0) {
                throw new Exception("Cannot find Signature element");
            }

            // Create a DOMValidateContext and specify a KeySelector
            // and document context.
            DOMValidateContext valContext = new DOMValidateContext(new X509KeySelector(), nl.item(0));

            // Unmarshal the XMLSignature.
            XMLSignature signature = fac.unmarshalXMLSignature(valContext);

            // Validate the XMLSignature.
            coreValidity = signature.validate(valContext);

            // Check core validation status.
            if (coreValidity == false) {
                System.err.println("Signature failed core validation");
                boolean sv = signature.getSignatureValue().validate(valContext);
                System.out.println("signature validation status: " + sv);
                if (sv == false) {
                    // Check the validation status of each Reference.
                    @SuppressWarnings("rawtypes")
                    Iterator i = signature.getSignedInfo().getReferences().iterator();
                    for (int j=0; i.hasNext(); j++) {
                        boolean refValid = ((Reference) i.next()).validate(valContext);
                        System.out.println("--> ref[\"+j+\"] validity status: " + refValid + " <--");
                    }
                }
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return coreValidity;

    }

    private static class X509KeySelector extends KeySelector {

        @SuppressWarnings("rawtypes")
        @Override
        public KeySelectorResult select(KeyInfo keyInfo, Purpose purpose, AlgorithmMethod method, XMLCryptoContext context)
                throws KeySelectorException {

            // Go through keyInfo node to get X509Data
            Iterator ki = keyInfo.getContent().iterator();
            while (ki.hasNext()) {
                XMLStructure info = (XMLStructure) ki.next();
                if (!(info instanceof X509Data))
                    continue;
                X509Data x509Data = (X509Data) info;

                // Go through X509Data to get X509Certificate and PublicKey
                Iterator xi = x509Data.getContent().iterator();
                while (xi.hasNext()) {
                    Object o = xi.next();
                    if (!(o instanceof X509Certificate))
                        continue;
                    final PublicKey key = ((X509Certificate)o).getPublicKey();


                    // Make sure the algorithm is compatible
                    // with the method.
                    if (algEquals(method.getAlgorithm(), key.getAlgorithm())) {
                        return new KeySelectorResult() {

                            public Key getKey() {
                                return key;
                            }
                        };
                    }

                }

            }
            throw new KeySelectorException("No key found!");
        }

        private boolean algEquals(String algURI, String algName) {
            if ((algName.equalsIgnoreCase("DSA") &&
                    algURI.equalsIgnoreCase(SignatureMethod.DSA_SHA1)) ||
                    (algName.equalsIgnoreCase("RSA_ENCRYPTION") &&
                            algURI.equalsIgnoreCase(SignatureMethod.RSA_SHA1)) ||
                    (algName.equalsIgnoreCase("RSA_ENCRYPTION") &&
                            algURI.equalsIgnoreCase("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"))
            ) {
                return true;
            } else {
                return false;
            }
        }
    }

    private static DocumentBuilder getDocumentBuilder() throws IOException, SAXException, ParserConfigurationException {
        // Create the XML Object element and insert FACTCA XML file into it
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        return dbf.newDocumentBuilder();
    }

}

