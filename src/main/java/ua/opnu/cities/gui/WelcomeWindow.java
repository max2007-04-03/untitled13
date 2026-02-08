package ua.opnu.cities.gui;

import javax.swing.*;
import java.awt.*;

public class WelcomeWindow extends JFrame {
    private final JTextField nameField;

    public WelcomeWindow() {
        setTitle("Welcome to Cities Game");
        setSize(400, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Окно по центру экрана
        setLayout(new FlowLayout());

        add(new JLabel("Введіть ваше ім'я:"));

        nameField = new JTextField(15);
        add(nameField);

        JButton submitButton = new JButton("Прийняти");
        add(submitButton);

        submitButton.addActionListener(_ -> {
            String playerName = nameField.getText().trim();
            if (!playerName.isEmpty()) {
                this.dispose();
                new GameWindow(playerName).setVisible(true); // Відкриваємо гру
            } else {
                JOptionPane.showMessageDialog(this, "Будь ласка, введіть ім'я!");
            }
        });
    }

    public void showWindow() {
        setVisible(true);
    }
}