package edu.sdccd.cisc191.model;

public class Student {
    private final int id;
    private String name;
    private double gpa;

    public Student(int id, String name, double gpa) {
        //  validate fields and assign them

        if (id <= 0) {
            throw new IllegalArgumentException("Id must be a number bigger than 0");
        } else if (name.isBlank()) {
            throw new IllegalArgumentException("Invalid name");
        } else if (gpa <= 0.0 || gpa >= 4.0) {
            throw new IllegalArgumentException("GPA must be a number between 0.0 and 4.0");
        }

        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("New name must be valid");
        }
        this.name = name;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        if (gpa <= 0.0 || gpa >= 4.0) {
            throw new IllegalArgumentException("New GPA must be between 0 and 4");
        }
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return String.format("Student Info {Id: %d, Name: %s, Gpa: %.2f}", id, name, gpa);
    }
}
