package Components;
import javax.swing.*;

public class ConfigSlider {
    public JPanel panel;
    public JSlider slider;

    public ConfigSlider(JPanel panel, JSlider slider) {
        this.panel = panel;
        this.slider = slider;
    }

    // Expose the JSlider instance so you can add a ChangeListener
    public JSlider getSlider() {
        return slider;
    }

    public JPanel getPanel() {
        return panel;
    }
}
