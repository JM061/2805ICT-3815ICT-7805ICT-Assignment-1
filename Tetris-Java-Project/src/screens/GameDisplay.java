package screens;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Game.GameSettings;


import Game.*;

public class GameDisplay extends JPanel {
    private TetrisApp app;
    private GameSettings gameSettings
            ;
    public int panelWidth;
    public int panelHeight;


    public GameDisplay(TetrisApp app, GameSettings settings) {
        this.gameSettings = settings;
       int fieldWidth  = gameSettings.getFieldWidth();
       int fieldHeight = gameSettings.getFieldHeight();

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
        backButton.addActionListener(new ActionListener() {C
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(app.getFrame(),
                        "Are you sure you want to go back?",
                        "Leave Game?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    app.showScreen("Home"); //If user selects yes, they will be taken back to the home screen
                }
            }
        });


        GameField gameField = new GameField(fieldHeight, fieldWidth);
        //GameField gameField = new GameField(fieldWidth, fieldHeight); // 20 rows and 10 columns
        //GameField gameField2 = new GameField(20, 10); // generate second play screen

        GamePanel.add(gameField);
        //GamePanel.add(gameField2);

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