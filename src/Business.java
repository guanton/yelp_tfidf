import java.util.Map;

public class Business {
    //each business in the dataset has an ID, a name, an address, a list of reviews (String), a number of reviews,
    //and finally, an tf-idf score associated to each word that appears in its reviews
    //tf-idf is the frequency of a word in a business's reviews divided by the number of other businesses whose reviews
    //contain at least one mention of the word
    //we will use these tf-idf scores to generate accurate tags that reflect each business
    String businessID;
    String businessName;
    String businessAddress;
    String reviews;
    int reviewCharCount;
    Map<String, Double> tfidfmap;

    public Business(String businessID, String businessName, String businessAddress, String reviews, int reviewCharCount,
                    Map<String, Double> idfmap) {
        this.businessID = businessID;
        this.businessName = businessName;
        this.businessAddress = businessAddress;
        this.reviews = reviews;
        this.reviewCharCount = reviewCharCount;
        this.tfidfmap = idfmap;
    }

    //converts the map of words with their tfidf scores to a string
    //use of mod for even spacing
    public String maptostring(Map<String, Double> tfidfmap) {
        String s = "";
        int x = 1;
        for (Map.Entry<String,Double> entry : tfidfmap.entrySet()) {
            s = s + "(" + entry.getKey() + ", " + entry.getValue() + "), ";
            if (x % 6 == 0) {
                s=s+"\n";
            }
            x++;
        }
        return s;
    }

    public void settfidfmap(Map<String, Double> tfidfmap) {
        this.tfidfmap = tfidfmap;
    }

    public String toString() {
        return "-------------------------------------------------------------------------------\n"
                + "Business ID: " + businessID + "\n"
                + "Business Name: " + businessName + "\n"
                + "Business Address: " + businessAddress + "\n"
//                + "Reviews: " + reviews + "\n"
                + "Character Count: " + reviewCharCount + "\n"
                + "tf-idf scores: " + maptostring(tfidfmap);
    }



}
