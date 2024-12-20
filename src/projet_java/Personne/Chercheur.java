package projet_java.Personne;


import java.util.Set;
import projet_java.BDConnect;
import projet_java.Geographie.Ville;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Chercheur extends Titulaire {
    private Set<Etudiant> etudiants;

    public Chercheur(String nom, String prenom, int age, Ville ville, Set<Discipline> disciplines, int numBureau, Set<Etudiant> etudiants) {
        super(nom, prenom, age, ville, disciplines, numBureau);
        this.etudiants = etudiants;
    }


    
    public static void SupprimeChercheur(Integer ID,  Integer Etudiant_ID) {

        StringBuilder delete = new StringBuilder( "DELETE FROM chercheur");

        delete.append("WHERE 1=1 ");
		if (ID != null) delete.append("AND ID =  ? ");
		if (Etudiant_ID != null) delete.append("AND Etudiant_ID =  ? ");
		
        try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(delete.toString())) {

        int paramIndex = 1;
		if (ID != null) pstmt.setInt(paramIndex++, ID);
		if (Etudiant_ID != null) pstmt.setInt(paramIndex++, Etudiant_ID);

        int rowsInserted = pstmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("supprime: succes");
            }
        else{
            System.out.println("supprime: erreur");
            }
        } catch (SQLException e) {
        }
    }
    public static void UpdateChercheur(Integer Etudiant_ID,Integer conID,Integer conEtudiant_ID) {

    StringBuilder update = new StringBuilder("UPDATE Chercheur SET ");
    boolean hasFields = false;


    if (Etudiant_ID != null) {
        update.append("Etudiant_ID = ?, ");
        hasFields = true;
    }


    if (hasFields) {
        update.deleteCharAt(update.length() - 2);
    } 


    update.append(" WHERE 1=1 ");

    if (conID != null) {
        update.append("AND ID = ? ");
    }
    if (conEtudiant_ID != null) {
        update.append("AND Etudiant_ID = ? ");
    }


    System.out.println("SQL : " + update);

    try (Connection conn = BDConnect.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(update.toString())) {

        int paramIndex = 1;

  
        if (Etudiant_ID != null) pstmt.setInt(paramIndex++, Etudiant_ID);


        if (conID != null) pstmt.setInt(paramIndex++, conID);
        if (conEtudiant_ID != null) pstmt.setInt(paramIndex++, conEtudiant_ID);


        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println(" update:succes");
        } else {
            System.out.println("update: il n'y a pas de data correspond");
        }

    } catch (SQLException e) {

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