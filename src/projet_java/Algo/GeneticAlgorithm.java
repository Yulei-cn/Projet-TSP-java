package projet_java.Algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class GeneticAlgorithm {

	// Initialisation de la population
    public Population initPopulation(List<City> cities, int sizepop) {
        return new Population(cities, sizepop);
    }

 // Sélection aléatoire de deux parents selon leur aptitude
    public Solution[] randomSelection(Population population) {
        List<Solution> solutions = population.getSolutions();
        Solution parent1 = solutions.get((int) (Math.random() * solutions.size()));
        Solution parent2 = solutions.get((int) (Math.random() * solutions.size()));
        return new Solution[]{parent1, parent2};
    }

 // Opération de croisement : échange simple d'une partie du chemin
    public Solution crossover(Solution parent1, Solution parent2) {
        List<City> path1 = parent1.getPath();
        List<City> path2 = parent2.getPath();
        List<City> childPath = new ArrayList<>(path1.subList(0, path1.size() / 2));

        for (City city : path2) {
            if (!childPath.contains(city)) {
                childPath.add(city);
            }
        }

        return new Solution(childPath, childPath.get(0));
    }

 // Opération de mutation : échange aléatoire de deux villes dans le chemin
    public void mutate(Solution solution, double probmutate) {
        if (Math.random() <= probmutate) {
            List<City> path = solution.getPath();
            int index1 = (int) (Math.random() * path.size());
            int index2 = (int) (Math.random() * path.size());
            Collections.swap(path, index1, index2);
        }
    }

 // Élitisme : conservation des meilleurs individus
    public void elitism(Population newPopulation, Population oldPopulation, double rateelite) {
        List<Solution> allSolutions = new ArrayList<>(newPopulation.getSolutions());
        allSolutions.addAll(oldPopulation.getSolutions());
        allSolutions.sort((s1, s2) -> Double.compare(s1.getFitness(), s2.getFitness()));

        int eliteCount = (int) (allSolutions.size() * rateelite);
        newPopulation.getSolutions().clear();
        newPopulation.getSolutions().addAll(allSolutions.subList(0, eliteCount));
    }
    
 // Sauvegarder les résultats dans un fichier
    public void saveResults(Solution bestSolution, String directoryPath, String fileName) {
        String filePath = Paths.get(directoryPath, fileName).toString(); // Combinaison du chemin complet

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Chemin optimal final :\n");
            writer.write(bestSolution.toString() + "\n");
            System.out.println("Résultats enregistrés" + filePath);
        } catch (IOException e) {
            System.out.println("Erreur！");
            e.printStackTrace();
        }
    }

 // Méthode principale de l'algorithme génétique
    public Solution solveTSP(List<City> cities, int sizepop, double probmutate, double rateelite, 
                            int maxIterations, String saveDirectory, String fileName) {
        Population population = initPopulation(cities, sizepop);
        Solution previousBestSolution = null;

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            Population newPopulation = new Population(new ArrayList<>(), 0);

            while (newPopulation.getSolutions().size() < sizepop) {
                Solution[] parents = randomSelection(population);
                Solution child1 = crossover(parents[0], parents[1]);
                Solution child2 = crossover(parents[1], parents[0]);

                mutate(child1, probmutate);
                mutate(child2, probmutate);

                newPopulation.getSolutions().add(child1);
                newPopulation.getSolutions().add(child2);
            }

            elitism(newPopulation, population, rateelite);
            population = newPopulation;

            Solution currentBestSolution = population.getBestSolution();

         // Si la meilleure solution actuelle ne change pas, terminer plus tôt
            if (previousBestSolution != null && currentBestSolution.equals(previousBestSolution)) {
                System.out.println("L'algorithme a convergé, fin anticipée.");
                break;
            }
            previousBestSolution = currentBestSolution;
        }

     // Obtenir la meilleure solution finale
        Solution bestSolution = population.getBestSolution();

        // Sauvegarder les résultats dans un fichier
        saveResults(bestSolution, saveDirectory, fileName);

        return bestSolution;
    }

}
