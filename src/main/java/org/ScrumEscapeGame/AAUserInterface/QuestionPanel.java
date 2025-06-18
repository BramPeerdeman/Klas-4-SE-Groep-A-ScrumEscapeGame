package org.ScrumEscapeGame.AAUserInterface;

import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.Items.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionPanel extends JPanel {
    private JLabel questionLabel;
    private JRadioButton[] answerButtons;
    private ButtonGroup buttonGroup;
    private JButton submitButton;
    private String correctAnswer;
    private GameUIService uiService;
    private GameContext context;

    // Embedded inventory panel (shows only player inventory).
    private InventoryPanel embeddedInventoryPanel;
    // A label to display messages (or you could use a JTextArea if you prefer).
    private JLabel messageLabel;
    // A hint label that already exists.
    private JLabel hintLabel;

    public QuestionPanel(InventoryPanel inventoryPanel, GameUIService uiService, GameContext context) {
        this.uiService = uiService;
        this.embeddedInventoryPanel = inventoryPanel;
        this.context = context;
        setLayout(new BorderLayout());

        // Embed the dedicated inventory panel.
        if (inventoryPanel != null) {
            add(inventoryPanel, BorderLayout.EAST);
        }

        // North: Panel for the question text and hint.
        JPanel northPanel = new JPanel(new GridLayout(0, 1));
        questionLabel = new JLabel("Question appears here");
        northPanel.add(questionLabel);
        hintLabel = new JLabel("");
        northPanel.add(hintLabel);
        add(northPanel, BorderLayout.NORTH);

        // Center: Answer choices.
        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.Y_AXIS));
        add(answerPanel, BorderLayout.CENTER);

        answerButtons = new JRadioButton[4];
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new JRadioButton("Choice " + (i + 1));
            buttonGroup.add(answerButtons[i]);
            answerPanel.add(answerButtons[i]);
        }

        // South: A panel that holds both the submit button and a message area.
        JPanel southPanel = new JPanel(new BorderLayout());
        submitButton = new JButton("Submit");
        southPanel.add(submitButton, BorderLayout.NORTH);
        // Create a message label that will be visible immediately.
        messageLabel = new JLabel(" ");
        southPanel.add(messageLabel, BorderLayout.SOUTH);
        add(southPanel, BorderLayout.SOUTH);

        // Map "Q" so that pressing it toggles the question panel.
        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke("Q"), "toggleQuestion");
        actionMap.put("toggleQuestion", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiService.toggleQuestionPanel();
            }
        });
    }

    // Allow the question panel to update the message area.
    public void appendMessage(String msg) {
        messageLabel.setText(msg);
    }

    public void setHintText(String hint) {
        hintLabel.setText(hint);
    }

    public void loadQuestion(String question, String[] choices, String correctAnswer) {
        questionLabel.setText(question);
        this.correctAnswer = correctAnswer;
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setText(choices[i]);
            answerButtons[i].setVisible(true);
        }
        buttonGroup.clearSelection();
        // Clear any previous hint or message.
        hintLabel.setText("");
        appendMessage(" ");
        embeddedInventoryPanel.refresh();
    }

    public String getSelectedAnswer() {
        for (JRadioButton btn : answerButtons) {
            if (btn.isSelected()) {
                return btn.getText();
            }
        }
        return null;
    }

    public void setQuestionText(String text) {
        questionLabel.setText(text);
    }

    public void setSubmitAction(ActionListener listener) {
        for (ActionListener al : submitButton.getActionListeners()) {
            submitButton.removeActionListener(al);
        }
        submitButton.addActionListener(listener);
    }

    public InventoryPanel getEmbeddedInventoryPanel() {
        return embeddedInventoryPanel;
    }

    public void clear() {
        // Clear out any previous texts (question, hint, and message).
        this.questionLabel.setText("");
        this.hintLabel.setText("");
        // Also clear selections and embedded messages.
        buttonGroup.clearSelection();
        embeddedInventoryPanel.clearMessages();
    }




}




