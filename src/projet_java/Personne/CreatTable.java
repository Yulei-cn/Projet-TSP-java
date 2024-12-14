package projet_java.Personne;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreatTable {
    public static void Creat() {
        // 数据库连接参数
        String url = "jdbc:postgresql://localhost:5432/projetSQL"; // 数据库URL
        String user = "postgres"; // 用户名
        String password = "zhu"; // 密码

        // 建表SQL语句
        String createPersonneTable = """
                CREATE TABLE IF NOT EXISTS Personne (
                    ID SERIAL PRIMARY KEY,
                    nom VARCHAR(50) NOT NULL,
                    prenom VARCHAR(50) NOT NULL,
                    age INT NOT NULL,
                    ville VARCHAR(50) NOT NULL
                );
                """;

        String createDisciplineTable = """
                CREATE TABLE IF NOT EXISTS Discipline (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(50) UNIQUE NOT NULL
                );
                """;

        String createTitulaireTable = """
                CREATE TABLE IF NOT EXISTS Titulaire (
                    ID INT PRIMARY KEY REFERENCES Personne(ID),
                    numBureau INT NOT NULL
                );
                """;

        String createEtudiantTable = """
                CREATE TABLE IF NOT EXISTS Etudiant (
                    ID INT PRIMARY KEY REFERENCES Personne(ID),
                    sujetDeThese VARCHAR(255) NOT NULL,
                    discipline INT REFERENCES Discipline(id),
                    anneeDeThese INT NOT NULL,
                    encadrant INT REFERENCES Titulaire(ID)
                );
                """;

        String createChercheurTable = """
                CREATE TABLE IF NOT EXISTS Chercheur (
                    ID INT PRIMARY KEY REFERENCES Titulaire(ID)
                );
                """;

        String createMCFTable = """
                CREATE TABLE IF NOT EXISTS MCF (
                    ID INT PRIMARY KEY REFERENCES Titulaire(ID),
                    etudiant INT REFERENCES Etudiant(ID)
                );
                """;

        // 执行建表语句
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            // 创建表
            stmt.execute(createPersonneTable);
            System.out.println("表 Personne 创建成功！");
            
            stmt.execute(createDisciplineTable);
            System.out.println("表 Discipline 创建成功！");
            
            stmt.execute(createTitulaireTable);
            System.out.println("表 Titulaire 创建成功！");
            
            stmt.execute(createEtudiantTable);
            System.out.println("表 Etudiant 创建成功！");
            
            stmt.execute(createChercheurTable);
            System.out.println("表 Chercheur 创建成功！");
            
            stmt.execute(createMCFTable);
            System.out.println("表 MCF 创建成功！");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
