package paoo.cappuccino.dal.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import paoo.cappuccino.config.AppContext;
import paoo.cappuccino.dal.IDalBackend;
import paoo.cappuccino.dal.IDalService;

public class testTEMPORAIRE {

  public static void main(String[] args) {

    try {
      System.out.println("start");
      AppContext.INSTANCE.setup("Cappuccino", "0.0.1", "test");
      IDalService service = (IDalService) MonoThread.getSqlService();
      IDalBackend serviceBis = (IDalBackend) MonoThread.getSqlService();
      if (service.startTransaction()) {
        PreparedStatement ps = serviceBis.getPrepardedStatement("SELECT * FROM businessDays.users");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
          System.out.println(rs.getString(5));
        }
      }

    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }


}
