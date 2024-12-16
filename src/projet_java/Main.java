//Main.java
package projet_java;

import projet_java.Personne.*;
import projet_java.Geographie.*;
import projet_java.Algo.*;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            // 创建表和触发器
            //CreatTable.Creat();

            // 插入基础数据
            //Discipline.insertAllDisciplines();
            //Personne.insertPersonne("Dupont", "Jean", 45, "Paris");

            // 插入 Titulaire 数据
	        //Titulaire.insertTitulaire(1, 1, 101);
	        //Titulaire.insertTitulaire(1, 2, 101);
	        Titulaire.insertTitulaire(1, 3, 101);
            
	        //Etudiant.insertEtudiant(1, "SujetDeJava", 1, 2, 1);
            
        	/*
        	List<Ville> villes = Ville.loadAllVilles();

            // 显示前 5 个城市信息
            for (int i = 0; i < Math.min(5, villes.size()); i++) {
                System.out.println(villes.get(i));
            }
            // 遍历所有区域
            for (Region region : Region.values()) {
                System.out.println(region);
            }

            // 根据城市名查找所属区域
            Region region = Region.findRegionByVille("Paris");
            if (region != null) {
                System.out.println("城市 Paris 所属区域：" + region.getNom());
            } else {
                System.out.println("未找到所属区域");
            }
            // 从数据库中加载城市列表
            List<Ville> villes = Ville.loadAllVilles();

            // 构建城市之间的距离矩阵
            Map<String, Map<String, Double>> distanceMatrix = City.buildDistanceMatrix(villes);

            // 输出部分距离矩阵，验证数据
            for (String city1 : distanceMatrix.keySet()) {
                System.out.println("从城市：" + city1);
                for (String city2 : distanceMatrix.get(city1).keySet()) {
                    System.out.printf("    到城市 %-20s 距离: %.2f%n", city2, distanceMatrix.get(city1).get(city2));
                }
                break; // 只打印第一个城市的距离
            }
            // 从数据库加载城市数据
            List<Ville> villes = Ville.loadAllVilles();

            // 将 Ville 转换为 City 对象
            List<City> cities = new ArrayList<>();
            for (Ville ville : villes) {
                cities.add(new City(ville.getNom(), ville.getLatitude(), ville.getLongitude()));
            }

            // 用户选择起点城市
            System.out.println("可用城市列表：");
            for (int i = 0; i < cities.size(); i++) {
                System.out.println((i + 1) + ". " + cities.get(i).getName());
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("请选择起点城市的编号：");
            int startCityIndex = scanner.nextInt() - 1;

            if (startCityIndex >= 0 && startCityIndex < cities.size()) {
                City startCity = cities.get(startCityIndex);
                Solution solution = new Solution(cities, startCity);
                System.out.println(solution);
            } else {
                System.out.println("无效的编号，请重新运行程序！");
            }*/
        
        } catch (Exception e) {
            System.out.println("触发器抛出异常：");
            e.printStackTrace();
        }
    }
}

