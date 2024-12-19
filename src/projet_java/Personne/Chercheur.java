package projet_java.Personne;


import java.util.Set;
import projet_java.BDConnect;
import projet_java.Geographie.Ville;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

class Chercheur extends Titulaire {
    private Set<Etudiant> etudiants;

    public Chercheur(String nom, String prenom, int age, Ville ville, Set<Discipline> disciplines, int numBureau, Set<Etudiant> etudiants) {
        super(nom, prenom, age, ville, disciplines, numBureau);
        this.etudiants = etudiants;
    }

    public Set<Etudiant> getEtudiants() {
        return etudiants;
    }

    public static void SupprimeChercheur(Integer ID,  Integer Etudiant_ID) {
        // 插入数据的 SQL 语句
        StringBuilder delete = new StringBuilder( "DELETE FROM chercheur");

        delete.append("WHERE 1=1 ");
		if (ID != null) delete.append("AND ID =  ? ");
		if (Etudiant_ID != null) delete.append("AND Etudiant_ID =  ? ");
		
        // 使用 BDConnect 获取连接并插入数据
        try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(delete.toString())) {

            // 设置占位符参数
        int paramIndex = 1;
		if (ID != null) pstmt.setInt(paramIndex++, ID);
		if (Etudiant_ID != null) pstmt.setInt(paramIndex++, Etudiant_ID);



            // 执行插入
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Etudiant 数据删除成功！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Chercheur{" +
                "nom='" + getNom() + '\'' +
                ", etudiants=" + etudiants +
                '}';
    }
}