package ssvv.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ssvv.example.curent.Curent;
import ssvv.example.domain.Nota;
import ssvv.example.domain.Student;
import ssvv.example.domain.Tema;
import ssvv.example.repository.NotaXMLRepo;
import ssvv.example.repository.StudentXMLRepo;
import ssvv.example.repository.TemaXMLRepo;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;

import static org.junit.jupiter.api.Assertions.*;

public class IncrementalIntegrationMockitoTest {
    Service service;

    StudentValidator studentValidator;
    TemaValidator temaValidator;
    NotaValidator notaValidator;

    @Mock
    StudentXMLRepo studentXMLRepo;
    @Mock
    TemaXMLRepo temaXMLRepo;
    @Mock
    NotaXMLRepo notaXMLRepo;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);

        studentValidator = new StudentValidator();
        temaValidator = new TemaValidator();
        notaValidator = new NotaValidator(studentXMLRepo, temaXMLRepo);

        this.service = new Service(this.studentXMLRepo, studentValidator,
                this.temaXMLRepo, temaValidator,
                this.notaXMLRepo, notaValidator);
    }

    @Test
    public void TestAddStudent_ValidStudent_StudentAddedCorrectly() {
        Student newStudent = new Student("1111", "a b", 999, "aa@yahoo.com");
        Mockito.when(this.studentXMLRepo.save(newStudent)).thenReturn(null);

        Student result = this.service.addStudent(newStudent);
        assertNull(result);
    }

    @Test
    public void TestAddStudentAndAddAssignment_ValidStudentAndAssignment_BothAddedCorrectly() {
        Student newStudent = new Student("1111", "a b", 999, "aa@yahoo.com");
        Tema newTema = new Tema("1", "a", 1, 1);
        Mockito.when(this.studentXMLRepo.save(newStudent)).thenReturn(newStudent);
        Mockito.when(this.temaXMLRepo.save(newTema)).thenReturn(null);

        Student resultStudent = this.service.addStudent(newStudent);
        Tema resultTema = this.service.addTema(newTema);

        assertEquals(resultStudent.getID(), "1111");
        assertNull(resultTema);
    }

    @Test
    public void TestAddStudentAndAddAssignmentAndAddNota_ValidGradeAndStudentAndAssignment_AllAddedCorrectly() {
        Student newStudent = new Student("1111", "a b", 999, "aa@yahoo.com");
        Tema newTema = new Tema("1", "a", 1, 1);
        Nota newNota = new Nota("1111", "1111", "1", 10, Curent.getStartDate().plusWeeks(3));
        Mockito.when(this.studentXMLRepo.save(newStudent)).thenReturn(newStudent);
        Mockito.when(this.temaXMLRepo.save(newTema)).thenReturn(newTema);
        Mockito.when(this.notaXMLRepo.save(newNota)).thenReturn(newNota);

        Student resultStudent = this.service.addStudent(newStudent);
        Tema resultTema = this.service.addTema(newTema);

        assertEquals(resultStudent.getID(), "1111");
        assertEquals(resultTema.getID(), "1");
    }
}