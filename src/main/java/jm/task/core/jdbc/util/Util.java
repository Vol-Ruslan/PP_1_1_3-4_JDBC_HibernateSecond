package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "AdminRoot";

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {

                Configuration configuration = new Configuration();

                Properties settings = new Properties();

//                properties.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
//                properties.put("hibernate.connection.url", URL);
//                properties.put("hibernate.connection.username", USERNAME);
//                properties.put("hibernate.connection.password", PASSWORD);
//                properties.put("hibernate.show_sql", "true");
//                properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//                properties.put("hibernate.current_session_context_class", "thread");
//                properties.put(Environment.HBM2DDL_AUTO, "create-drop");

                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, USERNAME);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static Connection connection() {

        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Ошибка в коннекте");
            e.printStackTrace();
        }
        return null;
    }


}
