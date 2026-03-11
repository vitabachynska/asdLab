package service;

import domain.*;
import repository.InmemoryStudents;
import repository.InmemoryTeachers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Service {
    private University university;
    private Faculty faculty;
    private Department department;
    //private final List<Department> departments = new ArrayList<>();
    //private final List<Student> students = new ArrayList<>();
    private final List<Teacher> teachers = new ArrayList<>();
    //private List<Faculty> faculties = new ArrayList<>();
    private InmemoryStudents studentRepository = new InmemoryStudents();
    public InmemoryStudents getRepo() { return this.studentRepository; }

    private InmemoryTeachers teacherRepository = new InmemoryTeachers();
    //public InmemoryTeachers getRepo2() { return this.teacherRepository; }



/*
    public Optional<Student> studentFindByPIB(String firstName, String lastName, String middleName) {
        return students.stream().filter(s -> s.getFirstName().equalsIgnoreCase(firstName)&&s.getLastName().equalsIgnoreCase(lastName)
                &&s.getMiddleName().equalsIgnoreCase(middleName)).findFirst();
    }

    //public Optional<Department> departmentFindByName(String name) {
    //    return departments.stream().filter(d -> d.getName().equalsIgnoreCase(name)).findFirst();
    //}
*/

/*
    public List<Student> findByCourse(int course) {
        return students.stream().filter(s -> s.getCourse() == course).toList();
    }
    public List<Student> findByGroup(int group) {
        return students.stream().filter(s -> s.getGroup() == group).toList();
    }
    public List<Student> sortByCourse(List<Student> students) {
        return students.stream().sorted(Comparator.comparingInt(Student::getCourse)).toList();
    }*/

    public boolean addUniversity(String fullName, String shortName, String city, String address){
        if (!Authorization.hasRole(Authorization.RoleForm.MANAGER)) {
            System.out.println("Помилка: Потрібні права менеджена");
            return false;
        }
        this.university = new University(fullName, shortName, city, address);
        return true;

    }
    public University getUniversity(){
        return university;
    }

    public Faculty getFaculty(){
        return faculty;
    }
    public Department getDepartment(){
        return department;
    }

    public boolean addFaculty(String code, String name, String shortName, Teacher dean, String contacts) {
        if (!Authorization.hasRole(Authorization.RoleForm.MANAGER)) {
            System.out.println("Помилка: Потрібні права менеджена");
            return false;
        }

        if(university != null){
            Faculty newFaculty = new Faculty(code, name, shortName, dean, contacts);
            university.addFaculty(newFaculty);
            return true;
        }
        else{
            System.out.println("Помилка. Спочатку створіть універстиет");
        }
        return false;

    }

    public List<Faculty> getAllFaculties(){
        if(university == null)
            return new ArrayList<>();
        return university.getFaculties();
    }

    public boolean deleteFaculty(String name){
        if (!Authorization.hasRole(Authorization.RoleForm.MANAGER)) {
            System.out.println("Помилка: Потрібні права менеджена");
            return false;
        }
        if(university == null)
            return false;
        boolean isDeleted = university.removeFacultyByName(name);
        return  isDeleted;
        //return university.removeFacultyByName(name);
    }

    public boolean updateFaculty(String name,String newCode, String newName, String newShortName, Teacher newDean, String newContacts) {
        if (!Authorization.hasRole(Authorization.RoleForm.MANAGER)) {
            System.out.println("Помилка: Потрібні права менеджена");
            return false;
        }
        if (university == null) return false;

        Faculty f = university.findFacultyByName(name);
        if (f != null) {
            f.setCode(newCode);
            f.setName(newName);
            f.setShortName(newShortName);
            f.setDean(newDean);
            f.setContacts(newContacts);
            return true;
        }
        return false;
    }

    public boolean updateDepartment(String fName, String oldDeptName, String newCode, String newName, Teacher newHead, String newLocation) {
        if (!Authorization.hasRole(Authorization.RoleForm.MANAGER)) {
            System.out.println("Помилка: Потрібні права менеджена");
            return false;
        }
        if (university == null) return false;

        Faculty f = university.findFacultyByName(fName);
        if (f != null) {
            Department d = f.findDepartmentByName(oldDeptName);
            if (d != null) {
                d.setCode(newCode);
                d.setName(newName);
                d.setHead(newHead);
                d.setLocation(newLocation);
                return true;
            }
        }
        return false;
    }

    public void addDepartment(String code, String name, Faculty faculty,  Teacher head, String location) {
        //Faculty faculty = university.findFacultyByName(name);
        if(faculty != null){
            Department newDepartment = new Department(code, name, faculty, null, location);
            faculty.addDepartment(newDepartment);
        }
        else{
            System.out.println("Помилка. Спочатку створіть факультет");
        }

    }
    public boolean addDepartment(String facultyName, String code, String name,  Teacher head, String location){
        if (!Authorization.hasRole(Authorization.RoleForm.MANAGER)) {
            System.out.println("Помилка: Потрібні права менеджена");
            return false;
        }
        if(university==null)
            return false;
        Faculty f = getUniversity().findFacultyByName(facultyName);
        if (f!=null){
            f.addDepartment(code, name, f, head, location);
            return true;
        }
        return false;
    }

    public boolean deleteDepartment(String facultyName, String deptName) {
        if (!Authorization.hasRole(Authorization.RoleForm.MANAGER)) {
            System.out.println("Помилка: Потрібні права менеджена");
            return false;
        }
        if (university == null) return false;

        Faculty f = university.findFacultyByName(facultyName);
        if (f != null) {
            return f.getDepartments().removeIf(d -> d.getName().equalsIgnoreCase(deptName));
        }
        return false;
    }

    public List<Department> getAllDepartments() {
        List<Department> allDepts = new ArrayList<>();
        if (university == null) return allDepts;

        for (Faculty f : university.getFaculties()) {
            allDepts.addAll(f.getDepartments());
        }
        return allDepts;
    }

    public Optional<Teacher> teacherFindById(String id) {
        return teachers.stream().filter(t -> t.getId().equals(id)).findFirst();
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

    public Teacher headFindByPIB(String firstName, String lastName, String middleName) {
        return teachers.stream()
                .filter(t -> t.getFirstName().equalsIgnoreCase(firstName)
                        && t.getLastName().equalsIgnoreCase(lastName)
                        && t.getMiddleName().equalsIgnoreCase(middleName)).findFirst().orElse(null);}


    public boolean addTeacher(String faculty, String department, Teacher teacher) {
        if (!Authorization.hasRole(Authorization.RoleForm.MANAGER)) {
            System.out.println("Помилка: Потрібні права менеджена");
            return false;
        }
        if (university == null) return false;

        Faculty f = university.findFacultyByName(faculty);
        if (f != null) {
            Department d = f.findDepartmentByName(department);
            if (d != null) {
                d.addTeacher(teacher);
                teacherRepository.teachers.add(teacher);
                //System.out.println(teachers.size());
                return true;
            }
        }
        return false;
    }

    public boolean addStudent(String faculty, String department, Student student) {
        if (!Authorization.hasRole(Authorization.RoleForm.MANAGER)) {
            System.out.println("Помилка: Потрібні права менеджена");
            return false;
        }
        if (university == null) return false;

        Faculty f = university.findFacultyByName(faculty);
        if (f != null) {
            Department d = f.findDepartmentByName(department);
            if (d != null) {
                d.addStudent(student);
                studentRepository.students.add(student);
                return true;
            }
        }
        return false;
    }

    public boolean deleteTeacher(String fName, String dName, String lastName) {
        if (university == null) return false;

        Faculty f = university.findFacultyByName(fName);
        if (f != null) {
            Department d = f.findDepartmentByName(dName);
            if (d != null) {
                return d.getTeachers().removeIf(s -> s.getLastName().equalsIgnoreCase(lastName));
            }
        }
        return false;
    }

    private Optional<Student> findStudentById(String id) {
        if (university == null) return Optional.empty();
        for (Faculty f : university.getFaculties()) {
            for (Department d : f.getDepartments()) {
                Optional<Student> student = d.getStudents().stream()
                        .filter(s -> s.getId().equals(id)).findFirst();
                if (student.isPresent()) return student;
            }
        }
        return Optional.empty();
    }

    public boolean deleteStudent(String id) {
        if (!Authorization.hasRole(Authorization.RoleForm.MANAGER)) {
            System.out.println("Помилка: Потрібні права менеджена");
            return false;
        }
        Optional<Student> studentOpt = findStudentById(id);
        if (studentOpt.isPresent()) {
            Student s = studentOpt.get();
            s.getDepartment().removeStudent(s);
            studentRepository.students.remove(s);
            return true;
        }
        return false;
    }

    private Optional<Teacher> findTeacherById(String id) {
        if (university == null) return Optional.empty();
        for (Faculty f : university.getFaculties()) {
            for (Department d : f.getDepartments()) {
                Optional<Teacher> teacher = d.getTeachers().stream()
                        .filter(t -> t.getId().equals(id)).findFirst();
                if (teacher.isPresent()) return teacher;
            }
        }
        return Optional.empty();
    }

    public boolean deleteTeacher(String id) {
        if (!Authorization.hasRole(Authorization.RoleForm.MANAGER)) {
            System.out.println("Помилка: Потрібні права менеджена");
            return false;
        }
        Optional<Teacher> teacherOpt = findTeacherById(id);
        if (teacherOpt.isPresent()) {
            Teacher t = teacherOpt.get();
            t.getDepartment().removeTeacher(t);
            teacherRepository.teachers.remove(t);
            return true;
        }
        return false;
    }

}


