import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LineGameSwing {
    private static final int SIZE = 15;
    private JFrame frame;
    private JButton[] buttons;
    private int currentPlayer;
    private JLabel instructions;

    public LineGameSwing() {
        frame = new JFrame("Line Game");
        buttons = new JButton[SIZE];
        currentPlayer = 1;
        initializeGUI();
    }

    private void initializeGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Instructions panel
        JPanel topPanel = new JPanel();
        instructions = new JLabel("Player 1 (X), select two adjacent empty boxes (highlighted will be on the left).");
        instructions.setFont(new Font("Arial", Font.PLAIN, 16));
        topPanel.add(instructions);
        frame.add(topPanel, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, SIZE));
        for (int i = 0; i < SIZE; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.BOLD, 24));
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(new ButtonClickListener(i));
            buttonPanel.add(buttons[i]);
        }
        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setSize(800, 150);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private int index;

        public ButtonClickListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (index < SIZE - 1 && buttons[index].getText().isEmpty() && buttons[index + 1].getText().isEmpty()) {
                if (currentPlayer == 1) {
                    buttons[index].setText("X");
                    buttons[index + 1].setText("X");
                } else {
                    buttons[index].setText("O");
                    buttons[index + 1].setText("O");
                }
                if (!hasValidMoves()) {
                    JOptionPane.showMessageDialog(frame, "Player " + currentPlayer + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    resetGame();
                } else {
                    currentPlayer = (currentPlayer == 1) ? 2 : 1;
                    updateInstructions();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid move! Choose two adjacent empty boxes.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean hasValidMoves() {
        for (int i = 0; i < SIZE - 1; i++) {
            if (buttons[i].getText().isEmpty() && buttons[i + 1].getText().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void updateInstructions() {
        instructions.setText("Player " + currentPlayer + (currentPlayer == 1 ? " (X)" : " (O)") + ", select two adjacent empty boxes (highlighted will be on the left).");
    }

    private void resetGame() {
        for (JButton button : buttons) {
            button.setText("");
        }
        currentPlayer = 1;
        updateInstructions();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LineGameSwing::new);
    }
}
