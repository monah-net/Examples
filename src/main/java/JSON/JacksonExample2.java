package JSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.util.List;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class JacksonExample2 {
    public class Staff {

        private String name;
        private int age;
        private String[] position;              //  Array
        private List<String> skills;            //  List
        private Map<String, BigDecimal> salary; //  Map

        // getters , setters, some boring stuff
    }

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();

        try {

            // JSON file to Java object
            Staff staff = mapper.readValue(new File("c:\\test\\staff.json"), Staff.class);

            // JSON string to Java object
            String jsonInString = "{\"name\":\"mkyong\",\"age\":37,\"skills\":[\"java\",\"python\"]}";
            Staff staff2 = mapper.readValue(jsonInString, Staff.class);

            // compact print
            System.out.println(staff2);

            // pretty print
            String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(staff2);

            System.out.println(prettyStaff1);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}