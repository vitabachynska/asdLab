package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Department {
    private String code;
    private String name;
    private Faculty faculty;
    private Teacher head;
    private String location;

    private List<Teacher> teachers = new ArrayList<>();
    private List<Student> students = new ArrayList<>();

    public Department(String code, String name, Faculty faculty, Teacher head, String location) {
        this.code = code;
        this.name = name;
        this.faculty = faculty;
        this.head = head;
        this.location = location;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Faculty getFaculty() { return faculty; }
    public void setFaculty(Faculty faculty) { this.faculty = faculty; }

    public Teacher getHead() { return head; }
    public void setHead(Teacher head) { this.head = head; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }


    public void addStudentDep(String id, String firstName, String lastName, String middleName,
                           LocalDate birthDate, String email, String phone,
                           String studentCardId, int course, int group,
                           int admissionYear, Student.TuitionForm tuitionForm, Student.StudentStatus status, Faculty
                              faculty, Department department) {

        students.add(new Student(id, firstName, lastName, middleName, birthDate,
                email, phone, studentCardId, course, group, admissionYear, tuitionForm, status, faculty, department));

    }

    public void addTeacher(String id, String firstName, String lastName, String middleName,
                           LocalDate birthDate, String email, String phone,
                           Teacher.TeachersPosition position, Teacher.TeachersDegree degree, Teacher.TeachersAcademicTitle academicTitle,
                           LocalDate hireDate, double workload, Faculty faculty, Department department)  {

        teachers.add(new Teacher(id, firstName, lastName, middleName, birthDate,
                email, phone, position, degree, academicTitle, hireDate, workload, faculty, department));

    }

    public void removeStudent(Student student) {
        this.students.remove(student);
    }
    public void removeTeacher(Teacher teacher) {
        this.students.remove(teacher);
    }


}
