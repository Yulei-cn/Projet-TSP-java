package projet_java.Personne;

import projet_java.BDConnect;
import projet_java.Geographie.Ville;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Personne {
    private String nom;
    private String prenom;
    private int age;
    private Ville ville;
    private final int ID;
    private static int nbPersonnes = 0;


    public Personne(String nom, String prenom, int age, Ville ville) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.ville = ville;
        this.ID = ++nbPersonnes;
    }

    public static void insertPersonne(String nom, String prenom, int age, String ville) {

        String insertSQL = "INSERT INTO Personne (nom, prenom, age, ville) VALUES (?, ?, ?, ?)";


        try (Connection conn = BDConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setInt(3, age);
            pstmt.setString(4, ville);

  
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("inserer:succes" );
            }
            else{
                System.out.println("erreur" );
            }
        } catch (SQLException e) {
        }
    }

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

    
    public static List<Personne> searchByDynamicConditionsEtudiant(String nom, String prenom, Integer age, String villeNom,
        Integer discipline, Integer anneeDeThese, Integer encadrant,Integer chercheur,Integer mcf) {
		List<Personne> result = new ArrayList<>();
		StringBuilder query = new StringBuilder(
		"SELECT DISTINCT p.nom, p.prenom, p.age, v.ville_nom, v.ville_latitude_deg, v.ville_longitude_deg "
		);
		
		query.append("FROM Personne p ")
		.append("JOIN villes_france_free v ON LOWER(p.ville) = LOWER(v.ville_nom) ")
        .append("JOIN Etudiant e ON p.ID = e.ID ")
        .append("LEFT JOIN Chercheur c ON e.ID=c.etudiant ")
        .append("LEFT JOIN MCF m ON e.ID=m.etudiant ");


		

		query.append("WHERE 1=1 ");
		if (nom != null) query.append("AND p.nom ILIKE ? ");
		if (prenom != null) query.append("AND p.prenom ILIKE  ? ");
		if (age != null) query.append("AND p.age = ? ");
		if (villeNom != null) query.append("AND p.ville ILIKE ? ");
		if (discipline != null) query.append("AND e.discipline = ? ");
		if (anneeDeThese != null) query.append("AND e.anneeDeThese = ? ");
		if (encadrant != null) query.append("AND e.encadrant =  ? ");
        if (chercheur != null) query.append("AND c.ID =  ? ");
        if (mcf != null) query.append("AND m.ID =  ? ");


        
		
		try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
		

		int paramIndex = 1;
		if (nom != null) pstmt.setString(paramIndex++, nom);
		if (prenom != null) pstmt.setString(paramIndex++, prenom);
		if (age != null) pstmt.setInt(paramIndex++, age);
		if (villeNom != null) pstmt.setString(paramIndex++, villeNom);
		if (discipline != null) pstmt.setInt(paramIndex++, discipline);
		if (anneeDeThese != null) pstmt.setInt(paramIndex++, anneeDeThese);
		if (encadrant != null) pstmt.setInt(paramIndex++, encadrant);
		if (chercheur != null) pstmt.setInt(paramIndex++, chercheur);
		if (mcf != null) pstmt.setInt(paramIndex++, mcf);


		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
		String nomResult = rs.getString("nom");
		String prenomResult = rs.getString("prenom");
		int ageResult = rs.getInt("age");
		String villeNomResult = rs.getString("ville_nom");
		double latitude = rs.getDouble("ville_latitude_deg");
		double longitude = rs.getDouble("ville_longitude_deg");
		
	
		Ville villeResult = new Ville(villeNomResult, latitude, longitude);
		

		result.add(new Personne(nomResult, prenomResult, ageResult, villeResult));
		}
		} catch (SQLException e) {

		System.out.println("erreur:verifier les conditions de recherche！");
		}
		return result;
		}

     public static List<Personne> searchByDynamicPersonne(String nom, String prenom, Integer age, String villeNom) {
		List<Personne> result = new ArrayList<>();
		StringBuilder query = new StringBuilder(
		"SELECT DISTINCT p.nom, p.prenom, p.age, v.ville_nom, v.ville_latitude_deg, v.ville_longitude_deg "
		);
		
	
		query.append("FROM Personne p ")
		.append("JOIN villes_france_free v ON LOWER(p.ville) = LOWER(v.ville_nom) ");



		

		query.append("WHERE 1=1 ");
		if (nom != null) query.append("AND p.nom ILIKE ? ");
		if (prenom != null) query.append("AND p.prenom ILIKE  ? ");
		if (age != null) query.append("AND p.age = ? ");
		if (villeNom != null) query.append("AND p.ville ILIKE ? ");



        
		
		try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
		

		int paramIndex = 1;
		if (nom != null) pstmt.setString(paramIndex++, nom);
		if (prenom != null) pstmt.setString(paramIndex++, prenom);
		if (age != null) pstmt.setInt(paramIndex++, age);
		if (villeNom != null) pstmt.setString(paramIndex++, villeNom);



		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
		String nomResult = rs.getString("nom");
		String prenomResult = rs.getString("prenom");
		int ageResult = rs.getInt("age");
		String villeNomResult = rs.getString("ville_nom");
		double latitude = rs.getDouble("ville_latitude_deg");
		double longitude = rs.getDouble("ville_longitude_deg");
		

		Ville villeResult = new Ville(villeNomResult, latitude, longitude);
		
	
		result.add(new Personne(nomResult, prenomResult, ageResult, villeResult));
		}
		} catch (SQLException e) {
		System.out.println("erreur:verifier les conditions de recherche");
		}
		return result;
		}


 public static List<Personne> searchByDynamicChercheur(String nom, String prenom, Integer age, String villeNom,
        Integer discipline,Integer numbureau) {
		List<Personne> result = new ArrayList<>();
		StringBuilder query = new StringBuilder(
		"SELECT DISTINCT p.nom, p.prenom, p.age, v.ville_nom, v.ville_latitude_deg, v.ville_longitude_deg "
		);

		query.append("FROM Personne p ")
		.append("JOIN villes_france_free v ON LOWER(p.ville) = LOWER(v.ville_nom) ")
        .append("JOIN Titulaire t ON p.ID = t.ID ")
        .append("JOIN Titulaire_Discipline d ON t.ID = d.ID ")
        .append("JOIN chercheur c ON d.ID=c.ID ");

		

		query.append("WHERE 1=1 ");
		if (nom != null) query.append("AND p.nom ILIKE  ? ");
		if (prenom != null) query.append("AND p.prenom ILIKE  ? ");
		if (age != null) query.append("AND p.age = ? ");
		if (villeNom != null) query.append("AND p.ville ILIKE ? ");
		if (discipline != null) query.append("AND d.discipline = ? ");
        if (numbureau != null) query.append("AND t.numbureau = ? ");

        
		System.out.println("Generated SQL query: " + query.toString());
		try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
		

		int paramIndex = 1;
		if (nom != null) pstmt.setString(paramIndex++, nom);
		if (prenom != null) pstmt.setString(paramIndex++, prenom);
		if (age != null) pstmt.setInt(paramIndex++, age);
		if (villeNom != null) pstmt.setString(paramIndex++, villeNom);
		if (discipline != null) pstmt.setInt(paramIndex++, discipline);
        if (numbureau != null) pstmt.setInt(paramIndex++, numbureau);
        



		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
		String nomResult = rs.getString("nom");
		String prenomResult = rs.getString("prenom");
		int ageResult = rs.getInt("age");
		String villeNomResult = rs.getString("ville_nom");
		double latitude = rs.getDouble("ville_latitude_deg");
		double longitude = rs.getDouble("ville_longitude_deg");
		

		Ville villeResult = new Ville(villeNomResult, latitude, longitude);

		result.add(new Personne(nomResult, prenomResult, ageResult, villeResult));
		}
		} catch (SQLException e) {
		System.out.println("erreur:verifier les conditions de recherche");
		}
		return result;
		}

 public static List<Personne> searchByDynamicMCF(String nom, String prenom, Integer age, String villeNom,
        Integer discipline,Integer numbureau) {
		List<Personne> result = new ArrayList<>();
		StringBuilder query = new StringBuilder(
		"SELECT DISTINCT p.nom, p.prenom, p.age, v.ville_nom, v.ville_latitude_deg, v.ville_longitude_deg "
		);

		query.append("FROM Personne p ")
		.append("JOIN villes_france_free v ON LOWER(p.ville) = LOWER(v.ville_nom) ")
        .append("JOIN Titulaire t ON p.ID = t.ID ")
        .append("JOIN Titulaire_Discipline d ON t.ID = d.ID ")
        .append("JOIN MCF m ON d.id=m.id ");


		

		query.append("WHERE 1=1 ");
		if (nom != null) query.append("AND p.nom ILIKE  ? ");
		if (prenom != null) query.append("AND p.prenom ILIKE  ? ");
		if (age != null) query.append("AND p.age = ? ");
		if (villeNom != null) query.append("AND p.ville ILIKE ? ");
		if (discipline != null) query.append("AND d.discipline = ? ");
        if (numbureau != null) query.append("AND t.numbureau = ? ");
        
		System.out.println("Generated SQL query: " + query.toString());
		try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
		

		int paramIndex = 1;
		if (nom != null) pstmt.setString(paramIndex++, nom);
		if (prenom != null) pstmt.setString(paramIndex++, prenom);
		if (age != null) pstmt.setInt(paramIndex++, age);
		if (villeNom != null) pstmt.setString(paramIndex++, villeNom);
		if (discipline != null) pstmt.setInt(paramIndex++, discipline);
        if (numbureau != null) pstmt.setInt(paramIndex++, numbureau);

		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
		String nomResult = rs.getString("nom");
		String prenomResult = rs.getString("prenom");
		int ageResult = rs.getInt("age");
		String villeNomResult = rs.getString("ville_nom");
		double latitude = rs.getDouble("ville_latitude_deg");
		double longitude = rs.getDouble("ville_longitude_deg");
		

		Ville villeResult = new Ville(villeNomResult, latitude, longitude);
		

		result.add(new Personne(nomResult, prenomResult, ageResult, villeResult));
		}
		} catch (SQLException e) {
		System.out.println("erreur:verifier les conditions de recherche");
		}
		return result;
		}
    
    public static List<Personne> searchByDynamicConditionsti(String nom, String prenom, Integer age, String villeNom,
            Integer discipline, Integer numbureau) {

		List<Personne> result = new ArrayList<>();
		StringBuilder query = new StringBuilder(
		"SELECT DISTINCT p.nom, p.prenom, p.age, v.ville_nom, v.ville_latitude_deg, v.ville_longitude_deg "
		);
		

		query.append("FROM Personne p ")
		.append("JOIN villes_france_free v ON LOWER(p.ville) = LOWER(v.ville_nom) ")
        .append("JOIN Titulaire t ON p.ID = t.ID ")
        .append("JOIN Titulaire_Discipline d ON t.ID=d.Id ");


		query.append("WHERE 1=1 ");
		if (nom != null) query.append("AND p.nom =  ? ");
		if (prenom != null) query.append("AND p.prenom ILIKE  ? ");
		if (age != null) query.append("AND p.age = ? ");
		if (villeNom != null) query.append("AND p.ville = ? ");
		if (discipline != null) query.append("AND d.discipline = ? ");
        if (numbureau != null) query.append("AND t.numbureau = ? ");

		
		try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
		

		int paramIndex = 1;
		if (nom != null) pstmt.setString(paramIndex++, nom);
		if (prenom != null) pstmt.setString(paramIndex++, prenom);
		if (age != null) pstmt.setInt(paramIndex++, age);
		if (villeNom != null) pstmt.setString(paramIndex++, villeNom);
		if (discipline != null) pstmt.setInt(paramIndex++, discipline);



		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
		String nomResult = rs.getString("nom");
		String prenomResult = rs.getString("prenom");
		int ageResult = rs.getInt("age");
		String villeNomResult = rs.getString("ville_nom");
		double latitude = rs.getDouble("ville_latitude_deg");
		double longitude = rs.getDouble("ville_longitude_deg");
		

		Ville villeResult = new Ville(villeNomResult, latitude, longitude);
		

		result.add(new Personne(nomResult, prenomResult, ageResult, villeResult));
		}
		} catch (SQLException e) {
		System.out.println("erreur:verifier les conditions de recherche");
		}
		return result;
		}



 
		
	public static int SupprimePersonne(Integer ID, String nom, String prenom, Integer age, String ville) {

        StringBuilder delete = new StringBuilder( "DELETE FROM personne ");

        delete.append("WHERE 1=1 ");
		if (ID != null) delete.append("AND ID =  ? ");
		if (nom != null) delete.append("AND nom ILIKE  ? ");
		if (prenom != null) delete.append("AND prenom Like ? ");
        if (age != null) delete.append("AND age = ? ");
		if (ville != null) delete.append("AND ville ILIKE  ? ");


        try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(delete.toString())) {


        int paramIndex = 1;
		if (ID != null) pstmt.setInt(paramIndex++, ID);
		if (nom != null) pstmt.setString(paramIndex++, nom);
		if (prenom != null) pstmt.setString(paramIndex++, prenom);
		if (age != null) pstmt.setInt(paramIndex++, age);
		if (ville != null) pstmt.setString(paramIndex++, ville);



            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Personne supprime :succes!");
                return 1;
            }
            else{
                System.out.println("erreur");
                return 0;
            }
        }catch (SQLException e) {
        }
        return 0;
    }

  public static void UpdatePersonne(String nom, String prenom, Integer age, String ville,
                                  Integer ID, String connom, String conprenom, Integer conage, String conville) {

    StringBuilder update = new StringBuilder("UPDATE Personne SET ");
    boolean hasFields = false;


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


    if (hasFields) {
        update.deleteCharAt(update.length() - 2);
    } else {
        System.out.println("没有需要更新的字段，更新操作已取消。");
        return;
    }


    update.append(" WHERE 1=1 ");
 

    if (ID != null) {
        update.append("AND ID = ? ");
     
    }
    if (connom != null) {
        update.append("AND nom = ? ");
      
    }
    if (conprenom != null) {
        update.append("AND prenom = ? ");
  
    }
    if (conage != null) {
        update.append("AND age = ? ");

    }
    if (conville != null) {
        update.append("AND ville = ? ");

    }

    System.out.println(" SQL : " + update);

    try (Connection conn = BDConnect.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(update.toString())) {

        int paramIndex = 1;


        if (nom != null) pstmt.setString(paramIndex++, nom);
        if (prenom != null) pstmt.setString(paramIndex++, prenom);
        if (age != null) pstmt.setInt(paramIndex++, age);
        if (ville != null) pstmt.setString(paramIndex++, ville);


        if (ID != null) pstmt.setInt(paramIndex++, ID);
        if (connom != null) pstmt.setString(paramIndex++, connom);
        if (conprenom != null) pstmt.setString(paramIndex++, conprenom);
        if (conage != null) pstmt.setInt(paramIndex++, conage);
        if (conville != null) pstmt.setString(paramIndex++, conville);


        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Personne update:succes");
        } else {
            System.out.println("Il n'y a pas de date correspond");
        }

    } catch (SQLException e) {
    }
  }



}