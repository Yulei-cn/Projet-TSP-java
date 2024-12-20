package projet_java.Geographie;

import projet_java.BDConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Ville {
    private String nom;      // Nom de la ville
    private double latitude; // Latitude
    private double longitude; // Longitude

    // Constructeur
    public Ville(String nom, double latitude, double longitude) {
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    // Charger toutes les informations des villes depuis la base de données
    public static List<Ville> loadAllVilles() {
        List<Ville> villes = new ArrayList<>();
        String sql = "SELECT ville_nom, ville_latitude_deg, ville_longitude_deg FROM villes_france_free";

        try (Connection conn = BDConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Parcourir le résultat
            while (rs.next()) {
                String nom = rs.getString("ville_nom");
                double latitude = rs.getDouble("ville_latitude_deg");
                double longitude = rs.getDouble("ville_longitude_deg");

                Ville ville = new Ville(nom, latitude, longitude);
                villes.add(ville);
            }

            System.out.println("Liste des villes chargée avec succès, total : " + villes.size() + " villes.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return villes;
    }

    // Nouvelle méthode dans la classe Ville
    public static Ville findByName(String name) {
        String sql = "SELECT ville_nom, ville_latitude_deg, ville_longitude_deg FROM villes_france_free WHERE LOWER(ville_nom) = LOWER(?)";
        Ville ville = null;

        try (Connection conn = BDConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("ville_nom");
                    double latitude = rs.getDouble("ville_latitude_deg");
                    double longitude = rs.getDouble("ville_longitude_deg");
                    ville = new Ville(nom, latitude, longitude);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (ville == null) {
            System.out.println("Aucune ville correspondante trouvée : " + name);
        }

        return ville;
    }

    @Override
    public String toString() {
        return "Ville{" +
                "nom='" + nom + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ville city = (Ville) obj;
        return Double.compare(city.latitude, latitude) == 0 &&
               Double.compare(city.longitude, longitude) == 0 &&
               nom.equalsIgnoreCase(city.nom);
    }

    @Override
    public int hashCode() {
        return nom.toLowerCase().hashCode();
    }
}
