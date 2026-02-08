package ua.opnu.cities.gui;

import ua.opnu.cities.engine.GameEngine;
import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private final transient GameEngine engine;
    private final JTextField inputField;
    private final JTextArea gameLog;
    private final JLabel infoLabel;

    public GameWindow(String playerName) {
        this.engine = new GameEngine();
        setTitle("Гра Міста: " + playerName);
        setSize(400, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        infoLabel = new JLabel("Ваш хід! Введіть назву міста:");
        add(infoLabel, BorderLayout.NORTH);

        gameLog = new JTextArea();
        gameLog.setEditable(false);
        add(new JScrollPane(gameLog), BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        JButton btn = new JButton("Зробити хід");

        panel.add(inputField, BorderLayout.CENTER);
        panel.add(btn, BorderLayout.EAST);
        add(panel, BorderLayout.SOUTH);

        btn.addActionListener(_ -> processMove());
        inputField.addActionListener(_ -> processMove());
    }

    private void processMove() {
        String city = inputField.getText().trim();

        if (city.equalsIgnoreCase("здаюсь")) {
            showEndGame("Ви здалися!");
            return;
        }

        String res = engine.validateAndProcess(city);
        if (res.equals("OK")) {
            gameLog.append("Ви: " + city + "\n");
            String compCity = engine.getComputerResponse();
            if (compCity != null) {
                gameLog.append("Комп'ютер: " + compCity + "\n");
                infoLabel.setText("Потрібне місто на літеру: "
                        + Character.toUpperCase(engine.getCurrentLetter()));
            } else {
                showEndGame("Ви перемогли! У комп'ютера закінчилися слова.");
            }
        } else {
            JOptionPane.showMessageDialog(this, res);
        }
        inputField.setText("");
    }

    private void showEndGame(String msg) {
        String stats = msg + "\nРахунок: Ви " + engine.getPlayerScore()
                + " - " + engine.getComputerScore() + " Комп'ютер";
        JOptionPane.showMessageDialog(this, stats);
        this.dispose();
    }
}