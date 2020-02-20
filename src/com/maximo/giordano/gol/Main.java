package com.maximo.giordano.gol;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            String patternName = JOptionPane.showInputDialog("Pattern Name");
            int numberOfSteps = Integer.parseInt(JOptionPane.showInputDialog("Number of Steps"));
            long delay = Long.parseLong(JOptionPane.showInputDialog("Delay"));

            Game game = new Game(Constants.CYCLIC_PATTERNS.get(patternName)[0]);
            View view = new View(game);

            Thread.sleep(delay);
            view.paint(game);

            for (int i = 1; i <= numberOfSteps; i++) {
                Thread.sleep(delay);
                game.next();
                view.paint(game);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
