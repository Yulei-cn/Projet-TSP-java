package projet_java.Personne;


import java.util.Set;
import projet_java.BDConnect;
import projet_java.Geographie.Ville;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Chercheur extends Titulaire {
    private Set<Etudiant> etudiants;

    public Chercheur(String nom, String prenom, int age, Ville ville, Set<Discipline> disciplines, int numBureau, Set<Etudiant> etudiants) {
        super(nom, prenom, age, ville, disciplines, numBureau);
        this.etudiants = etudiants;
    }


    public static void insertChercheur(int ID, int etudiantID) {
        String insertChercheurSQL = "INSERT INTO Chercheur (ID, etudiant) VALUES (?, ?)";

        try (Connection conn = BDConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertChercheurSQL)) {

            pstmt.setInt(1, ID);
            pstmt.setInt(2, etudiantID);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Chercheur 数据插入成功！");
            } else {
                System.out.println("Chercheur 数据插入失败！");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void SupprimeChercheur(Integer ID,  Integer Etudiant_ID) {
        // 插入数据的 SQL 语句
        StringBuilder delete = new StringBuilder( "DELETE FROM chercheur");

        delete.append("WHERE 1=1 ");
		if (ID != null) delete.append("AND ID =  ? ");
		if (Etudiant_ID != null) delete.append("AND Etudiant_ID =  ? ");
		
        // 使用 BDConnect 获取连接并插入数据
        try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(delete.toString())) {

            // 设置占位符参数
        int paramIndex = 1;
		if (ID != null) pstmt.setInt(paramIndex++, ID);
		if (Etudiant_ID != null) pstmt.setInt(paramIndex++, Etudiant_ID);



            // 执行插入
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Etudiant 数据删除成功！");
            }
           else{
                System.out.println("Etudiant 数据删除失败！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void UpdateChercheur(Integer Etudiant_ID,Integer conID,Integer conEtudiant_ID) {
    // 构建 UPDATE 语句
    StringBuilder update = new StringBuilder("UPDATE Chercheur SET ");
    boolean hasFields = false;

    // 拼接 SET 子句
    if (Etudiant_ID != null) {
        update.append("Etudiant_ID = ?, ");
        hasFields = true;
    }

    // 删除最后一个多余的逗号
    if (hasFields) {
        update.deleteCharAt(update.length() - 2);
    } else {
        System.out.println("没有需要更新的字段，更新操作已取消。");
        return;
    }

    // 拼接 WHERE 子句
    update.append(" WHERE 1=1 ");
    boolean hasConditions = false;

    if (conID != null) {
        update.append("AND ID = ? ");
        hasConditions = true;
    }
    if (conEtudiant_ID != null) {
        update.append("AND Etudiant_ID = ? ");
        hasConditions = true;
    }

    // 如果没有条件，取消操作，避免全表更新
    if (!hasConditions) {
        System.out.println("没有条件，无法执行更新操作。");
        return;
    }

    // 打印生成的 SQL 查询（调试用）
    System.out.println("生成的 SQL 查询: " + update);

    try (Connection conn = BDConnect.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(update.toString())) {

        int paramIndex = 1;

        // 设置 SET 子句的参数
        if (Etudiant_ID != null) pstmt.setInt(paramIndex++, Etudiant_ID);

        // 设置 WHERE 子句的参数
        if (conID != null) pstmt.setInt(paramIndex++, conID);
        if (conEtudiant_ID != null) pstmt.setInt(paramIndex++, conEtudiant_ID);

        // 执行更新语句
        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("MCF 数据更新成功！");
        } else {
            System.out.println("没有符合条件的数据被更新。");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    @Override
    public String toString() {
        return "Chercheur{" +
                "nom='" + getNom() + '\'' +
                ", etudiants=" + etudiants +
                '}';
    }
}