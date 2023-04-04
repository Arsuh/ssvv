package ssvv.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ssvv.example.domain.Student;
import ssvv.example.repository.StudentXMLRepo;
import ssvv.example.service.Service;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.ValidationException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class AddStudentTest
{
    private StudentXMLRepo studentFileRepository;
    private StudentValidator studentValidator;
    private Service service;

    @BeforeAll
    public static void createXML() {
        File xml = new File("fisiere/studentiTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"+
                    "<inbox>"+

                    "</inbox>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setup() {
        this.studentFileRepository = new StudentXMLRepo("fisiere/studentiTest.xml");
        this.studentValidator = new StudentValidator();
        this.service = new Service(this.studentFileRepository, this.studentValidator, null, null, null, null);
    }

    @AfterAll
    public static void removeXML() {
        new File("fisiere/studentiTest.xml").delete();
    }

    @Test
    public void testValidAddStudent() {
        Student student = new Student("555", "nume", 10, "email@domeniu.com");
        assertNull(service.addStudent(student));
    }

    @Test
    public void testInvalidStudentId() {
        Student student = new Student(null, "nume", 10, "email@domeniu.com");
        assertThrows(NullPointerException.class, () -> service.addStudent(student));
    }

    @Test
    public void testInvalidStudentName_null() {
        Student newStudent = new Student("1111", null, 999, "aa@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void testInvalidStudentName_empty() {
        Student newStudent = new Student("1111", "", 999, "aa@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void testInvalidStudentGroup() {
        Student student = new Student("666", "nume", -10, "email@domeniu.com");
        assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    public void testInvalidStudentEmail_null() {
        Student student = new Student("666", "nume", 10, null);
        assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    public void testInvalidStudentEmail_empty() {
        Student student = new Student("666", "nume", 10, "");
        assertThrows(ValidationException.class, () -> service.addStudent(student));
    }
}
