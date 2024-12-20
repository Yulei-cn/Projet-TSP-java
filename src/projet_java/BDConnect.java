package projet_java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDConnect {
	// Informations de connexion à la base de données
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/projetSQL";
    private static final String USER = "postgres";
    private static final String PASSWORD = "zhu";

 // Méthode pour obtenir une connexion à la base de données
    public static Connection getConnection() {
        Connection connec = null;
        try {
        	// Charger explicitement le driver PostgreSQL
            Class.forName("org.postgresql.Driver");

         // Obtenir une connexion via DriverManager
            connec = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connexion à la base de données réussie !");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL Driver PostgreSQL non trouvé !");
        } catch (SQLException e) {
            System.out.println("Échec");
        }
        return connec;
    }

    // Fermer la connexion
    public static void closeConnection(Connection connec) {
        try {
            if (connec != null) {
                connec.close();
                System.out.println("Connexion à la base de données fermée !");
            }
        } catch (SQLException e) {
        }
    }
}
