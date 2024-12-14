package projet_java.Personne;

import projet_java.Geographie.Ville;
import java.util.Set;

class Chercheur extends Titulaire {
    private Set<Etudiant> etudiants;

    public Chercheur(String nom, String prenom, int age, Ville ville, Set<Discipline> disciplines, int numBureau, Set<Etudiant> etudiants) {
        super(nom, prenom, age, ville, disciplines, numBureau);
        this.etudiants = etudiants;
    }

    public Set<Etudiant> getEtudiants() {
        return etudiants;
    }

    @Override
    public String toString() {
        return "Chercheur{" +
                "nom='" + getNom() + '\'' +
                ", etudiants=" + etudiants +
                '}';
    }
}