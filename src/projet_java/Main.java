package projet_java;

import projet_java.Geographie.Region;

public class Main {
    public static void main(String[] args) {
        // 从数据库中加载并显示所有区域信息
        Region.loadRegionsFromDatabase();
    }
}
