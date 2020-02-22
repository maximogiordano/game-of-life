package com.maximo.giordano.gol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Form {
    private static final int CELL_WIDTH = 10;
    private static final int CELL_HEIGHT = 10;

    private static final int MIN_ROWS = 1;
    private static final int MAX_ROWS = 100;
    private static final int MIN_COLUMNS = 1;
    private static final int MAX_COLUMNS = 100;

    private static final int MIN_PERIOD = 100;
    private static final int MAX_PERIOD = 10000;

    private Game game;
    private JTextField rows;
    private JTextField columns;
    private JTextField period;
    private JButton create;
    private JButton start;
    private JButton stop;
    private Canvas canvas;
    private MouseListener mouseListener;
    private JFrame frame;
    private ScheduledExecutorService scheduledExecutorService;

    public Form(Game game) {
        this.game = game;

        boolean[][] board = game.getBoard();
        int numberOfRows = board.length;
        int numberOfColumns = board[0].length;

        frame = new JFrame();
        rows = new JTextField(String.valueOf(numberOfRows), 10);
        columns = new JTextField(String.valueOf(numberOfColumns), 10);
        period = new JTextField("1000", 10);
        create = new JButton("Create");
        start = new JButton("Start");
        stop = new JButton("Stop");
        canvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                Form.this.paint(g);
            }
        };
        mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Form.this.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // ignore e
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // ignore e
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // ignore e
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // ignore e
            }
        };

        Panel leftPanel = new Panel(new GridLayout(4, 1));
        Panel rightPanel = new Panel(new GridLayout(1, 1));
        Panel buttons = new Panel(new GridLayout(1, 3));

        leftPanel.add(rows);
        leftPanel.add(columns);
        leftPanel.add(period);
        leftPanel.add(buttons);

        rightPanel.add(canvas);

        buttons.add(create);
        buttons.add(start);
        buttons.add(stop);

        frame.setLayout(new GridLayout(1, 2));
        frame.add(leftPanel);
        frame.add(rightPanel);

        rows.setBorder(BorderFactory.createTitledBorder("Rows"));
        columns.setBorder(BorderFactory.createTitledBorder("Columns"));
        period.setBorder(BorderFactory.createTitledBorder("Period"));
        canvas.setSize(CELL_WIDTH * numberOfColumns, CELL_HEIGHT * numberOfRows);

        create.addActionListener(this::create);
        start.addActionListener(this::start);
        stop.addActionListener(this::stop);
        canvas.addMouseListener(mouseListener);

        stop.setEnabled(false);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void paint(Graphics g) {
        boolean[][] board = game.getBoard();

        int numberOfRows = board.length;
        int numberOfColumns = board[0].length;

        for (int row = 0; row < numberOfRows; row++) {
            for (int col = 0; col < numberOfColumns; col++) {
                g.setColor(board[row][col] ? Color.GRAY : Color.WHITE);
                g.fillRect(CELL_WIDTH * col, CELL_HEIGHT * row, CELL_WIDTH, CELL_HEIGHT);
                g.setColor(Color.BLACK);
                g.drawRect(CELL_WIDTH * col, CELL_HEIGHT * row, CELL_WIDTH, CELL_HEIGHT);
            }
        }
    }

    private void mouseClicked(MouseEvent e) {
        boolean[][] board = game.getBoard();

        int row = e.getY() / CELL_HEIGHT;
        int col = e.getX() / CELL_WIDTH;

        if (row >= 0 && row < board.length && col >= 0 && col < board[row].length) {
            board[row][col] = !board[row][col];
            game.setBoard(board);
            canvas.repaint();
        }
    }

    private void create(ActionEvent actionEvent) {
        try {
            int rowsValue = Integer.parseInt(rows.getText());
            int columnsValue = Integer.parseInt(columns.getText());

            if (rowsValue < MIN_ROWS || rowsValue > MAX_ROWS) {
                throw new IllegalArgumentException("invalid number of rows");
            }

            if (columnsValue < MIN_COLUMNS || columnsValue > MAX_COLUMNS) {
                throw new IllegalArgumentException("invalid number of columns");
            }

            game.setBoard(new boolean[rowsValue][columnsValue]);
            canvas.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e);
        }
    }

    private void start(ActionEvent actionEvent) {
        try {
            int periodValue = Integer.parseInt(period.getText());

            if (periodValue < MIN_PERIOD || periodValue > MAX_PERIOD) {
                throw new IllegalArgumentException("invalid period");
            }

            create.setEnabled(false);
            start.setEnabled(false);
            canvas.removeMouseListener(mouseListener);
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            scheduledExecutorService.scheduleAtFixedRate(() -> {
                game.next();
                canvas.repaint();
            }, 0, periodValue, TimeUnit.MILLISECONDS);
            stop.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e);
        }
    }

    private void stop(ActionEvent actionEvent) {
        try {
            stop.setEnabled(false);
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
            canvas.addMouseListener(mouseListener);
            start.setEnabled(true);
            create.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e);
        }
    }

    public static void main(String[] args) {
        new Form(new Game(Constants.CYCLIC_PATTERNS.get("pulstar")[0]));
    }
}
