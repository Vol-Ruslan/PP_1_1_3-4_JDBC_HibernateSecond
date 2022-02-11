package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static long ID = 1;

    public void createUsersTable() {

        try (Statement statement = Util.connection().createStatement()) {
            String SQL = "SHOW TABLES LIKE 'users'";
            ResultSet resultSet = statement.executeQuery(SQL);
            if (resultSet.next()) {
                if (!(resultSet.getString(1).equals("users"))) {
                    String SQLSecond = "CREATE TABLE `mydbtest`.`users` (\n" + "  `id` INT NOT NULL,\n"
                            + "  `name` VARCHAR(45) NULL,\n"
                            + "  `lastName` VARCHAR(45) NULL,\n"
                            + "  `age` INT NULL);";
                    statement.executeUpdate(SQLSecond);
                }
            } else {
                String SQLSecond = "CREATE TABLE `mydbtest`.`users` (\n" + "  `id` INT NOT NULL,\n"
                        + "  `name` VARCHAR(45) NULL,\n"
                        + "  `lastName` VARCHAR(45) NULL,\n"
                        + "  `age` INT NULL);";
                statement.executeUpdate(SQLSecond);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.connection().createStatement()) {

            String SQL = "SHOW TABLES LIKE 'users'";
            ResultSet resultSet = statement.executeQuery(SQL);

            if (resultSet.next()) {
                if (resultSet.getString(1).equals("users")) {

                    String SQLSecond = "DROP TABLE users";
                    statement.executeUpdate(SQLSecond);

                    ID = 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = Util.connection().createStatement()) {
            String SQL = "SHOW TABLES LIKE 'users'";
            ResultSet resultSet = statement.executeQuery(SQL);
            if (resultSet.next()) {
                if (resultSet.getString(1).equals("users")) {
                    try (PreparedStatement preparedStatement =
                                 Util.connection().prepareStatement("INSERT INTO users VALUES(?,?,?,?)")) {
                        preparedStatement.setLong(1, ID++);
                        preparedStatement.setString(2, name);
                        preparedStatement.setString(3, lastName);
                        preparedStatement.setByte(4, age);

                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println("Opa");
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка в сохранении");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = Util.connection()
                .prepareStatement("DELETE FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = Util.connection().createStatement()) {
            String SQL = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.connection().createStatement()) {
            String SQL = "TRUNCATE users";
            statement.executeUpdate(SQL);
            ID = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
