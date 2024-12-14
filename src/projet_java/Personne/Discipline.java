package projet_java.Personne;

import projet_java.BDConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public enum Discipline {
    MATHEMATIQUES,
    INFORMATIQUE,
    GESTION,
    DROIT,
    SCIENCESSOCIALES;

    // 将所有枚举值插入数据库
    public static void insertAllDisciplines() {
        String insertSQL = "INSERT INTO Discipline (id, name) VALUES (?, ?) ON CONFLICT (id) DO NOTHING";

        try (Connection conn = BDConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            for (Discipline discipline : Discipline.values()) {
                pstmt.setInt(1, discipline.ordinal() + 1); // 使用 ordinal() 生成唯一的 ID
                pstmt.setString(2, discipline.name());
                pstmt.executeUpdate();
            }
            System.out.println("所有 Discipline 数据插入成功！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
