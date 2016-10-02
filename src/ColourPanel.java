import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by amriksadhra on 29/04/15.
 */
public class ColourPanel extends JFrame {
    private JButton ok = new JButton("Select Colour");
    private JPanel colourPanel = new JPanel(new GridBagLayout());
    private JColorChooser myChooser = new JColorChooser();
    private GridBagConstraints c = new GridBagConstraints();
    public int done = 0;

    public ColourPanel() {
        this.setTitle("Select Background colour");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);

        myChooser.setPreviewPanel(new JPanel());
        myChooser.getColor();

        /* Add Colour chooser to panel */
        c.gridx = 0;
        c.gridy = 0;
        colourPanel.add(myChooser, c);

        /* Add Button to panel */
        c.gridx = 0;
        c.gridy = 1;
        colourPanel.add(ok, c);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                done = 1;//Set done to 1 when colour selected. This value is polled from birdHandler
                ColourPanel.this.setVisible(false);
            }
        });

        /* Build UI */
        Dimension minSize = new Dimension(690, 350);//Restrict dimensions
        setMinimumSize(minSize);
        setMaximumSize(minSize);
        setSize(690, 350);
        getContentPane().add(colourPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String arg[]) {
        new ColourPanel();
    }

    public Color getColour() {
        return myChooser.getColor();
    }
}