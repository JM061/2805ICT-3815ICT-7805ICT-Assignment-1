package screens;
import javax.swing.*;
import java.awt.*;
import static Components.ComponentFactory.*;

    public class HomeScreen extends JPanel {

        public HomeScreen(TetrisApp app) {

            setLayout(new BorderLayout());
            JLabel titleLabel = new JLabel("Tetris");
            titleLabel.setFont(new Font("Dialog", Font.PLAIN, 32));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            //Create Button Panel
            JPanel buttonPanel = new JPanel();
            Box buttonBox = Box.createVerticalBox();

            //Create Buttons
            //Create Start Button
            buttonBox.add(createNavigationbutton("Start", "GameDisplay", 250, 80, app));
            buttonBox.add(Box.createVerticalStrut(15));

            //Create Config Button
            buttonBox.add(createNavigationbutton("Configuration", "Config", 250, 80, app));
            buttonBox.add(Box.createVerticalStrut(15));


            //Create High Score Button
            buttonBox.add(createNavigationbutton("High Scores", "HighScores", 250, 80, app));
            buttonBox.add(Box.createVerticalStrut(15));

            //Create Exit Button

            buttonBox.add(createNavigationbutton("Exit", "Exit", 250, 80, app));


            //Add Buttons to ButtonPanel
            buttonPanel.add(buttonBox);

            add(titleLabel, BorderLayout.NORTH);
            add(buttonPanel, BorderLayout.SOUTH);
        }
    }

