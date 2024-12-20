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



/*

    public static void requete(String string, String valeur) {


        // SQL 查询语句 (使用动态列名需特别小心，避免 SQL 注入)
       
         String querySQL = "SELECT * FROM personne WHERE " + string+ " = ?;";
        // 执行查询
        try (Connection conn = BDConnect.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(querySQL)) {
        	// SQL 查询语句 (使用动态列名需特别小心，避免 SQL 注入)
            // 设置参数 (列的值)
            pstmt.setString(1, valeur);
            // 执行查询
            ResultSet rs = pstmt.executeQuery();
            List<String> villes = new ArrayList<>()
            // 遍历结果集并输出数据
            System.out.println("查询结果：");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int age = rs.getInt("age");
                String ville = rs.getString("ville");



                // 输出查询结果
                System.out.println("ID: " + id + ", Nom: " + nom + ", Prenom: " + prenom +
                        ", Age: " + age + ", Ville: " + ville);
            }
        } catch (Exception e) {
            System.out.println("查询失败，请检查输入或数据库连接！");
            e.printStackTrace();
        } 
    }


*/
 public static List<String> requete(String string, String valeur) {
        // SQL 查询语句 (使用动态列名需特别小心，避免 SQL 注入)
       List<String> villes = new ArrayList<>();
         String querySQL = "SELECT * FROM personne WHERE " + string+ " = ?;";
        // 执行查询
        try (Connection conn = BDConnect.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(querySQL)) {
        	// SQL 查询语句 (使用动态列名需特别小心，避免 SQL 注入)
            // 设置参数 (列的值)
            pstmt.setString(1, valeur);
            // 执行查询
            ResultSet rs = pstmt.executeQuery();
            
            // 遍历结果集并输出数据
            System.out.println("查询结果：");
            while (rs.next()) {
                String ville = rs.getString("ville");
                villes.add(ville);             


                // 输出查询结果
                System.out.println(" Ville: " + ville);
            }
        } catch (Exception e) {
            System.out.println("查询失败，请检查输入或数据库连接！");
            e.printStackTrace();
        } 
        return villes;
    }

 public static List<String> requeteint(String string, int valeur) {
        // SQL 查询语句 (使用动态列名需特别小心，避免 SQL 注入)
       List<String> villes = new ArrayList<>();
         String querySQL = "SELECT * FROM personne WHERE " + string+ " = ?;";
        // 执行查询
        try (Connection conn = BDConnect.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(querySQL)) {
        	// SQL 查询语句 (使用动态列名需特别小心，避免 SQL 注入)
            // 设置参数 (列的值)
            pstmt.setInt(1, valeur);
            // 执行查询
            ResultSet rs = pstmt.executeQuery();
            
            // 遍历结果集并输出数据
            System.out.println("查询结果：");
            while (rs.next()) {
                String ville = rs.getString("ville");
                villes.add(ville);             


                // 输出查询结果
                System.out.println(" Ville: " + ville);
            }
        } catch (Exception e) {
            System.out.println("查询失败，请检查输入或数据库连接！");
            e.printStackTrace();
        } 
        return villes;
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

    
    public static List<Personne> searchByDynamicConditions(String nom, String prenom, Integer age, String villeNom,
        String discipline, Integer anneeDeThese, Integer encadrant,Integer chercheur,Integer mcf) {
		List<Personne> result = new ArrayList<>();
		StringBuilder query = new StringBuilder(
		"SELECT DISTINCT p.nom, p.prenom, p.age, v.ville_nom, v.ville_latitude_deg, v.ville_longitude_deg "
		);
		
	// 使用 LEFT JOIN 关联 Etudiant 表，避免过滤掉无匹配 Etudiant 数据的 Personne
		query.append("FROM Personne p ")
		.append("JOIN villes_france_free v ON LOWER(p.ville) = LOWER(v.ville_nom) ")
        .append("LEFT JOIN Etudiant e ON p.ID = e.ID ")
        .append("LEFT JOIN Chercheur c ON e.ID=c.etudiant ")
        .append("LEFT JOIN MCF m ON e.ID=m.etudiant ");


		
		// WHERE 子句：动态拼接查询条件
		query.append("WHERE 1=1 ");
		if (nom != null) query.append("AND p.nom =  ? ");
		if (prenom != null) query.append("AND p.prenom ILIKE  ? ");
		if (age != null) query.append("AND p.age = ? ");
		if (villeNom != null) query.append("AND p.ville = ? ");
		if (discipline != null) query.append("AND e.discipline = ? ");
		if (anneeDeThese != null) query.append("AND e.anneeDeThese = ? ");
		if (encadrant != null) query.append("AND e.encadrant =  ? ");
        if (chercheur != null) query.append("AND c.ID =  ? ");
        if (mcf != null) query.append("AND m.ID =  ? ");


        
		
		try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
		
		// 动态设置参数
		int paramIndex = 1;
		if (nom != null) pstmt.setString(paramIndex++, nom);
		if (prenom != null) pstmt.setString(paramIndex++, prenom);
		if (age != null) pstmt.setInt(paramIndex++, age);
		if (villeNom != null) pstmt.setString(paramIndex++, villeNom);
		if (discipline != null) pstmt.setString(paramIndex++, discipline);
		if (anneeDeThese != null) pstmt.setInt(paramIndex++, anneeDeThese);
		if (encadrant != null) pstmt.setInt(paramIndex++, encadrant);
		if (chercheur != null) pstmt.setInt(paramIndex++, chercheur);
		if (mcf != null) pstmt.setInt(paramIndex++, mcf);

		// 执行查询并处理结果
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
		String nomResult = rs.getString("nom");
		String prenomResult = rs.getString("prenom");
		int ageResult = rs.getInt("age");
		String villeNomResult = rs.getString("ville_nom");
		double latitude = rs.getDouble("ville_latitude_deg");
		double longitude = rs.getDouble("ville_longitude_deg");
		
		// 创建 Ville 对象并传入经纬度
		Ville villeResult = new Ville(villeNomResult, latitude, longitude);
		
		// 添加到结果列表
		result.add(new Personne(nomResult, prenomResult, ageResult, villeResult));
		}
		} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("查询失败，请检查输入条件或数据库连接！");
		}
		return result;
		}

 public static List<Personne> searchByDynamicChercheur(Integer chercheur) {
		List<Personne> result = new ArrayList<>();
		StringBuilder query = new StringBuilder(
		"SELECT DISTINCT p.nom, p.prenom, p.age, v.ville_nom, v.ville_latitude_deg, v.ville_longitude_deg "
		);
		
	// 使用 LEFT JOIN 关联 Etudiant 表，避免过滤掉无匹配 Etudiant 数据的 Personne
		query.append("FROM Personne p ")
		.append("JOIN villes_france_free v ON LOWER(p.ville) = LOWER(v.ville_nom) ")
        .append("LEFT JOIN Etudiant e ON p.ID = e.ID ")
        .append("LEFT JOIN Chercheur c ON e.ID=c.etudiant ");


		
		// WHERE 子句：动态拼接查询条件
		query.append("WHERE 1=1 ");
		if (chercheur != null) query.append("AND c.ID =  ? ");

        
		System.out.println("Generated SQL query: " + query.toString());
		try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
		
		// 动态设置参数
		int paramIndex = 1;
		if (chercheur != null) pstmt.setInt(paramIndex++, chercheur);

		// 执行查询并处理结果
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
		String nomResult = rs.getString("nom");
		String prenomResult = rs.getString("prenom");
		int ageResult = rs.getInt("age");
		String villeNomResult = rs.getString("ville_nom");
		double latitude = rs.getDouble("ville_latitude_deg");
		double longitude = rs.getDouble("ville_longitude_deg");
		
		// 创建 Ville 对象并传入经纬度
		Ville villeResult = new Ville(villeNomResult, latitude, longitude);
		
		// 添加到结果列表
		result.add(new Personne(nomResult, prenomResult, ageResult, villeResult));
		}
		} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("查询失败，请检查输入条件或数据库连接！");
		}
		return result;
		}

    
    public static List<Personne> searchByDynamicConditionsti(String nom, String prenom, Integer age, String villeNom,
            String discipline, Integer numbureau) {

		List<Personne> result = new ArrayList<>();
		StringBuilder query = new StringBuilder(
		"SELECT DISTINCT p.nom, p.prenom, p.age, v.ville_nom, v.ville_latitude_deg, v.ville_longitude_deg "
		);
		
	// 使用 LEFT JOIN 关联 Etudiant 表，避免过滤掉无匹配 Etudiant 数据的 Personne
		query.append("FROM Personne p ")
		.append("JOIN villes_france_free v ON LOWER(p.ville) = LOWER(v.ville_nom) ")
        .append("LEFT JOIN Titulaire t ON p.ID = t.ID ")
        .append("JOIN Titulaire_Discipline d ON t.ID=d.Id");

		
		// WHERE 子句：动态拼接查询条件
		query.append("WHERE 1=1 ");
		if (nom != null) query.append("AND p.nom =  ? ");
		if (prenom != null) query.append("AND p.prenom ILIKE  ? ");
		if (age != null) query.append("AND p.age = ? ");
		if (villeNom != null) query.append("AND p.ville = ? ");
		if (discipline != null) query.append("AND d.discipline = ? ");
        if (numbureau != null) query.append("AND t.numbureau = ? ");

		
		try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
		
		// 动态设置参数
		int paramIndex = 1;
		if (nom != null) pstmt.setString(paramIndex++, nom);
		if (prenom != null) pstmt.setString(paramIndex++, prenom);
		if (age != null) pstmt.setInt(paramIndex++, age);
		if (villeNom != null) pstmt.setString(paramIndex++, villeNom);
		if (discipline != null) pstmt.setString(paramIndex++, discipline);


		
		// 执行查询并处理结果
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
		String nomResult = rs.getString("nom");
		String prenomResult = rs.getString("prenom");
		int ageResult = rs.getInt("age");
		String villeNomResult = rs.getString("ville_nom");
		double latitude = rs.getDouble("ville_latitude_deg");
		double longitude = rs.getDouble("ville_longitude_deg");
		
		// 创建 Ville 对象并传入经纬度
		Ville villeResult = new Ville(villeNomResult, latitude, longitude);
		
		// 添加到结果列表
		result.add(new Personne(nomResult, prenomResult, ageResult, villeResult));
		}
		} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("查询失败，请检查输入条件或数据库连接！");
		}
		return result;
		}



 
		
	public static void SupprimePersonne(Integer ID, String nom, String prenom, Integer age, String ville) {
        // 插入数据的 SQL 语句
        StringBuilder delete = new StringBuilder( "DELETE FROM personne ");

        delete.append("WHERE 1=1 ");
		if (ID != null) delete.append("AND ID =  ? ");
		if (nom != null) delete.append("AND nom ILIKE  ? ");
		if (prenom != null) delete.append("AND prenom Like ? ");
        if (age != null) delete.append("AND age = ? ");
		if (ville != null) delete.append("AND ville ILIKE  ? ");

        // 使用 BDConnect 获取连接并插入数据
        try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(delete.toString())) {

            // 设置占位符参数
        int paramIndex = 1;
		if (ID != null) pstmt.setInt(paramIndex++, ID);
		if (nom != null) pstmt.setString(paramIndex++, nom);
		if (prenom != null) pstmt.setString(paramIndex++, prenom);
		if (age != null) pstmt.setInt(paramIndex++, age);
		if (ville != null) pstmt.setString(paramIndex++, ville);


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

  public static void UpdatePersonne(String nom, String prenom, Integer age, String ville,
                                  Integer ID, String connom, String conprenom, Integer conage, String conville) {
    // 构建 UPDATE 语句
    StringBuilder update = new StringBuilder("UPDATE Personne SET ");
    boolean hasFields = false;

    // 拼接 SET 子句
    if (nom != null) {
        update.append("nom = ?, ");
        hasFields = true;
    }
    if (prenom != null) {
        update.append("prenom = ?, ");
        hasFields = true;
    }
    if (age != null) {
        update.append("age = ?, ");
        hasFields = true;
    }
    if (ville != null) {
        update.append("ville = ?, ");
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

    if (ID != null) {
        update.append("AND ID = ? ");
        hasConditions = true;
    }
    if (connom != null) {
        update.append("AND nom = ? ");
        hasConditions = true;
    }
    if (conprenom != null) {
        update.append("AND prenom = ? ");
        hasConditions = true;
    }
    if (conage != null) {
        update.append("AND age = ? ");
        hasConditions = true;
    }
    if (conville != null) {
        update.append("AND ville = ? ");
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
        if (nom != null) pstmt.setString(paramIndex++, nom);
        if (prenom != null) pstmt.setString(paramIndex++, prenom);
        if (age != null) pstmt.setInt(paramIndex++, age);
        if (ville != null) pstmt.setString(paramIndex++, ville);

        // 设置 WHERE 子句的参数
        if (ID != null) pstmt.setInt(paramIndex++, ID);
        if (connom != null) pstmt.setString(paramIndex++, connom);
        if (conprenom != null) pstmt.setString(paramIndex++, conprenom);
        if (conage != null) pstmt.setInt(paramIndex++, conage);
        if (conville != null) pstmt.setString(paramIndex++, conville);

        // 执行更新语句
        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Personne 数据更新成功！");
        } else {
            System.out.println("没有符合条件的数据被更新。");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
  }



}