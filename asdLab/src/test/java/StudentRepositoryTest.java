import domain.Department;
import domain.Faculty;
import domain.Student;
import domain.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.InmemoryStudents;
import repository.InmemoryTeachers;
import service.Service;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.Optional;

public class StudentRepositoryTest {
    private InmemoryStudents repository1;


        @BeforeEach
        void setUp() {
            repository1 = new InmemoryStudents();
            Teacher dean = new Teacher(
                    "32873d", "Андрій", "Петренко", "Миколайович", LocalDate.of(1980, 1, 1), "andriw@ukma.edu.ua", "8778999273",
                    Teacher.TeachersPosition.PROFESSOR, Teacher.TeachersDegree.NONE, Teacher.TeachersAcademicTitle.PROFESSOR, LocalDate.of(2010, 9, 1),
                    1.0, null, null
            );

        }

    @Test
    void testFindByPIB() {
        repository1.addStudent("1", "Дар'я", "Бобко", "Ігорівна",
                LocalDate.of(2005,1,1), "email", "phone", "84657", 1, 1, 2024,
                Student.TuitionForm.BUDGET, Student.StudentStatus.STUDYING, null, null);

        Optional<Student> found = repository1.studentFindByPIB("дар'я", "бобко", "ігорівна");

        assertTrue(found.isPresent(), "Має знайти студента");
    }


    @Test
    void testFindById() {

        repository1.addStudent("1","Іван", "Іванов","Іванович",LocalDate.of(2005, 5, 10),
                "ivan@mail.com","+380991112233","73wdhja",1,4, 2023,
                Student.TuitionForm.BUDGET, Student.StudentStatus.STUDYING, null, null);

        Optional<Student> found = repository1.studentFindById("1");
        assertTrue(found.isPresent(), "Студент має бути знайдений в репозиторії");
        assertEquals("Іван", found.get().getFirstName());
        assertEquals("73wdhja", found.get().getStudentCardId());
    }



        @Test
        void testDeleteStudent() {
            repository1.addStudent("1","Іван", "Іванов","Іванович",LocalDate.of(2005, 5, 10),
                    "ivan@mail.com","+380991112233","73wdhja",1,4, 2023,
                    Student.TuitionForm.BUDGET, Student.StudentStatus.STUDYING, null, null);
            repository1.deleteStudent("1");

            assertTrue(repository1.studentFindById("1").isPresent(), "Студент має бути видалений");
        }

        @Test
        void testFindAll() {
            repository1.addStudent("1","Іван", "Іванов","Іванович",LocalDate.of(2005, 5, 10),
                    "ivan@mail.com","+380991112233","q748hjdaq",3,4, 2023,
                    Student.TuitionForm.BUDGET, Student.StudentStatus.STUDYING, null, null);
            repository1.addStudent("2","Олег", "Ковальчук","Іванович",LocalDate.of(2006, 6, 23),
                    "oleg@mail.com","+3809917264923","352748kjf",2,5, 2022,
                    Student.TuitionForm.BUDGET, Student.StudentStatus.STUDYING, null, null);
            assertEquals(2, repository1.getAllStudents().size(), "В списку має бути 2 студенти");
        }

        @Test
        void testUpdateStudent_NotFound() {

            boolean updated = repository1.updateStudent("999", "NewName", "Last", "Mid", "mail", "123", 1, 1, null, null);
            assertFalse(updated, "Студента нема, значить false");
        }
    }


