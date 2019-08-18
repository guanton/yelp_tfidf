import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class YelpAnalysis {


    //a dictionary containing every word that makes an appearance in the reviews contained in the dataset along with
    //the number of restaurants in the dataset whose reviews contain the word
    private Map<String, Integer> dictionary = new HashMap<>();
    //we will sort our businesses in order of decreasing number of characters in their reviews
    SortByReviewCharCount srcc = new SortByReviewCharCount();
    //a priority Queue representing every business in the .txt file
    private Queue<Business> businesses = new PriorityQueue<>(srcc.reversed());


    //testing: running this systematically prints the data from the top 10 businesses (most characters in reviews)
    public static void main(String[] args) {
        YelpAnalysis yp = new YelpAnalysis();
        yp.txtToString();
        System.out.println(yp.dictionary);
        for (Business b: yp.filtertop10()) {
            b.settfidfmap(yp.top30words(b));
            System.out.println(b.toString());
        }
    }


    //this method ta constructs one String for each Business in the .txt file, and then
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
//                System.out.println(b);
            }
        }
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
    public List<Business> filtertop10() {
        List<Business> top10 = new ArrayList<>();
        for (int x = 0; x < 10; x++) {
            top10.add(businesses.peek());
            businesses.remove();
        }
        return top10;
    }

    //creates a map where the keys are words in a business' reviews and the values are their tf-idf scores
    public Map<String, Double> top30words(Business b) {
        //store all the words in the reviews in a list
        List<String> wordsinreviews = Arrays.asList(b.reviews.split(" "));
        //first, create a map where each word that appeared in the reviews is related to its frequency within reviews
        Map<String, Integer> wordfrequencies = new HashMap<>();
        for (String s: wordsinreviews) {
            if (!wordfrequencies.containsKey(s)) {
                wordfrequencies.put(s, new Integer(1));
            } else {
                wordfrequencies.put(s, new Integer(wordfrequencies.get(s).intValue()+1));
            }
        }
        //now, we compute the idf scores
        Map<Double, String> maptfidf = new HashMap<>();
        for (String s : wordfrequencies.keySet()) {
            Double tfidf;
            //only assign a non-0 idf score if the word appears in at least 5 documents in the .txt file
            if (dictionary.get(s).intValue()>=5) {
                tfidf = new Double(wordfrequencies.get(s).doubleValue() / dictionary.get(s).doubleValue());
            } else {
                tfidf = new Double(0);
            }
            maptfidf.put(tfidf, s);
        }
        //now, store the idf's into a list so that we can sort them in decreasing order
        List<Double> listtfidf = new ArrayList<>(maptfidf.keySet());
        //sort the list from greatest idf to smallest idf
        Collections.sort(listtfidf, Collections.reverseOrder());
        //prepare the map that we will return
        Map<String, Double> finalmap = new LinkedHashMap<>();
        //we use the words that correspond to the first 30 entries of listtfidf
        for (int x=0; x<30; x++) {
            finalmap.put(maptfidf.get(listtfidf.get(x)), new Double(Math.round(100*listtfidf.get(x).floatValue())/100.00));
        }
        return finalmap;
    }




}









