package projet_java.Personne;

import projet_java.Geographie.Ville;
import java.util.Set;

class MCF extends Titulaire {
    private Etudiant etudiant;

    public MCF(String nom, String prenom, int age, Ville ville, Set<Discipline> disciplines, int numBureau, Etudiant etudiant) {
        super(nom, prenom, age, ville, disciplines, numBureau);
        this.etudiant = etudiant;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    @Override
    public String toString() {
        return "MCF{" +
                "nom='" + getNom() + '\'' +
                ", etudiant=" + etudiant.getNom() +
                '}';
    }
}











