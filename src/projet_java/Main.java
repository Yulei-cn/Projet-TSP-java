package projet_java;

import java.sql.Connection;
import java.util.*;

import projet_java.Algo.*;
import projet_java.Geographie.*;
import projet_java.Personne.*;


public class Main {
    public static void main(String[] args) {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);

        while (running) {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Créer des données et construire des tables");
            System.out.println("2. Gestion de la base de données et des rôles");
            System.out.println("3. Menu Algorithme Génétique");
            System.out.println("4. Autres Algorithmes et Méthodes");
            System.out.println("5. Quitter le programme");
            System.out.print("Veuillez choisir une option (1-5) : ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    createBD();
                    break;
                case 2:
                	BDManagement(scanner);
                    break;
                case 3:
                    SearchMenu.goSearchMenu(scanner);
                    break;
                case 4:
                	ALGOMenu(scanner);
                    break;
                case 5:
                    System.out.println("Programme terminé, au revoir !");
                    running = false;
                    break;
                default:
                    System.out.println("Option invalide, veuillez réessayer.");
            }
        }
        scanner.close();
    }

    private static void createBD() {
        CreatTable.Creat();
        Discipline.insertAllDisciplines();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tables et triggers créés avec succès !");
    }

    private static void BDManagement(Scanner scanner) {
        boolean running = true;

        try (Connection conn = BDConnect.getConnection()) {
            while (running) {
                System.out.println("Veuillez choisir une opération sur la base de données :");
                System.out.println("1. Opération d'insertion");
                System.out.println("2. Opération de mise à jour");
                System.out.println("3. Opération de suppression");
                System.out.println("4. Retour au menu principal");
                System.out.print("Veuillez choisir une option (1-4) : ");

                int roleChoice = scanner.nextInt();
                scanner.nextLine(); // Supprimer le caractère de retour à la ligne

                switch (roleChoice) {
                    case 1:
                        InsertMenu.executeInsertMenu(scanner);
                        break;
                    case 2:
                        UpdateMenu.executeUpdateMenu(scanner);
                        break;
                    case 3:
                        SupprimeMenu.Supprime(scanner);
                        break;
                    case 4:
                        running = false;
                        break;
                    default:
                        System.out.println("Choix invalide !");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static void ALGOMenu(Scanner scanner) {
        boolean backToMain = false;

        while (!backToMain) {
            System.out.println("\n=== Autres Algorithmes ===");
            System.out.println("1. Afficher toutes les villes");
            System.out.println("2. Parcourir toutes les régions");
            System.out.println("3. Construire une matrice de distances entre villes");
            System.out.println("4. Choisir une ville de départ");
            System.out.println("5. Retour au menu principal");
            System.out.print("Veuillez choisir une option (1-5) : ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1:
                    List<Ville> villes = Ville.loadAllVilles();
                    villes.forEach(System.out::println);
                    break;
                case 2:
                    for (Region region : Region.values()) {
                        System.out.println(region);
                    }
                    break;
                case 3:
                    villes = Ville.loadAllVilles();
                    Map<String, Map<String, Double>> distanceMatrix = City.buildDistanceMatrix(villes);
                    for (String city1 : distanceMatrix.keySet()) {
                        System.out.println("villes：" + city1);
                        for (String city2 : distanceMatrix.get(city1).keySet()) {
                            System.out.printf("    villes %-20s Distance: %.2f%n", city2, distanceMatrix.get(city1).get(city2));
                        }
                    }
                    break;
                case 4:
                    villes = Ville.loadAllVilles();
                    List<City> cities = new ArrayList<>();
                    for (Ville ville : villes) {
                        cities.add(new City(ville.getNom(), ville.getLatitude(), ville.getLongitude()));
                    }

                    System.out.println("Liste des villes disponibles :");
                    for (int i = 0; i < cities.size(); i++) {
                        System.out.println((i + 1) + ". " + cities.get(i).getName());
                    }

                    System.out.print("Veuillez choisir le numéro de la ville de départ :");
                    int startCityIndex = scanner.nextInt() - 1;

                    if (startCityIndex >= 0 && startCityIndex < cities.size()) {
                        City startCity = cities.get(startCityIndex);
                        Solution solution = new Solution(cities, startCity);
                        System.out.println(solution);
                    } else {
                        System.out.println("Numéro invalide, veuillez réessayer !");
                    }
                    break;
                case 5:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Option invalide, veuillez réessayer.");
            }
        }
    }
}
