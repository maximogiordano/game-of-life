package com.maximo.giordano.gol;

import javax.swing.*;
import java.awt.*;

public class View {
    private static final int CELL_WIDTH = 10;
    private static final int CELL_HEIGHT = 10;

    private JFrame frame;
    private Canvas canvas;

    public View(Game game) {
        this.frame = new JFrame();
        this.canvas = new Canvas();

        boolean[][] board = game.getBoard();

        int rows = board.length;
        int cols = board[0].length;

        canvas.setSize(CELL_WIDTH * cols, CELL_HEIGHT * rows);

        frame.setLayout(new FlowLayout());
        frame.add(canvas);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    public void paint(Game game) {
        boolean[][] board = game.getBoard();

        int rows = board.length;
        int cols = board[0].length;

        Graphics g = canvas.getGraphics();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                g.setColor(board[row][col] ? Color.BLACK : Color.WHITE);
                g.fillRect(CELL_WIDTH * col, CELL_HEIGHT * row, CELL_WIDTH, CELL_HEIGHT);
            }
        }

        frame.repaint();
    }
}
