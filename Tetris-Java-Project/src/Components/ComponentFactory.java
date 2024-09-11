package Components;
import screens.TetrisApp;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ComponentFactory {

    public static JLabel createLabel(String text) {
    JLabel label = new JLabel(text);
    label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    return label;
}
    //used to create the sliders with labels on config page
    //needs text for label, min / max values for slider and initial value
    public static ConfigSlider createLabelWithSlider(String text, int min, int max, int initial) {
        JPanel panel = new JPanel(new BorderLayout(50, 100));
        JLabel label = new JLabel(text);
        JSlider slider = new JSlider(min, max, initial);
        JLabel valueLabel = new JLabel(String.valueOf(initial)); // Label to display the slider's value
        slider.setMajorTickSpacing((max - min) / 5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        // Update the valueLabel when the slider'`s value changes
        slider.addChangeListener(e -> valueLabel.setText(String.valueOf(slider.getValue())));

        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.add(slider, BorderLayout.CENTER);
        sliderPanel.add(valueLabel, BorderLayout.EAST); // Add the value label to the right of the slider

        panel.add(label, BorderLayout.WEST);
        panel.add(sliderPanel, BorderLayout.CENTER);

        return new ConfigSlider(panel, slider); // Return both the panel and the slider
    }







    //creates checkbox with label for config page
    //requires text for the title of label
    public static JPanel createLabelWithCheckbox(String text, JCheckBox checkbox) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text);
        panel.add(label, BorderLayout.WEST);
        panel.add(checkbox, BorderLayout.EAST);
        return panel;
    }


    //used to create navigation buttons
    //requires buttonTitle navLocation(where the button will navigate to), width/height for size of button
    public static JButton createNavigationbutton(String buttonTitle, String navLocation, int width, int height, TetrisApp app){
            JButton navButton = new JButton(buttonTitle);

            Dimension navButtonSize = new Dimension(width, height);
            navButton.setPreferredSize(navButtonSize);
            navButton.setMaximumSize(navButtonSize);
            navButton.setMinimumSize(navButtonSize);
            navButton.addActionListener(e -> app.showScreen(navLocation));
            navButton.setBackground(Color.WHITE);
            return navButton;
    }

    //used to create the labels for the high score screen
    public static JPanel createUserScoreLabel(String Username, int Score){
        JPanel usersScorePanel = new JPanel(new BorderLayout(60, 20));
        JLabel userLabel = createLabel(Username);
        JLabel scoreLabel = createLabel(String.valueOf(Score));
        usersScorePanel.add(userLabel, BorderLayout.WEST);
        usersScorePanel.add(scoreLabel, BorderLayout.EAST);
        return usersScorePanel;
    }
}
