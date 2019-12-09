import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchEngine {
    private JButton searchButton;
    private JPanel panelMain;
    private YelpAnalysis yp;


    public SearchEngine() {

        //search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void display(){
        JFrame f = new JFrame("Yelp Search");
        f.setContentPane(new SearchEngine().panelMain);
        f.setSize(800, 600);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        SearchEngine s = new SearchEngine();
        s.display();
    }
}
