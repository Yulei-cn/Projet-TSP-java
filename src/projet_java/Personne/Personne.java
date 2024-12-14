package projet_java.Personne;

import projet_java.Geographie.Ville;

public class Personne {
    private String nom;
    private String prenom;
    private int age;
    private Ville ville;
    private final int ID;
    private static int nbPersonnes = 0;

    public Personne(String nom, String prenom, int age, Ville ville) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.ville = ville;
        this.ID = ++nbPersonnes;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() {
        return age;
    }

    public Ville getVille() {
        return ville;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", ville=" + ville +
                ", ID=" + ID +
                '}';
    }
}