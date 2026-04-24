import domain.Department;
import domain.Faculty;
import domain.Teacher;
import repository.InmemoryTeachers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Teacher Repository Tests")
public class TeacherRepositoryTest {

    private InmemoryTeachers repository;
    private Faculty testFaculty;
    private Department testDepartment;

    @BeforeEach
    void setUp() {
        repository = new InmemoryTeachers();
        InmemoryTeachers.teachers.clear();
        Teacher deanFI = new Teacher("T-101", "Олена","Ковальчук","Петрівна",LocalDate.of(1978, 10, 12),"o.kovalchuk@ukma.edu.ua", "+380671112233",Teacher.TeachersPosition.PROFESSOR, Teacher.TeachersDegree.PHD, Teacher.TeachersAcademicTitle.NONE, LocalDate.of(2015, 9, 1), 1.0, null,
                null);
        testFaculty = new Faculty("FI", "Факультет інформатики", "ФІ", deanFI, "вул. Волоська 8/5, корпус 4");
        testDepartment = new Department("INF","Кафедра інформатики",testFaculty,deanFI,"вул. Волоська 8/5, корпус 4, к. 321"
        );
    }

    @Test
    @DisplayName("Знайти вчителя за айді - успішно")
    void testFindTeacherByIdSuccess() {
        Teacher teacher = new Teacher("T001", "Іван", "Петренко", "Васильович",
                LocalDate.of(1975, 5, 15), "i@example.com", "+380661234567",
                Teacher.TeachersPosition.PROFESSOR, Teacher.TeachersDegree.PHD,
                Teacher.TeachersAcademicTitle.PROFESSOR, LocalDate.of(2000, 9, 1), 1.0, testFaculty, testDepartment);
        InmemoryTeachers.teachers.add(teacher);

        Optional<Teacher> found = repository.teacherFindById("T001");

        assertTrue(found.isPresent());
        assertEquals("Іван", found.get().getFirstName());
        assertEquals("Петренко", found.get().getLastName());
    }

