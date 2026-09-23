package app.web;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class AppInit implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            new CommentDao().init();
        } catch (Exception e) {
            throw new RuntimeException(
                    "Database initialization failed", e);
        }
    }
}
