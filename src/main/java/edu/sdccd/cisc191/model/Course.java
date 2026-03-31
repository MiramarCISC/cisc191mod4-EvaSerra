package edu.sdccd.cisc191.model;

public class Course {
    private int id;
    private String title;
    private int studentId;

    public Course(int id, String title, int studentId) {
        // validate fields and assign them
        if (id <= 0) {
            throw new IllegalArgumentException("Class Id must be a number bigger than 0");
        } else if (title.isBlank()) {
            throw new IllegalArgumentException("Invalid title");
        } else if (studentId <= 0) {
            throw new IllegalArgumentException("Class Id must be a number bigger than 0");
        }

        this.id = id;
        this.title = title;
        this.studentId = studentId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getStudentId() {
        return studentId;
    }

    @Override
    public String toString() {
        return String.format("Course Info: {Id: %d, Title: %s, Student Id: %d}", id, title, studentId);
    }
}
