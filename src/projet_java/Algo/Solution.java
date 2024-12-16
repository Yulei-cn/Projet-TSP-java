package projet_java.Algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {
    private List<City> path;  // Hamilton 循环路径
    private double fitness;   // 路径总长度（适应度）

    // 构造函数：指定起点城市，并初始化路径
    public Solution(List<City> cities, City startCity) {
        this.path = new ArrayList<>(cities); // 复制城市列表
        this.path.remove(startCity);         // 将起点从列表中移除
        Collections.shuffle(this.path);      // 随机打乱剩余城市
        this.path.add(0, startCity);         // 将起点添加到路径的开头
        this.path.add(startCity);            // 将起点添加到路径的末尾，形成循环
        calculateFitness();                  // 计算路径总长度
    }

    // 计算路径的总长度（fitness）
    private void calculateFitness() {
        fitness = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            City city1 = path.get(i);
            City city2 = path.get(i + 1);
            fitness += City.calculateDistance(city1, city2);
        }
    }

    // 获取路径总长度（fitness）
    public double getFitness() {
        return fitness;
    }

    // 获取路径
    public List<City> getPath() {
        return path;
    }

    // 输出路径信息
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Hamilton 循环路径：\n");
        for (City city : path) {
            sb.append(city.getName()).append(" -> ");
        }
        sb.append(String.format("\n路径总长度 (fitness)：%.2f km", fitness));
        return sb.toString();
    }
}
