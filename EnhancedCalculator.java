import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnhancedCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private String lastCommand;
    private boolean startNewNumber;
    private double result;
    private double xValue, yValue;
    private boolean isXSet, isYSet;

    public EnhancedCalculator() {
        // Create the frame
        setTitle("Enhanced Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize components
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);

        // Initialize button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 4, 5, 5));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "√", "^", "%",
                "(", ")", "CE", "π",
                "log", "x", "y"
        };

        for (String b : buttons) {
            JButton button = new JButton(b);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        // Add components to frame
        add(display, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        lastCommand = "=";
        startNewNumber = true;
        isXSet = false;
        isYSet = false;

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("0123456789.".contains(command)) {
            if (startNewNumber) {
                display.setText(command);
            } else {
                display.setText(display.getText() + command);
            }
            startNewNumber = false;
        } else if (command.equals("x")) {
            try {
                xValue = Double.parseDouble(display.getText());
                isXSet = true;
                startNewNumber = true;
                display.setText("x = " + xValue);
            } catch (NumberFormatException ex) {
                showErrorDialog("Invalid input for x value!");
            }
        } else if (command.equals("y")) {
            if (isXSet) {
                try {
                    yValue = Double.parseDouble(display.getText());
                    isYSet = true;
                    display.setText("x = " + xValue + ", y = " + yValue);
                } catch (NumberFormatException ex) {
                    showErrorDialog("Invalid input for y value!");
                }
            } else {
                showErrorDialog("Set x value first");
            }
        } else {
            if (startNewNumber) {
                if (command.equals("-")) {
                    display.setText(command);
                    startNewNumber = false;
                } else {
                    lastCommand = command;
                }
            } else {
                if (!command.equals("π")) {
                    try {
                        if (isXSet && isYSet) {
                            display.setText("x = " + xValue + ", y = " + yValue);
                        } else {
                            calculate(Double.parseDouble(display.getText()));
                        }
                    } catch (NumberFormatException ex) {
                        showErrorDialog("Invalid input!");
                    }
                } else {
                    calculate(Math.PI);
                }
                lastCommand = command;
                startNewNumber = true;
            }
        }
    }

    private void calculate(double x) {
        switch (lastCommand) {
            case "+":
                result += x;
                break;
            case "-":
                result -= x;
                break;
            case "*":
                result *= x;
                break;
            case "/":
                if (x != 0) {
                    result /= x;
                } else {
                    showErrorDialog("Cannot divide by zero!");
                }
                break;
            case "√":
                result = Math.sqrt(x);
                break;
            case "^":
                result = Math.pow(result, x);
                break;
            case "%":
                result = result % x;
                break;
            case "log":
                if (x > 0) {
                    result = Math.log10(x);
                } else {
                    showErrorDialog("Invalid input for log function!");
                }
                break;
            case "=":
                result = x;
                break;
            case "C":
                result = 0;
                break;
            case "CE":
                display.setText("");
                result = 0;
                break;
        }
        display.setText("" + result);
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        new EnhancedCalculator();
    }
}
