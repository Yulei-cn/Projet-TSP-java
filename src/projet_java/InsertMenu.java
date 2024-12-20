package projet_java;

import java.sql.*;
import java.util.Scanner;

import projet_java.Personne.Personne;

public class InsertMenu {

    public static void executeInsertMenu(Scanner scanner) {
        boolean running = true;

        try (Connection conn = BDConnect.getConnection()) {
            while (running) {
                System.out.println("Veuillez choisir un rôle :");
                System.out.println("1-Étudiant");
                System.out.println("2-Titulaire");
                System.out.println("3-Chercheur");
                System.out.println("4-MCF");
                System.out.println("5-Quitter le programme");
                System.out.print("Veuillez choisir une option (1-5) : ");
                int roleChoice = scanner.nextInt();
                scanner.nextLine(); 

                if (roleChoice == 5) {
                    running = false;
                    System.out.println("Programme terminé.");
                    break;
                }

             // Insertion générale dans la table Personne
                int personId = insertPersonne(scanner, conn);

                // Insertion dans les tables correspondantes selon le rôle
                switch (roleChoice) {
                    case 1: // Étudiant
                        if (insertEtudiant(scanner, conn, personId) != 1) {
                            Personne.SupprimePersonne(personId, null, null, null, null);
                        }
                        break;
                    case 2: // Titulaire
                        insertTitulaire(scanner, conn, personId);
                        break;
                    case 3: // Chercheur
                        insertChercheur(scanner, conn, personId);
                        break;
                    case 4: // MCF
                        insertMCF(scanner, conn, personId);
                        break;
                    default:
                        System.out.println("Choix invalide !");
                }
            }
        } catch (Exception e) {
        }
    }

