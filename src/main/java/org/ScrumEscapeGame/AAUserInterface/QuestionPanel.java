package org.ScrumEscapeGame.AAUserInterface;

import javax.swing.*;
import java.awt.*;

public class QuestionPanel extends JDialog {

    public QuestionPanel(JFrame parent) {
        super(parent, "Puzzle Attempt", true);

        setLayout(new BorderLayout());
        add(new JLabel("Puzzle will appear here."), BorderLayout.CENTER);

        setSize(400, 200);
        setLocationRelativeTo(parent);
    }
}
