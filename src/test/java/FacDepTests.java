import domain.Department;
import domain.Faculty;
import domain.Student;
import domain.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Faculty and Department Tests")
public class FacDepTests {

    private Faculty faculty;
    private Department department;
    private Teacher dean;
    private Teacher head;

    @BeforeEach
    void setUp() {
        dean = new Teacher("T001", "Іван", "Петренко", "Васильович",
                LocalDate.of(1975, 5, 15), "john@example.com", "+380661234567",
                Teacher.TeachersPosition.PROFESSOR, Teacher.TeachersDegree.PHD,
                Teacher.TeachersAcademicTitle.PROFESSOR, LocalDate.of(2000, 9, 1), 1.0, null, null);

        head = new Teacher("T002", "Олександр", "Коваленко", "Миколайович",
                LocalDate.of(1980, 3, 20), "jane@example.com", "+380661234568",
                Teacher.TeachersPosition.ASSOCIATE_PROFESSOR, Teacher.TeachersDegree.CANDIDATE_OF_SCIENCES,
                Teacher.TeachersAcademicTitle.ASSOCIATE_PROFESSOR, LocalDate.of(2005, 9, 1), 0.75, null, null);

        faculty = new Faculty("F001", "Факультет науки", "FS", dean, "+380441234567");
        department = new Department("D001", "Комп'ютерні науки", faculty, head, "Корпус 10");
    }

    @Test
    @DisplayName("Факультет конструктор з усіма параметрами")
    void testFacultyConstructor() {
        assertEquals("F001", faculty.getCode());
        assertEquals("Факультет науки", faculty.getName());
        assertEquals("FS", faculty.getShortName());
        assertEquals(dean, faculty.getDean());
        assertEquals("+380441234567", faculty.getContacts());
    }

    @Test
    @DisplayName("Факультет конструктор за замовчуванням")
    void testFacultyDefaultConstructor() {
        Faculty emptyFaculty = new Faculty();
        assertNull(emptyFaculty.getCode());
        assertNull(emptyFaculty.getName());
        assertNull(emptyFaculty.getShortName());
        assertNull(emptyFaculty.getDean());
        assertNull(emptyFaculty.getContacts());
    }

    @Test
    @DisplayName("Встановлення факультету")
    void testFacultySetters() {
        faculty.setCode("F002");
        faculty.setName("Faculty of Arts");
        faculty.setShortName("FA");
        faculty.setContacts("+380441234568");

        assertEquals("F002", faculty.getCode());
        assertEquals("Faculty of Arts", faculty.getName());
        assertEquals("FA", faculty.getShortName());
        assertEquals("+380441234568", faculty.getContacts());
    }

    @Test
    @DisplayName("Вставлення нового декана для факультету")
    void testFacultyDeanSetter() {
        Teacher newDean = new Teacher("T003", "Тетяна", "Кравченко", "Михайлівна",
                LocalDate.of(1970, 1, 1), "bob@example.com", "+380661234569",
                Teacher.TeachersPosition.PROFESSOR, Teacher.TeachersDegree.PHD,
                Teacher.TeachersAcademicTitle.PROFESSOR, LocalDate.of(1995, 9, 1), 1.0, null, null);

        faculty.setDean(newDean);
        assertEquals(newDean, faculty.getDean());
    }

    @Test
    @DisplayName("Факультет стрігом з деканом")
    void testFacultyToStringWithDean() {
        String expected = "Код : F001,\nПовна назва : Факультет науки\nСкорочена назва FS\nКонтакти : +380441234567\nДекан : Петренко Іван Васильович \n------------------------";
        assertEquals(expected, faculty.toString());
    }

    @Test
    @DisplayName("Кафедра конструктор з усіма параметрами")
    void testDepartmentConstructor() {
        assertEquals("D001", department.getCode());
        assertEquals("Комп'ютерні науки", department.getName());
        assertEquals(faculty, department.getFaculty());
        assertEquals(head, department.getHead());
        assertEquals("Корпус 10", department.getLocation());
    }

    @Test
    @DisplayName("Кафедра конструктор за замовчуванням")
    void testDepartmentDefaultConstructor() {
        Department emptyDept = new Department();
        assertNull(emptyDept.getCode());
        assertNull(emptyDept.getName());
        assertNull(emptyDept.getFaculty());
        assertNull(emptyDept.getHead());
        assertNull(emptyDept.getLocation());
    }

    @Test
    @DisplayName("Встановлення значень для кафедри")
    void testDepartmentSetters() {
        department.setCode("D002");
        department.setName("Комп'ютерні науки");
        department.setLocation("Корпус 10");

        assertEquals("D002", department.getCode());
        assertEquals("Комп'ютерні науки", department.getName());
        assertEquals("Корпус 10", department.getLocation());
    }

    @Test
    @DisplayName("Встановлення завідувача кафедри")
    void testDepartmentHeadSetter() {
        Teacher newHead = new Teacher("T004", "Тетяна", "Кравченко", "Михайлівна",
                LocalDate.of(1985, 7, 10), "t@example.com", "+380661234570",
                Teacher.TeachersPosition.ASSOCIATE_PROFESSOR, Teacher.TeachersDegree.CANDIDATE_OF_SCIENCES,
                Teacher.TeachersAcademicTitle.ASSOCIATE_PROFESSOR, LocalDate.of(2010, 9, 1), 0.5, null, null);

        department.setHead(newHead);
        assertEquals(newHead, department.getHead());
    }