    private static int insertPersonne(Scanner scanner, Connection conn) throws SQLException {
        System.out.println("Veuillez entrer le nom :");
        String nom = scanner.nextLine();

        System.out.println("Veuillez entrer le prénom :");
        String prenom = scanner.nextLine();

        System.out.println("Veuillez entrer l'âge :");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour à la ligne

        System.out.println("Veuillez entrer la ville :");
        String ville = scanner.nextLine();

        String insertPersonneSQL = "INSERT INTO Personne (nom, prenom, age, ville) VALUES (?, ?, ?, ?) RETURNING ID";
        try (PreparedStatement stmt = conn.prepareStatement(insertPersonneSQL)) {
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setInt(3, age);
            stmt.setString(4, ville);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int personId = rs.getInt("ID");
                    System.out.println("Insertion réussie dans Personne，ID 为：" + personId);
                    return personId;
                } else {
                    throw new SQLException("Échec de l'insertion dans Personne !");
                }
            }
        }
    }

    private static int insertEtudiant(Scanner scanner, Connection conn, int personId) {
        try {

            conn.setAutoCommit(false);

            System.out.println("Veuillez entrer le sujet de thèse :");
            String sujetDeThese = scanner.nextLine();

            System.out.println("Veuillez entrer l'ID de la discipline :");
            int disciplineId = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Veuillez entrer l'année de thèse :");
            int anneeDeThese = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Veuillez entrer l'ID de l'encadrant :");
            int encadrantId = scanner.nextInt();

         // Validation de l'ID de l'encadrant
            String checkEncadrantSQL = "SELECT COUNT(*) FROM Titulaire WHERE ID = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkEncadrantSQL)) {
                checkStmt.setInt(1, encadrantId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    System.out.println("Erreur : L'ID de l'encadrant n'existe pas !");
                    conn.rollback(); // Annulation de la transaction
                    return 0;
                }
            }

            String insertEtudiantSQL = "INSERT INTO Etudiant (ID, sujetDeThese, discipline, anneeDeThese, encadrant) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertEtudiantSQL)) {
                stmt.setInt(1, personId);
                stmt.setString(2, sujetDeThese);
                stmt.setInt(3, disciplineId);
                stmt.setInt(4, anneeDeThese);
                stmt.setInt(5, encadrantId);
                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    conn.commit(); // Validation de la transaction
                    System.out.println("Données de l'étudiant insérées avec succès !");
                    return 1;
                } else {
                    conn.rollback(); // Annulation de la transaction
                    System.out.println("Échec de l'insertion des données de l'étudiant !");
                    return 0;
                }
            }
        } catch (SQLException e) {
            try {
            	conn.rollback(); // En cas d'exception, annulation de la transaction
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return 0;
        } finally {
            try {
                conn.setAutoCommit(true); // Rétablissement du mode de validation automatique
            } catch (SQLException e) {
            }
        }
    }


    private static void insertTitulaire(Scanner scanner, Connection conn, int personId) throws SQLException {
        System.out.println("Veuillez entrer le numéro du bureau :");
        int numBureau = scanner.nextInt();
        scanner.nextLine(); 
        
        System.out.println("Veuillez entrer l'ID de la discipline :");
        int disciplineId = scanner.nextInt();
        scanner.nextLine(); 

        String insertTitulaireSQL = "INSERT INTO Titulaire (ID, numBureau) VALUES (?, ?)";
        String insertTitulaireDisciplineSQL = "INSERT INTO Titulaire_Discipline (ID, discipline_ID) VALUES (?, ?)";


        conn.setAutoCommit(false);

        try (PreparedStatement stmt1 = conn.prepareStatement(insertTitulaireSQL);
             PreparedStatement stmt2 = conn.prepareStatement(insertTitulaireDisciplineSQL)) {
             
            stmt1.setInt(1, personId);
            stmt1.setInt(2, numBureau);
            stmt1.executeUpdate();


            stmt2.setInt(1, personId);
            stmt2.setInt(2, disciplineId);
            stmt2.executeUpdate();


            conn.commit();
            System.out.println("Titulaire et Titulaire_Discipline insere:succes！");
        } catch (SQLException e) {
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
        }
    }


    private static void insertChercheur(Scanner scanner, Connection conn, int personId) throws SQLException {
        System.out.println("Veuillez entrer l'ID de l'étudiant ::");
        int etudiantID = scanner.nextInt();
        scanner.nextLine(); 

        System.out.println("Veuillez entrer le numéro du bureau :");
        int numBureau = scanner.nextInt();
        scanner.nextLine(); 
        String insertTitulaireSQL = "INSERT INTO Titulaire (ID, numBureau) VALUES (?, ?)";
        try (PreparedStatement stmtTitulaire = conn.prepareStatement(insertTitulaireSQL)) {
            stmtTitulaire.setInt(1, personId);
            stmtTitulaire.setInt(2, numBureau);

            int rowsInsertedTitulaire = stmtTitulaire.executeUpdate();
            if (rowsInsertedTitulaire > 0) {
                System.out.println("réussie Titulaire（avec numéro de bureau）！");
            } else {
                System.out.println("Titulaire Échec!");
            }
        }
     // Insertion dans Chercheur
        String insertChercheurSQL = "INSERT INTO Chercheur (ID, etudiant) VALUES (?, ?)";
        try (PreparedStatement stmtChercheur = conn.prepareStatement(insertChercheurSQL)) {
            stmtChercheur.setInt(1, personId);
            stmtChercheur.setInt(2, etudiantID);

            int rowsInserted = stmtChercheur.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Insertion réussie dans Chercheur !");
            } else {
                System.out.println("Chercheur Échec！");
            }
        }
    }

    
    private static void insertMCF(Scanner scanner, Connection conn, int personId) throws SQLException {
        System.out.println("Veuillez entrer l'ID de l'étudiant responsable :");
        int etudiantId = scanner.nextInt();
        scanner.nextLine(); // le retour à la ligne

        System.out.println("Veuillez entrer le numéro du bureau :");
        int numBureau = scanner.nextInt();
        scanner.nextLine(); // le retour à la ligne

        // Insertion dans Titulaire
        String insertTitulaireSQL = "INSERT INTO Titulaire (ID, numBureau) VALUES (?, ?)";
        try (PreparedStatement stmtTitulaire = conn.prepareStatement(insertTitulaireSQL)) {
            stmtTitulaire.setInt(1, personId);
            stmtTitulaire.setInt(2, numBureau);
            stmtTitulaire.executeUpdate();
            System.out.println("Insertion réussie dans Titulaire (avec numéro de bureau) !");
        }

        // Insertion dans MCF
        String insertMCFSQL = "INSERT INTO MCF (ID, etudiant) VALUES (?, ?)";
        try (PreparedStatement stmtMCF = conn.prepareStatement(insertMCFSQL)) {
            stmtMCF.setInt(1, personId);
            stmtMCF.setInt(2, etudiantId);
            stmtMCF.executeUpdate();
            System.out.println("Insertion réussie dans MCF !");
        }
    }
}
