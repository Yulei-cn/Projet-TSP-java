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

    public static void SupprimeTitulaire(Integer ID, Integer numbureau) {
        // 插入数据的 SQL 语句
        StringBuilder delete = new StringBuilder( "DELETE FROM Titulaire ");

        delete.append("WHERE 1=1 ");
		if (ID != null) delete.append("AND ID =  ? ");
		if (numbureau != null) delete.append("AND numbureau =  ? ");


        // 使用 BDConnect 获取连接并插入数据
        try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(delete.toString())) {

            // 设置占位符参数
        int paramIndex = 1;
		if (ID != null) pstmt.setInt(paramIndex++, ID);
		if (numbureau != null) pstmt.setInt(paramIndex++, numbureau);



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





    public static void SupprimeTitulaire_Discipline(Integer discipline, Integer ID) {
        // 插入数据的 SQL 语句
        StringBuilder delete = new StringBuilder( "DELETE FROM Titulaire ");

        delete.append("WHERE 1=1 ");
		if (ID != null) delete.append("AND ID =  ? ");
		if (discipline != null) delete.append("AND discipline_ID =  ? ");


        // 使用 BDConnect 获取连接并插入数据
        try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(delete.toString())) {

            // 设置占位符参数
        int paramIndex = 1;
		if (ID != null) pstmt.setInt(paramIndex++, ID);
		if (discipline != null) pstmt.setInt(paramIndex++, discipline);



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

public static void UpdateTitulaire(Integer numbureau,Integer conID, Integer connumbureau) {
    // 构建 UPDATE 语句
    StringBuilder update = new StringBuilder("UPDATE Titulaire SET ");
    boolean hasFields = false;

    // 拼接 SET 子句
    if (numbureau != null) {
        update.append("numbureau = ?, ");
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
   if (connumbureau != null) {
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
        if (numbureau != null) pstmt.setInt(paramIndex++, numbureau);

        // 设置 WHERE 子句的参数
        if (conID != null) pstmt.setInt(paramIndex++, conID);
        if (connumbureau != null) pstmt.setInt(paramIndex++, connumbureau);

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

public static void UpdateTitulaire_Discipline(Integer discipline_ID,Integer conID, Integer condiscipline_ID) {
    // 构建 UPDATE 语句
    StringBuilder update = new StringBuilder("UPDATE Titulaire_Discipline SET ");
    boolean hasFields = false;

    // 拼接 SET 子句
    if (discipline_ID != null) {
        update.append("discipline_ID = ?, ");
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
    if (condiscipline_ID != null) {
        update.append("AND discipline_ID = ? ");
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
        if ( discipline_ID!= null) pstmt.setInt(paramIndex++, discipline_ID);

        // 设置 WHERE 子句的参数
        if (conID != null) pstmt.setInt(paramIndex++, conID);
        if (condiscipline_ID != null) pstmt.setInt(paramIndex++, condiscipline_ID);   
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
        return "Titulaire{" +
                "nom='" + getNom() + '\'' +
                ", disciplines=" + disciplines +
                ", numBureau=" + numBureau +
                '}';
    }
}
