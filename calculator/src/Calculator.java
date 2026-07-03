import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    int boardWidth = 360;
    int boardHeight = 250;

    Color customLightGrey = new Color(212,212,210);
    Color customDarkGrey = new Color(80,80,80);
    Color customBlack = new Color(28,28,28);
    Color customOrange = new Color(235,149,0);

    String[] buttonsValues = {
        "AC", "+/-", "%", "÷",
        "7", "8", "9", "×",
        "6", "5", "4", "-",
        "3", "2", "1", "+",
        "0", ".", "√", "="
    };

    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSysmbols = {"AC", "+/-", "%"};

    JFrame frame = new JFrame("Caluclator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    String A = "0";
    String opreator = null;
    String B = null;

    Calculator() {
        frame.setSize(boardHeight, boardWidth);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.WHITE);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        for (String buttonValue : buttonsValues) {
            JButton button = new JButton();
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));

            if (Arrays.asList(topSysmbols).contains(buttonValue)) {
                button.setBackground(customLightGrey);
                button.setForeground(customBlack);
            } else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            } else {
                button.setBackground(customDarkGrey);
                button.setForeground(Color.white);
            }

            buttonsPanel.add(button);

            button.addActionListener(e -> {
                JButton btn = (JButton) e.getSource();
                String pressed = btn.getText();

                if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                    if (pressed.equals("=")) {
                        if (A != null) {
                            B = displayLabel.getText();
                            double numA = Double.parseDouble(A);
                            double numB = Double.parseDouble(B);

                            switch (opreator) {
                                case "+" -> displayLabel.setText(removeZeroDecimal(numA + numB));
                                case "-" -> displayLabel.setText(removeZeroDecimal(numA - numB));
                                case "×" -> displayLabel.setText(removeZeroDecimal(numA * numB));
                                case "÷" -> displayLabel.setText(removeZeroDecimal(numA / numB));
                            }
                            clearAll();
                        }
                    } else if ("+-×÷".contains(pressed)) {
                        if (opreator == null) {
                            A = displayLabel.getText();
                            displayLabel.setText("0");
                            B = "0";
                        }
                        opreator = buttonValue;
                    }
                } else if (Arrays.asList(topSysmbols).contains(buttonValue)) {
                    switch (pressed) {
                        case "AC" -> {
                            clearAll();
                            displayLabel.setText("0");
                        }
                        case "+/-" -> {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay *= -1;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                        case "%" -> {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay /= 100;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                    }
                } else {
                    if (pressed.equals(".")) {
                        if (!displayLabel.getText().contains(".")) {
                            displayLabel.setText(displayLabel.getText() + ".");
                        }
                    } else if ("012345789".contains(pressed)) {
                        if (displayLabel.getText().equals("0")) {
                            displayLabel.setText(pressed);
                        } else {
                            displayLabel.setText(displayLabel.getText() + pressed);
                        }
                    }
                }
            });
        }

        frame.setVisible(true);
    }

    void clearAll() {
        A = "0";
        opreator = null;
        B = null;
    }

    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }
}

