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
    //in particular, it prints out a list of the keywords associated to eachbusiness

    public static void main(String[] args) {
        YelpAnalysis yp = new YelpAnalysis();
        yp.txtToString();
        for (Business b: yp.filtertop10()) {
            b.settfidfmap(yp.top30words(b));
            System.out.println(b.toString());
        }
    }



    //this method constructs one String for each Business in the .txt file (dataset), and then
    //sends each String as a parameter to the method strToBusiness to construct a list of Businesses

    public void txtToString() {
        InputStream in = null;
        StringBuilder sb = new StringBuilder();
        try {
            //set the input stream to the file containing the dataset
            in = new FileInputStream("yelpDatasetParsed_full.txt");
            in = new BufferedInputStream(in);
            while (true) {
                int res = in.read();
                if (res == -1) {
                    break;
                }
                char result = (char) res;  //reads the next character in the file


                // if there are no more characters in the file, then result will be -1, and we exit the loop
                //since the format of the text in the file is {, Business info, }, {, Business info, }, ...
                // we can build a string representing each business by looking inside the brackets


                if (result == '{') {
                    continue;
                }
                if (result == '}') {
                    businesses.add(strToBusiness(sb.toString())); //construct a business with sb
                    sb = new StringBuilder();
                    continue;
                } else {
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

    //this is a helper method that converts a String that represents all of a Business' data into a Business object
    public Business strToBusiness(String sb) {
        List<String> BusFieldStrings = Arrays.asList(sb.split(","));
        Business b = null;
        //fields that we will fill:
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
                reviewCharCount = charCount(reviews);
                this.dictionaryHelper(reviews);
                b = new Business(businessID, businessName, businessAddress, reviews, reviewCharCount, null);
//                System.out.println(b);
            }
        }
        return b;
    }

    //helper function that counts how many characters there are in a business' reviews
    public int charCount(String reviews) {
        return reviews.length();
    }

    //updates the dictionary with a new batch of reviews
    public void dictionaryHelper(String reviews) {
        //split the reviews into a list of individual words
        List<String> wordsInReviews = Arrays.asList(reviews.split(" "));
        // this removes duplicate words in the batch of reviews (we don't want to double count words from one batch
        // of reviews)
        List<String> wordsInReviews_noDups = new ArrayList<>();
        for (String s : wordsInReviews) {
            if (!wordsInReviews_noDups.contains(s.toLowerCase())) {
                wordsInReviews_noDups.add(s.toLowerCase());
            }
        }
        //now we go through the list of unique words and adjust the dictionary accordingly
        for (String s : wordsInReviews_noDups) {
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
            top10.add(businesses.peek()); //recall that businesses is sorted by character count already
            businesses.remove();
        }
        return top10;
    }

    // this method creates a map where the keys are words in a business' reviews and the values are their tf-idf scores
    public Map<String, Double> top30words(Business b) {
        //store all the words in the reviews in a list
        List<String> wordsInReviews = Arrays.asList(b.reviews.split(" "));
        //first, create a map that correlates each word to the number of times it appears in a business' reviews
        Map<String, Integer> wordFrequencies = new HashMap<>();
        for (String s: wordsInReviews) {
            if (!wordFrequencies.containsKey(s)) {
                wordFrequencies.put(s, new Integer(1));
            } else {
                wordFrequencies.put(s, new Integer(wordFrequencies.get(s).intValue()+1));
            }
        }
        //now, we compute the tf-idf scores
        Map<Double, String> map_tfidf = new HashMap<>();
        for (String s : wordFrequencies.keySet()) {
            Double tfidf;
            //only assign a non-0 idf score if the word appears in at least 5 documents in the .txt file
            if (dictionary.get(s).intValue()>=5) {
                tfidf = new Double(wordFrequencies.get(s).doubleValue() / dictionary.get(s).doubleValue());
            } else {
                tfidf = new Double(0);
            }
            map_tfidf.put(tfidf, s);
        }
        //now, store the idf's into a list so that we can sort them in decreasing order
        List<Double> listtfidf = new ArrayList<>(map_tfidf.keySet());
        Collections.sort(listtfidf, Collections.reverseOrder());
        //prepare the map that we will return
        Map<String, Double> finalmap = new LinkedHashMap<>();
        //we use the words that correspond to the first 30 entries of listtfidf
        for (int x=0; x<30; x++) {
            finalmap.put(map_tfidf.get(listtfidf.get(x)), new Double(Math.round(100*listtfidf.get(x).floatValue())/100.00));
        }
        return finalmap;
    }




}









