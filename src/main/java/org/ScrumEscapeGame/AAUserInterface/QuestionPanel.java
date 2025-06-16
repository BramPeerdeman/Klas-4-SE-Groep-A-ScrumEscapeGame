package org.ScrumEscapeGame.AAUserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;

public class QuestionPanel extends JPanel {

    private final JTextArea questionArea;
    private final ButtonGroup answerGroup;
    private final JPanel answersPanel;
    private final JButton submitButton;
    private String correctAnswer;

    public QuestionPanel() {
        setLayout(new BorderLayout());

        questionArea = new JTextArea();
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setText("Puzzle will appear here.");
        add(new JScrollPane(questionArea), BorderLayout.NORTH);

        answersPanel = new JPanel();
        answersPanel.setLayout(new BoxLayout(answersPanel, BoxLayout.Y_AXIS));
        add(answersPanel, BorderLayout.CENTER);

        submitButton = new JButton("Submit");
        add(submitButton, BorderLayout.SOUTH);

        answerGroup = new ButtonGroup();
    }

    // Call this method when loading a question.
    public void loadQuestion(String questionText, List<String> answers, String correctAnswer) {
        this.correctAnswer = correctAnswer;
        questionArea.setText(questionText);
        answersPanel.removeAll();
        answerGroup.clearSelection();

        for (String ans : answers) {
            JRadioButton button = new JRadioButton(ans);
            answerGroup.add(button);
            answersPanel.add(button);
        }

        answersPanel.revalidate();
        answersPanel.repaint();
    }

    // Register the listener for submit button.
    public void setSubmitAction(ActionListener listener) {
        submitButton.addActionListener(listener);
    }

    public String getSelectedAnswer() {
        for (Enumeration<AbstractButton> buttons = answerGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    public boolean isAnswerCorrect() {
        String selected = getSelectedAnswer();
        return selected != null && selected.equals(correctAnswer);
    }
}
