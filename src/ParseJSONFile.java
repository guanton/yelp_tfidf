import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.collect.MinMaxPriorityQueue;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ParseJSONFile {

    SortByTfidf stfidf = new SortByTfidf();
    MinMaxPriorityQueue<Business> businesses = MinMaxPriorityQueue.orderedBy(stfidf.reversed()).maximumSize(10).create();

    public static void main(String[] args) {
        ParseJSONFile pjf = new ParseJSONFile();
        pjf.parseJson("");
    }


    //searches json file for businesses that contain the relevant keywords
    public void parseJson(String query){
        try {
            //create parser
            JsonFactory jf = new JsonFactory();
            JsonParser jParser= jf.createParser(new File("myJSON.json"));
            ObjectMapper objMap = new ObjectMapper(jf);
            jParser.setCodec(objMap);
            //skip preliminary brackets
            jParser.nextToken();
            jParser.nextToken();
            jParser.nextToken();
            while (jParser.nextToken() != JsonToken.END_OBJECT) {
                String fieldname = jParser.getCurrentName();
                //"^#" indicates that we have reached a new Business
//                System.out.println(fieldname);
                Map<String, Double> parsedTfidf = parseTfidf(jParser);


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

    public Map<String, Double> parseTfidf(JsonParser jParser) throws IOException{
//        CsvMapper csvMap = new CsvMapper();
//        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        jParser.nextToken();
        String keyWord = jParser.getText();
        ObjectCodec codec = jParser.getCodec();
        JsonNode node = codec.readTree(jParser);
        Object tfidf = node.get(keyWord);
        System.out.println(keyWord + ": " + tfidf) ;
        jParser.nextToken();
        String keyWord2 = jParser.getText();
        System.out.println(keyWord2);

        return null;
    }




}


