package ua.opnu.cities.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CityDatabase {
    private final Set<String> cities;

    public CityDatabase() {
        this.cities = new HashSet<>();
        loadCitiesFromFile();
    }

    private void loadCitiesFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("cities.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String city = line.trim();
                if (!city.isEmpty()) {
                    cities.add(city);
                }
            }
        } catch (IOException e) {
            System.err.println("Помилка при читанні файлу: " + e.getMessage());
            cities.add("Київ");
            cities.add("Одеса");
        }
    }

    public boolean contains(String city) {
        return cities.stream().anyMatch(c -> c.equalsIgnoreCase(city));
    }

    public String findCityByLetter(char letter, Set<String> usedCities) {
        char searchLetter = Character.toLowerCase(letter);
        for (String city : cities) {
            if (Character.toLowerCase(city.charAt(0)) == searchLetter) {
                boolean alreadyUsed = usedCities.stream()
                        .anyMatch(used -> used.equalsIgnoreCase(city));
                if (!alreadyUsed) {
                    return city;
                }
            }
        }
        return null;
    }
}
