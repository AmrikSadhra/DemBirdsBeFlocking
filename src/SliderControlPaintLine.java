import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderControlPaintLine extends JFrame {
    JSlider slider1;

    double transX = 0.0;

    double transY = 0.0;

    float width = 1.0f;

    double numBirds;

    public SliderControlPaintLine() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 3));
        getContentPane().add(controlPanel, BorderLayout.NORTH);

        JLabel label1 = new JLabel("Number of Birds: ");

        controlPanel.add(label1);

        slider1 = createSlider(controlPanel, JSlider.HORIZONTAL, 0, 300, 150, 100, 50);

        setSize(500,500);
        setVisible(true);
    }
    public static void main(String arg[]) {
        new SliderControlPaintLine();
    }
    public JSlider createSlider(JPanel panel, int orientation, int minimumValue, int maximumValue,
                                int initValue, int majorTickSpacing, int minorTickSpacing) {
        JSlider slider = new JSlider(orientation, minimumValue, maximumValue, initValue);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(majorTickSpacing);
        slider.setMinorTickSpacing(minorTickSpacing);
        slider.setPaintLabels(true);
        slider.addChangeListener(new SliderListener());
        panel.add(slider);
        return slider;
    }

    class SliderListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            JSlider tempSlider = (JSlider) e.getSource();

            if (tempSlider.equals(slider1)) {
                numBirds =  slider1.getValue() - 150.0;
                System.out.println(numBirds);
            }
        }
    }

}