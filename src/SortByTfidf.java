import java.util.Comparator;

public class SortByTfidf implements Comparator<Business>{
    public int compare(Business a, Business b) {
        return (int) a.tfidf - (int) b.tfidf;
    }
}
