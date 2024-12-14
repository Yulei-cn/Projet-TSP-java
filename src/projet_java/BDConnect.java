package projet_java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDConnect {
    // 数据库连接信息
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/projetSQL";
    private static final String USER = "postgres";
    private static final String PASSWORD = "zhu";

    // 获取数据库连接的方法
    public static Connection getConnection() {
        Connection connec = null;
        try {
            // 使用 DriverManag
            connec = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("成功连接到数据库！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connec;
    }

    // 关闭连接的方法
    public static void closeConnection(Connection connec) {
        try {
            if (connec != null) {
                connec.close();
                System.out.println("数据库连接已关闭！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
