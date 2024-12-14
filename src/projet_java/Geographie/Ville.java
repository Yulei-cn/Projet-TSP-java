package projet_java.Geographie;

public class Ville {
    private String nom;
    private String codePostal;
    private int population;
    private double superficie;
    private Region region;

    public Ville(String nom, String codePostal, int population, double superficie, Region region) {
        this.nom = nom;
        this.codePostal = codePostal;
        this.population = population;
        this.superficie = superficie;
        this.region = region;
    }

    // Getters and setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Ville{" +
                "nom='" + nom + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", population=" + population +
                ", superficie=" + superficie +
                ", region=" + region.getNom() +
                '}';
    }
}