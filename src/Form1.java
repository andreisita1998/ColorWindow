import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form1 extends JFrame {
    public Form1() {
        super("Option dialog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));
        JLabel label = new JLabel("Choose settings");

        JButton settingsButton = new JButton("Settings");
        JButton closeButton = new JButton("Close");

        panel.add(label);
        panel.add(settingsButton);
        panel.add(closeButton);

        getContentPane().add(panel);
        setVisible(true);

        closeButton.addActionListener(e -> {
            dispose();
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SecondForm().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Form1::new);
    }
}