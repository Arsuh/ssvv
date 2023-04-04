package ssvv.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ssvv.example.domain.Tema;
import ssvv.example.service.Service;
import ssvv.example.validation.ValidationException;
import ssvv.example.validation.TemaValidator;
import ssvv.example.repository.TemaXMLRepo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddAssignmentTest {
    private Service service;

    @BeforeAll
    static void createXML() {
        File xml = new File("fisiere/assignmentTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + "<inbox>" + "</inbox>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setup() {
        this.service = new Service(null, null, new TemaXMLRepo("fisiere/assignmentTest.xml"), new TemaValidator(), null, null);
    }

    @AfterAll
    static void removeXML() {
        new File("fisiere/assignmentTest.xml").delete();
    }

    @Test
    void TestAddAssignment_ValidAssignment_AssignmentAddedCorrectly() {
        Tema newTema = new Tema("1", "a", 1, 1);
        this.service.addTema(newTema);
        assertEquals(this.service.getAllTeme().iterator().next(), newTema);
    }

    @Test
    void TestAddAssignment_EmptyID_ThrowsValidationException() {
        Tema newTema = new Tema("", "a", 1, 1);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }

    @Test
    void TestAddAssignment_NullID_ThrowsValidationException() {
        Tema newTema = new Tema(null, "a", 1, 1);
        assertThrows(NullPointerException.class, () -> this.service.addTema(newTema));
    }

    @Test
    void TestAddAssignment_EmptyDescription_ThrowsValidationException() {
        Tema newTema = new Tema("1", "", 1, 1);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }

    @Test
    void TestAddAssignment_NullDescription_ThrowsValidationException() {
        Tema newTema = new Tema("1", null, 1, 1);
        assertThrows(NullPointerException.class, () -> this.service.addTema(newTema));
    }

    @Test
    void TestAddAssignment_DeadlineTooLarge_ThrowsValidationException() {
        Tema newTema = new Tema("1", "a", 15, 1);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }

    @Test
    void TestAddAssignment_ReceivedTooSmall_ThrowsValidationException() {
        Tema newTema = new Tema("1", "a", 1, 0);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }

    @Test
    void TestAddAssignment_ReceivedTooLarge_ThrowsValidationException() {
        Tema newTema = new Tema("1", "a", 0, 15);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }
}
