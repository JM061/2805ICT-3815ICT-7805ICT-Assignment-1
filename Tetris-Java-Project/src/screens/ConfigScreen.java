package screens;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;

import static Components.ComponentFactory.*;
public class ConfigScreen extends JPanel{
    public ConfigScreen(TetrisApp app){
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Configuration");
        titleLabel.setFont(new Font("Dialog",Font.PLAIN, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            //Create Panels
            //JPanel titlePanel = new JPanel();
            JPanel buttonPanel = new JPanel();


            titleLabel.setFont(new Font("Dialog", Font.PLAIN, 32));


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

            //Add Config Settings to Page
            JPanel ConfigOptions = new JPanel();
            Box OptionsBox = Box.createVerticalBox();

            ConfigOptions.setBackground(Color.WHITE);
            //Create Options for config page.
            //Create labels with slider inputs
            OptionsBox.add(createLabelWithSlider("Field Width: ", 0, 10, 5));
            OptionsBox.add(createLabelWithSlider("Field Height: ", 0, 10, 5));
            OptionsBox.add(createLabelWithSlider("Game Level: ", 0, 10, 5));
            //Create labels with checkboxes
            OptionsBox.add(createLabelWithCheckbox("Music (ON | OFF):" ));
            OptionsBox.add(createLabelWithCheckbox("Sound Effects (ON | OFF):" ));
            OptionsBox.add(createLabelWithCheckbox("AI Play (ON | OFF):" ));
            OptionsBox.add(createLabelWithCheckbox("Extend Mode (ON | OFF):" ));
            ConfigOptions.add(OptionsBox);




            add(ConfigOptions, BorderLayout.CENTER);

    }

    // Define a font you want to use
    Font labelFont = new Font("Arial", Font.PLAIN, 14);
}

//need to add
//Field Width - slider
// Field Height - slider
//Game Level - slider
//Music on/off - checkbox
//Sound Effects on/off - checkbox
//Ai Play on/off - check box
//Extend Mode on/off - checkbox