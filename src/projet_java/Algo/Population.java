package projet_java.Algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {
    private List<Solution> solutions; // 种群中的所有解（路径）

    // 构造函数：生成随机初始种群
    public Population(List<City> cities, int populationSize) {
        solutions = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            // 随机生成路径，并添加到种群中
            Solution solution = new Solution(cities, cities.get(0)); // 默认以第一个城市作为起点
            solutions.add(solution);
        }
    }

    // 获取种群中的所有解
    public List<Solution> getSolutions() {
        return solutions;
    }

    // 获取种群中的最佳解（路径长度最短的路径）
    public Solution getBestSolution() {
        Solution bestSolution = solutions.get(0);
        for (Solution solution : solutions) {
            if (solution.getFitness() < bestSolution.getFitness()) {
                bestSolution = solution;
            }
        }
        return bestSolution;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Population 包含以下解：\n");
        for (Solution solution : solutions) {
            sb.append(solution).append("\n");
        }
        return sb.toString();
    }
}
