package ssvv.example.app;


import ssvv.example.repository.NotaFileRepository;
import ssvv.example.repository.StudentFileRepository;
import ssvv.example.repository.StudentXMLRepo;
import ssvv.example.repository.TemaXMLRepo;
import ssvv.example.repository.NotaXMLRepo;
import ssvv.example.repository.TemaFileRepository;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;
import ssvv.example.view.UI;



public class MainApplication {

    public static void main(String[] args) {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

//       StudentFileRepository studentFileRepository = new StudentFileRepository(filenameStudent);
//        TemaFileRepository temaFileRepository = new TemaFileRepository(filenameTema);
//        NotaValidator notaValidator = new NotaValidator(studentFileRepository, temaFileRepository);
//        NotaFileRepository notaFileRepository = new NotaFileRepository(filenameNota);

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        UI ui = new UI(service);
        ui.run();
    }

}
