//Titulaire.java
package projet_java.Personne;

import projet_java.BDConnect;
import projet_java.Geographie.Ville;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

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


            pstmt.setInt(1, titulaireId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) { 
                    int count = rs.getInt(1); 
                    exists = count > 0; 
                }
            }

        } catch (SQLException e) {
        }

        return exists; 
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
                System.out.println("Titulaire inserer:succes");
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
                System.out.println("Titulaire discipline inserer :succe");
                }
            } 
        catch (SQLException e) {
            }
        }
    }


    public static void SupprimeTitulaire(Integer ID, Integer numbureau) {
       
        StringBuilder delete = new StringBuilder( "DELETE FROM Titulaire ");

        delete.append("WHERE 1=1 ");
		if (ID != null) delete.append("AND ID =  ? ");
		if (numbureau != null) delete.append("AND numbureau =  ? ");


       
        try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(delete.toString())) {

           
        int paramIndex = 1;
		if (ID != null) pstmt.setInt(paramIndex++, ID);
		if (numbureau != null) pstmt.setInt(paramIndex++, numbureau);



          
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Titulaire supprime:succes");
            }
               else{
                System.out.println("erreur");
            }
        } catch (SQLException e) {
        }
    }





    public static void SupprimeTitulaire_Discipline(Integer discipline, Integer ID) {

        StringBuilder delete = new StringBuilder( "DELETE FROM Titulaire ");

        delete.append("WHERE 1=1 ");
		if (ID != null) delete.append("AND ID =  ? ");
		if (discipline != null) delete.append("AND discipline_ID =  ? ");


    
        try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(delete.toString())) {

  
        int paramIndex = 1;
		if (ID != null) pstmt.setInt(paramIndex++, ID);
		if (discipline != null) pstmt.setInt(paramIndex++, discipline);



          
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Titulaire_Discipline supprime:succes");
            }
            else{
                System.out.println("Titulaire_Discipline supprime:erreur");
            }
        } catch (SQLException e) {
        }
    }

public static void UpdateTitulaire(Integer numbureau,Integer conID, Integer connumbureau) {

    StringBuilder update = new StringBuilder("UPDATE Titulaire SET ");
    boolean hasFields = false;


    if (numbureau != null) {
        update.append("numbureau = ?, ");
        hasFields = true;
    }


    if (hasFields) {
        update.deleteCharAt(update.length() - 2);
    } 

 
    update.append(" WHERE 1=1 ");


    if (conID != null) {
        update.append("AND ID = ? ");

    }
   if (connumbureau != null) {
        update.append("AND numbureau = ? ");

    }

    System.out.println("SQL : " + update);

    try (Connection conn = BDConnect.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(update.toString())) {

        int paramIndex = 1;

        if (numbureau != null) pstmt.setInt(paramIndex++, numbureau);

        if (conID != null) pstmt.setInt(paramIndex++, conID);
        if (connumbureau != null) pstmt.setInt(paramIndex++, connumbureau);

        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Titulaire update:succes");
        } else {
            System.out.println("Il n'y a pas de data correspondant");
        }

    } catch (SQLException e) {
    }
}

public static void UpdateTitulaire_Discipline(Integer discipline_ID,Integer conID, Integer condiscipline_ID) {

    StringBuilder update = new StringBuilder("UPDATE Titulaire_Discipline SET ");
    boolean hasFields = false;

    if (discipline_ID != null) {
        update.append("discipline_ID = ?, ");
        hasFields = true;
    }

    if (hasFields) {
        update.deleteCharAt(update.length() - 2);
    } 

    update.append(" WHERE 1=1 ");
 
    if (conID != null) {
        update.append("AND ID = ? ");

    }
    if (condiscipline_ID != null) {
        update.append("AND discipline_ID = ? ");
    }

 


    System.out.println("SQL : " + update);

    try (Connection conn = BDConnect.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(update.toString())) {

        int paramIndex = 1;


        if ( discipline_ID!= null) pstmt.setInt(paramIndex++, discipline_ID);

        if (conID != null) pstmt.setInt(paramIndex++, conID);
        if (condiscipline_ID != null) pstmt.setInt(paramIndex++, condiscipline_ID);   
  
        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Titulaire_Discipline update:succes");
        } else {
            System.out.println("Il n'y a pas de data correspondant");
        }

    } catch (SQLException e) {
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
