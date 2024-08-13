package screens;
import javax.swing.*;
import java.awt.*;

public class GameDisplay extends JPanel{
    public GameDisplay(TetrisApp app){
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Play");
        titleLabel.setFont(new Font("Dialog",Font.PLAIN, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //Create Panels
        //JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        //code to create button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> app.showScreen("Home"));
        backButton.setBackground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(100, 50));
        backButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));



        //Adding buttons to buttonPanel
        buttonPanel.add(backButton);
        //Set location of button panel
        add(titleLabel, BorderLayout.PAGE_START);
        add(buttonPanel, BorderLayout.PAGE_END);
    }



}
