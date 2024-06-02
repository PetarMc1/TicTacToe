package com.petarmc.TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame {
    private char currentPlayer = 'O';
    private JButton[] buttons = new JButton[9];
    private JLabel infoLabel = new JLabel("Circle goes first");
    private boolean gameEnded = false;

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel gameBoard = new JPanel(new GridLayout(3, 3));
        gameBoard.setBackground(Color.DARK_GRAY);
        add(gameBoard, BorderLayout.CENTER);

        infoLabel.setForeground(Color.CYAN);
        infoLabel.setBackground(Color.DARK_GRAY);
        infoLabel.setOpaque(true);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(new Font("Permanent Marker", Font.PLAIN, 30));
        add(infoLabel, BorderLayout.NORTH);

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Permanent Marker", Font.PLAIN, 60));
            buttons[i].setBackground(Color.GRAY);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(new ButtonClickListener(i));
            gameBoard.add(buttons[i]);
        }

        JButton resetButton = new JButton("New Game");
        resetButton.setFont(new Font("Permanent Marker", Font.PLAIN, 16));
        resetButton.setBackground(Color.DARK_GRAY);
        resetButton.setForeground(Color.CYAN);
        resetButton.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        resetButton.setFocusPainted(false);
        resetButton.setOpaque(true);
        resetButton.addActionListener(e -> resetGame());
        add(resetButton, BorderLayout.SOUTH);
    }

    private class ButtonClickListener implements ActionListener {
        private int index;

        public ButtonClickListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameEnded || !buttons[index].getText().equals("")) return;

            buttons[index].setText(String.valueOf(currentPlayer));
            buttons[index].setForeground(currentPlayer == 'O' ? Color.BLUE : Color.RED);
            if (checkForWin()) {
                infoLabel.setText((currentPlayer == 'O' ? "Circle" : "Cross") + " wins!");
                gameEnded = true;
            } else if (isBoardFull()) {
                infoLabel.setText("It's a draw!");
                gameEnded = true;
            } else {
                currentPlayer = (currentPlayer == 'O') ? 'X' : 'O';
                infoLabel.setText("Now it's " + (currentPlayer == 'O' ? "Circle" : "Cross") + "'s turn");
            }
        }
    }

    private boolean checkForWin() {
        int[][] winCombos = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        for (int[] combo : winCombos) {
            if (buttons[combo[0]].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[combo[1]].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[combo[2]].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (JButton button : buttons) {
            if (button.getText().equals("")) {
                return false;
            }
        }
        return true;
    }

    private void resetGame() {
        for (JButton button : buttons) {
            button.setText("");
            button.setForeground(Color.WHITE);
        }
        currentPlayer = 'O';
        infoLabel.setText("Circle goes first");
        gameEnded = false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToe frame = new TicTacToe();
            frame.setVisible(true);
        });
    }
}
