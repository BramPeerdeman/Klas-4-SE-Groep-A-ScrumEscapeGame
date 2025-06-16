package org.ScrumEscapeGame.AAUserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class QuestionPanel extends JPanel {
    private JLabel questionLabel;
    private JRadioButton[] answerButtons;
    private ButtonGroup buttonGroup;
    private JButton submitButton;

    private String correctAnswer;

    public QuestionPanel(InventoryPanel inventoryPanel) {
        setLayout(new BorderLayout());

        // Safe: only add inventoryPanel if it's not null
        if (inventoryPanel != null) {
            add(inventoryPanel, BorderLayout.EAST);
        }

        questionLabel = new JLabel("Question appears here");
        add(questionLabel, BorderLayout.NORTH);

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

        submitButton = new JButton("Submit");
        add(submitButton, BorderLayout.SOUTH);
    }

    public void loadQuestion(String question, String[] choices, String correctAnswer) {
        questionLabel.setText(question);
        this.correctAnswer = correctAnswer;
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setText(choices[i]);
        }
        buttonGroup.clearSelection();
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

    public boolean isAnswerCorrect() {
        for (JRadioButton button : answerButtons) {
            if (button.isSelected() && button.getText().equals(correctAnswer)) {
                return true;
            }
        }
        return false;
    }

}
