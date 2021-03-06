/**
 * 
 */
package DBRPC.DBAcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author jinyu
 *
 */
public class PoolManager {
    public static PoolManager getInstance(){
        PoolManager dBService = null;
        if (dBService == null) {
            dBService = new PoolManager();
        }
        return dBService;
    }

    private HikariDataSource dataSource;
    Connection conn = null ;
    
    // 调用SQL      
    PreparedStatement pst = null;
    private PoolManager()
    {
         init();
    }
private void init()
{
    HikariConfig config = new HikariConfig("config/db.properties");
    dataSource= new HikariDataSource(config);
    
}
public Connection getConnection() throws SQLException {
    try {
        return dataSource.getConnection();
    } catch (SQLException e) {
        e.printStackTrace();
       
        return null;
    }    }

public boolean stop() throws SQLException {
    dataSource.close();
    return true;
}


}
