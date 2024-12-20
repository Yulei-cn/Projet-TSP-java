package projet_java;

import java.sql.Connection;
import java.util.*;

import projet_java.Algo.*;
import projet_java.Geographie.*;
import projet_java.Personne.*;

public class SupprimeMenu {

	public static void Supprime(Scanner scanner) {
	    System.out.println("Veuillez choisir une opération de suppression :");
	    System.out.println("1. Supprimer les données des étudiants");
	    System.out.println("2. Supprimer les données des personnes");
	    System.out.println("3. Supprimer les données des chercheurs");
	    System.out.println("4. Supprimer les données des MCF");
	    System.out.println("5. Supprimer les données des titulaires");
	    System.out.println("6. Supprimer les données des disciplines des titulaires");
	    System.out.println("7. Retour au menu précédent");
	    System.out.print("Veuillez choisir une option (1-7) : ");

	    int choice = scanner.nextInt();
	    scanner.nextLine(); 
	    System.out.println("Cette opération est irréversible. Veuillez confirmer en entrant 'CONFIRM' : ");
	    String confirmation = scanner.nextLine().trim();
	    if (!"CONFIRM".equalsIgnoreCase(confirmation)) {
	        System.out.println("Suppression annulée.");
	        return;
	    }

	    try {
	        switch (choice) {
	            case 1:
	                System.out.print("Veuillez entrer l'ID de l'étudiant (optionnel): ");
	                Integer etudiantID = parseNullableInput(scanner.nextLine().trim(), Integer.class);
	                System.out.print("Veuillez entrer le sujet de thèse (optionnel) : ");
	                String sujetDeThese = parseNullableInput(scanner.nextLine().trim(), String.class);
	                System.out.print("Veuillez entrer l'ID de la discipline (optionnel) : ");
	                Integer disciplineId = parseNullableInput(scanner.nextLine().trim(), Integer.class);
	                System.out.print("Veuillez entrer l'année de thèse (optionnel): ");
	                Integer anneeDeThese = parseNullableInput(scanner.nextLine().trim(), Integer.class);
	                System.out.print("Veuillez entrer l'ID de l'encadrant (optionnel): ");
	                Integer encadrantId = parseNullableInput(scanner.nextLine().trim(), Integer.class);

	                Etudiant.SupprimeEtudiant(etudiantID, sujetDeThese, disciplineId, anneeDeThese, encadrantId);
	                break;
	            case 2:
	                System.out.print("Veuillez entrer l'ID de la personne (optionnel): ");
	                Integer personneID = parseNullableInput(scanner.nextLine().trim(), Integer.class);
	                System.out.print("Veuillez entrer le nom (optionnel): ");
	                String nom = parseNullableInput(scanner.nextLine().trim(), String.class);
	                System.out.print("Veuillez entrer le prénom (optionnel): ");
	                String prenom = parseNullableInput(scanner.nextLine().trim(), String.class);
	                System.out.print("Veuillez entrer l'âge (optionnel): ");
	                Integer age = parseNullableInput(scanner.nextLine().trim(), Integer.class);
	                System.out.print("Veuillez entrer la ville (optionnel): ");
	                String ville = parseNullableInput(scanner.nextLine().trim(), String.class);
					if (Personne.SupprimePersonne(personneID, nom, prenom, age, ville)!=1){
						 System.out.println("Erreur : 不可以删除");
					};

	    /* try {
	                    Personne.SupprimePersonne(personneID, nom, prenom, age, ville);
	                } catch (Exception e) {
	                    System.out.println("Erreur : La suppression a échoué en raison d'une contrainte de clé étrangère.");
	                    System.out.println("Voulez-vous effectuer une suppression en cascade ? (o/n) : ");
	                    String cascadeChoice = scanner.nextLine().trim();
	                    if ("o".equalsIgnoreCase(cascadeChoice)) {
	                        performCascadeDelete(personneID, scanner);
	                    } else {
	                        System.out.println("Suppression annulée.");
	                    }
	                }*/
	                break;
	            case 3:
	                System.out.print("Veuillez entrer l'ID du MCF (optionnel) : ");
	                Integer chercheurID = parseNullableInput(scanner.nextLine().trim(), Integer.class);
	                System.out.print("Veuillez entrer l'ID de l'étudiant (optionnel) : ");
	                Integer chercheurEtudiantID = parseNullableInput(scanner.nextLine().trim(), Integer.class);

	                Chercheur.SupprimeChercheur(chercheurID, chercheurEtudiantID);
	                break;
	            case 4:
	                System.out.print("Veuillez entrer l'ID du titulaire (optionnel) : ");
	                Integer mcfID = parseNullableInput(scanner.nextLine().trim(), Integer.class);
	                System.out.print("Veuillez entrer le numéro du bureau (optionnel) : ");
	                Integer mcfEtudiantID = parseNullableInput(scanner.nextLine().trim(), Integer.class);

	                MCF.SupprimeMCF(mcfID, mcfEtudiantID);
	                break;
	            case 5:
	                System.out.print("Veuillez entrer l'ID du titulaire (optionnel): ");
	                Integer titulaireID = parseNullableInput(scanner.nextLine().trim(), Integer.class);
	                System.out.print("Veuillez entrer le numéro du bureau (optionnel): ");
	                Integer titulaireNumbureau = parseNullableInput(scanner.nextLine().trim(), Integer.class);

	                Titulaire.SupprimeTitulaire(titulaireID, titulaireNumbureau);
	                break;
	            case 6:
	                System.out.print("Veuillez entrer l'ID de la discipline du titulaire (optionnel) : ");
	                Integer disciplineID = parseNullableInput(scanner.nextLine().trim(), Integer.class);
	                System.out.print("Veuillez entrer l'ID de la discipline (optionnel) : ");
	                Integer titulaireDisciplineID = parseNullableInput(scanner.nextLine().trim(), Integer.class);

	                Titulaire.SupprimeTitulaire_Discipline(disciplineID, titulaireDisciplineID);
	                break;
	            case 7:
	                System.out.println("Retour au menu précédent...");
	                break;
	            default:
	                System.out.println("Choix invalide !");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	private static void performCascadeDelete(Integer personneID, Scanner scanner) {
	    System.out.println("Exécution de la suppression en cascade...");
	    // Implémentez ici la logique pour supprimer toutes les données associées à cette personne
	    // Exemple :
	    // 1. Supprimez les enregistrements des tables dépendantes
	    // 2. Ensuite, supprimez les données dans la table "Personne"
	    System.out.println("Suppression en cascade terminée pour ID : " + personneID);
	}


    @SuppressWarnings("unchecked")
    private static <T> T parseNullableInput(String input, Class<T> type) {
        if (input.isEmpty()) {
            return null;
        }

        try {
            if (type == Integer.class) {
                return (T) Integer.valueOf(input);
            } else if (type == String.class) {
                return (T) input;
            }
        } catch (Exception e) {
            System.out.println("Entrée invalide : " + input + ", elle sera ignorée.");
        }

        return null;
    }
}
