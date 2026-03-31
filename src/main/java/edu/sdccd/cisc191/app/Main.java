package edu.sdccd.cisc191.app;

import edu.sdccd.cisc191.model.Course;
import edu.sdccd.cisc191.model.Student;
import edu.sdccd.cisc191.repository.CourseRepository;
import edu.sdccd.cisc191.repository.JdbcCourseRepository;
import edu.sdccd.cisc191.repository.JdbcStudentRepository;
import edu.sdccd.cisc191.service.StudentService;
import edu.sdccd.cisc191.util.DatabaseInitializer;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        //  initialize database

        DatabaseInitializer.initialize();

        //  create student service and repositories

        StudentService studentService = new StudentService(new JdbcStudentRepository());
        CourseRepository courseRepository = new JdbcCourseRepository();

        // Creating students

        studentService.addStudent(new Student(1, "Logan", 3.0));
        studentService.addStudent(new Student(2, "Charles", 3.6));
        studentService.addStudent(new Student(3, "Erik", 3.75));
        studentService.addStudent(new Student(4, "Scott", 2.85));
        studentService.addStudent(new Student(5, "Anna Marie", 3.4));
        studentService.addStudent(new Student(6, "Ororo", 3.35));

        // Creating courses

        courseRepository.save(new Course(141, "Pre-Calculus", 6));
        courseRepository.save(new Course(10, "World History", 1));
        courseRepository.save(new Course(20, "Physics", 3));
        courseRepository.save(new Course(30, "Biology", 2));
        courseRepository.save(new Course(40, "Chemistry", 5));
        courseRepository.save(new Course(1001, "English Literature", 4));
        courseRepository.save(new Course(50, "Statistics", 3));

        // print all students and courses
        List<Student> studentList = studentService.getAllStudents();

        System.out.println("Students:");
        studentList.forEach(System.out::println);

        List<Course> courseList = courseRepository.findAll();

        System.out.println("\nCourses:");
        courseList.forEach(System.out::println);

        // find one student by ID
        System.out.println("\nFinding Student with id 1:");
        System.out.println(studentService.getStudent(1));

        // print courses for a student
        System.out.println("\nFinding Courses for Student with id 3:");
        courseList = courseRepository.findByStudentId(3);

        courseList.forEach(System.out::println);

        // update one GPA

        System.out.println("\nUpdating GPA of student with id 6:\nBefore the change:");
        System.out.println(studentService.getStudent(6));
        studentService.changeGpa(6, 3.5);
        System.out.println("After the change:");
        System.out.println(studentService.getStudent(6));

        // Delete one student
        System.out.println("\nDeleting student with id 4:");
        studentService.removeStudent(4);

        // print remaining students and courses
        studentList = studentService.getAllStudents();
        courseList = courseRepository.findAll();

        System.out.println("\nRemaining Students: ");
        studentList.forEach(System.out::println);

        System.out.println("\nRemaining Courses: ");
        courseList.forEach(System.out::println);
    }
}
