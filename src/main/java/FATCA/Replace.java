package FATCA;

public class Replace {
    public static void main(String[] args) {
        String before = "<CONDITION><CONDITION type=\"SINGULAR_CONCRETE\"><COLUMN><ALIAS>root</ALIAS><COLNAME>birth_country_code</COLNAME></COLUMN><OPERATION>!=</OPERATION><VALUE/></CONDITION><COPULA>OR</COPULA><CONDITION type=\"EXPRESSION_SINGULAR\"><COLUMN><ALIAS>root</ALIAS><COLNAME>birth_country_code</COLNAME></COLUMN><OPERATION>IS NOT</OPERATION><VALUE>\n" +
                "NULL</VALUE></CONDITION></CONDITION>";
        String after = before.replaceFirst("<CONDITION><CONDITION type=\"SINGULAR_CONCRETE\"><COLUMN><ALIAS>","").replaceAll("</ALIAS><COLNAME>",".").replaceAll("</COLNAME></COLUMN><OPERATION>"," ").replaceAll("</OPERATION><VALUE/></CONDITION><COPULA>"," '' ").replaceAll("</COPULA><CONDITION type=\"EXPRESSION_SINGULAR\"><COLUMN><ALIAS>"," ").replaceAll("</OPERATION><VALUE>"," ").replaceAll("</VALUE></CONDITION></CONDITION>"," ");
        System.out.println(after);
        String tin = "<IdentitasUnik>20181234567891230000000002</IdentitasUnik>";
        String updatedTin = tin.replaceAll("<IdentitasUnik>.*</IdentitasUnik>","<IdentitasUnik>123</IdentitasUnik>");
        System.out.println(updatedTin);
    }
}
