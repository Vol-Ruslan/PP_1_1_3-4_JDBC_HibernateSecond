package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Vasya", "Volkov", (byte) 21);
        userService.saveUser("Kirill", "Pozov", (byte) 45);
        userService.saveUser("Sergey", "Yakovlev", (byte) 24);
        userService.saveUser("Viktoriya", "Shiz", (byte) 30);

        List<User> userList = userService.getAllUsers();

        System.out.println(userList);

    }
}
