import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class YelpAnalysis {

    //map where the keys are every word that appears in any review, and the value is how many documents they appear in
    //i.e. basically a dictionary of word frequencies
    private Map<String, Integer> dictionary = new HashMap<>();
    SortByReviewCharCount srcc = new SortByReviewCharCount();
    //priority Queue representing every business in the .txt file
    private Queue<Business> businesses = new PriorityQueue<>(srcc.reversed());


    //testing
    public static void main(String[] args) {
        YelpAnalysis yp = new YelpAnalysis();
        yp.txtToString();
        System.out.println(yp.dictionary);
    }

    //given a .txt file, this constructs one String for each Business in the .txt file, and then
    //sends that String as a parameter for strToBusiness to construct a list of Businesses
    public void txtToString() {
        InputStream in = null;
        StringBuilder sb = new StringBuilder();
        try {
            //set the input stream to the file containing the dataset
            in = new FileInputStream("yelpDatasetParsed_short.txt");
            in = new BufferedInputStream(in);
            while (true) {
                int res = in.read();
                if (res == -1) {
                    break;
                }
                //reads the next character in the file
                char result = (char) res;
                // if there are no more characters in the file, then result will be -1, and we exit the loop
                //since the format of the file is {, Business info, }, {, Business info, }, ...
                // we can build a string representing each business by looking inside the brackets
                if (result == '{') {
                    continue;
                }
                if (result == '}') {
                    //construct a business with sb
                    businesses.add(strToBusiness(sb.toString()));
                    sb = new StringBuilder();
                    continue;
                } else {
                    //we are interested in what is between the brackets
                    sb.append(result);
                }

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
                this.dictionaryhelper(reviews);
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

    //updates the dictionary with a new batch of reviews
    public void dictionaryhelper(String reviews) {
        //split the reviews into a list of individual words
        List<String> wordsinreviews = Arrays.asList(reviews.split(" "));
        // this removes duplicate words in the list of words
        List<String> wordsinreviews_nodups = new ArrayList<>();
        for (String s : wordsinreviews) {
            if (!wordsinreviews_nodups.contains(s.toLowerCase())) {
                wordsinreviews_nodups.add(s.toLowerCase());
            }
        }
        //now we go through the list of unique words and adjust the dictionary correspondingly
        for (String s : wordsinreviews_nodups) {
            //if the dictionary doesn't already have the word, then add it to the dictionary with value 1
            if (!dictionary.containsKey(s.toLowerCase())) {
                dictionary.put(s.toLowerCase(), new Integer(1));
            } else {
                //otherwise, increment the value associated to the word by 1
                dictionary.put(s.toLowerCase(), new Integer(dictionary.get(s).intValue() + 1));
            }
        }
    }

    //extracts an ordered list of the top 10 businesses by review character count
    public List<Business> filtertop10(Queue<Business> businesses) {
        List<Business> top10 = new ArrayList<>();
        for (int x = 0; x < 10; x++) {
            top10.add(businesses.peek());
            businesses.remove();
        }
        return top10;
    }




}









