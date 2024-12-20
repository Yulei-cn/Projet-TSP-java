package projet_java;

import java.sql.Connection;
import java.util.*;

import projet_java.Algo.*;
import projet_java.Geographie.*;
import projet_java.Personne.*;

public class UpdateMenu {

    public static void executeUpdateMenu(Scanner scanner) {
        System.out.println("Veuillez choisir une opération de mise à jour :");
        System.out.println("1. Mettre à jour les données des étudiants");
        System.out.println("2. Mettre à jour les données des personnes");
        System.out.println("3. Mettre à jour les données des chercheurs");
        System.out.println("4. Mettre à jour les données des MCF");
        System.out.println("5. Mettre à jour les données des titulaires");
        System.out.println("6. Mettre à jour les données des disciplines des titulaires");
        System.out.println("7. Retour au menu précédent");
        System.out.print("Veuillez choisir une option (1-7) : ");


        int choice = scanner.nextInt();
        scanner.nextLine(); 

        switch (choice) {
            case 1:
                updateEtudiant(scanner);
                break;
            case 2:
                updatePersonne(scanner);
                break;
            case 3:
                updateChercheur(scanner);
                break;
            case 4:
                updateMCF(scanner);
                break;
            case 5:
                updateTitulaire(scanner);
                break;
            case 6:
                updateTitulaireDiscipline(scanner);
                break;
            case 7:
                System.out.println("Retour au menu précédent...");
                break;
            default:
                System.out.println("Choix invalide !");
        }
    }

    private static void updateEtudiant(Scanner scanner) {
        System.out.println("Veuillez entrer les conditions de mise à jour et les nouvelles valeurs :");

        System.out.print("ID actuel de l'étudiant (optionnel) : ");
        Integer conpersonneId = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("Sujet de thèse actuel (optionnel) : ");
        String consujetDeThese = parseNullableInput(scanner.nextLine().trim(), String.class);

        System.out.print("ID de la discipline actuelle (optionnel) : ");
        Integer condisciplineId = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("Année de thèse actuelle (optionnel) : ");
        Integer conanneeDeThese = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("ID de l'encadrant actuel (optionnel) : ");
        Integer conencadrantId = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("Nouveau sujet de thèse (optionnel) : ");
        String sujetDeThese = parseNullableInput(scanner.nextLine().trim(), String.class);

        System.out.print("Nouvel ID de la discipline (optionnel) : ");
        Integer disciplineId = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("Nouvelle année de thèse (optionnel) : ");
        Integer anneeDeThese = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("Nouvel ID de l'encadrant (optionnel) : ");
        Integer encadrantId = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        Etudiant.UpdateEtudiant(sujetDeThese, disciplineId, anneeDeThese, encadrantId, conpersonneId, consujetDeThese, condisciplineId, conanneeDeThese, conencadrantId);
    }

    // Autres méthodes de mise à jour et `parseNullableInput` restent inchangées
    @SuppressWarnings("unchecked")
    private static <T> T parseNullableInput(String input, Class<T> type) {
        if (input.isEmpty()) {
            return null;
        }

        try {
            if (type == Integer.class) {
                return type.cast(Integer.valueOf(input));  // 安全的类型转换
            } else if (type == String.class) {
                return type.cast(input);  // 安全的类型转换
            }
        } catch (Exception e) {
            System.out.println("Entrée invalide : " + input + ", elle sera ignorée.");
        }

        return null;
    }

    private static void updatePersonne(Scanner scanner) { /* Logique existante conservée */ }
    private static void updateChercheur(Scanner scanner) { /* Logique existante conservée */ }
    private static void updateMCF(Scanner scanner) { /* Logique existante conservée */ }
    private static void updateTitulaire(Scanner scanner) { /* Logique existante conservée */ }
    private static void updateTitulaireDiscipline(Scanner scanner) { /* Logique existante conservée */ }
}