    @Test
    @DisplayName("Виводить кафедру стрінгом")
    void testDepartmentToStringWithHead() {
        String expected = "Код : D001,\nПовна назва : Комп'ютерні науки\nЛокація Корпус 10\nФакультет : Факультет науки\nЗавідувач : Коваленко Олександр Миколайович \n------------------------";
        assertEquals(expected, department.toString());
    }

    @Test
    @DisplayName("Факультет додає кафедру за параметрами")
    void testFacultyAddDepartmentByParameters() {
        faculty.addDepartment("D002", "Комп'ютерні науки", faculty, head, "Корпус 10");

        List<Department> departments = faculty.getDepartments();
        assertEquals(1, departments.size());
        assertEquals("Комп'ютерні науки", departments.get(0).getName());
        assertEquals("Корпус 10", departments.get(0).getLocation());
    }

    @Test
    @DisplayName("Факультет знаходить кафедру за іменем")
    void testFacultyFindDepartmentByNameSuccess() {
        faculty.addDepartment(department);

        Department found = faculty.findDepartmentByName("Комп'ютерні науки");
        assertNotNull(found);
        assertEquals("D001", found.getCode());
    }

    @Test
    @DisplayName("Факультет шукає кафедру за назвою - відсутня")
    void testFacultyFindDepartmentByNameNotFound() {
        faculty.addDepartment(department);

        Department found = faculty.findDepartmentByName("NonExistent");
        assertNull(found);
    }

    @Test
    @DisplayName("Факультет видаляє кафедру - успішно")
    void testFacultyDeleteDepartmentSuccess() {
        faculty.addDepartment(department);

        boolean deleted = faculty.deleteDepartment("Комп'ютерні науки");
        assertTrue(deleted);
        assertTrue(faculty.getDepartments().isEmpty());
    }

    @Test
    @DisplayName("Кафедра додає студента")
    void testDepartmentAddStudent() {
        Student student = new Student("S001", "Іван", "Петренко", "Васильович",
                LocalDate.of(2000, 5, 15), "i@example.com", "+380661234571",
                "SC001", 3, 101, 2019, Student.TuitionForm.BUDGET,
                Student.StudentStatus.STUDYING, faculty, department);

        department.addStudent(student);

        List<Student> students = department.getStudents();
        assertEquals(1, students.size());
        assertEquals("Іван", students.get(0).getFirstName());
    }

    @Test
    @DisplayName("Кафедра додає викладача")
    void testDepartmentAddTeacher() {
        Teacher teacher = new Teacher("T005", "Іван", "Петренко", "Васильович",
                LocalDate.of(1982, 9, 20), "i@example.com", "+380661234572",
                Teacher.TeachersPosition.ASSOCIATE_PROFESSOR, Teacher.TeachersDegree.CANDIDATE_OF_SCIENCES,
                Teacher.TeachersAcademicTitle.ASSOCIATE_PROFESSOR, LocalDate.of(2007, 9, 1), 0.6, faculty, department);

        department.addTeacher(teacher);

        List<Teacher> teachers = department.getTeachers();
        assertEquals(1, teachers.size());
        assertEquals("Іван", teachers.get(0).getFirstName());
    }

    @Test
    @DisplayName("Кафедра видаляє студента")
    void testDepartmentRemoveStudent() {
        Student student = new Student("S002", "Іван", "Петренко", "Васильович",
                LocalDate.of(1999, 3, 10), "i@example.com", "+380661234573",
                "SC002", 2, 102, 2020, Student.TuitionForm.CONTRACT,
                Student.StudentStatus.STUDYING, faculty, department);

        department.addStudent(student);
        assertEquals(1, department.getStudents().size());

        department.removeStudent(student);
        assertTrue(department.getStudents().isEmpty());
    }

    @Test
    @DisplayName("Видалення викладача з кафедри")
    void testDepartmentRemoveTeacher() {
        Teacher teacher = new Teacher("T006", "Ольга", "Іванова", "Миколаївна",
                LocalDate.of(1983, 11, 5), "o@example.com", "+380661234574",
                Teacher.TeachersPosition.PROFESSOR, Teacher.TeachersDegree.PHD,
                Teacher.TeachersAcademicTitle.PROFESSOR, LocalDate.of(2008, 9, 1), 0.8, faculty, department);

        department.addTeacher(teacher);
        assertEquals(1, department.getTeachers().size());

        department.removeTeacher(teacher);
        assertTrue(department.getTeachers().isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "Математика, корпус 1",
            "Фізика, корпус 5",
            "Хімія, корпус 9",
            "Біологія, корпус 11",
    })
    @DisplayName("Додавання факультетів і кафедр")
    void testFacultyAddMultipleDepartments(String deptName, String location) {
        faculty.addDepartment("D" + deptName.substring(0, 2).toUpperCase(), deptName, faculty, head, location);

        List<Department> departments = faculty.getDepartments();
        assertEquals(1, departments.size());

        Department addedDept = departments.get(0);
        assertEquals(deptName, addedDept.getName());
        assertEquals(location, addedDept.getLocation());
        assertEquals(faculty, addedDept.getFaculty());
        assertEquals(head, addedDept.getHead());
    }
}
