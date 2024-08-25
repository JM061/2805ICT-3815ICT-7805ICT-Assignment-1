package screens;
import javax.swing.*;
import java.awt.*;
import Game.*;

public class GameDisplay extends JPanel {
    private TetrisApp app;
    public int panelWidth;
    public int panelHeight;


    public GameDisplay(TetrisApp app) {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Play");
        titleLabel.setFont(new Font("Dialog", Font.PLAIN, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //Create Panels
        //JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel GamePanel = new JPanel();
        Box GameBox = Box.createVerticalBox();
        //code to create button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> app.showScreen("Home"));
        backButton.setBackground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(100, 50));
        backButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));



        GameField gameField = new GameField(20, 10); // Example: 20 rows and 10 columns
        GamePanel.add(gameField);

        // GameBox.add(gameField);
        //GameBox.setPreferredSize(new Dimension(200, 400));
        //GamePanel.setBackground(Color.WHITE);




        //Adding buttons to buttonPanel
        buttonPanel.add(backButton);

        //Set location of button panel

        add(titleLabel, BorderLayout.PAGE_START);
        add(GamePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
    }
}