import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.File;
import java.io.IOException;

public class ParseJSONFile {

    public static void main(String[] args) {
        try {
            //create parser
            JsonParser jParser= new JsonFactory().createParser(new File("myJSON.json"));
            while (jParser.nextToken() != JsonToken.END_OBJECT) {
                String fieldname = jParser.getCurrentName();
                //new Business
//                if (Integer.parseInt(fieldname)*0 == 0) {
//
//                    jParser.nextToken();
//                    parsedName = jParser.getText();
//                }
//
//                if ("age".equals(fieldname)) {
//                    jParser.nextToken();
//                    parsedAge = jParser.getIntValue();
//                }
//
//                if ("address".equals(fieldname)) {
//                    jParser.nextToken();
//                    while (jParser.nextToken() != JsonToken.END_ARRAY) {
//                        addresses.add(jParser.getText());
//                    }
//                }
            }

        } catch (IOException e) {

            System.out.println("couldn't find file");
        }






    }




}


