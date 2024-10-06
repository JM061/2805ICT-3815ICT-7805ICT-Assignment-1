package screens;
import Components.ComponentFactory;
import Components.ConfigSlider;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import static Components.ComponentFactory.*;
import static Components.ConfigSlider.*;

import DataHandling.ConfigHandler;
import Game.GameSettings;

public class ConfigScreen extends JPanel {

    private JCheckBox musicCheckbox;
    private JCheckBox soundEffectsCheckbox;
    private JCheckBox AIPlayCheckbox;
    private JCheckBox extendedModeCheckbox;
    private JCheckBox player2Checkbox;

    private JRadioButton playerButton;
    private JRadioButton aiButton;
    private JRadioButton externalButton;

    private JRadioButton playerButton2;
    private JRadioButton aiButton2;
    private JRadioButton externalButton2;


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
        int fieldWidth = gameSettings.getFieldWidth();
        ConfigSlider widthSlider = createLabelWithSlider("Field Width: ", 5, 20, ConfigHandler.getFieldWidth());
        widthSlider.getSlider().addChangeListener(e -> {
            int newWidth = widthSlider.getSlider().getValue();
            ConfigHandler.setFieldWidth(newWidth); // Save the new width to the config file
        });

        int fieldHeight = gameSettings.getFieldHeight();
        ConfigSlider heightSlider = createLabelWithSlider("Field Height: ", 10, 30, ConfigHandler.getFieldHeight());
        heightSlider.getSlider().addChangeListener(e -> {
            int newHeight = heightSlider.getSlider().getValue();
            ConfigHandler.setFieldHeight(newHeight); // Save the new height to the config file
        });
        ConfigSlider gameLevelSlider = createLabelWithSlider("Game Level: ", 1, 10, ConfigHandler.getInitLevel());
        gameLevelSlider.getSlider().addChangeListener(e->{
            int newLevel = gameLevelSlider.getSlider().getValue();
            ConfigHandler.setGameLevel(newLevel);
        });

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
        player2Checkbox = new JCheckBox();


        musicCheckbox.setSelected(ConfigHandler.getMusicStatus());
        soundEffectsCheckbox.setSelected(ConfigHandler.getSoundEffectsStatus());
        //AIPlayCheckbox.setSelected(ConfigHandler.get);
        extendedModeCheckbox.setSelected(ConfigHandler.getExtendedMode());
        player2Checkbox.setSelected(ConfigHandler.getPlayer2Status());

        //add checkboxes with labels
        OptionsBox.add(createLabelWithCheckbox("Music (ON | OFF):", musicCheckbox));
        OptionsBox.add(Box.createRigidArea(new Dimension(0, 15))); // Adds 15px vertical gap
        OptionsBox.add(createLabelWithCheckbox("Sound Effects (ON | OFF):", soundEffectsCheckbox));
        OptionsBox.add(Box.createRigidArea(new Dimension(0, 15)));
        OptionsBox.add(createLabelWithCheckbox("AI Play (ON | OFF):", AIPlayCheckbox));
        OptionsBox.add(Box.createRigidArea(new Dimension(0, 15)));
        OptionsBox.add(createLabelWithCheckbox("Extend Mode (ON | OFF):", extendedModeCheckbox));
        OptionsBox.add(Box.createRigidArea(new Dimension(0, 15)));
        OptionsBox.add(createLabelWithCheckbox("Player 2 (ON | OFF):", player2Checkbox));
        player2Checkbox.setEnabled(false); //disables p2 button by default

        //add action listener to the event checkbox
        extendedModeCheckbox.addActionListener(e -> {checkExtendedModeSelected();
            //enables/disables player 2 button
            player2Checkbox.setEnabled(isExtendedModeEnabled());
            //enables buttons for player 1
            playerButton.setEnabled(isExtendedModeEnabled());
            aiButton.setEnabled(isExtendedModeEnabled());
            externalButton.setEnabled(isExtendedModeEnabled());

            //enables player 2 buttons
            playerButton2.setEnabled(isExtendedModeEnabled());
            aiButton2.setEnabled(isExtendedModeEnabled());
            externalButton2.setEnabled(isExtendedModeEnabled());
        });

