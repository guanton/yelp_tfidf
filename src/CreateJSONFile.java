import org.apache.wink.json4j.OrderedJSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class CreateJSONFile {
    public static void main(String[] args) throws Exception  {
        YelpAnalysis yp = new YelpAnalysis();
        yp.init(false);
        String query = "pizza hut";
        yp.txtToString(query);
        OrderedJSONObject saves = new OrderedJSONObject();
        yp.secondPass(query);
        int x = 1;
        for (Business b : yp.businessSet) {
            OrderedJSONObject business = new OrderedJSONObject();
            business.put("business_name", b.businessName);
            business.put("business_address", b.businessAddress);
            for (String s: b.tfidfmap.keySet()) {
                business.put(s, b.tfidfmap.get(s));
            }
            saves.put(Integer.toString(x), business);
            x++;
        }
        try(FileWriter file = new FileWriter("myJSON.json")) {
            file.write(saves.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();

        }


    }
}
