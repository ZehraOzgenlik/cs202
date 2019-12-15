package tr.edu.ozyegin.cs202.listener;

import tr.edu.ozyegin.cs202.repository.DatabaseManager;
import tr.edu.ozyegin.cs202.util.Utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            DatabaseManager.openConnection();
        } catch (Exception e) {
            Utils.logError(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            DatabaseManager.closeConnection();
        } catch (SQLException e) {
            Utils.logError(e);
        }
    }
}
