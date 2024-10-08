package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class Main {
    public static void main(String[] args) {
        Util util = new Util();
        UserServiceImpl userService = new UserServiceImpl();

        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            userService.createUsersTable();
            // заполняем таблицу данными
            userService.saveUser("il", "gim", (byte) 19);
            userService.saveUser("rus", "sid", (byte) 20);
            userService.saveUser("nan", "nana", (byte) 21);
            session.getTransaction().commit();
            // вывод в консоль данные таблицы
            userService.getAllUsers().forEach(System.out::println);
            // удаление юзера
            userService.removeUserById(2);
            session.getTransaction().commit();
            userService.getAllUsers().forEach(System.out::println);
            // очистка таблицы
            userService.cleanUsersTable();
            session.getTransaction().commit();
            // удаление таблицы
            userService.dropUsersTable();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