        musicCheckbox.addActionListener(e-> checkMusicSelected());

        soundEffectsCheckbox.addActionListener(e->checkSoundEffectsSelected());

        AIPlayCheckbox.addActionListener(e->checkAIPlaySelected());

        player2Checkbox.addActionListener(e->checkPlayer2Selected());

        //add configOptions to display
        ConfigOptions.add(OptionsBox);
        add(ConfigOptions, BorderLayout.CENTER);

        //options for player 1
        JLabel player1Label = ComponentFactory.createLabel("Player 1: ");
         playerButton = new JRadioButton("Player");
         aiButton = new JRadioButton("AI");
         externalButton = new JRadioButton("External");

        ButtonGroup p1ButtonGroup = new ButtonGroup();
        p1ButtonGroup.add(playerButton);
        p1ButtonGroup.add(aiButton);
        p1ButtonGroup.add(externalButton);

        //set radio buttons to disabled by default
        playerButton.setEnabled(false);
        aiButton.setEnabled(false);
        externalButton.setEnabled(false);

        //options for player 2
        JLabel player2Label = ComponentFactory.createLabel("Player 2: ");
         playerButton2 = new JRadioButton("Player");
         aiButton2 = new JRadioButton("AI");
         externalButton2 = new JRadioButton("External");

        playerButton2.setEnabled(false);
        aiButton2.setEnabled(false);
        externalButton2.setEnabled(false);

        ButtonGroup p2ButtonGroup = new ButtonGroup();
        p2ButtonGroup.add(playerButton2);
        p2ButtonGroup.add(aiButton2);
        p2ButtonGroup.add(externalButton2);

        //adds radio buttons to options box
        OptionsBox.add(player1Label);
        OptionsBox.add(playerButton);
        OptionsBox.add(aiButton);
        OptionsBox.add(externalButton);

        OptionsBox.add(player2Label);
        OptionsBox.add(playerButton2);
        OptionsBox.add(aiButton2);
        OptionsBox.add(externalButton2);
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

    public boolean isPlayer2Selected(){
        return player2Checkbox.isSelected();
    }

    //check if AI Play is enabled
    public boolean isAIPlayEnabled() {
        return AIPlayCheckbox.isSelected();
    }

    // handle extended mode logic
    public void checkExtendedModeSelected() {
        if (isExtendedModeEnabled()) {
            System.out.println("Extended Mode is enabled");
            ConfigHandler.setExtendedMode(isExtendedModeEnabled());

            // Perform actions related to Extended Mode
        } else {
            System.out.println("Extended Mode is disabled");
            ConfigHandler.setExtendedMode(isExtendedModeEnabled());
            // Handle Extended Mode being disabled
        }
    }

    public void checkMusicSelected() {
        if (isMusicOn()) {
            System.out.println("Music is on");
            ConfigHandler.setMusic(isMusicOn());
        } else{
            System.out.println("Music is off");
            ConfigHandler.setMusic(isMusicOn());

        }
    }

    public void checkSoundEffectsSelected(){
        if (isSoundEffectsOn()) {
            System.out.println("Sound Effects is on");
            ConfigHandler.setSoundEffects(isSoundEffectsOn());
        } else {
            System.out.println("Sound Effects is disabled");
            ConfigHandler.setSoundEffects(isSoundEffectsOn());

        }

    }
    public void checkAIPlaySelected(){
        if (isAIPlayEnabled()) {
            System.out.println("AI Play is on");
        } else {
            System.out.println("AI Play is off");
        }
    }


    public void checkPlayer2Selected(){
        if (isPlayer2Selected()) {
            System.out.println("Sound Effects is on");
            ConfigHandler.setPlayer2(isPlayer2Selected());
        } else {
            System.out.println("Sound Effects is disabled");
            ConfigHandler.setPlayer2(isPlayer2Selected());

        }

    }

}

