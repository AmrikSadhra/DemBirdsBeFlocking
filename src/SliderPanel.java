import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class SliderPanel extends JFrame {
    public JSlider slider1, slider2, slider3, slider4, slider5, slider6, slider7, slider8, slider9, slider10, slider11;
    private JButton save, load;
    public JCheckBox v1Enable, v2Enable, v3Enable, v4Enable, v5Enable;
    public JTextArea messages;
    private final JFileChooser fc = new JFileChooser();//File chooser
    private final JFileChooser dc = new JFileChooser();//Directory chooser
    private GridBagConstraints c = new GridBagConstraints();

    public SliderPanel() {
        dc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        dc.setDialogTitle("Select save directory");

        JPanel controlPanel = new JPanel(new GridBagLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
        this.setTitle("Control Panel");

        getContentPane().add(controlPanel, BorderLayout.CENTER);

        /* --------IT LOOKS AMAZING ON MACOSX I PROMISE ----------*/
        /* -----------------------Build UI----------------------- */
        /* Center of Mass */
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        v1Enable = new JCheckBox("", true);
        controlPanel.add(v1Enable, c);
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 0;
        JLabel v1Weight = new JLabel("Center of Mass", SwingConstants.CENTER);
        controlPanel.add(v1Weight, c);
        c.gridx = 3;
        c.gridy = 0;
        slider1 = createSlider(controlPanel, JSlider.HORIZONTAL, 0, 10, 5, 5, 1);

        /* Collision Avoidance */
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        v2Enable = new JCheckBox("", true);
        controlPanel.add(v2Enable, c);
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 1;
        JLabel v2Weight = new JLabel("Collision Avoidance", SwingConstants.CENTER);
        controlPanel.add(v2Weight, c);
        c.gridx = 3;
        c.gridy = 1;
        slider2 = createSlider(controlPanel, JSlider.HORIZONTAL, 0, 10, 5, 5, 1);

        /* Velocity Matching */
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        v3Enable = new JCheckBox("", true);
        controlPanel.add(v3Enable, c);
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 2;
        JLabel v3Weight = new JLabel("Match Velocity", SwingConstants.CENTER);
        controlPanel.add(v3Weight, c);
        c.gridx = 3;
        c.gridy = 2;
        slider3 = createSlider(controlPanel, JSlider.HORIZONTAL, 0, 10, 1, 5, 1);

        /* Bounding Strength */
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 3;
        v4Enable = new JCheckBox("", true);
        controlPanel.add(v4Enable, c);
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 3;
        JLabel v4Weight = new JLabel("Bounding Strength", SwingConstants.CENTER);
        controlPanel.add(v4Weight, c);
        c.gridx = 3;
        c.gridy = 3;
        slider4 = createSlider(controlPanel, JSlider.HORIZONTAL, 0, 30, 20, 5, 1);

        /* Mouse Following */
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 4;
        v5Enable = new JCheckBox("", false);
        controlPanel.add(v5Enable, c);
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 4;
        JLabel v5Weight = new JLabel("Mouse Following", SwingConstants.CENTER);
        controlPanel.add(v5Weight, c);
        c.gridx = 3;
        c.gridy = 4;
        slider5 = createSlider(controlPanel, JSlider.HORIZONTAL, 0, 10, 10, 5, 1);

        /* Max Velocity */
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 5;
        JLabel maxVelocity = new JLabel("Max Velocity", SwingConstants.CENTER);
        controlPanel.add(maxVelocity, c);
        c.gridx = 3;
        c.gridy = 5;
        slider6 = createSlider(controlPanel, JSlider.HORIZONTAL, 0, 10, 3, 5, 1);

        /* Bird Spacing */
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 6;
        JLabel birdSpacing = new JLabel("Bird Spacing", SwingConstants.CENTER);
        controlPanel.add(birdSpacing, c);
        c.gridx = 3;
        c.gridy = 6;
        slider7 = createSlider(controlPanel, JSlider.HORIZONTAL, 0, 100, 50, 5, 1);

        /* Bounding Encouragement */
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 7;
        JLabel boundEncourage = new JLabel("Bound Encouragement", SwingConstants.CENTER);
        controlPanel.add(boundEncourage, c);
        c.gridx = 3;
        c.gridy = 7;
        slider8 = createSlider(controlPanel, JSlider.HORIZONTAL, 0, 10, 10, 5, 1);

        /* Max Turn Angle */
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 8;
        JLabel maxTurnAngle = new JLabel("Turn Angle", SwingConstants.CENTER);
        controlPanel.add(maxTurnAngle, c);
        c.gridx = 3;
        c.gridy = 8;
        slider9 = createSlider(controlPanel, JSlider.HORIZONTAL, 0, 100, 7, 5, 1);

        /* CoM Division */
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 9;
        JLabel centerOfMass = new JLabel("CoM Division", SwingConstants.CENTER);
        controlPanel.add(centerOfMass, c);
        c.gridx = 3;
        c.gridy = 9;
        slider10 = createSlider(controlPanel, JSlider.HORIZONTAL, 1, 200, 50, 5, 1);

        /* Detection Radius */
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 10;
        JLabel radius = new JLabel("Detection Radius", SwingConstants.CENTER);
        controlPanel.add(radius, c);
        c.gridx = 3;
        c.gridy = 10;
        slider11 = createSlider(controlPanel, JSlider.HORIZONTAL, 1, 500, 100, 5, 1);

        /* Message Scroll Pane */
        c.gridwidth = 5;
        c.gridheight = 3;
        c.gridx = 0;
        c.gridy = 12;
        messages = new JTextArea(5, 30);
        messages.setEditable(false);
        controlPanel.add(messages, c);
        JScrollPane messagesScroll = new JScrollPane(messages);
        controlPanel.add(messagesScroll, c);

        /* Save Button */
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 2;
        c.gridy = 11;
        save = new JButton("Save Settings");
        controlPanel.add(save, c);
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = dc.showSaveDialog(save);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = dc.getCurrentDirectory();
                    Utils.FileSave(SliderPanel.this, file);
                }
            }
        });

        /* Load Button */
        c.gridx = 4;
        c.gridy = 11;
        load = new JButton("Load Settings");
        controlPanel.add(load, c);
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(load);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    Utils.FileLoad(SliderPanel.this, file);
                }
            }
        });

        Dimension minSize = new Dimension(400, 475);
        setMinimumSize(minSize);
        setMaximumSize(minSize);
        setSize(400, 475);
        setVisible(true);
    }

    public static void main(String arg[]) {
        new SliderPanel();
    }

    /* Helper function to create sliders */
    private JSlider createSlider(JPanel panel, int orientation, int minimumValue, int maximumValue, int initValue, int majorTickSpacing, int minorTickSpacing) {
        JSlider slider = new JSlider(orientation, minimumValue, maximumValue, initValue);
        slider.setMajorTickSpacing(majorTickSpacing);
        slider.setMinorTickSpacing(minorTickSpacing);
        panel.add(slider, c);
        return slider;
    }
}