import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Timer;
import javax.swing.*;
import javax.swing.border.*;

public class SecondForm extends JFrame {

    private JRadioButton radOnTime;
    private JRadioButton radCountdown;
    private JTextField txtOnTime;
    private JTextField txtCountdown;
    private JButton btnColor;
    private JButton btnStart;
    private JButton btnStop;


    private Timer timer;
    private Color currentColor = Color.BLACK;
    private JPanel secondWindow;
    private JLabel lblCurrentColor;
    private Color selectedColor;
    private JFrame colorWindow;

    public SecondForm() {
        setTitle("Settings");

        JPanel contentPane = new JPanel(new GridBagLayout());
        setContentPane(contentPane);

        GridBagConstraints c = new GridBagConstraints();

        radOnTime = new JRadioButton("On time", true);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        contentPane.add(radOnTime, c);

        radCountdown = new JRadioButton("Countdown (seconds)");
        c.gridx = 1;
        c.gridy = 1;
        contentPane.add(radCountdown, c);

        ButtonGroup bg = new ButtonGroup();
        bg.add(radOnTime);
        bg.add(radCountdown);

        txtOnTime = new JTextField(10);
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0, 10, 10, 10);
        contentPane.add(txtOnTime, c);

        txtCountdown = new JTextField(10);
        c.gridx = 1;
        c.gridy = 2;
        contentPane.add(txtCountdown, c);

        btnColor = new JButton("Choose Color");
        c.gridx = 0;
        c.gridy = 3;
        contentPane.add(btnColor, c);

        lblCurrentColor = new JLabel("No color selected");
        c.gridx = 2;
        c.gridy = 3;
        contentPane.add(lblCurrentColor, c);

        String[] speeds = {"1", "2", "3", "4", "5"};
        JComboBox<String> speedComboBox = new JComboBox<>(speeds);
        TitledBorder title = BorderFactory.createTitledBorder("Speed");
        speedComboBox.setBorder(title);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 6;
        speedComboBox.setPreferredSize(new Dimension(80, 60));
        contentPane.add(speedComboBox, c);

        btnStart = new JButton("Start countdown");
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        contentPane.add(btnStart);

        btnStop = new JButton("Stop");
        c.gridx = 0;
        c.gridy = 10;
        c.gridwidth = 8;
        contentPane.add(btnStop);

        btnColor.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Choose Color", currentColor);
            if (newColor != null) {
                currentColor = newColor;
                lblCurrentColor.setText("rgb(" + currentColor.getRed() + "," + currentColor.getGreen() + "," + currentColor.getBlue() + ")");
                lblCurrentColor.setForeground(currentColor);
            }
        });
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int countdownSeconds = 0;
                int speedSeconds = Integer.parseInt(speedComboBox.getSelectedItem().toString());

                if (radCountdown.isSelected()) {
                    try {
                        countdownSeconds = Integer.parseInt(txtCountdown.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid countdown value. Please enter a valid integer.");
                        return;
                    }

                    if (countdownSeconds < 0) {
                        JOptionPane.showMessageDialog(null, "Countdown value must be a positive integer.");
                        return;
                    }

                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.SECOND, countdownSeconds);
                    Date triggerTime = calendar.getTime();

                    TimerTask task = new TimerTask() {
                        public void run() {

                            colorWindow = new JFrame("Colors");
                            colorWindow.setSize(200, 200);
                            colorWindow.setLocationRelativeTo(null);

                            JPanel colorPanel = new JPanel();
                            colorWindow.add(colorPanel);

                            TimerTask colorTask = new TimerTask() {
                                boolean isColor1 = true;
                                public void run() {
                                    if (isColor1) {
                                        colorPanel.setBackground(currentColor);
                                    } else {
                                        colorPanel.setBackground(selectedColor);
                                    }
                                    isColor1 = !isColor1;
                                }
                            };
                            timer = new Timer();
                            timer.schedule(colorTask, 0, speedSeconds * 1000);

                            colorWindow.setVisible(true);
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(task, triggerTime);
                } else if (radOnTime.isSelected()) {

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date triggerTime;
                    try {
                        triggerTime = dateFormat.parse(txtOnTime.getText());
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid date/time value. Please enter a valid value in the format yyyy-MM-dd HH:mm:ss.");
                        return;
                    }
                    long delay = triggerTime.getTime() - new Date().getTime();
                    if (delay <= 0) {
                        JOptionPane.showMessageDialog(null, "Selected date/time must be in the future.");
                        return;
                    }

                    TimerTask task = new TimerTask() {
                        public void run() {
                            colorWindow = new JFrame("Colors");
                            colorWindow.setSize(200, 200);
                            colorWindow.setLocationRelativeTo(null);

                            JPanel colorPanel = new JPanel();
                            colorWindow.add(colorPanel);

                            TimerTask colorTask = new TimerTask() {
                                boolean isColor1 = true;
                                public void run() {
                                    if (isColor1) {
                                        colorPanel.setBackground(currentColor);
                                    } else {
                                        colorPanel.setBackground(selectedColor);
                                    }
                                    isColor1 = !isColor1;
                                }
                            };
                            timer = new Timer();
                            timer.schedule(colorTask,0, speedSeconds * 1000);

                            colorWindow.setVisible(true);
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(task, triggerTime);
                }
            }
        });
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                colorWindow.setVisible(false);
                colorWindow.dispose();
            }
        });

    }

}