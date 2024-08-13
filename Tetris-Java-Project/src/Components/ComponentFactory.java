package Components;
import javax.swing.*;
import java.awt.*;

public class ComponentFactory {
    public static JPanel createConfigLabelWithSlider(String text, int min, int max, int initial, Font font) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel label = createConfigLabel(text, font);
        JSlider slider = new JSlider(min, max, initial);
        slider.setMajorTickSpacing((max - min) / 5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        panel.add(label, BorderLayout.WEST);
        panel.add(slider, BorderLayout.CENTER);
        return panel;
    }

    public static JPanel createConfigLabelWithCheckbox(String text, Font font) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel label = createConfigLabel(text, font);
        JCheckBox checkBox = new JCheckBox();
        panel.add(label, BorderLayout.WEST);
        panel.add(checkBox, BorderLayout.EAST);
        return panel;
    }

    public static JLabel createConfigLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        label.setFont(font);
        return label;
    }
}
