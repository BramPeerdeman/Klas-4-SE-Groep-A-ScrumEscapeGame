package org.ScrumEscapeGame.cli;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConsoleWindow extends JFrame {
    private JTextArea outputArea;
    private JTextField inputField;

    public ConsoleWindow() {
        setTitle("Game Console");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);

        inputField = new JTextField();
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = inputField.getText();
                inputField.setText("");
                Game.handleCommand(command);
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(inputField, BorderLayout.SOUTH);

        add(panel);
    }

    public void printWelcomeMessage() {
        printMessage("Welcome to the Text-Based Game. Type 'exit' to quit.");
    }

    public void printMessage(String message) {
        outputArea.append(message + "\n");
    }

    public void displayMap() {
        String map = "Map of the game:\n"
                + "  _______   _______   _______ \n"
                + " |       | |       | |       |\n"
                + " |   X   |-|   Y   |-|   Z   |\n"
                + " |_______| |_______| |_______|\n"
                + "You are at X. Y is to the east, Z is to the east of Y.";
        printMessage(map);
    }
}

