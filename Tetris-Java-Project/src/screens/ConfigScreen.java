package screens;
import javax.swing.*;
import java.awt.*;

public class ConfigScreen extends JPanel{
    public ConfigScreen(Main app){
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Configuration");
        titleLabel.setFont(new Font("Dialog",Font.PLAIN, 32));

            //Create Panels
            //JPanel titlePanel = new JPanel();
            JPanel buttonPanel = new JPanel();


            titleLabel.setFont(new Font("Dialog", Font.PLAIN, 32));


            //code to create buttons
            JButton saveButton = new JButton("Save");
            JButton backButton = new JButton("Back");


            //Adding buttons to buttonPanel
            buttonPanel.add(saveButton);
            buttonPanel.add(backButton);



            //Set location of button panel
            add(titleLabel, BorderLayout.NORTH);
            add(buttonPanel, BorderLayout.PAGE_END);
    }
}
