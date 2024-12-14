package projet_java.Personne;

import projet_java.Geographie.Ville;
import projet_java.BDConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Personne {
    private String nom;
    private String prenom;
    private int age;
    private Ville ville;
    private final int ID;
    private static int nbPersonnes = 0;

    // 构造函数
    public Personne(String nom, String prenom, int age, Ville ville) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.ville = ville;
        this.ID = ++nbPersonnes;
    }

    // 插入数据到数据库
    public static void insertPersonne(String nom, String prenom, int age, String ville) {
        // 插入数据的 SQL 语句
        String insertSQL = "INSERT INTO Personne (nom, prenom, age, ville) VALUES (?, ?, ?, ?)";

        // 使用 BDConnect 获取连接并插入数据
        try (Connection conn = BDConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            // 设置占位符参数
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setInt(3, age);
            pstmt.setString(4, ville);

            // 执行插入
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("插入成功！记录数：" + rowsInserted);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() {
        return age;
    }

    public Ville getVille() {
        return ville;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", ville=" + ville +
                ", ID=" + ID +
                '}';
    }
}
