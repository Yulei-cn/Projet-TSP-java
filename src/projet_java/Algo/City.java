package projet_java.Algo;

import projet_java.Geographie.Ville;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class City {
    private String name;        // 城市名称
    private double latitude;    // 纬度
    private double longitude;   // 经度

    // 构造函数
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

    // 计算两个城市之间的欧几里得距离
    public static double calculateDistance(City c1, City c2) {
        double xDiff = c1.getLongitude() - c2.getLongitude();
        double yDiff = c1.getLatitude() - c2.getLatitude();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    // 构建距离矩阵（存储城市之间的距离）
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
        System.out.println("距离矩阵已成功构建！");
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
