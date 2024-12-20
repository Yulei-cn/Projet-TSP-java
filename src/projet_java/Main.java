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
        boolean running = true;
        Scanner scanner = new Scanner(System.in);

        while (running) {
            System.out.println("\n=== 主菜单 ===");
            System.out.println("1. 新建数据和构建表");
            System.out.println("2. 数据库和角色管理");
            System.out.println("3. 遗传算法菜单");
            System.out.println("4. 其他算法和方法");
            System.out.println("5. 退出程序");
            System.out.print("请选择一个选项 (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 清除换行符

            switch (choice) {
                case 1:
                    createDataAndTables();
                    break;
                case 2:
                    databaseAndRoleManagement(scanner);
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

    private static void createDataAndTables() {
        CreatTable.Creat();
        System.out.println("数据库表和触发器创建完成！");
    }

    private static void databaseAndRoleManagement(Scanner scanner) {
        boolean running = true;

        try (Connection conn = BDConnect.getConnection()) {
            while (running) {
                System.out.println("请选择数据库操作：");
                System.out.println("1. 更新学生数据");
                System.out.println("2. 更新人员数据");
                System.out.println("3. 更新研究员数据");
                System.out.println("4. 更新MCF数据");
                System.out.println("5. 更新导师数据");
                System.out.println("6. 更新导师学科数据");
                System.out.println("7. 返回主菜单");
                System.out.print("请选择一个选项 (1-7): ");

                int roleChoice = scanner.nextInt();
                scanner.nextLine(); // 清除换行符

                if (roleChoice == 7) {
                    running = false;
                    break;
                }

                switch (roleChoice) {
                    case 1:
                        updateEtudiant(scanner);
                        break;
                    case 2:
                    	updatePersonne(scanner);
                        break;
                    case 3:
                        updateChercheur(scanner);
                        break;
                    case 4:
                        updateMCF(scanner);
                        break;
                    case 5:
                        updateTitulaire(scanner);
                        break;
                    case 6:
                        updateTitulaireDiscipline(scanner);
                        break;
                    default:
                        System.out.println("无效的选择！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T parseNullableInput(String input, Class<T> type) {
        if (input.isEmpty()) {
            return null;
        }

        try {
            if (type == Integer.class) {
                return (T) Integer.valueOf(input);
            } else if (type == String.class) {
                return (T) input;
            }
        } catch (Exception e) {
            System.out.println("无效的输入：" + input + "，将被忽略。");
        }

        return null;
    }

    private static void updateEtudiant(Scanner scanner) {
        System.out.println("请输入更新条件和新值：");

        System.out.print("现有的学生ID (可选): ");
        Integer conpersonneId = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("现有的论文题目 (可选): ");
        String consujetDeThese = parseNullableInput(scanner.nextLine().trim(), String.class);

        System.out.print("现有的学科编号 (可选): ");
        Integer condisciplineId = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("现有的论文年份 (可选): ");
        Integer conanneeDeThese = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("现有的导师ID (可选): ");
        Integer conencadrantId = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("新论文题目 (可选): ");
        String sujetDeThese = parseNullableInput(scanner.nextLine().trim(), String.class);

        System.out.print("新学科编号 (可选): ");
        Integer disciplineId = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("新论文年份 (可选): ");
        Integer anneeDeThese = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("新导师ID (可选): ");
        Integer encadrantId = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        Etudiant.UpdateEtudiant(sujetDeThese, disciplineId, anneeDeThese, encadrantId, conpersonneId, consujetDeThese, condisciplineId, conanneeDeThese, conencadrantId);
    }

    private static void updatePersonne(Scanner scanner) {
        System.out.println("请输入更新条件和新值：");

        System.out.print("现有的人员ID (可选): ");
        Integer conID = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("现有的姓名 (可选): ");
        String connom = parseNullableInput(scanner.nextLine().trim(), String.class);

        System.out.print("现有的姓氏 (可选): ");
        String conprenom = parseNullableInput(scanner.nextLine().trim(), String.class);

        System.out.print("现有的年龄 (可选): ");
        Integer conage = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("现有的城市 (可选): ");
        String conville = parseNullableInput(scanner.nextLine().trim(), String.class);

        System.out.print("新姓名 (可选): ");
        String nom = parseNullableInput(scanner.nextLine().trim(), String.class);

        System.out.print("新姓氏 (可选): ");
        String prenom = parseNullableInput(scanner.nextLine().trim(), String.class);

        System.out.print("新年龄 (可选): ");
        Integer age = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("新城市 (可选): ");
        String ville = parseNullableInput(scanner.nextLine().trim(), String.class);

        Personne.UpdatePersonne(nom, prenom, age, ville, conID, connom, conprenom, conage, conville);
    }

    private static void updateChercheur(Scanner scanner) {
        System.out.println("请输入更新条件和新值：");

        System.out.print("现有的研究员ID (可选): ");
        Integer conID = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("现有的学生ID (可选): ");
        Integer conEtudiant_ID = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("新学生ID (可选): ");
        Integer Etudiant_ID = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        Chercheur.UpdateChercheur(Etudiant_ID, conID, conEtudiant_ID);
    }
    private static void updateMCF(Scanner scanner) {
        System.out.println("请输入更新条件和新值：");

        System.out.print("现有的MCF ID (可选): ");
        Integer conID = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("现有的学生ID (可选): ");
        Integer conetudiant = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("新学生ID (可选): ");
        Integer etudiant = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        MCF.UpdateMCF(etudiant, conID, conetudiant);
    }
    private static void updateTitulaire(Scanner scanner) {
        System.out.println("请输入更新条件和新值：");

        System.out.print("现有的导师ID (可选): ");
        Integer conID = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("现有的办公室编号 (可选): ");
        Integer connumbureau = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("新办公室编号 (可选): ");
        Integer numbureau = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        Titulaire.UpdateTitulaire(numbureau, conID, connumbureau);
    }

    private static void updateTitulaireDiscipline(Scanner scanner) {
        System.out.println("请输入更新条件和新值：");

        System.out.print("现有的导师学科ID (可选): ");
        Integer conID = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("现有的学科ID (可选): ");
        Integer condiscipline_ID = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("新学科ID (可选): ");
        Integer discipline_ID = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        Titulaire.UpdateTitulaire_Discipline(discipline_ID, conID, condiscipline_ID);
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

                        List<Personne> personnes = Personne.searchByDynamicConditions(
                            nom, prenom, age, nomVille, discipline, anneeDeThese, encadrant
                        );

                        if (personnes.isEmpty()) {
                            System.out.println("Aucune donnée correspondante trouvée, veuillez réessayer。");
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
                        System.out.println("Le nombre de villes est insuffisant pour calculer le chemin optimal ! Il faut au moins deux villes。");
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
        }
    }
}
