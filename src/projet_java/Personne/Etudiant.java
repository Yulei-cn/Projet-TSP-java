package projet_java.Personne;

import projet_java.Geographie.Ville;

class Etudiant extends Personne {
    private String sujetDeThese;
    private Discipline discipline;
    private int anneeDeThese;
    private Titulaire encadrant;

    public Etudiant(String nom, String prenom, int age, Ville ville, String sujetDeThese, Discipline discipline, int anneeDeThese, Titulaire encadrant) {
        super(nom, prenom, age, ville);
        this.sujetDeThese = sujetDeThese;
        this.discipline = discipline;
        this.anneeDeThese = anneeDeThese;
        this.encadrant = encadrant;
    }

    public String getSujetDeThese() {
        return sujetDeThese;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public int getAnneeDeThese() {
        return anneeDeThese;
    }

    public Titulaire getEncadrant() {
        return encadrant;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "nom='" + getNom() + '\'' +
                ", sujetDeThese='" + sujetDeThese + '\'' +
                ", discipline=" + discipline +
                ", encadrant=" + encadrant.getNom() +
                '}';
    }
}