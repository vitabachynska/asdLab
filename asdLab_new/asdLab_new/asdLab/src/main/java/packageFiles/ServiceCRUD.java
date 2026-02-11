package packageFiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ServiceCRUD {
    private University university;
    private final List<Student> students = new ArrayList<>();
    private final List<Teacher> teachers = new ArrayList<>();
    private List<Faculty> faculties = new ArrayList<>();

    public Optional<Student> studentFindById(String id) {
        return students.stream().filter(s -> s.getId().equals(id)).findFirst();
    }
    public Optional<Teacher> teacherFindById(String id) {
        return teachers.stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    public Optional<Student> studentFindByPIB(String firstName, String lastName, String middleName) {
        return students.stream().filter(s -> s.getFirstName().equalsIgnoreCase(firstName)&&s.getLastName().equalsIgnoreCase(lastName)
                &&s.getMiddleName().equalsIgnoreCase(middleName)).findFirst();
    }
    public Optional<Teacher> teacherFindByPIB(String firstName, String lastName, String middleName) {
        return teachers.stream().filter(t -> t.getFirstName().equalsIgnoreCase(firstName)&&t.getLastName().equalsIgnoreCase(lastName)
                &&t.getMiddleName().equalsIgnoreCase(middleName)).findFirst();
    }
    public Teacher deanFindByPIB(String firstName, String lastName, String middleName) {
        return teachers.stream()
                .filter(t -> t.getFirstName().equalsIgnoreCase(firstName)
                        && t.getLastName().equalsIgnoreCase(lastName)
                        && t.getMiddleName().equalsIgnoreCase(middleName)).findFirst().orElse(null);
    }

    public List<Student> findByCourse(int course) {
        return students.stream().filter(s -> s.getCourse() == course).toList();
    }
    public List<Student> findByGroup(int group) {
        return students.stream().filter(s -> s.getGroup() == group).toList();
    }
    public List<Student> sortByCourse(List<Student> students) {
        return students.stream().sorted(Comparator.comparingInt(Student::getCourse)).toList();
    }

    public void addUniversity(String fullName, String shortName, String city, String address){
        this.university = new University(fullName, shortName, city, address);

    }
    public University getUniversity(){
        return university;
    }

    public void addFaculty(String code, String name, String shortName, Teacher dean, String contacts) {
        faculties.add(new Faculty(code, name, shortName, dean, contacts));
    }


    public void addStudent(String id, String firstName, String lastName, String middleName,
                           LocalDate birthDate, String email, String phone,
                           String studentCardId, int course, int group,
                           int admissionYear, Student.TuitionForm tuitionForm, Student.StudentStatus status) {

        students.add(new Student(id, firstName, lastName, middleName, birthDate,
                email, phone, studentCardId, course, group, admissionYear, tuitionForm, status));

    }

    public List<Student> getAllStudents() {
        return students;
    }
    public List<Teacher> getAllTeachers() {
        return teachers;
    }
    public List<Faculty> getAllFaculties() {
        return faculties;
    }


    public boolean updateStudent(String id, String newFirstName, String newLastName, String newMiddleName,
                                 String newEmail, String newPhone,
                                 int newCourse, int newGroup,
                                 Student.TuitionForm newTuitionForm, Student.StudentStatus newStatus) {
        Optional<Student> optionalStudent = studentFindById(id);
        if (optionalStudent.isPresent()) {
            Student s = optionalStudent.get();
            s.setFirstName(newFirstName);
            s.setLastName(newLastName);
            s.setMiddleName(newMiddleName);
            s.setEmail(newEmail);
            s.setPhone(newPhone);
            s.setCourse(newCourse);
            s.setGroup(newGroup);
            s.setTuitionForm(newTuitionForm);
            s.setStatus(newStatus);
            return true;
        }
        return false;
    }

    public boolean updateTeacher(String id, String newFirstName, String newLastName, String newMiddleName,
                                 String newEmail, String newPhone,
                                 Teacher.TeachersPosition position, Teacher.TeachersDegree degree,
                                 Teacher.TeachersAcademicTitle academicTitle, double workload) {
        Optional<Teacher> optionalTeacher = teacherFindById(id);
        if (optionalTeacher.isPresent()) {
            Teacher t = optionalTeacher.get();
            t.setFirstName(newFirstName);
            t.setLastName(newLastName);
            t.setMiddleName(newMiddleName);
            t.setEmail(newEmail);
            t.setPhone(newPhone);
            t.setPosition(position);
            t.setDegree(degree);
            t.setAcademicTitle(academicTitle);
            t.setWorkload(workload);
            return true;
        }
        return false;
    }





    public boolean deleteStudent(String id){
        Optional<Student> optionalStudent = studentFindById(id);
        if(optionalStudent.isPresent()){
            Student s = optionalStudent.get();
            students.remove(s);
            return true;
        }
        return false;
    }

    public boolean deleteTeacher(String id){
        Optional<Teacher> optionalTeacher = teacherFindById(id);
        if(optionalTeacher.isPresent()){
            Teacher t = optionalTeacher.get();
            students.remove(t);
            return true;
        }
        return false;
    }

    public void searchingByPIB(String firstName, String lastName, String middleName){
        Optional<Student> optionalStudent = studentFindByPIB(firstName, lastName, middleName);
        if(optionalStudent.isPresent()){
            Student s = optionalStudent.get();
            System.out.println(s);
        }else{
            System.out.println("Студент не знайдений =(");
        }
    }

    public void searchingTeacherByPIB(String firstName, String lastName, String middleName){
        Optional<Teacher> optionalTeacher = teacherFindByPIB(firstName, lastName, middleName);
        if(optionalTeacher.isPresent()){
            Teacher t = optionalTeacher.get();
            System.out.println(t);
        }else{
            System.out.println("Викладач не знайдений =(");
        }
    }

    public void searchingByCourse(int course){
        List<Student> optionalStudent = findByCourse(course);
        if (optionalStudent.isEmpty()) {
            System.out.println("Студенти не знайдені =(");
        } else {
            for (Student s : optionalStudent) {
                System.out.println(s);
            }
        }
    }

    public void searchingByGroup(int group){
        List<Student> optionalStudent = findByGroup(group);
        if (optionalStudent.isEmpty()) {
            System.out.println("Студенти не знайдені =(");
        } else {
            for (Student s : optionalStudent) {
                System.out.println(s);
            }
        }
    }

    public void sortingCourse (){
        List<Student> optionalStudent = sortByCourse(students);
        if (optionalStudent.isEmpty()) {
            System.out.println("Студентів поки немає =(");
        } else {
            for (Student s : optionalStudent) {
                System.out.println(s);
            }
        }
    }

    public void addTeacher(String id, String firstName, String lastName, String middleName,
                           LocalDate birthDate, String email, String phone,
                           Teacher.TeachersPosition position, Teacher.TeachersDegree degree, Teacher.TeachersAcademicTitle academicTitle,
                           LocalDate hireDate, double workload)  {

        teachers.add(new Teacher(id, firstName, lastName, middleName, birthDate,
                email, phone, position, degree, academicTitle, hireDate, workload));

    }




}
