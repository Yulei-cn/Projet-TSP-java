//CreatTable.java
package projet_java.Personne;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

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

        String createTitulaire_DisciplineTable="""
                CREATE TABLE Titulaire_Discipline(
                    ID INT REFERENCES Titulaire(ID),
                    discipline_ID INT REFERENCES Discipline(ID),
                    PRIMARY KEY (ID, discipline_ID)
                );
            """;

        String createTriggerFunction = """
                CREATE OR REPLACE FUNCTION check_titulaire_discipline()
                RETURNS TRIGGER AS $$
                DECLARE
                    discipline_count INT;
                BEGIN
                    SELECT COUNT(*) INTO discipline_count
                    FROM Titulaire_Discipline
                    WHERE ID = NEW.ID;

                    IF discipline_count > 1 THEN 
                        RAISE EXCEPTION 'A Titulaire cannot have more than 2 Disciplines.';
                    END IF;

                    RETURN NEW;
                END;
                $$ LANGUAGE plpgsql;
                CREATE TRIGGER check_titulaire_discipline_trigger
                BEFORE INSERT OR UPDATE ON Titulaire_Discipline
                FOR EACH ROW
                EXECUTE FUNCTION check_titulaire_discipline();
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

        // 验证触发器是否附加成功的 SQL
        String validateTrigger = """
                SELECT event_object_table, trigger_name
                FROM information_schema.triggers
                WHERE event_object_table = 'Titulaire';
                """;

        // 执行建表和触发器创建
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            // 开启事务
            conn.setAutoCommit(false);

            try {
                // 创建表
                stmt.execute(createPersonneTable);
                stmt.execute(createDisciplineTable);
                stmt.execute(createTitulaireTable);
                stmt.execute(createEtudiantTable); 
                stmt.execute(createChercheurTable);
                stmt.execute(createMCFTable);
                 stmt.execute(createTitulaire_DisciplineTable);
                // 创建触发器和触发器函数
                stmt.execute(createTriggerFunction);


                // 验证触发器是否附加成功
                try (ResultSet rs = stmt.executeQuery(validateTrigger)) {
                    while (rs.next()) {
                        String tableName = rs.getString("event_object_table");
                        String triggerName = rs.getString("trigger_name");
                        System.out.printf("触发器 %s 已附加到表 %s%n", triggerName, tableName);
                    }
                }

                // 提交事务
                conn.commit();
                System.out.println("所有表和触发器已成功创建！");
            } catch (Exception e) {
                // 如果发生错误，回滚事务
                conn.rollback();
                throw e;
            } finally {
                // 恢复自动提交
                conn.setAutoCommit(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