    @Test
    @DisplayName("Знайти вчителя за айді - не існує")
    void testFindTeacherByIdNotFound() {
        Optional<Teacher> found = repository.teacherFindById("укаргуоумтул");
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Пошук за ПІБ")
    void testFindTeacherByPIBSuccess() {
        Teacher teacher = new Teacher("T002", "Іван", "Петренко", "Васильович",
                LocalDate.of(1980, 3, 20), "i@example.com", "+380661234568",
                Teacher.TeachersPosition.ASSOCIATE_PROFESSOR, Teacher.TeachersDegree.CANDIDATE_OF_SCIENCES,
                Teacher.TeachersAcademicTitle.ASSOCIATE_PROFESSOR, LocalDate.of(2005, 9, 1), 0.75, testFaculty, testDepartment);
        InmemoryTeachers.teachers.add(teacher);

        Optional<Teacher> found = repository.teacherFindByPIB("Іван", "Петренко", "Васильович");

        assertTrue(found.isPresent());
        assertEquals("T002", found.get().getId());
    }

    @Test
    @DisplayName("Знайти вчителя незважаючи на шрифт")
    void testFindTeacherByPIBCaseInsensitive() {
        Teacher teacher = new Teacher("T003", "Іван", "Петренко", "Васильович",
                LocalDate.of(1985, 7, 10), "i@example.com", "+380661234569",
                Teacher.TeachersPosition.ASSOCIATE_PROFESSOR, Teacher.TeachersDegree.NONE,
                Teacher.TeachersAcademicTitle.SENIOR_RESEARCHER, LocalDate.of(2010, 9, 1), 0.5, testFaculty, testDepartment);
        InmemoryTeachers.teachers.add(teacher);

        Optional<Teacher> found = repository.teacherFindByPIB("ІВАН", "Петренко", "Васильович");

        assertTrue(found.isPresent());
        assertEquals("Іван", found.get().getFirstName());
    }

    @Test
    @DisplayName("Знайти неіснуючого вчителя")
    void testFindTeacherByPIBNotFound() {
        Optional<Teacher> found = repository.teacherFindByPIB("NonExistent", "Teacher", "Name");

        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Немає жодного вчителя")
    void testGetAllTeachersEmpty() {
        List<Teacher> teachers = repository.getAllTeachers();
        assertTrue(teachers.isEmpty());
        assertEquals(0, teachers.size());
    }

    @Test
    @DisplayName("Вивести тільки одного")
    void testGetAllTeachersSingle() {
        Teacher teacher = new Teacher("T004", "Іван", "Петренко", "Васильович",
                LocalDate.of(1978, 1, 12), "i@example.com", "+380661234570",
                Teacher.TeachersPosition.PROFESSOR, Teacher.TeachersDegree.PHD,
                Teacher.TeachersAcademicTitle.PROFESSOR, LocalDate.of(2001, 9, 1), 1.0, testFaculty, testDepartment);
        InmemoryTeachers.teachers.add(teacher);

        List<Teacher> teachers = repository.getAllTeachers();

        assertEquals(1, teachers.size());
        assertEquals("Robert", teachers.get(0).getFirstName());
    }

    @Test
    @DisplayName("Вивести множину вчителів")
    void testGetAllTeachersMultiple() {
        for (int i = 0; i < 5; i++) {
            Teacher teacher = new Teacher("T00" + i, "Teacher" + i, "Last" + i, "Middle" + i,
                    LocalDate.of(1980 + i, i + 1, 10), "teacher" + i + "@example.com", "+38066123456" + i,
                    Teacher.TeachersPosition.ASSOCIATE_PROFESSOR, Teacher.TeachersDegree.CANDIDATE_OF_SCIENCES,
                    Teacher.TeachersAcademicTitle.ASSOCIATE_PROFESSOR, LocalDate.of(2005, 9, 1), 0.5, testFaculty, testDepartment);
            InmemoryTeachers.teachers.add(teacher);
        }

        List<Teacher> teachers = repository.getAllTeachers();

        assertEquals(5, teachers.size());
    }

    @Test
    @DisplayName("Повернути викладачів")
    void testGetAllTeachersReturnsSameReference() {
        Teacher teacher = new Teacher("T005", "Марія", "Іванченко", "Сергіївна",
                LocalDate.of(1982, 6, 25), "mary@example.com", "+380661234571",
                Teacher.TeachersPosition.PROFESSOR, Teacher.TeachersDegree.PHD,
                Teacher.TeachersAcademicTitle.PROFESSOR, LocalDate.of(2003, 9, 1), 1.0, testFaculty, testDepartment);
        InmemoryTeachers.teachers.add(teacher);

        List<Teacher> teachers1 = repository.getAllTeachers();
        List<Teacher> teachers2 = repository.getAllTeachers();

        assertSame(teachers1, teachers2);
    }

    @Test
    @DisplayName("Вивести всіх викладачів після додавання")
    void testGetAllTeachersAfterAdding() {
        InmemoryTeachers.teachers.add(new Teacher("T006", "Давид", "Іваненко", "Максимович",
                LocalDate.of(1979, 2, 8), "david@example.com", "+380661234572",
                Teacher.TeachersPosition.ASSOCIATE_PROFESSOR, Teacher.TeachersDegree.CANDIDATE_OF_SCIENCES,
                Teacher.TeachersAcademicTitle.ASSOCIATE_PROFESSOR, LocalDate.of(2004, 9, 1), 0.6, testFaculty, testDepartment));

        List<Teacher> teachers = repository.getAllTeachers();

        assertEquals(1, teachers.size());
    }

    @Test
    @DisplayName("Онвлення викладача")
    void testUpdateTeacherSuccess() {
        Teacher teacher = new Teacher("T007", "Ема", "Денисенко", "Андріївна",
                LocalDate.of(1981, 9, 30), "emma@example.com", "+380661234573",
                Teacher.TeachersPosition.ASSOCIATE_PROFESSOR, Teacher.TeachersDegree.CANDIDATE_OF_SCIENCES,
                Teacher.TeachersAcademicTitle.ASSOCIATE_PROFESSOR, LocalDate.of(2006, 9, 1), 0.7, testFaculty, testDepartment);
        InmemoryTeachers.teachers.add(teacher);

        boolean updated = repository.updateTeacher("T007", "Ема", "Денисенко", "Андріївна",
                "emma.new@example.com", "+380661234599",
                Teacher.TeachersPosition.PROFESSOR, Teacher.TeachersDegree.PHD,
                Teacher.TeachersAcademicTitle.PROFESSOR, 1.0);

        assertTrue(updated);
        assertEquals("emma.new@example.com", teacher.getEmail());
        assertEquals("+380661234599", teacher.getPhone());
        assertEquals(Teacher.TeachersPosition.PROFESSOR, teacher.getPosition());
        assertEquals(1.0, teacher.getWorkload());
    }

    @Test
    @DisplayName("Не знайдено викладача на оновлення")
    void testUpdateTeacherNotFound() {
        boolean updated = repository.updateTeacher("NONEXISTENT", "І'мя", "Прізвище", "Побатькові",
                "email@example.com", "+380661234574",
                Teacher.TeachersPosition.PROFESSOR, Teacher.TeachersDegree.PHD,
                Teacher.TeachersAcademicTitle.PROFESSOR, 1.0);

        assertFalse(updated);
    }

    @Test
    @DisplayName("Зміна тільки імені у викладача")
    void testUpdateTeacherFirstName() {
        Teacher teacher = new Teacher("T008", "Original", "Name", "Middle",
                LocalDate.of(1983, 4, 14), "original@example.com", "+380661234575",
                Teacher.TeachersPosition.ASSOCIATE_PROFESSOR, Teacher.TeachersDegree.CANDIDATE_OF_SCIENCES,
                Teacher.TeachersAcademicTitle.ASSOCIATE_PROFESSOR, LocalDate.of(2007, 9, 1), 0.5, testFaculty, testDepartment);
        InmemoryTeachers.teachers.add(teacher);

        repository.updateTeacher("T008", "Updated", "Name", "Middle",
                "original@example.com", "+380661234575",
                Teacher.TeachersPosition.ASSOCIATE_PROFESSOR, Teacher.TeachersDegree.CANDIDATE_OF_SCIENCES,
                Teacher.TeachersAcademicTitle.ASSOCIATE_PROFESSOR, 0.5);

        assertEquals("Updated", teacher.getFirstName());
    }

    @Test
    @DisplayName("Оновити дані про викладача змінивши посаду")
    void testUpdateTeacherDegree() {
        Teacher teacher = new Teacher("T009", "Вячеслав", "Петренко", "Петрович",
                LocalDate.of(1984, 11, 22), "v@example.com", "+380661234576",
                Teacher.TeachersPosition.ASSOCIATE_PROFESSOR, Teacher.TeachersDegree.NONE,
                Teacher.TeachersAcademicTitle.NONE, LocalDate.of(2008, 9, 1), 0.4, testFaculty, testDepartment);
        InmemoryTeachers.teachers.add(teacher);

        repository.updateTeacher("T009",  "Вячеслав", "Петренко", "Петрович",
                "v@example.com", "+380661234576",
                Teacher.TeachersPosition.PROFESSOR, Teacher.TeachersDegree.PHD,
                Teacher.TeachersAcademicTitle.PROFESSOR, 1.0);

        assertEquals(Teacher.TeachersDegree.PHD, teacher.getDegree());
        assertEquals(Teacher.TeachersAcademicTitle.PROFESSOR, teacher.getAcademicTitle());
    }

    @Test
    @DisplayName("Оновити викладача зберігаючи айді")
    void testUpdateTeacherPreservesId() {
        Teacher teacher = new Teacher("T010", "Миколи", "Козак", "Георгович",
                LocalDate.of(1977, 8, 5), "grace@example.com", "+380661234577",
                Teacher.TeachersPosition.PROFESSOR, Teacher.TeachersDegree.PHD,
                Teacher.TeachersAcademicTitle.PROFESSOR, LocalDate.of(2002, 9, 1), 1.0, testFaculty, testDepartment);
        InmemoryTeachers.teachers.add(teacher);
        String originalId = teacher.getId();

        repository.updateTeacher("T010", "Микола", "Козак", "Георгович",
                "grace.new@example.com", "+380661234577",
                Teacher.TeachersPosition.PROFESSOR, Teacher.TeachersDegree.PHD,
                Teacher.TeachersAcademicTitle.PROFESSOR, 1.0);

        assertEquals(originalId, teacher.getId());
    }

    @ParameterizedTest
    @ValueSource(strings = {"T011", "T012", "T013", "T014", "T015"})
    @DisplayName("Знайти викладача за ID - Parameterized")
    void testFindTeacherByIdParameterized(String teacherId) {
        Teacher teacher = new Teacher(teacherId, "Викладач", "Ім'я", "Побатькові",
                LocalDate.of(1980, 1, 1), "teacher@example.com", "+380661234578",
                Teacher.TeachersPosition.ASSOCIATE_PROFESSOR, Teacher.TeachersDegree.CANDIDATE_OF_SCIENCES,
                Teacher.TeachersAcademicTitle.ASSOCIATE_PROFESSOR, LocalDate.of(2009, 9, 1), 0.5, testFaculty, testDepartment);
        InmemoryTeachers.teachers.add(teacher);

        Optional<Teacher> found = repository.teacherFindById(teacherId);

        assertTrue(found.isPresent());
        assertEquals(teacherId, found.get().getId());
    }

    @ParameterizedTest
    @CsvSource({
            "Михайло, Ілля, Павло, PROFESSOR, PHD",
            "Анастасія, Олександра, Марія, ASSOCIATE_PROFESSOR, CANDIDATE_OF_SCIENCES",
            "Том, Артур, Андрій, PROFESSOR, NONE",
            "Андрій, Тайлер, Ема, PROFESSOR, PHD",
            "Данило, Джейк, Георгій, ASSOCIATE_PROFESSOR, CANDIDATE_OF_SCIENCES"
    })
    @DisplayName("Test 21-25: Знайти викладача за ПІБ та атрибутами - Parameterized CSV")
    void testFindTeacherByVariousAttributes(String firstName, String lastName, String middleName, String position, String degree) {
        Teacher.TeachersPosition pos = Teacher.TeachersPosition.valueOf(position);
        Teacher.TeachersDegree deg = Teacher.TeachersDegree.valueOf(degree);
        Teacher.TeachersAcademicTitle title = Teacher.TeachersAcademicTitle.NONE;

        Teacher teacher = new Teacher("T" + System.nanoTime(), firstName, lastName, middleName,
                LocalDate.of(1980, 1, 1), "email@example.com", "+380661234579",
                pos, deg, title, LocalDate.of(2010, 9, 1), 0.5, testFaculty, testDepartment);
        InmemoryTeachers.teachers.add(teacher);

        Optional<Teacher> found = repository.teacherFindByPIB(firstName, lastName, middleName);

        assertTrue(found.isPresent());
        assertEquals(firstName, found.get().getFirstName());
        assertEquals(lastName, found.get().getLastName());
        assertEquals(deg, found.get().getDegree());
    }


    static Stream<String> provideEmails() {
        return Stream.of(
                "test1@example.com",
                "test2@university.edu",
                "test3@mail.com",
                "test4@domain.org",
                "test5@institute.com"
        );
    }

    @ParameterizedTest
    @MethodSource("provideEmails")
    @DisplayName("Оновлення email викладача - Method Source Parameterized")
    void testUpdateTeacherEmailsMethodSource(String newEmail) {
        String teacherId = "T" + System.nanoTime();
        Teacher teacher = new Teacher(teacherId, "Іван", "Петренко", "Васильович",
                LocalDate.of(1986, 12, 10), "old@example.com", "+380661234580",
                Teacher.TeachersPosition.PROFESSOR, Teacher.TeachersDegree.PHD,
                Teacher.TeachersAcademicTitle.PROFESSOR, LocalDate.of(2011, 9, 1), 1.0, testFaculty, testDepartment);
        InmemoryTeachers.teachers.add(teacher);

        boolean updated = repository.updateTeacher(teacherId, "Іван", "Петренко", "Васильович",
                newEmail, "+380661234580",
                Teacher.TeachersPosition.PROFESSOR, Teacher.TeachersDegree.PHD,
                Teacher.TeachersAcademicTitle.PROFESSOR, 1.0);

        assertTrue(updated);
        assertEquals(newEmail, teacher.getEmail());
    }
}
