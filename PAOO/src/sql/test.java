package sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test {

	public static void main(String[] args) {
		DALServices services = new DALServicesImpl();

		try {
			Statement pr = services.getPrepardedStatement();
			ResultSet rs = pr.executeQuery("SELECT * FROM businessDays.users");
			services.closeStatement(pr);

			while (rs.next()) {
				System.out.println(rs.getString("username"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}
}
