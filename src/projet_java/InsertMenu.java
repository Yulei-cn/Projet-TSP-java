package projet_java;

import java.sql.*;
import java.util.Scanner;

import projet_java.Personne.Personne;

public class InsertMenu {

    public static void executeInsertMenu(Scanner scanner) {
        boolean running = true;

        try (Connection conn = BDConnect.getConnection()) {
            while (running) {
                System.out.println("请选择角色：");
                System.out.println("1-学生 (Etudiant)");
                System.out.println("2-导师 (Titulaire)");
                System.out.println("3-研究员 (Chercheur)");
                System.out.println("4-MCF");
                System.out.println("5-退出程序");
                System.out.print("请选择一个选项 (1-5): ");
                int roleChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (roleChoice == 5) {
                    running = false;
                    System.out.println("程序已退出。");
                    break;
                }

                // 通用信息插入到 Personne 表
                int personId = insertPersonne(scanner, conn);

                // 根据角色插入对应表
                switch (roleChoice) {
                    case 1: // 学生
                        if (insertEtudiant(scanner, conn, personId)!=1){
                            Personne.SupprimePersonne(personId, null, null, null, null);
                        };
                        break;
                    case 2: // 导师
                        insertTitulaire(scanner, conn, personId);
                        break;
                    case 3: // 研究员
                        insertChercheur(scanner, conn, personId);
                        break;
                    case 4: // MCF
                        insertMCF(scanner, conn, personId);
                        break;
                    default:
                        System.out.println("无效的选择！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int insertPersonne(Scanner scanner, Connection conn) throws SQLException {
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
        try (PreparedStatement stmt = conn.prepareStatement(insertPersonneSQL)) {
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setInt(3, age);
            stmt.setString(4, ville);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int personId = rs.getInt("ID");
                    System.out.println("成功插入 Personne，ID 为：" + personId);
                    return personId;
                } else {
                    throw new SQLException("Personne 插入失败！");
                }
            }
        }
    }

    private static int insertEtudiant(Scanner scanner, Connection conn, int personId) {
        try {
            // 开始事务
            conn.setAutoCommit(false);

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

            // 检查导师 ID 是否存在
            String checkEncadrantSQL = "SELECT COUNT(*) FROM Titulaire WHERE ID = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkEncadrantSQL)) {
                checkStmt.setInt(1, encadrantId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    System.out.println("错误：导师ID不存在！");
                    conn.rollback(); // 回滚事务
                    return 0;
                }
            }

            // 插入学生数据
            String insertEtudiantSQL = "INSERT INTO Etudiant (ID, sujetDeThese, discipline, anneeDeThese, encadrant) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertEtudiantSQL)) {
                stmt.setInt(1, personId);
                stmt.setString(2, sujetDeThese);
                stmt.setInt(3, disciplineId);
                stmt.setInt(4, anneeDeThese);
                stmt.setInt(5, encadrantId);
                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    conn.commit(); // 提交事务
                    System.out.println("Etudiant 数据插入成功！");
                    return 1;
                } else {
                    conn.rollback(); // 回滚事务
                    System.out.println("Etudiant 数据插入失败！");
                    return 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback(); // 如果发生异常，回滚事务
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return 0;
        } finally {
            try {
                conn.setAutoCommit(true); // 恢复自动提交模式
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private static void insertTitulaire(Scanner scanner, Connection conn, int personId) throws SQLException {
        System.out.println("请输入办公室编号:");
        int numBureau = scanner.nextInt();

        String insertTitulaireSQL = "INSERT INTO Titulaire (ID, numBureau) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertTitulaireSQL)) {
            stmt.setInt(1, personId);
            stmt.setInt(2, numBureau);
            stmt.executeUpdate();
            System.out.println("成功插入 Titulaire！");
        }
    }

    private static void insertChercheur(Scanner scanner, Connection conn, int personId) throws SQLException {
        System.out.println("请输入学生ID:");
        int etudiantID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("请输入办公室编号:");
        int numBureau = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        String insertTitulaireSQL = "INSERT INTO Titulaire (ID, numBureau) VALUES (?, ?)";
        try (PreparedStatement stmtTitulaire = conn.prepareStatement(insertTitulaireSQL)) {
            stmtTitulaire.setInt(1, personId);
            stmtTitulaire.setInt(2, numBureau);

            int rowsInsertedTitulaire = stmtTitulaire.executeUpdate();
            if (rowsInsertedTitulaire > 0) {
                System.out.println("成功插入 Titulaire（包含办公室编号）！");
            } else {
                System.out.println("Titulaire 数据插入失败！");
            }
        }
        // 插入 Chercheur 表
        String insertChercheurSQL = "INSERT INTO Chercheur (ID, etudiant) VALUES (?, ?)";
        try (PreparedStatement stmtChercheur = conn.prepareStatement(insertChercheurSQL)) {
            stmtChercheur.setInt(1, personId);
            stmtChercheur.setInt(2, etudiantID);

            int rowsInserted = stmtChercheur.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("成功插入 Chercheur！");
            } else {
                System.out.println("Chercheur 数据插入失败！");
            }
        }

        // 同时插入 Titulaire 表，确保 Chercheur 记录正确的办公室号

    }

    
    private static void insertMCF(Scanner scanner, Connection conn, int personId) throws SQLException {
        System.out.println("请输入负责的学生 ID:");
        int etudiantId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("请输入办公室编号:");
        int numBureau = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        String insertTitulaireSQL = "INSERT INTO Titulaire (ID, numBureau) VALUES (?, ?)";
        try (PreparedStatement stmtTitulaire = conn.prepareStatement(insertTitulaireSQL)) {
            stmtTitulaire.setInt(1, personId);
            stmtTitulaire.setInt(2, numBureau);
            stmtTitulaire.executeUpdate();
            System.out.println("成功插入 Titulaire（包含办公室编号）！");
        // 插入 MCF 表
        String insertMCFSQL = "INSERT INTO MCF (ID, etudiant) VALUES (?, ?)";
        try (PreparedStatement stmtMCF = conn.prepareStatement(insertMCFSQL)) {
            stmtMCF.setInt(1, personId);
            stmtMCF.setInt(2, etudiantId);
            stmtMCF.executeUpdate();
            System.out.println("成功插入 MCF！");
        }

        // 同时插入 Titulaire 表，确保 MCF 记录正确的办公室号

        }
    }

}
