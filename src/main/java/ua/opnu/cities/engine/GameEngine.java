package ua.opnu.cities.engine;

import lombok.Getter; // Не забудь імпорт!
import java.util.HashSet;
import java.util.Set;

public class GameEngine {
    private final CityDatabase database;
    private final Set<String> usedCities;
    private char lastLetter;

    @Getter // Тепер метод getPlayerScore створюється автоматично
    private int playerScore = 0;

    @Getter // Тепер метод getComputerScore створюється автоматично
    private int computerScore = 0;

    public GameEngine() {
        this.database = new CityDatabase();
        this.usedCities = new HashSet<>();
        this.lastLetter = '\0';
    }

    public String validateAndProcess(String cityName) {
        if (!database.contains(cityName)) return "Такого міста не існує в базі!";
        if (usedCities.contains(cityName)) return "Це місто вже було використане!";
        if (lastLetter != '\0' && Character.toLowerCase(cityName.charAt(0)) != lastLetter) {
            return "Місто має починатися на літеру: " + Character.toUpperCase(lastLetter);
        }

        usedCities.add(cityName);
        playerScore++;
        lastLetter = determineNextLetter(cityName);
        return "OK";
    }

    public String getComputerResponse() {
        String computerCity = database.findCityByLetter(lastLetter, usedCities);
        if (computerCity != null) {
            usedCities.add(computerCity);
            computerScore++;
            lastLetter = determineNextLetter(computerCity);
            return computerCity;
        }
        return null;
    }

    private char determineNextLetter(String city) {
        String lower = city.toLowerCase();
        int idx = lower.length() - 1;
        char last = lower.charAt(idx);
        // Додано 'ц' згідно з правилами української мови у грі [cite: 29]
        if (last == 'ь' || last == 'и' || last == 'й' || last == 'ц') {
            last = lower.charAt(idx - 1);
        }
        return last;
    }

    // Залишаємо цей метод, але тепер використаємо його в GUI!
    public char getCurrentLetter() { return lastLetter; }
}