import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class YelpAnalysis {

    //map where the keys are every word that appears in any review, and the value is how many documents they appear in
    //basically a dictionary of word frequencies
    private Map<String, Integer> dictionary = new HashMap<>();


    //testing
    public static void main(String[] args) {
        YelpAnalysis yp = new YelpAnalysis();
        yp.txtToString();
    }

    //given a .txt file, this constructs one String corresponding to each Business in the .txt file, and then
    //sends that String as a parameter for strToBusiness to construct a list of Businesses
    public void txtToString() {
        InputStream in = null;
        StringBuilder sb = new StringBuilder();
        try {
            //set the input stream to the file containing the dataset
            in = new FileInputStream("yelpDatasetParsed_short.txt");
            in = new BufferedInputStream(in);
            while (true) {
                //reads the next character in the file
                char result = (char) in.read();
                //since the format of the file is {, Business info, }, {, Business info, }, ...
                // we can build a string representing each business by looking inside the brackets
                if (result == '{') {
                    continue;
                }
                if (result == '}') {
                    //construct a business with sb
                    strToBusiness(sb.toString());
                    sb.setLength(0);
                    continue;
                } else {
                    sb.append(result);
                }
                // if there are no more characters in the file, then result will be -1, and we exit the loop
                if (result == -1) break;
            }
        } catch (IOException e) {
            System.out.println("couldn't find file");
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                System.out.println("could't close file");
            }
        }
    }


    public Business strToBusiness(String sb) {
        List<String> BusFieldStrings = Arrays.asList(sb.split(","));
        System.out.println(BusFieldStrings.size());
        Business b = null;
        //fields that we will fill
        String businessID = "";
        String businessName = "";
        String businessAddress = "";
        String reviews = "";
        int reviewCharCount = 0;
        for (int x = 0; x < BusFieldStrings.size(); x++) {
            if (x == 0) {
                //note: trim just removes whitespace (spaces) that comes before or after the string
                businessID = BusFieldStrings.get(x).trim();
            } else if (x == 1) {
                businessName = BusFieldStrings.get(x).trim();
            } else if (x == 2) {
                businessAddress = BusFieldStrings.get(x).trim();
            } else {
                reviews = BusFieldStrings.get(x).trim();
                reviewCharCount = charcount(reviews);
                b = new Business(businessID, businessName, businessAddress, reviews, reviewCharCount, null);
            }
        }
        System.out.println(b);
        return b;
    }

    //helper function that counts how many characters there are in a business' reviews
    public int charcount(String reviews) {
        return reviews.length();
    }



}









