package projet_java.Personne;

import java.util.Set;
import projet_java.BDConnect;
import projet_java.Geographie.Ville;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

class MCF extends Titulaire {
    private Etudiant etudiant;

    public MCF(String nom, String prenom, int age, Ville ville, Set<Discipline> disciplines, int numBureau, Etudiant etudiant) {
        super(nom, prenom, age, ville, disciplines, numBureau);
        this.etudiant = etudiant;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

   public static void SupprimeMCF(Integer ID,  Integer etudiant) {
        // 插入数据的 SQL 语句
        StringBuilder delete = new StringBuilder( "DELETE FROM MCF");

        delete.append("WHERE 1=1 ");
		if (ID != null) delete.append("AND ID =  ? ");
		if (etudiant != null) delete.append("AND etudiant =  ? ");
		
        // 使用 BDConnect 获取连接并插入数据
        try (Connection conn = BDConnect.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(delete.toString())) {

            // 设置占位符参数
        int paramIndex = 1;
		if (ID != null) pstmt.setInt(paramIndex++, ID);
		if (etudiant != null) pstmt.setInt(paramIndex++, etudiant);



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
        return "MCF{" +
                "nom='" + getNom() + '\'' +
                ", etudiant=" + etudiant.getNom() +
                '}';
    }
}











