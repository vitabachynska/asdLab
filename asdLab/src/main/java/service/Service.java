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

    public void addUniversity(String fullName, String shortName, String city, String address){
        this.university = new University(fullName, shortName, city, address);

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

    public void addFaculty(String code, String name, String shortName, Teacher dean, String contacts) {
        if(university != null){
            Faculty newFaculty = new Faculty(code, name, shortName, dean, contacts);
            university.addFaculty(newFaculty);
        }
        else{
            System.out.println("Помилка. Спочатку створіть універстиет");
        }

    }

    public List<Faculty> getAllFaculties(){
        if(university == null)
            return new ArrayList<>();
        return university.getFaculties();
    }

    public boolean deleteFaculty(String name){
        if(university == null)
            return false;
        boolean isDeleted = university.removeFacultyByName(name);
        return  isDeleted;
        //return university.removeFacultyByName(name);
    }

    public boolean updateFaculty(String name,String newCode, String newName, String newShortName, Teacher newDean, String newContacts) {
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
        Optional<Student> studentOpt = findStudentById(id);
        if (studentOpt.isPresent()) {
            Student s = studentOpt.get();
            s.getDepartment().removeStudent(s);
            studentRepository.students.remove(s);
            return true;
        }
        return false;
    }

    /*public List<Student> getStudentsByDepartment(String facultyName, String deptName) {
        if (university == null) return new ArrayList<>();

        Faculty f = university.findFacultyByName(facultyName);
        if (f != null) {
            Optional<Department> d = f.departmentFindByName(deptName);
            if (d.isPresent()) {
                return d.get().getStudents();
            }
        }
        return new ArrayList<>();
    }*/

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
        Optional<Teacher> teacherOpt = findTeacherById(id);
        if (teacherOpt.isPresent()) {
            Teacher t = teacherOpt.get();
            t.getDepartment().removeTeacher(t);
            teacherRepository.teachers.remove(t);
            return true;
        }
        return false;
    }

    /*public List<Student> getAllStudents() {
        List<Student> allStudents = new ArrayList<>();
        if (university == null) return allStudents;

        for (Faculty f : university.getFaculties()) {
            for (Department d : f.getDepartments()) {
                allStudents.addAll(d.getStudents());
            }
        }
        return allStudents;
    }*/

    /*public List<Teacher> getAllTeachers() {
        List<Teacher> allTeachers = new ArrayList<>();
        if (university == null) return allTeachers;

        for (Faculty f : university.getFaculties()) {
            for (Department d : f.getDepartments()) {
                allTeachers.addAll(d.getTeachers());
            }
        }
        return allTeachers;
    }*/
    //public void addDepartment(String code, String name, Faculty faculty,  Teacher head, String location) {
    //    departments.add(new Department(code, name, faculty, head, location));
    //}


    /*public void addStudent(String id, String firstName, String lastName, String middleName,
                           LocalDate birthDate, String email, String phone,
                           String studentCardId, int course, int group,
                           int admissionYear, Student.TuitionForm tuitionForm, Student.StudentStatus status, Faculty faculty, Department department) {

        students.add(new Student(id, firstName, lastName, middleName, birthDate,
                email, phone, studentCardId, course, group, admissionYear, tuitionForm, status, faculty, department));

    }

    public List<Student> getAllStudents() {
        return students;
    }
    public List<Teacher> getAllTeachers() {
        return teachers;
    }
    public List<Faculty> getAllFaculties() {
        if (university == null)
            return new ArrayList<>();
        return university.getFaculties();
    }
    //public List<Department> getAllDepartments() {
    //    return departments;
    //}



*/
    /*public boolean updateTeacher(String id, String newFirstName, String newLastName, String newMiddleName,
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
    }*/

/*



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

    /*public boolean deleteDepartment(String name){
        Optional<Department> optionalDepartment = departmentFindByName(name);
        if(optionalDepartment.isPresent()){
            Department t = optionalDepartment.get();
            departments.remove(t);
            return true;
        }
        return false;
    }*/

    //public void deleteUni() {
    //faculties.clear();
    //university = null;
    //}

    /*public void searchingByPIB(String firstName, String lastName, String middleName){
        Optional<Student> optionalStudent = studentFindByPIB(firstName, lastName, middleName);
        if(optionalStudent.isPresent()){
            Student s = optionalStudent.get();
            System.out.println(s);
        }else{
            System.out.println("Студент не знайдений =(");
        }
    }
*//*
    public void searchingTeacherByPIB(String firstName, String lastName, String middleName){
        Optional<Teacher> optionalTeacher = teacherFindByPIB(firstName, lastName, middleName);
        if(optionalTeacher.isPresent()){
            Teacher t = optionalTeacher.get();
            System.out.println(t);
        }else
            System.out.println("Викладач не знайдений =(");
    }*/


    /*
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
                           LocalDate hireDate, double workload, Faculty faculty, Department department)  {

        teachers.add(new Teacher(id, firstName, lastName, middleName, birthDate,
                email, phone, position, degree, academicTitle, hireDate, workload, faculty, department));

    }



    /*public Optional<Department> departmentByName(String code, String name) {
        return departments.stream().filter(d -> d.getName().equals(name)&&
                d.getCode().equals(code)).findFirst();
    }*/

    /*public boolean deleteStudentFromDepartment(String id) {
        Optional<Student> optionalStudent = studentFindById(id);

        if (optionalStudent.isPresent()) {
            Student s = optionalStudent.get();
            Department dept = s.getDepartment();
            dept.removeStudent(s);
            return true;
        } return false;
    }
    public boolean deleteTeacherFromDepartment(String id) {
        Optional<Teacher> optionalTeacher = teacherFindById(id);

        if (optionalTeacher.isPresent()) {
            Teacher t = optionalTeacher.get();
            Department dept = t.getDepartment();
            dept.removeTeacher(t);
            return true;
        } return false;

    }*/




}


