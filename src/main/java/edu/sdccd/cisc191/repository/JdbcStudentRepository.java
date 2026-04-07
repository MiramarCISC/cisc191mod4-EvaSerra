package edu.sdccd.cisc191.repository;

import edu.sdccd.cisc191.model.Student;
import edu.sdccd.cisc191.util.DatabaseConfig;
import edu.sdccd.cisc191.util.DatabaseInitializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcStudentRepository implements StudentRepository {

    @Override
    public void save(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("A null Student cannot be saved");
        }

        try (Connection connection = DatabaseConfig.getConnection()) {
            PreparedStatement prepStatement = connection.prepareStatement(
                    "INSERT INTO students VALUES (?, ?, ?)" );

            prepStatement.setInt(1, student.getId());
            prepStatement.setString(2, student.getName());
            prepStatement.setDouble(3, student.getGpa());

            prepStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Student findById(int id) {
        if(id <=0){
            throw new IllegalArgumentException("Id must be a number bigger than 0");
        }
        try(Connection conn = DatabaseConfig.getConnection()){
            PreparedStatement prepStatement = conn.prepareStatement(
                    "SELECT * FROM students WHERE id = ?"
            );

            prepStatement.setInt(1, id);

            ResultSet result = prepStatement.executeQuery();

            if(result.first()){
                return new Student(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getDouble("gpa") // use column name instead of number
                );
            } else{ return null; }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> findAll() {
        ArrayList<Student> studentList = new ArrayList<>();

        try(Connection conn = DatabaseConfig.getConnection()) {
            PreparedStatement prepStat = conn.prepareStatement(
                    "SELECT * FROM students ORDER BY id");
            ResultSet result = prepStat.executeQuery();

            while (result.next()){
                studentList.add(
                        new Student(
                                result.getInt("id"),
                                result.getString("name"),
                                result.getDouble("gpa")
                        )
                );
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return  studentList;
    }

    @Override
    public void updateGpa(int id, double newGpa) {

        if (id <= 0) {
            throw new IllegalArgumentException("Id must be a number bigger than 0");
        } else if (!(newGpa >= 0.0 || newGpa >= 4.0)) {
            throw new IllegalArgumentException("The new GPA must be a number between 0.0 and 4.0");
        }

        try(Connection conn = DatabaseConfig.getConnection()) {
            PreparedStatement prepStat = conn.prepareStatement(
                    "UPDATE students SET gpa  = ? WHERE id = ?");

            prepStat.setDouble("gpa", newGpa);
            prepStat.setInt("id", id);

            prepStat.executeUpdate();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be a number bigger than 0");
        }

        try(Connection conn = DatabaseConfig.getConnection()) {
            PreparedStatement prepStat = conn.prepareStatement(
                    "DELETE FROM students WHERE id = ?");

            prepStat.setInt("id", id);

            prepStat.executeUpdate();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
}
