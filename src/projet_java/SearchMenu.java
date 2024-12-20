package projet_java;

import java.util.List;
import java.util.Scanner;
import projet_java.Personne.*;
import projet_java.Geographie.*;
import projet_java.Algo.*;

import java.util.ArrayList;
import java.util.Objects;

public class SearchMenu {
    // look list city
    private static List<City> accumulatedCities = new ArrayList<>();

    public static void goSearchMenu(Scanner scanner) {
        boolean backToMain = false;

        while (!backToMain) {
            System.out.println("\n=== Menu de Recherche ===");
            System.out.println("1. Recherche d'étudiants avec des critères dynamiques");
            System.out.println("2. Recherche des personnes avec des critères dynamiques");
            System.out.println("3. Recherche des chercheurs avec des critères dynamiques");
            System.out.println("4. Recherche des MCF avec des critères dynamiques");
            System.out.println("5. Recherche des Titulaire avec des critères dynamiques");
            System.out.println("6. Retour au menu précédent");
            System.out.print("Veuillez choisir une option (1-6) : ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    searchEtudiant(scanner);
                    break;
                case 2:
                    searchPersonne(scanner);
                    break;
                case 3:
                    searchChercheur(scanner);
                    break;
                case 4:
                    searchMCF(scanner);
                    break;
                case 5:
                    searchTitulaire(scanner);
                    break;
                case 6:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Option invalide, veuillez réessayer.");
            }
        }
    }

    private static void searchEtudiant(Scanner scanner) {
        System.out.print("Nom : ");
        String nom = scanner.nextLine().trim();
        if (nom.isEmpty()) nom = null;

        System.out.print("Prénom : ");
        String prenom = scanner.nextLine().trim();
        if (prenom.isEmpty()) prenom = null;

        System.out.print("Âge : ");
        String ageInput = scanner.nextLine().trim();
        Integer age = ageInput.isEmpty() ? null : Integer.parseInt(ageInput);

        System.out.print("Ville : ");
        String nomVille = scanner.nextLine().trim();
        if (nomVille.isEmpty()) nomVille = null;

        System.out.print("Discipline (ex: INFORMATIQUE) : ");
        String disciplineInput = scanner.nextLine().trim();
        Integer discipline = disciplineInput.isEmpty() ? null : Integer.parseInt(disciplineInput);

        System.out.print("Année de thèse : ");
        String anneeDeTheseInput = scanner.nextLine().trim();
        Integer anneeDeThese = anneeDeTheseInput.isEmpty() ? null : Integer.parseInt(anneeDeTheseInput);

        System.out.print("l'encadrant ID: ");
        String encadrantInput = scanner.nextLine().trim();
        Integer encadrant = encadrantInput.isEmpty() ? null : Integer.parseInt(encadrantInput);

        System.out.print("Chercheur ID: ");
        String chercheurInput = scanner.nextLine().trim();
        Integer chercheur = chercheurInput.isEmpty() ? null : Integer.parseInt(chercheurInput);

        System.out.print("mcf ID : ");
        String mcfInput = scanner.nextLine().trim();
        Integer mcf = mcfInput.isEmpty() ? null : Integer.parseInt(mcfInput);

        List<Personne> result = Personne.searchByDynamicConditionsEtudiant(nom, prenom, age, nomVille, discipline, anneeDeThese, encadrant, chercheur, mcf);

        handleSearchResults(scanner, result);
    }

    private static void searchPersonne(Scanner scanner) {
        System.out.print("Nom : ");
        String nom = scanner.nextLine().trim();
        if (nom.isEmpty()) nom = null;

        System.out.print("Prénom : ");
        String prenom = scanner.nextLine().trim();
        if (prenom.isEmpty()) prenom = null;

        System.out.print("Âge : ");
        String ageInput = scanner.nextLine().trim();
        Integer age = ageInput.isEmpty() ? null : Integer.parseInt(ageInput);

        System.out.print("Ville : ");
        String nomVille = scanner.nextLine().trim();
        if (nomVille.isEmpty()) nomVille = null;

        List<Personne> result = Personne.searchByDynamicPersonne(nom, prenom, age, nomVille);
        handleSearchResults(scanner, result);
    }

    private static void searchChercheur(Scanner scanner) {
        System.out.print("Nom : ");
        String nom = scanner.nextLine().trim();
        if (nom.isEmpty()) nom = null;

        System.out.print("Prénom : ");
        String prenom = scanner.nextLine().trim();
        if (prenom.isEmpty()) prenom = null;

        System.out.print("Âge : ");
        String ageInput = scanner.nextLine().trim();
        Integer age = ageInput.isEmpty() ? null : Integer.parseInt(ageInput);

        System.out.print("Ville : ");
        String nomVille = scanner.nextLine().trim();
        if (nomVille.isEmpty()) nomVille = null;

        System.out.print("Discipline (ex: INFORMATIQUE) : ");
        String disciplineInput = scanner.nextLine().trim();
        Integer discipline = disciplineInput.isEmpty() ? null : Integer.parseInt(disciplineInput);

        System.out.print(" numero de bureau : ");
        String numbureauInput = scanner.nextLine().trim();
        Integer numBureau = numbureauInput.isEmpty() ? null : Integer.parseInt(numbureauInput);

        List<Personne> result = Personne.searchByDynamicChercheur(nom, prenom, age, nomVille, discipline, numBureau);
        handleSearchResults(scanner, result);
    }

