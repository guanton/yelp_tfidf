import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchEngine {
    private JButton searchButton;
    private JPanel panelMain;
    private JTextField textField;
    private JPanel pan;
    private YelpAnalysis yp;


    public SearchEngine() {
        yp = new YelpAnalysis();
        //search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = textField.getText();
                yp.txtToString(textField.getText());
                for (Business b: yp.filtertop10()) {
                    System.out.println(b);
                }
            }
        });
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = textField.getText();
            }
        });
    }

    public void display(){
        JFrame f = new JFrame("Yelp Search");
        f.setContentPane(new SearchEngine().pan);
        f.setSize(800, 600);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        SearchEngine s = new SearchEngine();
        s.display();
    }
}
