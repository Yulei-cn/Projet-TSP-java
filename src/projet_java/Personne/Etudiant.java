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

    public static int insertEtudiant(int personneId, String sujetDeThese, int disciplineId, int anneeDeThese, int encadrantId) {
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
                return 1;
            }
            else{
				return 0;
			}
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return 0;
    }

    public static void SupprimeEtudiant(Integer personneId, String sujetDeThese, Integer disciplineId, Integer anneeDeThese, Integer encadrantId) {
        // 插入数据的 SQL 语句
        StringBuilder delete = new StringBuilder( "DELETE FROM ETUDIANT ");

        delete.append("WHERE 1=1 ");
		if (personneId != null) delete.append("AND ID =  ? ");
		if (sujetDeThese != null) delete.append("AND sujetdethese ILIKE  ? ");
		if (disciplineId != null) delete.append("AND discipline = ? ");
        if (anneeDeThese != null) delete.append("AND anneeDeThese = ? ");
		if (encadrantId != null) delete.append("AND encadrant =  ? ");

        // 使用 BDConnect 获取连接并插入数据
        try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(delete.toString())) {

            // 设置占位符参数
        int paramIndex = 1;
		if (personneId != null) pstmt.setInt(paramIndex++, personneId);
		if (sujetDeThese != null) pstmt.setString(paramIndex++, sujetDeThese);
		if (disciplineId != null) pstmt.setInt(paramIndex++, disciplineId);
		if (anneeDeThese != null) pstmt.setInt(paramIndex++, anneeDeThese);
		if (encadrantId != null) pstmt.setInt(paramIndex++, encadrantId);


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

    public static void UpdateEtudiant(String sujetDeThese, Integer disciplineId, Integer anneeDeThese, Integer encadrantId,
            Integer conpersonneId, String consujetDeThese, Integer condisciplineId, 
            Integer conanneeDeThese, Integer conencadrantId) {
		// 动态拼接 SQL 语句
		StringBuilder update = new StringBuilder("UPDATE Etudiant SET ");
		
		// 拼接 SET 子句
		boolean hasFields = false;
		if (sujetDeThese != null) {
		update.append("sujetDeThese = ?, ");
		hasFields = true;
		}
		if (disciplineId != null) {
		update.append("discipline = ?, ");
		hasFields = true;
		}
		if (anneeDeThese != null) {
		update.append("anneeDeThese = ?, ");
		hasFields = true;
		}
		if (encadrantId != null) {
		update.append("encadrant = ?, ");
		hasFields = true;
		}
		
		// 删除最后的逗号
		if (hasFields) {
		update.deleteCharAt(update.length() - 2); // 移除最后一个逗号和空格
		} else {
		System.out.println("没有需要更新的字段，更新操作已取消。");
		return;
		}
		
		// 拼接 WHERE 子句
		update.append(" WHERE 1=1 ");
		boolean hasConditions = false;
		if (conpersonneId != null) {
		update.append("AND ID = ? ");
		hasConditions = true;
		}
		if (consujetDeThese != null) {
		update.append("AND sujetDeThese ILIKE ? ");
		hasConditions = true;
		}
		if (condisciplineId != null) {
		update.append("AND discipline = ? ");
		hasConditions = true;
		}
		if (conanneeDeThese != null) {
		update.append("AND anneeDeThese = ? ");
		hasConditions = true;
		}
		if (conencadrantId != null) {
		update.append("AND encadrant = ? ");
		hasConditions = true;
		}
		
		// 如果没有条件，取消更新
		if (!hasConditions) {
		System.out.println("没有条件，无法执行更新操作。");
		return;
		}
		
		// 打印生成的 SQL 查询（调试用）
		System.out.println("生成的 SQL 查询: " + update);
		
		// 执行 SQL 更新
		try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(update.toString())) {
		
		int paramIndex = 1;
		
		// 设置 SET 子句参数
		if (sujetDeThese != null) pstmt.setString(paramIndex++, sujetDeThese);
		if (disciplineId != null) pstmt.setInt(paramIndex++, disciplineId);
		if (anneeDeThese != null) pstmt.setInt(paramIndex++, anneeDeThese);
		if (encadrantId != null) pstmt.setInt(paramIndex++, encadrantId);
		
		// 设置 WHERE 子句参数
		if (conpersonneId != null) pstmt.setInt(paramIndex++, conpersonneId);
		if (consujetDeThese != null) pstmt.setString(paramIndex++, consujetDeThese);
		if (condisciplineId != null) pstmt.setInt(paramIndex++, condisciplineId);
		if (conanneeDeThese != null) pstmt.setInt(paramIndex++, conanneeDeThese);
		if (conencadrantId != null) pstmt.setInt(paramIndex++, conencadrantId);
		
		// 执行更新
		int rowsUpdated = pstmt.executeUpdate();
		if (rowsUpdated > 0) {
		System.out.println("Etudiant 数据更新成功！");
		} else {
		System.out.println("未找到匹配的记录，未执行任何更新。");
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
