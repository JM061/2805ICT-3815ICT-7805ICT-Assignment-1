package screens;
import Components.ConfigSlider;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import static Components.ComponentFactory.*;
import static Components.ConfigSlider.*;
import Game.GameSettings;

public class ConfigScreen extends JPanel {

    private JCheckBox musicCheckbox;
    private JCheckBox soundEffectsCheckbox;
    private JCheckBox AIPlayCheckbox;
    private JCheckBox extendedModeCheckbox;

    public ConfigScreen(TetrisApp app, GameSettings gameSettings) {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Configuration");
        titleLabel.setFont(new Font("Dialog", Font.PLAIN, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //create Panels
        JPanel buttonPanel = new JPanel();

        //create back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> app.showScreen("Home"));
        backButton.setBackground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(100, 50));
        backButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        //adding buttons to buttonPanel
        buttonPanel.add(backButton);

        //set location of button panel
        add(titleLabel, BorderLayout.PAGE_START);
        add(buttonPanel, BorderLayout.PAGE_END);

        //add Config Settings to Page
        JPanel ConfigOptions = new JPanel();
        Box OptionsBox = Box.createVerticalBox();

        // Create labels with slider inputs
        ConfigSlider widthSlider = createLabelWithSlider("Field Width: ", 0, 20, 5);
        ConfigSlider heightSlider = createLabelWithSlider("Field Height: ", 0, 20, 5);
        ConfigSlider gameLevelSlider = createLabelWithSlider("Game Level: ", 0, 20, 5);

        // Add sliders to OptionsBox with vertical gaps
        OptionsBox.add(Box.createRigidArea(new Dimension(0, 15))); // Adds 15px vertical gap
        OptionsBox.add(widthSlider.panel);
        OptionsBox.add(Box.createRigidArea(new Dimension(0, 15)));
        OptionsBox.add(heightSlider.panel);
        OptionsBox.add(Box.createRigidArea(new Dimension(0, 15)));
        OptionsBox.add(gameLevelSlider.panel);
        OptionsBox.add(Box.createRigidArea(new Dimension(0, 30)));

        // Create checkboxes
        musicCheckbox = new JCheckBox();
        soundEffectsCheckbox = new JCheckBox();
        AIPlayCheckbox = new JCheckBox();
        extendedModeCheckbox = new JCheckBox();

        //add checkboxes with labels
        OptionsBox.add(createLabelWithCheckbox("Music (ON | OFF):", musicCheckbox));
        OptionsBox.add(Box.createRigidArea(new Dimension(0, 15))); // Adds 15px vertical gap
        OptionsBox.add(createLabelWithCheckbox("Sound Effects (ON | OFF):", soundEffectsCheckbox));
        OptionsBox.add(Box.createRigidArea(new Dimension(0, 15)));
        OptionsBox.add(createLabelWithCheckbox("AI Play (ON | OFF):", AIPlayCheckbox));
        OptionsBox.add(Box.createRigidArea(new Dimension(0, 15)));
        OptionsBox.add(createLabelWithCheckbox("Extend Mode (ON | OFF):", extendedModeCheckbox));

        //add action listener to the event checkbox
        extendedModeCheckbox.addActionListener(e -> checkExtendedModeSelected());


        //add configOptions to display
        ConfigOptions.add(OptionsBox);
        add(ConfigOptions, BorderLayout.CENTER);
    }

    // Method to check if extended mode is selected
    public boolean isExtendedModeEnabled() {
        return extendedModeCheckbox.isSelected();
    }

    // Check if music is selected
    public boolean isMusicOn() {
        return musicCheckbox.isSelected();
    }

    //check if sound effect is selected
    public boolean isSoundEffectsOn() {
        return soundEffectsCheckbox.isSelected();
    }

    //check if AI Play is enabled
    public boolean isAIPlayEnabled() {
        return AIPlayCheckbox.isSelected();
    }

    // handle extended mode logic
    public void checkExtendedModeSelected() {
        if (isExtendedModeEnabled()) {
            System.out.println("Extended Mode is enabled");
            // Perform actions related to Extended Mode
        } else {
            System.out.println("Extended Mode is disabled");
            // Handle Extended Mode being disabled
        }
    }
}

