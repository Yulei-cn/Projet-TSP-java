package projet_java.Personne;

import projet_java.BDConnect;
import projet_java.Geographie.Ville;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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



    public static void SupprimeEtudiant(Integer personneId, String sujetDeThese, Integer disciplineId, Integer anneeDeThese, Integer encadrantId) {
   
        StringBuilder delete = new StringBuilder( "DELETE FROM ETUDIANT ");

        delete.append("WHERE 1=1 ");
		if (personneId != null) delete.append("AND ID =  ? ");
		if (sujetDeThese != null) delete.append("AND sujetdethese ILIKE  ? ");
		if (disciplineId != null) delete.append("AND discipline = ? ");
        if (anneeDeThese != null) delete.append("AND anneeDeThese = ? ");
		if (encadrantId != null) delete.append("AND encadrant =  ? ");


        try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(delete.toString())) {

        int paramIndex = 1;
		if (personneId != null) pstmt.setInt(paramIndex++, personneId);
		if (sujetDeThese != null) pstmt.setString(paramIndex++, sujetDeThese);
		if (disciplineId != null) pstmt.setInt(paramIndex++, disciplineId);
		if (anneeDeThese != null) pstmt.setInt(paramIndex++, anneeDeThese);
		if (encadrantId != null) pstmt.setInt(paramIndex++, encadrantId);


            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Etudiant supprime:succes！");
            }
            else{
                System.out.println("erreur");
            }
        } catch (SQLException e) {
        }
    }

    public static void UpdateEtudiant(String sujetDeThese, Integer disciplineId, Integer anneeDeThese, Integer encadrantId,
            Integer conpersonneId, String consujetDeThese, Integer condisciplineId, 
            Integer conanneeDeThese, Integer conencadrantId) {

		StringBuilder update = new StringBuilder("UPDATE Etudiant SET ");
		

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
		

		if (hasFields) {
		update.deleteCharAt(update.length() - 2); 
		} 
		

		update.append(" WHERE 1=1 ");
		if (conpersonneId != null) {
		update.append("AND ID = ? ");
		}
		if (consujetDeThese != null) {
		update.append("AND sujetDeThese ILIKE ? ");
		}
		if (condisciplineId != null) {
		update.append("AND discipline = ? ");
		}
		if (conanneeDeThese != null) {
		update.append("AND anneeDeThese = ? ");
		}
		if (conencadrantId != null) {
		update.append("AND encadrant = ? ");
		}
		
	

		

		System.out.println("SQL : " + update);
	
		try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(update.toString())) {
		
		int paramIndex = 1;
		

		if (sujetDeThese != null) pstmt.setString(paramIndex++, sujetDeThese);
		if (disciplineId != null) pstmt.setInt(paramIndex++, disciplineId);
		if (anneeDeThese != null) pstmt.setInt(paramIndex++, anneeDeThese);
		if (encadrantId != null) pstmt.setInt(paramIndex++, encadrantId);
		

		if (conpersonneId != null) pstmt.setInt(paramIndex++, conpersonneId);
		if (consujetDeThese != null) pstmt.setString(paramIndex++, consujetDeThese);
		if (condisciplineId != null) pstmt.setInt(paramIndex++, condisciplineId);
		if (conanneeDeThese != null) pstmt.setInt(paramIndex++, conanneeDeThese);
		if (conencadrantId != null) pstmt.setInt(paramIndex++, conencadrantId);
		

		int rowsUpdated = pstmt.executeUpdate();
		if (rowsUpdated > 0) {
		System.out.println("update:succes");
		} else {
		System.out.println("Il n'y a pas de date correspondant");
		}
		} catch (SQLException e) {
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
