package repository;
import domain.Department;
import domain.Faculty;
import domain.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class InmemoryStudents {
    public static final List<Student> students = new ArrayList<>();

    public Optional<Student> studentFindById(String id) {
        return students.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    public Optional<Student> studentFindByPIB(String firstName, String lastName, String middleName) {
        return students.stream().filter(s -> s.getFirstName().equalsIgnoreCase(firstName)&&s.getLastName().equalsIgnoreCase(lastName)
                &&s.getMiddleName().equalsIgnoreCase(middleName)).findFirst();
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



    public void addStudent(String id, String firstName, String lastName, String middleName,
                           LocalDate birthDate, String email, String phone,
                           String studentCardId, int course, int group,
                           int admissionYear, Student.TuitionForm tuitionForm, Student.StudentStatus status, Faculty faculty,
    Department department) {

        students.add(new Student(id, firstName, lastName, middleName, birthDate,
                email, phone, studentCardId, course, group, admissionYear, tuitionForm, status, faculty, department));

    }

    public List<Student> getAllStudents() {
        return students;
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

    public boolean deleteStudent(String id){
        Optional<Student> optionalStudent = studentFindById(id);
        if(optionalStudent.isPresent()){
            Student s = optionalStudent.get();
            students.remove(s);
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

}
