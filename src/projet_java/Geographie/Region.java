package projet_java.Geographie;

import java.util.Arrays;
import java.util.List;

public enum Region {
    // Instances d'énumération : nom, ville capitale, superficie, population, liste des départements inclus
    ILE_DE_FRANCE("Île-de-France", new Ville("Paris", 48.8566, 2.3522), 12011, 12278210, Arrays.asList("75", "77", "78", "91", "92", "93", "94", "95")),
    AUVERGNE_RHONE_ALPES("Auvergne-Rhône-Alpes", new Ville("Lyon", 45.75, 4.85), 69711, 8061973, Arrays.asList("01", "03", "07", "15", "26", "38", "42", "43", "63", "69", "73", "74")),
    PROVENCE_ALPES_COTE_DAZUR("Provence-Alpes-Côte d'Azur", new Ville("Marseille", 43.2965, 5.3698), 31400, 5033382, Arrays.asList("04", "05", "06", "13", "83", "84"));

    private final String nom;
    private final Ville chefLieu; // Ville capitale
    private final double superficie; // Superficie
    private final int population; // Population
    private final List<String> departements; // Liste des départements inclus

    // Constructeur
    Region(String nom, Ville chefLieu, double superficie, int population, List<String> departements) {
        this.nom = nom;
        this.chefLieu = chefLieu;
        this.superficie = superficie;
        this.population = population;
        this.departements = departements;
    }

    // Obtenir le nom de la région
    public String getNom() {
        return nom;
    }

    // Obtenir la ville capitale
    public Ville getChefLieu() {
        return chefLieu;
    }

    // Obtenir la superficie de la région
    public double getSuperficie() {
        return superficie;
    }

    // Obtenir la population de la région
    public int getPopulation() {
        return population;
    }

    // Obtenir la liste des départements
    public List<String> getDepartements() {
        return departements;
    }

    // Trouver une région par le nom de sa ville capitale
    public static Region findRegionByVille(String villeNom) {
        for (Region region : Region.values()) {
            if (region.getChefLieu().getNom().equalsIgnoreCase(villeNom)) {
                return region;
            }
        }
        return null; // Retourner null si non trouvé
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