    private static void searchMCF(Scanner scanner) {
        System.out.print("Nom : ");
        String nom = scanner.nextLine().trim();
        if (nom.isEmpty()) nom = null;

        System.out.print("Prénom : ");
        String prenom = scanner.nextLine().trim();
        if (prenom.isEmpty()) prenom = null;

        System.out.print("Âge : ");
        String ageInput = scanner.nextLine().trim();
        Integer age = ageInput.isEmpty() ? null : Integer.parseInt(ageInput);

        System.out.print("Ville : ");
        String nomVille = scanner.nextLine().trim();
        if (nomVille.isEmpty()) nomVille = null;

        System.out.print("Discipline (ex: INFORMATIQUE) : ");
        String disciplineInput = scanner.nextLine().trim();
        Integer discipline = disciplineInput.isEmpty() ? null : Integer.parseInt(disciplineInput);

        System.out.print("numero de bureau : ");
        String numbureauInput = scanner.nextLine().trim();
        Integer numBureau = numbureauInput.isEmpty() ? null : Integer.parseInt(numbureauInput);

        List<Personne> result = Personne.searchByDynamicMCF(nom, prenom, age, nomVille, discipline, numBureau);
        handleSearchResults(scanner, result);
    }

    private static void searchTitulaire(Scanner scanner) {
        System.out.print("Nom : ");
        String nom = scanner.nextLine().trim();
        if (nom.isEmpty()) nom = null;

        System.out.print("Prénom : ");
        String prenom = scanner.nextLine().trim();
        if (prenom.isEmpty()) prenom = null;

        System.out.print("Âge : ");
        String ageInput = scanner.nextLine().trim();
        Integer age = ageInput.isEmpty() ? null : Integer.parseInt(ageInput);

        System.out.print("Ville : ");
        String nomVille = scanner.nextLine().trim();
        if (nomVille.isEmpty()) nomVille = null;

        System.out.print("Discipline : ");
        String disciplineInput = scanner.nextLine().trim();
        Integer discipline = disciplineInput.isEmpty() ? null : Integer.parseInt(disciplineInput);

        System.out.print("numero de bureau : ");
        String numbureauInput = scanner.nextLine().trim();
        Integer numBureau = numbureauInput.isEmpty() ? null : Integer.parseInt(numbureauInput);

        List<Personne> result = Personne.searchByDynamicConditionsti(nom, prenom, age, nomVille, discipline, numBureau);
        handleSearchResults(scanner, result);
    }

    /*
      Traiter les résultats de chaque recherche :
      Ajouter les villes trouvées à accumulatedCities. Si aucun résultat n'est trouvé, informer l'utilisateur.
      En cas de résultats, afficher les résultats et ajouter les villes correspondantes à accumulatedCities, 
      puis demander à l'utilisateur s'il souhaite continuer la recherche.
      Si l'utilisateur choisit de ne pas continuer et qu'il y a au moins 2 villes, exécuter l'algorithme génétique.
     */

    private static void handleSearchResults(Scanner scanner, List<Personne> result) {
        if (result.isEmpty()) {
            System.out.println("Aucun résultat trouvé.");
        } else {
            System.out.println("Connexion à la base de données réussie !");
            result.forEach(System.out::println);
            // get new city
            List<City> newCities = result.stream()
                    .map(Personne::getVille)
                    .filter(Objects::nonNull)
                    .map(ville -> new City(ville.getNom(), ville.getLatitude(), ville.getLongitude()))
                    .distinct()
                    .toList();

            // Ajoutez les nouvelles villes trouvées à la liste cumulative des villes tout en évitant les doublons.
            for (City city : newCities) {
                if (!accumulatedCities.contains(city)) {
                    accumulatedCities.add(city);
                }
            }

            // e nombre de villes est inférieur à 2, il est donc impossible d'exécuter l'algorithme. Vous pouvez toutefois ajouter d'autres critères de recherche.
            if (accumulatedCities.size() < 2) {
                System.out.println("Le nombre de villes est insuffisant pour calculer le chemin optimal ! Il faut au moins deux villes。");
                System.out.println("Retour au menu de recherche pour ajouter d'autres critères.");
            }
        }

        // continuer la recherche ou exécuter l'algorithme ?
        promptForMoreCriteriaOrRunGA(scanner);
    }


    private static void promptForMoreCriteriaOrRunGA(Scanner scanner) {
        System.out.print("Voulez-vous ajouter d'autres critères de recherche ? (o/n) : ");
        String choix = scanner.nextLine().trim().toLowerCase();

        if (!choix.equals("o")) {
            if (accumulatedCities.size() < 2) {
                System.out.println("Le nombre de villes est insuffisant pour calculer le chemin optimal ! Il faut au moins deux villes。");
                System.out.println("Retour au menu de recherche.");
            } else {
                // 》》》ALGO
                runGeneticAlgorithm(scanner, accumulatedCities);
            }
        } else {
            System.out.println("Retour au menu de recherche pour ajouter d'autres critères.");
        }
    }

    // list city >=2 ，》》》ALGO

    private static void runGeneticAlgorithm(Scanner scanner, List<City> villes) {
        System.out.println("Calcul du chemin optimal en cours...");
        System.out.print("Veuillez entrer le chemin du répertoire de sauvegarde : ");
        String saveDirectory = scanner.nextLine().trim();
        System.out.print("Veuillez entrer le nom du fichier de résultat(txt) : ");
        String fileName = scanner.nextLine().trim();

        GeneticAlgorithm ga = new GeneticAlgorithm();
        Solution bestSolution = ga.solveTSP(villes, 10, 0.1, 0.2, 50, saveDirectory, fileName);
        System.out.println("Chemin optimal calculé：");
        System.out.println(bestSolution);


        accumulatedCities.clear();
    }
}
