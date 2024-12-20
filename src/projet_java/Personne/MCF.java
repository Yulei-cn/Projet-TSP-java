package projet_java.Personne;

import java.util.Set;
import projet_java.BDConnect;
import projet_java.Geographie.Ville;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class MCF extends Titulaire {
    private Etudiant etudiant;

    public MCF(String nom, String prenom, int age, Ville ville, Set<Discipline> disciplines, int numBureau, Etudiant etudiant) {
        super(nom, prenom, age, ville, disciplines, numBureau);
        this.etudiant = etudiant;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

   public static void SupprimeMCF(Integer ID,  Integer etudiant) {
        // 插入数据的 SQL 语句
        StringBuilder delete = new StringBuilder( "DELETE FROM MCF");

        delete.append("WHERE 1=1 ");
		if (ID != null) delete.append("AND ID =  ? ");
		if (etudiant != null) delete.append("AND etudiant =  ? ");
		
        // 使用 BDConnect 获取连接并插入数据
        try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(delete.toString())) {

            // 设置占位符参数
        int paramIndex = 1;
		if (ID != null) pstmt.setInt(paramIndex++, ID);
		if (etudiant != null) pstmt.setInt(paramIndex++, etudiant);



            // 执行插入
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Etudiant 数据删除成功！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
   }

    public static void UpdateMCF(Integer etudiant,Integer conID, Integer conetudiant) {
    // 构建 UPDATE 语句
    StringBuilder update = new StringBuilder("UPDATE MCF SET ");
    boolean hasFields = false;

    // 拼接 SET 子句
    if (etudiant != null) {
        update.append("etudiant = ?, ");
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
   if (conetudiant != null) {
        update.append("AND numbureau = ? ");
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
        if (etudiant != null) pstmt.setInt(paramIndex++, etudiant);

        // 设置 WHERE 子句的参数
        if (conID != null) pstmt.setInt(paramIndex++, conID);
        if (conetudiant != null) pstmt.setInt(paramIndex++, conetudiant);

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
        return "MCF{" +
                "nom='" + getNom() + '\'' +
                ", etudiant=" + etudiant.getNom() +
                '}';
    }
}











