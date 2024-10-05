package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        Util util = new Util();

        String query;
        Transaction transaction = null;
        List<User> users = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            String createTable = "CREATE TABLE IF NOT EXISTS mydbtest.users" +
                    "(id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(256) NOT NULL, " +
                    "lastname VARCHAR(256), " +
                    "age TINYINT)";
            transaction = session.beginTransaction();
            session.createSQLQuery(createTable).executeUpdate();
            session.save(new User("il", "gm", (byte) 19));
            session.save(new User("rus", "sid", (byte) 24));
            users = session.createQuery("from users", User.class).list();
            users.forEach(System.out::println);


            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
