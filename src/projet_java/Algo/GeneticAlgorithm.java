package projet_java.Algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class GeneticAlgorithm {

    // 初始化种群
    public Population initPopulation(List<City> cities, int sizepop) {
        return new Population(cities, sizepop);
    }

    // 根据适应度随机选择两个父路径
    public Solution[] randomSelection(Population population) {
        List<Solution> solutions = population.getSolutions();
        Solution parent1 = solutions.get((int) (Math.random() * solutions.size()));
        Solution parent2 = solutions.get((int) (Math.random() * solutions.size()));
        return new Solution[]{parent1, parent2};
    }

    // 交叉操作：简单交换一部分路径
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

    // 变异操作：随机交换路径中的两个城市
    public void mutate(Solution solution, double probmutate) {
        if (Math.random() <= probmutate) {
            List<City> path = solution.getPath();
            int index1 = (int) (Math.random() * path.size());
            int index2 = (int) (Math.random() * path.size());
            Collections.swap(path, index1, index2);
        }
    }

    // 精英保留：保留部分最优个体
    public void elitism(Population newPopulation, Population oldPopulation, double rateelite) {
        List<Solution> allSolutions = new ArrayList<>(newPopulation.getSolutions());
        allSolutions.addAll(oldPopulation.getSolutions());
        allSolutions.sort((s1, s2) -> Double.compare(s1.getFitness(), s2.getFitness()));

        int eliteCount = (int) (allSolutions.size() * rateelite);
        newPopulation.getSolutions().clear();
        newPopulation.getSolutions().addAll(allSolutions.subList(0, eliteCount));
    }
    
    public void saveResultsToFile(Solution bestSolution, String directoryPath, String fileName) {
        String filePath = Paths.get(directoryPath, fileName).toString(); // 组合完整路径

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("最终最优路径：\n");
            writer.write(bestSolution.toString() + "\n");
            System.out.println("结果已成功保存到文件：" + filePath);
        } catch (IOException e) {
            System.out.println("写入文件时发生错误！");
            e.printStackTrace();
        }
    }

 // 遗传算法主方法
    public Solution solveTSP(List<City> cities, int sizepop, double probmutate, double rateelite, 
                            int maxIterations, String saveDirectory, String fileName) {
        Population population = initPopulation(cities, sizepop);

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
            System.out.println("第 " + (iteration + 1) + " 代最佳路径：" + population.getBestSolution());
        }

        // 获取最终最优解
        Solution bestSolution = population.getBestSolution();

        // 保存结果到文件
        saveResultsToFile(bestSolution, saveDirectory, fileName);

        return bestSolution;
    }

}
