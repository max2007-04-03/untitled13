package ua.opnu.cities.engine;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CityDatabase {
    private final Set<String> cities;
    private static final Logger LOG = Logger.getLogger(CityDatabase.class.getName());

    public CityDatabase() {
        this.cities = new HashSet<>();
        loadCitiesFromFile();
    }

    private void loadCitiesFromFile() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("cities.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String city = line.trim();
                if (!city.isEmpty()) {
                    cities.add(city.toLowerCase());
                }
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Помилка при читанні файлу cities.txt.", e);
            cities.add("київ");
            cities.add("одеса");
        }
    }

    public boolean contains(String city) {
        return cities.contains(city.toLowerCase());
    }

    public String findCityByLetter(char letter, Set<String> usedCities) {
        char searchLetter = Character.toLowerCase(letter);
        for (String city : cities) {
            if (city.charAt(0) == searchLetter && !usedCities.contains(city)) {
                return city.substring(0, 1).toUpperCase() + city.substring(1);
            }
        }
        return null;
    }
}