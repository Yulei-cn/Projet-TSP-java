package projet_java.Geographie;

import java.util.Arrays;
import java.util.List;

public enum Region {
    // 枚举实例：名称、首府城市、面积、人口、包含的省份列表
    ILE_DE_FRANCE("Île-de-France", new Ville("Paris", 48.8566, 2.3522), 12011, 12278210, Arrays.asList("75", "77", "78", "91", "92", "93", "94", "95")),
    AUVERGNE_RHONE_ALPES("Auvergne-Rhône-Alpes", new Ville("Lyon", 45.75, 4.85), 69711, 8061973, Arrays.asList("01", "03", "07", "15", "26", "38", "42", "43", "63", "69", "73", "74")),
    PROVENCE_ALPES_COTE_DAZUR("Provence-Alpes-Côte d'Azur", new Ville("Marseille", 43.2965, 5.3698), 31400, 5033382, Arrays.asList("04", "05", "06", "13", "83", "84"));

    private final String nom;
    private final Ville chefLieu; // 首府城市
    private final double superficie; // 面积
    private final int population; // 人口
    private final List<String> departements; // 包含的省份列表

    // 构造函数
    Region(String nom, Ville chefLieu, double superficie, int population, List<String> departements) {
        this.nom = nom;
        this.chefLieu = chefLieu;
        this.superficie = superficie;
        this.population = population;
        this.departements = departements;
    }

    // 获取区域名称
    public String getNom() {
        return nom;
    }

    // 获取首府城市
    public Ville getChefLieu() {
        return chefLieu;
    }

    // 获取区域面积
    public double getSuperficie() {
        return superficie;
    }

    // 获取区域人口
    public int getPopulation() {
        return population;
    }

    // 获取省份列表
    public List<String> getDepartements() {
        return departements;
    }

    // 根据城市名查找区域
    public static Region findRegionByVille(String villeNom) {
        for (Region region : Region.values()) {
            if (region.getChefLieu().getNom().equalsIgnoreCase(villeNom)) {
                return region;
            }
        }
        return null; // 如果未找到返回 null
    }

    @Override
    public String toString() {
        return "Region{" +
                "nom='" + nom + '\'' +
                ", chefLieu=" + chefLieu.getNom() +
                ", superficie=" + superficie + " km²" +
                ", population=" + population +
                ", departements=" + departements +
                '}';
    }
}
