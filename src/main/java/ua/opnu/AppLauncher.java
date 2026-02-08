package ua.opnu;

import ua.opnu.cities.gui.WelcomeWindow;
import javax.swing.SwingUtilities;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomeWindow welcome = new WelcomeWindow();
            welcome.showWindow();
        });
    }
}