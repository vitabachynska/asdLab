package repository;

import domain.Teacher;
import DTO.TeacherDTO;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class InmemoryTeachers {
    public static final List<Teacher> teachers = new ArrayList<>();

    public Optional<Teacher> teacherFindById(String id) {
        log.trace("Виклик пошуку викладача за ID: {}", id);
        return teachers.stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    public Optional<Teacher> teacherFindByPIB(String firstName, String lastName, String middleName) {
        log.debug("Пошук викладача за ПІБ: {} {} {}", lastName, firstName, middleName);
        return teachers.stream().filter(t -> t.getFirstName().equalsIgnoreCase(firstName)&&t.getLastName().equalsIgnoreCase(lastName)
                &&t.getMiddleName().equalsIgnoreCase(middleName)).findFirst();
    }



    public List<Teacher> getAllTeachers() {
        log.trace("Запит на отримання повного списку викладачів. Поточна кількість: {}", teachers.size());
        return teachers;
    }



    public boolean updateTeacher(String id, String newFirstName, String newLastName, String newMiddleName,
                                 String newEmail, String newPhone,
                                 Teacher.TeachersPosition position, Teacher.TeachersDegree degree,
                                 Teacher.TeachersAcademicTitle academicTitle, double workload
    ) {
        log.debug("Спроба оновлення даних для викладача з ID: {}", id);
        Optional<Teacher> optionalTeacher = teacherFindById(id);
        if (optionalTeacher.isPresent()) {
            Teacher t = optionalTeacher.get();
            log.trace("Оновлення параметрів даниз викладача для ID: {}", id);
            t.setFirstName(newFirstName);
            t.setLastName(newLastName);
            t.setMiddleName(newMiddleName);
            t.setEmail(newEmail);
            t.setPhone(newPhone);
            t.setPosition(position);
            t.setDegree(degree);
            t.setAcademicTitle(academicTitle);
            t.setWorkload(workload);
            //t.setFaculty(newFaculty);
            //t.setDepartment(newDepartment);
            log.info("Дані викладача {} {} (ID: {}) успішно оновлено", newLastName, newFirstName, id);
            return true;
        }
        log.warn("Не вдалося оновити дані. Викладача з ID: {} не знайдено", id);
        return false;
    }

    public void searchingTeacherByPIB(String firstName, String lastName, String middleName){
        //System.out.println(teachers.size());
        log.debug("Запуск пошуку викладача за ПІБ: {} {} {}", lastName, firstName, middleName);
        Optional<Teacher> optionalTeacher = teacherFindByPIB(firstName, lastName, middleName);
        if(optionalTeacher.isPresent()){
            Teacher t = optionalTeacher.get();
            System.out.println(t);
        }else{
            log.warn("Пошук не дав результатів для викладача: {} {} {}", lastName, firstName, middleName);
            System.out.println("Викладач не знайдений =(");}
    }

    public Teacher deanFindByPIB(String firstName, String lastName, String middleName) {
        log.trace("Пошук декана серед викладачів за ПІБ");
        return teachers.stream()
                .filter(t -> t.getFirstName().equalsIgnoreCase(firstName)
                        && t.getLastName().equalsIgnoreCase(lastName)
                        && t.getMiddleName().equalsIgnoreCase(middleName)).findFirst().orElse(null);
    }

    public Teacher headFindByPIB(String firstName, String lastName, String middleName) {
        log.trace("Пошук завідувача кафедри серед викладачів за ПІБ");
        return teachers.stream()
                .filter(t -> t.getFirstName().equalsIgnoreCase(firstName)
                        && t.getLastName().equalsIgnoreCase(lastName)
                        && t.getMiddleName().equalsIgnoreCase(middleName)).findFirst().orElse(null);}

    public List<TeacherDTO> getTeacherReports() {
        log.info("Генерація звіту за всіма викладачами");
        return teachers.stream()
                .map(t -> new TeacherDTO(
                        t.getId(),
                        t.getFirstName(),
                        t.getLastName(),
                        t.getMiddleName(),
                        t.getFaculty() != null ? t.getFaculty().getName() : "Не вказано",
                        t.getDepartment() != null ? t.getDepartment().getName() : "Не вказано"
                ))
                .toList();
    }

}
