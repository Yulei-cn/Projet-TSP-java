//Titulaire.java
package projet_java.Personne;

import projet_java.BDConnect;
import projet_java.Geographie.Ville;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class Titulaire extends Personne {
    private Set<Discipline> disciplines;
    private int numBureau;

    public Titulaire(String nom, String prenom, int age, Ville ville, Set<Discipline> disciplines, int numBureau) {
        super(nom, prenom, age, ville);
        this.disciplines = disciplines;
        this.numBureau = numBureau;
    }

    public Set<Discipline> getDisciplines() {
        return disciplines;
    }

    public int getNumBureau() {
        return numBureau;
    }

    public static boolean checkTitulaireExists(int titulaireId) {
        String checkSQL = "SELECT COUNT(*) FROM Titulaire WHERE ID = ?";
        boolean exists = false;

        try (Connection conn = BDConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(checkSQL)) {

            // 设置参数
            pstmt.setInt(1, titulaireId);

            // 执行查询
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) { // 移动到结果行
                    int count = rs.getInt(1); // 获取结果数量
                    exists = count > 0; // 如果数量大于 0，表示存在
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists; // 返回是否存在
    }

    public static void insertTitulaire(int personneId, int disciplineId, int numBureau) {
        String insertTitulaireSQL = "INSERT INTO Titulaire (ID, numBureau) VALUES (?, ?)";
        String insertTitulaireDisciplineSQL = "INSERT INTO Titulaire_Discipline (ID, discipline_ID) VALUES (?, ?)";
        if (checkTitulaireExists(personneId)) {
            try (Connection conn = BDConnect.getConnection();
            PreparedStatement pstmt2 = conn.prepareStatement(insertTitulaireDisciplineSQL)) {
            pstmt2.setInt(1, personneId);
            pstmt2.setInt(2, disciplineId);
            int rowsInserted2 = pstmt2.executeUpdate();
            if (rowsInserted2>0) {
                System.out.println("Titulaire 数据插入成功！");
                }
            }
            catch (SQLException e) {
            e.printStackTrace();
            }
        }
        else{
        try (Connection conn = BDConnect.getConnection();
            PreparedStatement pstmt1 = conn.prepareStatement(insertTitulaireSQL);
            PreparedStatement pstmt2 = conn.prepareStatement(insertTitulaireDisciplineSQL)) {
            pstmt1.setInt(1, personneId);
            pstmt1.setInt(2, numBureau);
            pstmt2.setInt(1, personneId);
            pstmt2.setInt(2, disciplineId);
            int rowsInserted1 = pstmt1.executeUpdate();
            int rowsInserted2 = pstmt2.executeUpdate();
            if (rowsInserted1 > 0 && rowsInserted2>0) {
                System.out.println("Titulaire 数据插入成功！");
                }
            } 
        catch (SQLException e) {
            e.printStackTrace();

            }
        }
    }


/*
    
    // 按学科查询导师列表
    public static void getTitulairesByDisciplie(int disciplineId) {
        String querySQL = """
                SELECT t.ID, p.nom, p.prenom, t.numBureau
                FROM Titulaire t
                JOIN Personne p ON t.ID = p.ID
                JOIN Titulaire_Discipline d ON t.ID=d.ID
                WHERE d.discipline = ?;
                """;

        try (Connection conn = BDConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {

            pstmt.setInt(1, disciplineId); // 设置学科 ID 参数
            ResultSet rs = pstmt.executeQuery();

            System.out.println("按学科查询的导师列表：");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int numBureau = rs.getInt("numBureau");

                System.out.printf("ID: %d, 姓名: %s %s, 办公室编号: %d%n", id, nom, prenom, numBureau);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
*/
        public static List<String> getTitulairesByDisciplie(int disciplineId) {
        String querySQL = """
                SELECT p.ville
                FROM Titulaire t
                JOIN Personne p ON t.ID = p.ID
                JOIN Titulaire_Discipline d ON t.ID=d.ID
                WHERE d.discipline = ?;
                """;
        List<String> villes = new ArrayList<>();
        try (Connection conn = BDConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {

            pstmt.setInt(1, disciplineId); // 设置学科 ID 参数
            ResultSet rs = pstmt.executeQuery();

            System.out.println("按学科查询的导师列表：");
            while (rs.next()) {
                String ville = rs.getString("ville");
                villes.add(ville);

                System.out.printf("Ville: %s", ville);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return villes;
    }

     public static List<String> getTitulairesByBureau(int disciplineId) {
        String querySQL = """
                SELECT p.ville
                FROM Titulaire t
                JOIN Personne p ON t.ID = p.ID
                JOIN Titulaire_Discipline d ON t.ID=d.ID
                WHERE t.numBureau = ?;
                """;
        List<String> villes = new ArrayList<>();
        try (Connection conn = BDConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {

            pstmt.setInt(1, disciplineId); // 设置学科 ID 参数
            ResultSet rs = pstmt.executeQuery();

            System.out.println("按学科查询的导师列表：");
            while (rs.next()) {
                String ville = rs.getString("ville");
                villes.add(ville);

                System.out.printf("Ville: %s", ville);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return villes;
    }

    




    @Override
    public String toString() {
        return "Titulaire{" +
                "nom='" + getNom() + '\'' +
                ", disciplines=" + disciplines +
                ", numBureau=" + numBureau +
                '}';
    }
}
