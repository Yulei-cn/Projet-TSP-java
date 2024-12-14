package projet_java.Personne;

import projet_java.Geographie.Ville;
import java.util.Set;

class Titulaire extends Personne {
    private Set<Discipline> disciplines;
    private int numBureau;

    public Titulaire(String nom, String prenom, int age, Ville ville, Set<Discipline> disciplines, int numBureau) {
        super(nom, prenom, age, ville);
        this.disciplines = disciplines;
        this.numBureau = numBureau;
    }

    public Set<Discipline> getDisciplines() {
        return disciplines;
    }

    public int getNumBureau() {
        return numBureau;
    }

    @Override
    public String toString() {
        return "Titulaire{" +
                "nom='" + getNom() + '\'' +
                ", disciplines=" + disciplines +
                ", numBureau=" + numBureau +
                '}';
    }
}
