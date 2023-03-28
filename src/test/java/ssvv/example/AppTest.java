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
public class AppTest 
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
    public void testInvalidAddStudent() {
        Student student = new Student("666", "nume", -10, "email@domeniu.com");
        assertThrows(ValidationException.class, () -> service.addStudent(student));
    }
}
