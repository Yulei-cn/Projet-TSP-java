package projet_java.Personne;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreatTable {
    public static void Creat() {

        String url = "jdbc:postgresql://localhost:5432/projetSQL"; 
        String user = "postgres"; 
        String password = "zhu"; 


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

        String createTitulaireDisciplineTable = """
                CREATE TABLE IF NOT EXISTS Titulaire_Discipline (
                    ID INT REFERENCES Titulaire(ID),
                    discipline_ID INT REFERENCES Discipline(ID),
                    PRIMARY KEY (ID, discipline_ID)
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
                    ID INT REFERENCES Titulaire(ID),
                    etudiant INT REFERENCES Etudiant(ID),
                    PRIMARY KEY(ID, etudiant)
                );
                """;

        String createMCFTable = """
                CREATE TABLE IF NOT EXISTS MCF (
                    ID INT PRIMARY KEY REFERENCES Titulaire(ID),
                    etudiant INT REFERENCES Etudiant(ID)
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

                    IF discipline_count >= 2 THEN 
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


        String createRoleValidationTrigger = """
				CREATE OR REPLACE FUNCTION validate_unique_role()
				RETURNS TRIGGER AS $$
				BEGIN
				    -- 仅在插入时检测，而不是在自动生成时检测
				    IF TG_OP = 'INSERT' AND NOT EXISTS (
				        SELECT 1 FROM Titulaire WHERE ID = NEW.ID
				    ) THEN
				        IF (NEW.ID IN (SELECT ID FROM Etudiant)) THEN
				            RAISE EXCEPTION 'ID % already exists as an Etudiant', NEW.ID;
				        ELSIF (NEW.ID IN (SELECT ID FROM Titulaire)) THEN
				            RAISE EXCEPTION 'ID % already exists as a Titulaire', NEW.ID;
				        ELSIF (NEW.ID IN (SELECT ID FROM Chercheur)) THEN
				            RAISE EXCEPTION 'ID % already exists as a Chercheur', NEW.ID;
				        END IF;
				    END IF;
				
				    RETURN NEW;
				END;
				$$ LANGUAGE plpgsql;

                CREATE TRIGGER unique_role_trigger
                BEFORE INSERT ON Etudiant
                FOR EACH ROW
                EXECUTE FUNCTION validate_unique_role();

                CREATE TRIGGER unique_role_trigger_titulaire
                BEFORE INSERT ON Titulaire
                FOR EACH ROW
                EXECUTE FUNCTION validate_unique_role();

                CREATE TRIGGER unique_role_trigger_chercheur
                BEFORE INSERT ON Chercheur
                FOR EACH ROW
                EXECUTE FUNCTION validate_unique_role();
                """;


        String createAutoInsertTriggerForMCF = """
                CREATE OR REPLACE FUNCTION auto_insert_titulaire_for_mcf()
                RETURNS TRIGGER AS $$
                BEGIN
                    -- 检查 Personne 是否存在Vérifier si la personne existe
                    IF NOT EXISTS (SELECT 1 FROM Personne WHERE ID = NEW.ID) THEN
                        INSERT INTO Personne (ID, nom, prenom, age, ville)
                        VALUES (NEW.ID, '默认名字', '默认姓氏', 30, '默认城市');
                    END IF;

                    -- 检查 Titulaire 是否存在Vérifier si le titulaire existe
                    IF NOT EXISTS (SELECT 1 FROM Titulaire WHERE ID = NEW.ID) THEN
                        INSERT INTO Titulaire (ID, numBureau)
                        VALUES (NEW.ID, 101); -- 默认办公室编号Numéro de bureau par défaut
                    END IF;

                    RETURN NEW;
                END;
                $$ LANGUAGE plpgsql;

                CREATE TRIGGER auto_insert_titulaire_trigger_for_mcf
                BEFORE INSERT ON MCF
                FOR EACH ROW
                EXECUTE FUNCTION auto_insert_titulaire_for_mcf();
                """;

        // 插入 Chercheur 时自动生成 Titulaire 和 Personne Lors de l'insertion d'un Chercheur, générer automatiquement un Titulaire et une Personne
        String createAutoInsertTriggerForChercheur = """
                CREATE OR REPLACE FUNCTION auto_insert_titulaire_for_chercheur()
                RETURNS TRIGGER AS $$
                BEGIN
                    -- 检查 Personne 是否存在Vérifier si la personne existe
                    IF NOT EXISTS (SELECT 1 FROM Personne WHERE ID = NEW.ID) THEN
                        INSERT INTO Personne (ID, nom, prenom, age, ville)
                        VALUES (NEW.ID, '默认名字', '默认姓氏', 30, '默认城市');
                    END IF;

                    -- 检查 Titulaire 是否存在Vérifier si le titulaire existe
                    IF NOT EXISTS (SELECT 1 FROM Titulaire WHERE ID = NEW.ID) THEN
                        INSERT INTO Titulaire (ID, numBureau)
                        VALUES (NEW.ID, 102); -- 默认办公室编号Numéro de bureau par défaut
                    END IF;

                    RETURN NEW;
                END;
                $$ LANGUAGE plpgsql;

                CREATE TRIGGER auto_insert_titulaire_trigger_for_chercheur
                BEFORE INSERT ON Chercheur
                FOR EACH ROW
                EXECUTE FUNCTION auto_insert_titulaire_for_chercheur();
                """;

        // 执行建表和触发器创建 Exécution de la création des tables et des déclencheurs
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            // 开启事务Démarrer une transaction
            conn.setAutoCommit(false);

            try {
                // 创建表Créer les tables
                stmt.execute(createPersonneTable);
                stmt.execute(createDisciplineTable);
                stmt.execute(createTitulaireTable);
                stmt.execute(createEtudiantTable);
                stmt.execute(createChercheurTable);
                stmt.execute(createMCFTable);
                stmt.execute(createTitulaireDisciplineTable);

                stmt.execute(createTriggerFunction);
                stmt.execute(createRoleValidationTrigger);
                stmt.execute(createAutoInsertTriggerForMCF);
                stmt.execute(createAutoInsertTriggerForChercheur);

                // 提交事务Valider la transaction
                conn.commit();
                System.out.println("所有表和触发器已成功创建！");
            } catch (Exception e) {
                // 如果发生错误，回滚事务En cas d'erreur, annuler la transaction
                conn.rollback();
                throw e;
            } finally {
                // 恢复自动提交Restaurer l'auto-commit
                conn.setAutoCommit(true);
            }

        } catch (Exception e) {
        }
    }
}
