import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.google.common.collect.MinMaxPriorityQueue;


import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class ParseJSONFile {

    Set<Business> businessSet = new HashSet<>();
    SortByTfidf stfidf = new SortByTfidf();
    MinMaxPriorityQueue<Business> businesses = MinMaxPriorityQueue.orderedBy(stfidf.reversed()).maximumSize(10).create();

    public static void main(String[] args) {
        ParseJSONFile pjf = new ParseJSONFile();
        pjf.parseJson();
        pjf.search("pizza");
        for (Business b: pjf.businesses) {
            System.out.println(b);
        }
    }


    //searches json file for businesses that contain the relevant keywords
    public void parseJson(){
        try {
            //create parser
            JsonFactory jf = new JsonFactory();
            JsonParser jParser= jf.createParser(new File("myJSON.json"));
            ObjectMapper objMap = new ObjectMapper(jf);
            jParser.setCodec(objMap);
            //skip preliminary brackets
            jParser.nextToken();
            jParser.nextToken();
            boolean businessdone = false;
            Business b = new Business();
            while (jParser.nextToken() != JsonToken.END_OBJECT) {
                String fieldname = jParser.getCurrentName();
                if (fieldname.equals("tfidf")) {
                    b = new Business();
                    b.settfidfmap(parseTfidf(jParser));
                    businessdone = false;
                } else if (fieldname.equals("business_name")) {
                    ObjectCodec codec = jParser.getCodec();
                    JsonNode node = codec.readTree(jParser);
                    b.businessName = node.textValue();
                } else if (fieldname.equals("business_address")) {
                    ObjectCodec codec = jParser.getCodec();
                    JsonNode node = codec.readTree(jParser);
                    b.businessAddress = node.textValue();
                    businessdone = true;
                }
                jParser.nextToken();
                if (businessdone) {
                    businessSet.add(b);
                    jParser.nextToken();
                }
            }
        } catch (IOException e) {
            System.out.println("couldn't find file");
        } catch (NullPointerException e) {
            //parsing is done, will look into better fix later
        }
    }


    public void search(String query) {
        for (Business b: businessSet) {
            b.assignTfidf(query);
            businesses.offer(b);
        }
    }


    //at start, the token is "["
    //at end the token is "]"
    public TreeMap<String, Double> parseTfidf(JsonParser jParser) throws IOException{
        TreeMap<String, Double> tfidfmap = new TreeMap<>();
        jParser.nextToken();
        jParser.nextToken();
        boolean endArray = false;
        while (!endArray) {
            jParser.nextToken();
            String keyWord = jParser.getText();
            ObjectCodec codec = jParser.getCodec();
            JsonNode node = codec.readTree(jParser);
            DoubleNode tfidf1 = (DoubleNode) node.get(keyWord);
            String tfidf2 = tfidf1.toString();
            Double tfidf = Double.valueOf(tfidf2);
            tfidfmap.put(keyWord, tfidf);
//            System.out.println(keyWord + ": " + tfidf) ;
            jParser.nextToken();
            //check if reached end of array
            if (jParser.getText().equals("]")) {
                endArray = true;
            }

        }
        return tfidfmap;
    }

    public MinMaxPriorityQueue<Business> getBusinesses() {
        return businesses;
    }




}


