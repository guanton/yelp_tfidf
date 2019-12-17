import org.apache.wink.json4j.JSON;
import org.apache.wink.json4j.JSONObject;
import org.apache.wink.json4j.OrderedJSONObject;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;

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
            JsonObject business = new JsonObject();
            //convert a business' tf-idf map to JSON
            JsonObject tfidf = new JsonObject();
            tfidf.putAll(b.tfidfmap);
            business.put("tfidf", tfidf);
            //convert name and address to JSON
            JSONObject bn = new JSONObject();
            business.put("business_name", b.businessName);
            JSONObject ba = new JSONObject();
            business.put("business_address", b.businessAddress);
            //preface each Business Object with its business ID
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
