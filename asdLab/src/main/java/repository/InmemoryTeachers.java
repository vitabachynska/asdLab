package repository;

import domain.Student;
import domain.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InmemoryTeachers {
    public final List<Teacher> teachers = new ArrayList<>();

    public Optional<Teacher> teacherFindById(String id) {
        return teachers.stream().filter(t -> t.getId().equals(id)).findFirst();
    }
    public boolean updateTeacher(String id, String newFirstName, String newLastName, String newMiddleName,
                                 String newEmail, String newPhone,
                                 Teacher.TeachersPosition position, Teacher.TeachersDegree degree,
                                 Teacher.TeachersAcademicTitle academicTitle, double workload
    ) {
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
            //t.setFaculty(newFaculty);
            //t.setDepartment(newDepartment);
            return true;
        }
        return false;
    }
    public Optional<Teacher> teacherFindByPIB(String firstName, String lastName, String middleName) {
        return teachers.stream().filter(t -> t.getFirstName().equalsIgnoreCase(firstName)&&t.getLastName().equalsIgnoreCase(lastName)
                &&t.getMiddleName().equalsIgnoreCase(middleName)).findFirst();
    }
    public void searchingTeacherByPIB(String firstName, String lastName, String middleName){
        Optional<Teacher> optionalTeacher = teacherFindByPIB(firstName, lastName, middleName);
        if(optionalTeacher.isPresent()){
            Teacher t = optionalTeacher.get();
            System.out.println(t);
        }else
            System.out.println("Викладач не знайдений =(");
    }
}
