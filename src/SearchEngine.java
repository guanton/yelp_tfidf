import com.google.common.collect.MinMaxPriorityQueue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchEngine {
    private JFrame f;
    private JButton searchButton;
    private JPanel panelMain;
    private JTextField textField;
    private JPanel pan;
    private YelpAnalysis yp;
    private JLabel[] outputs;
    private String query;
    private searchPanel sp;


    public SearchEngine() {
        yp = new YelpAnalysis();
        //search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                query = textField.getText();
                System.out.println(query);
                yp.init(false);
                yp.txtToString(query);
                sp = new searchPanel(yp.getBusinesses());
                displayResults();
            }
        });
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                query = textField.getText();
            }
        });
    }

    public void display(){
        f = new JFrame("Yelp Search");
        f.setContentPane(new SearchEngine().pan);
        f.setSize(800, 600);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    private class searchPanel extends javax.swing.JPanel {
        private searchPanel(MinMaxPriorityQueue businesses) {
            //initialize empty board with extra row for buttons
            this.setLayout(new GridLayout(10,1));

            //make row with only Play button (or no play button if there is no human)
            for (int i = 0; i < 10; i++) {
                Business b = yp.getBusinesses().removeFirst();
                this.add(new JLabel(b.businessName));
            }

        }


    }

    public void displayResults(){
        f = new JFrame("Results for " + query);
        f.setContentPane(sp);
        f.setSize(800, 600);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        SearchEngine s = new SearchEngine();
        s.display();
    }
}
