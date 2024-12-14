package projet_java.Personne;

import projet_java.BDConnect;
import projet_java.Geographie.Ville;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

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

    public static void insertTitulaire(int personneId, int numBureau) {
        // 插入数据的 SQL 语句
        String insertSQL = "INSERT INTO Titulaire (ID, numBureau) VALUES (?, ?)";

        // 使用 BDConnect 获取连接并插入数据
        try (Connection conn = BDConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            // 设置占位符参数
            pstmt.setInt(1, personneId); // 使用 `Personne` 的 ID 作为外键
            pstmt.setInt(2, numBureau);

            // 执行插入
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Titulaire 数据插入成功！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
