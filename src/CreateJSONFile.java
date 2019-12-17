import org.apache.wink.json4j.OrderedJSONObject;
import org.json.simple.JsonArray;


import java.io.FileWriter;
import java.io.IOException;

public class CreateJSONFile {
    public static void main(String[] args) throws Exception  {
        YelpAnalysis yp = new YelpAnalysis();
        yp.init(false);
        String query = "";
        yp.txtToString(query);
        JsonArray saves = new JsonArray();
        yp.secondPass(query);
        for (Business b : yp.businessSet) {
            OrderedJSONObject business = new OrderedJSONObject();
            //convert a business' tf-idf map to JSON
            JsonArray tfidf = new JsonArray();
            for (String s: b.tfidfmap.keySet()) {
                OrderedJSONObject bt = new OrderedJSONObject();
                bt.put(s, b.tfidfmap.get(s));
                tfidf.add(bt);
            }
            business.put("tfidf", tfidf);
            //convert name and address to JSON
            business.put("business_name", b.businessName);
            business.put("business_address", b.businessAddress);
            saves.add(business);
        }
        try(FileWriter file = new FileWriter("myJSON.json")) {
            file.write(saves.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
