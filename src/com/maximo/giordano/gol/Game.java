package com.maximo.giordano.gol;

public class Game {
    private boolean[][] board;

    public Game(boolean[][] board) {
        setBoard(board);
    }

    public void setBoard(boolean[][] board) {
        checkBoardIsNotNull(board);
        checkRowsIsPositive(board);
        checkRowsAreNotNull(board);
        checkColsIsPositive(board);
        checkBoardIsRectangular(board);

        this.board = copy(board);
    }

    public boolean[][] getBoard() {
        return copy(board);
    }

    public void next() {
        board = getNextBoard();
    }

    private void checkBoardIsNotNull(boolean[][] board) {
        if (board == null) {
            throw new NullPointerException("board is null");
        }
    }

    private void checkRowsIsPositive(boolean[][] board) {
        int rows = board.length;

        if (rows <= 0) {
            throw new IllegalArgumentException("rows is " + rows);
        }
    }

    private void checkRowsAreNotNull(boolean[][] board) {
        int rows = board.length;

        for (int row = 0; row < rows; row++) {
            if (board[row] == null) {
                throw new NullPointerException("row " + row + " is null");
            }
        }
    }

    private void checkColsIsPositive(boolean[][] board) {
        int cols = board[0].length;

        if (cols <= 0) {
            throw new IllegalArgumentException("cols is " + cols);
        }
    }

    private void checkBoardIsRectangular(boolean[][] board) {
        int rows = board.length;
        int cols = board[0].length;

        for (int row = 1; row < rows; row++) {
            if (board[row].length != cols) {
                throw new IllegalArgumentException("the number of columns of row " + row + " is not " + cols);
            }
        }
    }

    private boolean[][] copy(boolean[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        boolean[][] dest = new boolean[rows][cols];

        for (int row = 0; row < rows; row++) {
            System.arraycopy(board[row], 0, dest[row], 0, cols);
        }

        return dest;
    }

    private boolean[][] getNextBoard() {
        int rows = board.length;
        int cols = board[0].length;
        boolean[][] nextBoard = new boolean[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                nextBoard[row][col] = willBeAlive(row, col);
            }
        }

        return nextBoard;
    }

    private boolean willBeAlive(int row, int col) {
        boolean currentlyAlive = board[row][col];
        int numberOfLiveNeighbours = getNumberOfLiveNeighbours(row, col);

        return currentlyAlive && (numberOfLiveNeighbours == 2 || numberOfLiveNeighbours == 3) ||
                !currentlyAlive && numberOfLiveNeighbours == 3;
    }

    private int getNumberOfLiveNeighbours(int row, int col) {
        int numberOfLiveNeighbours = 0;

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if ((i != row || j != col) && isAlive(i, j)) {
                    numberOfLiveNeighbours++;
                }
            }
        }

        return numberOfLiveNeighbours;
    }

    private boolean isAlive(int row, int col) {
        int rows = board.length;
        int cols = board[0].length;

        return row >= 0 && row < rows && col >= 0 && col < cols && board[row][col];
    }
}
