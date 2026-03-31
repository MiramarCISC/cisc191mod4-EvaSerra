package edu.sdccd.cisc191.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        //  create students table if it does not exist

        String sqlStu = "Create TABLE IF NOT EXISTS students(" +
                "id INT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "gpa DOUBLE NOT NULL)";
        //  create courses table if it does not exist

        String sqlCla = "Create TABLE IF NOT EXISTS courses(" +
                "id INT PRIMARY KEY," +
                "title VARCHAR(100) NOT NULL," +
                "student_id INT," +
                "FOREIGN KEY (student_id) " +
                "REFERENCES students(id) ON DELETE CASCADE)";
        try (Connection connection = DatabaseConfig.getConnection()) {
            Statement stmnt = connection.createStatement();
            stmnt.addBatch(sqlStu);
            stmnt.addBatch(sqlCla);

            //Deletes existing data when run avoids mistakes of repeated data
            stmnt.execute("DROP TABLE IF EXISTS courses");
            stmnt.execute("DROP TABLE IF EXISTS students");

            stmnt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}