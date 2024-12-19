package projet_java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import projet_java.Algo.*;
import projet_java.Geographie.*;
import projet_java.Personne.*;

public class Main {
    public static void main(String[] args) {
       /* boolean running = true;
        Scanner scanner = new Scanner(System.in);

        while (running) {
            System.out.println("\n=== 主菜单 ===");
            System.out.println("1. 数据库和角色管理");
            System.out.println("2. 留空选项");
            System.out.println("3. 遗传算法菜单");
            System.out.println("4. 其他算法和方法");
            System.out.println("5. 退出程序");
            System.out.print("请选择一个选项 (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 清除换行符

            switch (choice) {
                case 1:
                    databaseAndRoleManagement(scanner);
                    break;
                case 2:
                    System.out.println("留空选项，待扩展...");
                    break;
                case 3:
                    geneticAlgorithmMenu(scanner);
                    break;
                case 4:
                    otherAlgorithmsMenu(scanner);
                    break;
                case 5:
                    System.out.println("程序退出，再见！");
                    running = false;
                    break;
                default:
                    System.out.println("无效选项，请重新输入。");
            }
        }
        scanner.close();
    }

    private static void databaseAndRoleManagement(Scanner scanner) {
        boolean running = true;
        CreatTable.Creat();
        System.out.println("数据库表和触发器创建完成！");
        Discipline.insertAllDisciplines();

        try (Connection conn = BDConnect.getConnection()) {
            while (running) {
                System.out.println("请选择角色：");
                System.out.println("1-Etudiant");
                System.out.println("2-Titulaire");
                System.out.println("3-Chercheur");
                System.out.println("4-MCF");
                System.out.println("5-返回主菜单");

                int roleChoice = scanner.nextInt();
                scanner.nextLine(); // 清除换行符

                if (roleChoice == 5) {
                    running = false;
                    break;
                }

                System.out.println("请输入名字:");
                String nom = scanner.nextLine();

                System.out.println("请输入姓氏:");
                String prenom = scanner.nextLine();

                System.out.println("请输入年龄:");
                int age = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                System.out.println("请输入城市:");
                String ville = scanner.nextLine();

                String insertPersonneSQL = "INSERT INTO Personne (nom, prenom, age, ville) VALUES (?, ?, ?, ?) RETURNING ID";
                int personId;
                try (PreparedStatement stmt = conn.prepareStatement(insertPersonneSQL)) {
                    stmt.setString(1, nom);
                    stmt.setString(2, prenom);
                    stmt.setInt(3, age);
                    stmt.setString(4, ville);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            personId = rs.getInt("ID");
                            System.out.println("成功插入 Personne，ID 为：" + personId);
                        } else {
                            throw new Exception("Personne 插入失败！");
                        }
                    }
                }

                switch (roleChoice) {
                    case 1: // 学生
                        System.out.println("请输入论文题目:");
                        String sujetDeThese = scanner.nextLine();

                        System.out.println("请输入学科编号:");
                        int disciplineId = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("请输入论文年份:");
                        int anneeDeThese = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("请输入导师ID:");
                        int encadrantId = scanner.nextInt();

                        String insertEtudiantSQL = "INSERT INTO Etudiant (ID, sujetDeThese, discipline, anneeDeThese, encadrant) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement stmt = conn.prepareStatement(insertEtudiantSQL)) {
                            stmt.setInt(1, personId);
                            stmt.setString(2, sujetDeThese);
                            stmt.setInt(3, disciplineId);
                            stmt.setInt(4, anneeDeThese);
                            stmt.setInt(5, encadrantId);
                            stmt.executeUpdate();
                            System.out.println("成功插入 Etudiant！");
                        }
                        break;

                    case 2: // 导师
                        System.out.println("请输入办公室编号:");
                        int numBureau = scanner.nextInt();

                        String insertTitulaireSQL = "INSERT INTO Titulaire (ID, numBureau) VALUES (?, ?)";
                        try (PreparedStatement stmt = conn.prepareStatement(insertTitulaireSQL)) {
                            stmt.setInt(1, personId);
                            stmt.setInt(2, numBureau);
                            stmt.executeUpdate();
                            System.out.println("成功插入 Titulaire！");
                        }
                        break;

                    case 3: // 研究员
                        System.out.println("研究员逻辑需要扩展...");
                        break;

                    case 4: // MCF
                        System.out.println("请输入负责的学生 ID:");
                        int etudiantId = scanner.nextInt();

                        String insertMCFSQL = "INSERT INTO MCF (ID, etudiant) VALUES (?, ?)";
                        try (PreparedStatement stmt = conn.prepareStatement(insertMCFSQL)) {
                            stmt.setInt(1, personId);
                            stmt.setInt(2, etudiantId);
                            stmt.executeUpdate();
                            System.out.println("成功插入 MCF！");
                        }
                        break;

                    default:
                        System.out.println("无效的选择！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void geneticAlgorithmMenu(Scanner scanner) {
        boolean backToMain = false;

        while (!backToMain) {
            System.out.println("\n=== 遗传算法菜单 ===");
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
                        String encadrantInput = scanner.nextLine().trim();
                        Integer encadrant = encadrantInput.isEmpty() ? null : Integer.parseInt(encadrantInput);

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

    private static void otherAlgorithmsMenu(Scanner scanner) {
        boolean backToMain = false;

        while (!backToMain) {
            System.out.println("\n=== 其他算法菜单 ===");
            System.out.println("1. 显示所有城市");
            System.out.println("2. 遍历所有区域");
            System.out.println("3. 构建城市距离矩阵");
            System.out.println("4. 用户选择起点城市");
            System.out.println("5. 返回主菜单");
            System.out.print("请选择一个选项 (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 清除换行符

            switch (choice) {
                case 1:
                    List<Ville> villes = Ville.loadAllVilles();
                    villes.forEach(System.out::println);
                    break;
                case 2:
                    for (Region region : Region.values()) {
                        System.out.println(region);
                    }
                    break;
                case 3:
                    villes = Ville.loadAllVilles();
                    Map<String, Map<String, Double>> distanceMatrix = City.buildDistanceMatrix(villes);
                    for (String city1 : distanceMatrix.keySet()) {
                        System.out.println("从城市：" + city1);
                        for (String city2 : distanceMatrix.get(city1).keySet()) {
                            System.out.printf("    到城市 %-20s 距离: %.2f%n", city2, distanceMatrix.get(city1).get(city2));
                        }
                    }
                    break;
                case 4:
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
                case 5:
                    backToMain = true;
                    break;
                default:
                    System.out.println("无效选项，请重新输入。");
            }
        }*/
        Etudiant.UpdateEtudiant(null,3,null,null,2,null,null,null,null);
    }
}

