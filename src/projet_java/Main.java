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
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            // 显示菜单
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. 插入 Titulaire 数据");
            System.out.println("2. 插入 Etudiant 数据");
            System.out.println("3. 城市信息菜单");
            System.out.println("4. 算法菜单");
            System.out.println("5. 退出");
            System.out.print("请选择一个选项 (1-5): ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    try {
                        insertTitulaire();
                    } catch (Exception e) {
                        System.out.println("触发器限制：导师不能同时教授超过两门课程。");
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    insertEtudiant();
                    break;
                case 3:
                    cityMenu();
                    break;
                case 4:
                    algorithmMenu();
                    break;
                case 5:
                    System.out.println("程序退出，再见！");
                    exit = true;
                    break;
                default:
                    System.out.println("无效选项，请重新输入。");
            }
        }

        scanner.close();
    }

    private static void insertTitulaire() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入 Titulaire 的 ID: ");
        int id = scanner.nextInt();
        System.out.print("请输入 Discipline ID: ");
        int disciplineId = scanner.nextInt();
        System.out.print("请输入 Bureau Number: ");
        int bureau = scanner.nextInt();

        Titulaire.insertTitulaire(id, disciplineId, bureau);
        System.out.println("Titulaire 数据插入成功！");
    }

    private static void insertEtudiant() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入 Etudiant 的 ID: ");
        int id = scanner.nextInt();
        System.out.print("请输入 Sujet De These: ");
        String sujet = scanner.next();
        System.out.print("请输入 Discipline ID: ");
        int disciplineId = scanner.nextInt();
        System.out.print("请输入 Encadrant ID: ");
        int encadrantId = scanner.nextInt();

        Etudiant.insertEtudiant(id, sujet, disciplineId, encadrantId, 1);
        System.out.println("Etudiant 数据插入成功！");
    }

    private static void cityMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean backToMain = false;

        while (!backToMain) {
            System.out.println("\n=== 城市信息菜单 ===");
            System.out.println("1. 显示所有城市");
            System.out.println("2. 显示前 5 个城市");
            System.out.println("3. 遍历所有区域");
            System.out.println("4. 根据城市名查找所属区域");
            System.out.println("5. 构建城市距离矩阵");
            System.out.println("6. 用户选择起点城市");
            System.out.println("7. 返回主菜单");
            System.out.print("请选择一个选项 (1-7): ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Ville.loadAllVilles().forEach(System.out::println);
                    break;
                case 2:
                    List<Ville> villes = Ville.loadAllVilles();
                    for (int i = 0; i < Math.min(5, villes.size()); i++) {
                        System.out.println(villes.get(i));
                    }
                    break;
                case 3:
                    for (Region region : Region.values()) {
                        System.out.println(region);
                    }
                    break;
                case 4:
                    System.out.print("请输入城市名称: ");
                    String cityName = scanner.next();
                    Region region = Region.findRegionByVille(cityName);
                    if (region != null) {
                        System.out.println("城市 " + cityName + " 所属区域：" + region.getNom());
                    } else {
                        System.out.println("未找到所属区域");
                    }
                    break;
                case 5:
                    villes = Ville.loadAllVilles();
                    Map<String, Map<String, Double>> distanceMatrix = City.buildDistanceMatrix(villes);
                    for (String city1 : distanceMatrix.keySet()) {
                        System.out.println("从城市：" + city1);
                        for (String city2 : distanceMatrix.get(city1).keySet()) {
                            System.out.printf("    到城市 %-20s 距离: %.2f%n", city2, distanceMatrix.get(city1).get(city2));
                        }
                        break;
                    }
                    break;
                case 6:
                    villes = Ville.loadAllVilles();
                    List<City> cities = new ArrayList<>();
                    for (Ville ville : villes) {
                        cities.add(new City(ville.getNom(), ville.getLatitude(), ville.getLongitude()));
                    }

                    System.out.println("可用城市列表：");
                    for (int i = 0; i < cities.size(); i++) {
                        System.out.println((i + 1) + ". " + cities.get(i).getName());
                    }

                    System.out.print("请选择起点城市的编号：");
                    int startCityIndex = scanner.nextInt() - 1;

                    if (startCityIndex >= 0 && startCityIndex < cities.size()) {
                        City startCity = cities.get(startCityIndex);
                        Solution solution = new Solution(cities, startCity);
                        System.out.println(solution);
                    } else {
                        System.out.println("无效的编号，请重新运行程序！");
                    }
                    break;
                case 7:
                    backToMain = true;
                    break;
                default:
                    System.out.println("无效选项，请重新输入。");
            }
        }
    }

    private static void algorithmMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean backToMain = false;

        while (!backToMain) {
            System.out.println("\n=== 算法菜单 ===");
            System.out.println("1. 使用自定义查询条件并运行遗传算法");
            System.out.println("2. 返回主菜单");
            System.out.print("请选择一个选项 (1-2): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 清除换行符

            switch (choice) {
                case 1:
                    List<City> villes = new ArrayList<>();
                    boolean continuer = true;

                    while (continuer) {
                        System.out.println("\n请输入搜索条件 (按回车键跳过)：");
                        System.out.print("Nom : ");
                        String nom = scanner.nextLine().trim();
                        if (nom.isEmpty()) nom = null;

                        System.out.print("Prénom : ");
                        String prenom = scanner.nextLine().trim();
                        if (prenom.isEmpty()) prenom = null;

                        System.out.print("Âge : ");
                        String ageInput = scanner.nextLine().trim();
                        Integer age = ageInput.isEmpty() ? null : Integer.parseInt(ageInput);

                        System.out.print("Ville : ");
                        String nomVille = scanner.nextLine().trim();
                        if (nomVille.isEmpty()) nomVille = null;

                        System.out.print("Discipline (ex: INFORMATIQUE) : ");
                        String discipline = scanner.nextLine().trim().toUpperCase();
                        if (discipline.isEmpty()) discipline = null;

                        System.out.print("Année de thèse : ");
                        String anneeDeTheseInput = scanner.nextLine().trim();
                        Integer anneeDeThese = anneeDeTheseInput.isEmpty() ? null : Integer.parseInt(anneeDeTheseInput);

                        System.out.print("Nom de l'encadrant : ");
                        String encadrant = scanner.nextLine().trim();
                        if (encadrant.isEmpty()) encadrant = null;

                        // 查询符合条件的人员数据
                        List<Personne> personnes = Personne.searchByDynamicConditions(
                            nom, prenom, age, nomVille, discipline, anneeDeThese, encadrant
                        );

                        if (personnes.isEmpty()) {
                            System.out.println("Aucune donnée correspondante trouvée, veuillez réessayer.");
                        } else {
                            for (Personne personne : personnes) {
                                Ville ville = personne.getVille();
                                if (ville != null) {
                                    City city = new City(ville.getNom(), ville.getLatitude(), ville.getLongitude());
                                    if (!villes.contains(city)) {
                                        villes.add(city);
                                    }
                                }
                            }
                            System.out.println("Liste des villes collectées jusqu'à présent : " + villes);
                        }

                        System.out.print("Voulez-vous ajouter d'autres critères de recherche ? (o/n) : ");
                        String choix = scanner.nextLine().trim().toLowerCase();
                        if (!choix.equals("o")) {
                            continuer = false;
                        }
                    }

                    if (villes.size() < 2) {
                        System.out.println("Le nombre de villes est insuffisant pour calculer le chemin optimal ! Il faut au moins deux villes.");
                    } else {
                        System.out.println("Calcul du chemin optimal en cours...");
                        System.out.print("请输入保存目录路径：");
                        String saveDirectory = scanner.nextLine().trim();
                        System.out.print("请输入结果文件名：");
                        String fileName = scanner.nextLine().trim();

                        GeneticAlgorithm ga = new GeneticAlgorithm();
                        Solution bestSolution = ga.solveTSP(villes, 10, 0.1, 0.2, 50, saveDirectory, fileName);
                        System.out.println("最优路径计算完成：");
                        System.out.println(bestSolution);
                    }
                    break;
                case 2:
                    backToMain = true;
                    break;
                default:
                    System.out.println("无效选项，请重新输入。");
            }
        }
    }
}
