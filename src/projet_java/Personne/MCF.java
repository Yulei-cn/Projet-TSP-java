package projet_java.Personne;

import java.util.Set;
import projet_java.BDConnect;
import projet_java.Geographie.Ville;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


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

        StringBuilder delete = new StringBuilder( "DELETE FROM MCF");

        delete.append("WHERE 1=1 ");
		if (ID != null) delete.append("AND ID =  ? ");
		if (etudiant != null) delete.append("AND etudiant =  ? ");
		

        try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(delete.toString())) {

        int paramIndex = 1;
		if (ID != null) pstmt.setInt(paramIndex++, ID);
		if (etudiant != null) pstmt.setInt(paramIndex++, etudiant);



            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("mcf supprime:succes！");
            }
            else{
                System.out.println("erreur！");
            }
        } catch (SQLException e) {
        }
   }

    public static void UpdateMCF(Integer etudiant,Integer conID, Integer conetudiant) {
   
    StringBuilder update = new StringBuilder("UPDATE MCF SET ");
    boolean hasFields = false;


    if (etudiant != null) {
        update.append("etudiant = ?, ");
        hasFields = true;
    }


    if (hasFields) {
        update.deleteCharAt(update.length() - 2);
    } 

    update.append(" WHERE 1=1 ");


    if (conID != null) {
        update.append("AND ID = ? ");
    }
   if (conetudiant != null) {
        update.append("AND numbureau = ? ");
    }


    
    System.out.println("SQL : " + update);

    try (Connection conn = BDConnect.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(update.toString())) {

        int paramIndex = 1;

     
        if (etudiant != null) pstmt.setInt(paramIndex++, etudiant);


        if (conID != null) pstmt.setInt(paramIndex++, conID);
        if (conetudiant != null) pstmt.setInt(paramIndex++, conetudiant);


        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("update:succes");
        } else {
            System.out.println("update: il n'y a pas de data correspond");
        }

    } catch (SQLException e) {
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











