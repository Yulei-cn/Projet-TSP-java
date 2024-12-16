package projet_java.Geographie;

import projet_java.BDConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Ville {
    private String nom;      // 城市名称
    private double latitude; // 纬度
    private double longitude; // 经度

    // 构造函数
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

    // 从数据库加载所有城市信息
    public static List<Ville> loadAllVilles() {
        List<Ville> villes = new ArrayList<>();
        String sql = "SELECT ville_nom, ville_latitude_deg, ville_longitude_deg FROM villes_france_free";

        try (Connection conn = BDConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // 遍历结果集
            while (rs.next()) {
                String nom = rs.getString("ville_nom");
                double latitude = rs.getDouble("ville_latitude_deg");
                double longitude = rs.getDouble("ville_longitude_deg");

                Ville ville = new Ville(nom, latitude, longitude);
                villes.add(ville);
            }

            System.out.println("成功加载城市列表，共 " + villes.size() + " 个城市。");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return villes;
    }

    @Override
    public String toString() {
        return "Ville{" +
                "nom='" + nom + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
