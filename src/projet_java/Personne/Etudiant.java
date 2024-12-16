package projet_java.Personne;

import projet_java.BDConnect;
import projet_java.Geographie.Ville;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Etudiant extends Personne {
    private String sujetDeThese;
    private Discipline discipline;
    private int anneeDeThese;
    private Titulaire encadrant;

    public Etudiant(String nom, String prenom, int age, Ville ville, String sujetDeThese, Discipline discipline, int anneeDeThese, Titulaire encadrant) {
        super(nom, prenom, age, ville);
        this.sujetDeThese = sujetDeThese;
        this.discipline = discipline;
        this.anneeDeThese = anneeDeThese;
        this.encadrant = encadrant;
    }

    public String getSujetDeThese() {
        return sujetDeThese;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public int getAnneeDeThese() {
        return anneeDeThese;
    }

    public Titulaire getEncadrant() {
        return encadrant;
    }

    public static void insertEtudiant(int personneId, String sujetDeThese, int disciplineId, int anneeDeThese, int encadrantId) {
        // 插入数据的 SQL 语句
        String insertSQL = "INSERT INTO Etudiant (ID, sujetDeThese, discipline, anneeDeThese, encadrant) VALUES (?, ?, ?, ?, ?)";

        // 使用 BDConnect 获取连接并插入数据
        try (Connection conn = BDConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            // 设置占位符参数
            pstmt.setInt(1, personneId); // 使用 `Personne` 的 ID 作为外键
            pstmt.setString(2, sujetDeThese);
            pstmt.setInt(3, disciplineId); // 使用 Discipline 表的 ID
            pstmt.setInt(4, anneeDeThese);
            pstmt.setInt(5, encadrantId); // 使用 Titulaire 表的 ID

            // 执行插入
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Etudiant 数据插入成功！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 按学科查询学生列表
    public static void getEtudiantsByDiscipline(int disciplineId) {
        String querySQL = """
                SELECT e.ID, p.nom, p.prenom, e.sujetDeThese 
                FROM Etudiant e
                JOIN Personne p ON e.ID = p.ID
                WHERE e.discipline = ?
                """;

        try (Connection conn = BDConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {

            pstmt.setInt(1, disciplineId); // 设置学科 ID 参数
            ResultSet rs = pstmt.executeQuery();

            System.out.println("按学科查询的学生列表：");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String sujetDeThese = rs.getString("sujetDeThese");

                System.out.printf("ID: %d, 姓名: %s %s, 论文主题: %s%n", id, nom, prenom, sujetDeThese);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // 按导师查询学生列表
    public static void getEtudiantsByEncadrant(int encadrantId) {
        String querySQL = """
                SELECT e.ID, p.nom, p.prenom, e.sujetDeThese 
                FROM Etudiant e
                JOIN Personne p ON e.ID = p.ID
                WHERE e.encadrant = ?
                """;

        try (Connection conn = BDConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {

            pstmt.setInt(1, encadrantId); // 设置导师 ID 参数
            ResultSet rs = pstmt.executeQuery();

            System.out.println("按导师查询的学生列表：");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String sujetDeThese = rs.getString("sujetDeThese");

                System.out.printf("ID: %d, 姓名: %s %s, 论文主题: %s%n", id, nom, prenom, sujetDeThese);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }   
    }
    
    @Override
    public String toString() {
        return "Etudiant{" +
                "nom='" + getNom() + '\'' +
                ", sujetDeThese='" + sujetDeThese + '\'' +
                ", discipline=" + discipline +
                ", encadrant=" + encadrant.getNom() +
                '}';
    }
}
