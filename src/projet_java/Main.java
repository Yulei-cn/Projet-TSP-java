package projet_java;

import projet_java.Geographie.Region;
import projet_java.Personne.CreatTable;
import projet_java.Personne.Personne;
import projet_java.Personne.Discipline;
import projet_java.Personne.Titulaire;
import projet_java.Personne.Etudiant;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n选择操作：");
            System.out.println("1. 插入所有 Discipline 数据");
            System.out.println("2. 插入 Personne 数据");
            System.out.println("3. 插入 Titulaire 数据");
            System.out.println("4. 插入 Etudiant 数据");
            System.out.println("5. 退出");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> Discipline.insertAllDisciplines();
                case 2 -> Personne.insertPersonne("Dupont", "Jean", 30, "Paris");
                case 3 -> Titulaire.insertTitulaire(1, 101);
                case 4 -> Etudiant.insertEtudiant(2, "sujetDeJAVA", 1, 2, 1);
                case 5 -> {
                    System.out.println("退出程序");
                    scanner.close();
                    return;
                }
                default -> System.out.println("无效的选择，请重新输入！");
            }
        }
    }
}
