package projet_java.Geographie;

import java.sql.*;
import projet_java.BDConnect;

public class Region {
    private String nom;

    // 构造函数
    public Region(String nom) {
        this.nom = nom;
    }

    // 获取区域名称
    public String getNom() {
        return nom;
    }

    // 从数据库中加载所有区域数据
    public static void loadRegionsFromDatabase() {
        Connection connec = null;

        try {
            // 1. 从 BDConnect 获取连接
            connec = BDConnect.getConnection();

            if (connec != null) {
                // 2. 创建 Statement 对象
                Statement statement = connec.createStatement();

                // 3. 执行查询操作
                String requete = "SELECT name FROM regions"; 
                ResultSet res = statement.executeQuery(requete);

                // 4. 遍历查询结果并创建 Region 对象
                while (res.next()) {
                    String nom = res.getString("name");
                    Region region = new Region(nom);
                    System.out.println("加载了区域：" + region.getNom());
                }

                // 5. 关闭 ResultSet
                res.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 6. 使用 BDConnect 关闭连接
            BDConnect.closeConnection(connec);
        }
    }

    // 其他可能的方法，例如显示区域信息
    public void displayRegionInfo() {
        System.out.println("区域名称：" + nom);
    }
    
 // 查询马赛的信息
    public static void getVilleInfo(String nomVille) {
        Connection connec = null;

        try {
            // 1. 从 BDConnect 获取连接
            connec = BDConnect.getConnection();

            if (connec != null) {
                // 2. 创建 PreparedStatement 对象
            	String requete = "SELECT ville_nom, ville_code_commune FROM villes_france_free WHERE LOWER(ville_nom) = LOWER(?)";

                PreparedStatement pstmt = connec.prepareStatement(requete);
                pstmt.setString(1, nomVille);

                // 3. 执行查询操作
                ResultSet res = pstmt.executeQuery();

                // 4. 遍历查询结果并显示
                while (res.next()) {
                    String villeNom = res.getString("ville_nom");
                    String codeCommune = res.getString("ville_code_commune");
                    System.out.println("城市名称：" + villeNom + ", 邮编：" + codeCommune);
                }

                // 5. 关闭 ResultSet
                res.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 6. 使用 BDConnect 关闭连接
            BDConnect.closeConnection(connec);
        }
    }

}
