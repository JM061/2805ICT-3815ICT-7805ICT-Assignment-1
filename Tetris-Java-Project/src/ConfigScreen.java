import javax.swing.*;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ConfigScreen {
    public static void main(String[] args) {
        JFrame configScreenFrame = new JFrame("Software Engineering A1: Configuration");
        configScreenFrame.setSize(800, 600);
        configScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create Panels
        JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();


        JLabel titleLabel = new JLabel("Configuration");
        titleLabel.setFont(new Font("Dialog",Font.PLAIN, 32));


        //code to create buttons
        JButton saveButton = new JButton("Save");
        JButton backButton = new JButton("Back");




        //Adding buttons to buttonPanel
        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        //Adding title to titlePanel
        titlePanel.add(titleLabel);
        configScreenFrame.add(titlePanel, BorderLayout.CENTER);

        //Set location of button panel
        configScreenFrame.add(buttonPanel, BorderLayout.PAGE_END);
        configScreenFrame.setVisible(true);

    }
}