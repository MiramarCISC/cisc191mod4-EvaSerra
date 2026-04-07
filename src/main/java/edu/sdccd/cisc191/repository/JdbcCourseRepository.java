package edu.sdccd.cisc191.repository;

import edu.sdccd.cisc191.model.Course;
import edu.sdccd.cisc191.model.Student;
import edu.sdccd.cisc191.util.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcCourseRepository implements CourseRepository {

    @Override
    public void save(Course course) {
        if (course == null) {
            throw new RuntimeException("Course can't be null");
        }

        try(Connection connection = DatabaseConfig.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO courses (id, title, student_id) VALUES (?, ?, ?)" // ensures correct even if order change
            );

            preparedStatement.setInt(1, course.getId());
            preparedStatement.setString(2, course.getTitle());
            preparedStatement.setInt(3, course.getStudentId());

            preparedStatement.executeUpdate();

        } catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @Override
    public List<Course> findByStudentId(int studentId) {
        //  query courses by student_id and map to List<Course>
        if (studentId <= 0) {
            throw new RuntimeException("Student Ids must be a number higher than 0");
        }

        ArrayList<Course> courseList = new ArrayList<>();

        try(Connection conn = DatabaseConfig.getConnection()){
            PreparedStatement prepStatement = conn.prepareStatement(
                    "SELECT * FROM courses WHERE student_id = ?"
            );

            prepStatement.setInt(1, studentId);

            ResultSet result = prepStatement.executeQuery();

            while(result.next()){
                courseList.add(
                    new  Course(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getInt("student_id")
                    )
                );
            }
        } catch (SQLException e){
            throw new RuntimeException();
        }

        return courseList;
    }

    @Override
    public List<Course> findAll() {
        ArrayList<Course> courseList = new ArrayList<>();

        try(Connection conn = DatabaseConfig.getConnection()){
            PreparedStatement prepStatement = conn.prepareStatement(
                    "SELECT * FROM courses"
            );

            ResultSet result = prepStatement.executeQuery();

            while(result.next()){
                courseList.add(
                        new  Course(
                                // won't break even if column order change
                                result.getInt("id"),
                                result.getString("title"),
                                result.getInt("student_id")
                        )
                );
            }
        } catch (SQLException e){
            throw new RuntimeException();
        }
        return courseList;
    }

}
