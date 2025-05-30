package screens;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Game.GameSettings;
import Game.GameField.*;
import DataHandling.ConfigHandler;

import Game.*;

public class GameDisplay extends JPanel {
    private TetrisApp app;
    private GameSettings gameSettings;
    public int panelWidth;
    public int panelHeight;
    private GameField gameField;
    private JPanel gamePanel;
    private GameInfoDisplay gameInfoDisplay;
    //private GameInfoDisplay gameInfoDisplay2;

    private GameField gameField2;
    private GameInfoDisplay gameInfoDisplay2;


    public GameDisplay(TetrisApp app, GameSettings settings) {
        this.gameSettings = settings;

        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Play");
        titleLabel.setFont(new Font("Dialog", Font.PLAIN, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //Create Panels
        //JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        gamePanel = new JPanel();
        Box GameBox = Box.createVerticalBox();
        //code to create button
        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(100, 50));
        backButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        backButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(app.getFrame(),
                    "Are you sure you want to go back?",
                    "Leave Game?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                app.showScreen("Home"); // If user selects yes, they will be taken back to the home screen
            }
        });

        //Adding buttons to buttonPanel
        buttonPanel.add(backButton);

        //Set location of button panel
        add(titleLabel, BorderLayout.PAGE_START);
        add(gamePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
    }



    public void resetGame(){

        int fieldWidth  = gameSettings.getFieldWidth();
        int fieldHeight = gameSettings.getFieldHeight();
        int initalLevel = ConfigHandler.getInitLevel();
        gamePanel.removeAll();

        gameField = new GameField(fieldHeight, fieldWidth, this, true);
        gameInfoDisplay = new GameInfoDisplay(initalLevel, 0, initalLevel, 0);
        gameField.addObserver(gameInfoDisplay);


        if (ConfigHandler.getPlayer2Status()) {
            // Player 2
            gameField2 = new GameField(fieldHeight, fieldWidth, this, false);
            gameInfoDisplay2 = new GameInfoDisplay(1, 0, 1, 0);
            gameField2.addObserver(gameInfoDisplay2);
            gamePanel.add(gameField2);
            gamePanel.add(gameInfoDisplay2);

        }

        gameField.clearGrid();
        gameField.gameStart();
        gamePanel.add(gameField);
        gamePanel.add(gameInfoDisplay);

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    public void onShow(){
        resetGame();
    }
    }