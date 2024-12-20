package projet_java.Algo;

import projet_java.Geographie.Ville;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class City {
    private String name;        // Nom de la ville
    private double latitude;    // Latitude
    private double longitude;   // Longitude

 // Constructeur
    public City(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

 // Calculer la distance euclidienne entre deux villes
    public static double calculateDistance(City c1, City c2) {
        double xDiff = c1.getLongitude() - c2.getLongitude();
        double yDiff = c1.getLatitude() - c2.getLatitude();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

 // Construire une matrice de distances (distances entre les villes)
    public static Map<String, Map<String, Double>> buildDistanceMatrix(List<Ville> villes) {
        Map<String, Map<String, Double>> distanceMatrix = new HashMap<>();

        for (Ville v1 : villes) {
            Map<String, Double> distances = new HashMap<>();
            City city1 = new City(v1.getNom(), v1.getLatitude(), v1.getLongitude());

            for (Ville v2 : villes) {
                City city2 = new City(v2.getNom(), v2.getLatitude(), v2.getLongitude());
                double distance = calculateDistance(city1, city2);

                distances.put(city2.getName(), distance);
            }

            distanceMatrix.put(city1.getName(), distances);
        }
        System.out.println("La matrice de distances a été construite avec succès !");
        return distanceMatrix;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
