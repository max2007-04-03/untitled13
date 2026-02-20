package ua.opnu.cities.engine;

import lombok.Getter;
import java.util.HashSet;
import java.util.Set;

public class GameEngine {
    private final CityDatabase database;
    private final Set<String> usedCities;
    private char lastLetter;

    @Getter
    private int playerScore = 0;

    @Getter
    private int computerScore = 0;

    public GameEngine() {
        this.database = new CityDatabase();
        this.usedCities = new HashSet<>();
        this.lastLetter = '\0';
    }

    public String validateAndProcess(String cityName) {
        String lowerCity = cityName.toLowerCase();
        if (!database.contains(lowerCity)) {
            return "Такого міста не існує в базі!";
        }
        if (usedCities.contains(lowerCity)) {
            return "Це місто вже було використане!";
        }
        if (lastLetter != '\0' && Character.toLowerCase(cityName.charAt(0)) != lastLetter) {
            return "Місто має починатися на літеру: " + Character.toUpperCase(lastLetter);

        }
        usedCities.add(lowerCity);
        playerScore++;
        lastLetter = determineNextLetter(cityName);
        return "OK";
    }

    public String getComputerResponse() {
        String computerCity = database.findCityByLetter(lastLetter, usedCities);
        if (computerCity != null) {
            usedCities.add(computerCity.toLowerCase());
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
        if (last == 'ь' || last == 'и' || last == 'й' || last == 'ц') {
            last = lower.charAt(idx - 1);
        }
        return last;
    }

    public char getCurrentLetter() {
        return lastLetter;
    }
}