package repository;
import domain.Department;
import domain.Faculty;
import domain.Student;
import DTO.StudentDTO;
import exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
public class InmemoryStudents {
    public static final List<Student> students = new ArrayList<>();
    public Optional<Student> studentFindById(String id) {
        log.trace("Виклик studentFindById з параметром id: {}", id);
        return students.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    public Optional<Student> studentFindByPIB(String firstName, String lastName, String middleName) {
        log.debug("Діагностика пошуку студента за ПІБ: {} {} {}", lastName, firstName, middleName);
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
        log.trace("Виконання сортування списку за курсом");
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
        log.trace("Запит на отримання повного списку студентів. Кількість: {}", students.size());
        return students;
    }

    public boolean updateStudent(String id, String newFirstName, String newLastName, String newMiddleName,
                                 String newEmail, String newPhone,
                                 int newCourse, int newGroup,
                                 Student.TuitionForm newTuitionForm, Student.StudentStatus newStatus) {
        log.debug("Спроба оновлення даних студента ID: {}", id);
        Optional<Student> optionalStudent = studentFindById(id);
        if (optionalStudent.isPresent()) {
            Student s = optionalStudent.get();
            log.trace("Оновлення полів для студента {}: курс {} -> {}", id, s.getCourse(), newCourse);
            s.setFirstName(newFirstName);
            s.setLastName(newLastName);
            s.setMiddleName(newMiddleName);
            s.setEmail(newEmail);
            s.setPhone(newPhone);
            s.setCourse(newCourse);
            s.setGroup(newGroup);
            s.setTuitionForm(newTuitionForm);
            s.setStatus(newStatus);
            log.info("Дані студента ID: {} успішно оновлено", id);
            return true;
        }
        log.warn("Спроба оновити неіснуючого студента ID: {}", id);
        return false;
    }

    public boolean deleteStudent(String id){
        log.debug("Запит на видалення студента ID: {}", id);
        Optional<Student> optionalStudent = studentFindById(id);
        if(optionalStudent.isPresent()){
            Student s = optionalStudent.get();
            students.remove(s);
            log.info("Студента ID: {} видалено з системи", id);
            return true;
        }
        log.warn("Не вдалося видалити студента ID: {}, оскільки його не знайдено", id);
        return false;
    }

    public void searchingStudentByPIB(String firstName, String lastName, String middleName){
        Student s = studentFindByPIB(firstName, lastName, middleName)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Студента з ПІБ " + lastName + " " + firstName + " " + middleName + " не знайдено =("
                ));

        System.out.println(s);
    }

    /*public void searchingByCourse(int course){
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
    }*/

    public void sortingStudentsCourse(){
        log.debug("Запуск сортування студентів за курсом");
        List<Student> optionalStudent = sortByCourse(students);
        if (optionalStudent.isEmpty()) {
            log.warn("Спроба сортування порожнього списку студентів");
            System.out.println("Студентів поки немає =(");
        } else {
            for (Student s : optionalStudent) {
                System.out.println(s);
            }
        }
    }

    public List<StudentDTO> getCompactStudentReport() {
        log.info("Генерація звіту студентів. Поточна їх кількість: {}", students.size());
        return students.stream()
                .map(s -> new StudentDTO(
                        s.getId(),
                        s.getFirstName(),
                        s.getLastName(),
                        s.getMiddleName(),
                        s.getFaculty().getName(),
                        s.getCourse()
                ))
                .toList();
    }

}
