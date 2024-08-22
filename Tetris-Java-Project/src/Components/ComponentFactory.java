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
    public static JPanel createLabelWithSlider(String text, int min, int max, int initial) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel label = createLabel(text);
        JSlider slider = new JSlider(min, max, initial);
        JLabel valueLabel = new JLabel(String.valueOf(initial)); // Label to display the slider's value
        slider.setMajorTickSpacing((max - min) / 5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        // Update the valueLabel when the slider's value changes
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                valueLabel.setText(String.valueOf(slider.getValue()));
            }
        });
        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.add(slider, BorderLayout.CENTER);
        sliderPanel.add(valueLabel, BorderLayout.EAST); // Add the value label to the right of the slider

        panel.add(label, BorderLayout.WEST);
        panel.add(sliderPanel, BorderLayout.CENTER);
        return panel;
    };

    public static JPanel createLabelWithCheckbox(String text) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel label = createLabel(text);
        JCheckBox checkBox = new JCheckBox();
        panel.add(label, BorderLayout.WEST);
        panel.add(checkBox, BorderLayout.EAST);
        return panel;
    }



    public static JButton createNavigationbutton(String buttonTitle, String navLocation, int width, int height, TetrisApp app){
            JButton navButton = new JButton(buttonTitle);
            navButton.addActionListener(e -> app.showScreen(navLocation));
            navButton.setBackground(Color.WHITE);
            navButton.setPreferredSize(new Dimension(width, height));
            navButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            return navButton;
    }


    public static JPanel createUserScoreLabel(String Username, int Score){
        JPanel usersScorePanel = new JPanel(new BorderLayout(10, 10));
        JLabel userLabel = createLabel(Username);
        JLabel scoreLabel = createLabel(String.valueOf(Score));
        usersScorePanel.add(userLabel, BorderLayout.WEST);
        usersScorePanel.add(scoreLabel, BorderLayout.EAST);
        return usersScorePanel;
    }
}
