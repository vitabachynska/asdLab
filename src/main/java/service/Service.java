package service;

import DTO.DepartmentDTO;
import DTO.FacultyDTO;
import DTO.StudentDTO;
import DTO.TeacherDTO;
import domain.*;
import repository.InmemoryStudents;
import repository.InmemoryTeachers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Service {
    public University university;
    //private final List<Teacher> teachers = new ArrayList<>();
    public InmemoryStudents studentRepository = new InmemoryStudents();
    public InmemoryTeachers teacherRepository = new InmemoryTeachers();
    //private boolean hasRights;



    private final FileHandler fileHandler = new FileHandler();

    public void syncWithFile() {
        System.out.println("Синхронізація з файлом ...");
        UniversityData dataToSave = new UniversityData(University.faculties);
        fileHandler.saveAllData(dataToSave);
    }

    public void startup() {
        UniversityData loadedData = fileHandler.loadAllData();
        if (loadedData != null && loadedData.faculties() != null) {
            for (Faculty f : loadedData.faculties()) {
                this.university.addFaculty(f);

                if (f.getDepartments() != null) {
                    for (Department d : f.getDepartments()) {
                        d.setFaculty(f);

                        // Restore teacher relationships
                        if (d.getTeachers() != null) {
                            for (Teacher t : d.getTeachers()) {
                                t.setFaculty(f);
                                t.setDepartment(d);
                            }
                        }

                        // Restore student relationships
                        if (d.getStudents() != null) {
                            for (Student s : d.getStudents()) {
                                s.setFaculty(f);
                                s.setDepartment(d);
                            }
                        }
                    }
                }
            }

            University.faculties = new ArrayList<>(this.university.getFaculties());

            // Load all teachers from departments into the repository
            for (Faculty f : this.university.getFaculties()) {
                for (Department d : f.getDepartments()) {
                    if (d.getTeachers() != null) {
                        teacherRepository.teachers.addAll(d.getTeachers());
                    }
                    if (d.getStudents() != null) {
                        studentRepository.students.addAll(d.getStudents());
                    }
                }
            }

            System.out.println("Дані НаУКМА успішно завантажені!");
        }
    }




    public boolean addUniversity(String fullName, String shortName, String city, String address){
        if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
            System.out.println("Помилка: Потрібні права менеджера або відкритий доступ до них або відкритий доступ до них");
            return false;
        }
        this.university = new University(fullName, shortName, city, address);
        return true;

    }
    public University getUniversity(){
        return university;
    }

    //public Faculty getFaculty(){
    //    return faculty;
    //}
    // Department getDepartment(){
//return department;
   // }

    public boolean addFaculty(String code, String name, String shortName, Teacher dean, String contacts) {
       // if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
         //   System.out.println("Помилка: Потрібні права менеджера або відкритий доступ до них або відкритий доступ до них");
           // return false;
     //   }

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
       // if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
         //   System.out.println("Помилка: Потрібні права менеджера або відкритий доступ до них або відкритий доступ до них");
           // return false;
       // }
        if(university == null)
            return false;
        boolean isDeleted = university.removeFacultyByName(name);
        return  isDeleted;
        //return university.removeFacultyByName(name);
    }

    public boolean updateFaculty(String name,String newCode, String newName, String newShortName, Teacher newDean, String newContacts) {
      //  if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
        //    System.out.println("Помилка: Потрібні права менеджера або відкритий доступ до них або відкритий доступ до них");
        //    return false;
       // }
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
       // if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
         //   System.out.println("Помилка: Потрібні права менеджера або відкритий доступ до них або відкритий доступ до них");
           // return false;
       // }
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
       // if (!Authorization.can(RoleForm.MANAGER) && !Validation.hasRights) {
         //   System.out.println("Помилка: Потрібні права менеджера або відкритий доступ до них або відкритий доступ до них");
           // return false;
       // }
        if(university==null)
            return false;
        Faculty f = getUniversity().findFacultyByName(facultyName);
        if (f!=null){
            f.addDepartment(code, name, f, head, location);
            return true;
        }
        return false;
    }

    public boolean deleteDepartment(String facultyName, Department deptName) {
      //  if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
        //    System.out.println("Помилка: Потрібні права менеджера або відкритий доступ до них або відкритий доступ до них");
          //  return false;
        //}
        if (university == null) return false;

        Faculty f = university.findFacultyByName(String.valueOf(facultyName));
        if (f != null) {
            return f.getDepartments().removeIf(d -> d.getName().equalsIgnoreCase(String.valueOf(deptName)));
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

    /*public Optional<Teacher> teacherFindById(String id) {
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
*/

    public boolean addTeacher(String faculty, String department, Teacher teacher) {
      //  if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
        //    System.out.println("Помилка: Потрібні права менеджера або відкритий доступ до них або відкритий доступ до них");
          //  return false;
       // }
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
    //    if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
      //      System.out.println(Authorization.can(RoleForm.MANAGER));
        //    System.out.println("Помилка: Потрібні права менеджера або відкритий доступ до них або відкритий доступ до них");
          //  return false;
       // }
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
    //    if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
      //      System.out.println("Помилка: Потрібні права менеджера або відкритий доступ до них або відкритий доступ до них");
        //    return false;
       // }
        Optional<Student> studentOpt = findStudentById(id);
        if (studentOpt.isPresent()) {
            Student s = studentOpt.get();
            s.getDepartment().removeStudent(s);
            studentRepository.students.remove(s);
            return true;
        }
        return false;
    }

    public Optional<Teacher> findTeacherById(String id) {
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
    //    if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
      //      System.out.println("Помилка: Потрібні права менеджера або відкритий доступ до них або відкритий доступ до них");
        //    return false;
       // }
        Optional<Teacher> teacherOpt = findTeacherById(id);
        if (teacherOpt.isPresent()) {
            Teacher t = teacherOpt.get();
            t.getDepartment().removeTeacher(t);
            teacherRepository.teachers.remove(t);
            return true;
        }
        return false;
    }
    public <T> List<T> findAny(List<T> list, Predicate<T> condition) {
        return list.stream()
                .filter(condition)
                .toList();
    }

    public List<FacultyDTO> getFacultyReports() {
        return University.faculties.stream()
                .map(f -> new FacultyDTO(f.getName()))
                .toList();
    }

    public List<DepartmentDTO> getDepartmentReports() {
        return University.faculties.stream()
                .flatMap(f -> f.getDepartments().stream())
                .map(d -> new DepartmentDTO(
                        d.getName(),
                        d.getFaculty() != null ? d.getFaculty().getName() : "Не вказано"
                ))
                .toList();
    }

    public void listDTOforTeacher(){
        List<TeacherDTO> teacherDtos = teacherRepository.getTeacherReports();

        if (teacherDtos.isEmpty()) {
            System.out.println("Жодного викладача поки не зареєстровано :(");
        } else {
            System.out.println("\n--------ВИКЛАДАЧІ--------\n------------------------");
            teacherDtos.forEach(t -> {
                System.out.println(" ID: " + t.id());
                System.out.println(" ПІБ: " + t.lastName() + " " + t.firstName() + " " + t.middleName());
                System.out.println(" Факультет: " + t.facultyName());
                System.out.println(" Кафедра: " + t.departmentName());
                System.out.println("------------------------------------");
            });
        }
    }

    public void listDTOforFaculties(){
        List<FacultyDTO> facultyDtos = getFacultyReports();

        if (facultyDtos.isEmpty()) {
            System.out.println("Жодного факультету поки не зареєстровано :(");
        } else {
            System.out.println("\n--------ФАКУЛЬТЕТИ--------\n------------------------");
            facultyDtos.forEach(f -> {
                System.out.println(" Назва: " + f.name());
                System.out.println("------------------------------------");
            });
        }
    }

    public void listDTOforDepartments(){
        List<DepartmentDTO> deptDtos = getDepartmentReports();

        if (deptDtos.isEmpty()) {
            System.out.println("Жодної кафедри поки не зареєстровано :(");
        } else {
            System.out.println("\n--------КАФЕДРИ--------\n------------------------");
            deptDtos.forEach(d -> {
                System.out.println(" Назва: " + d.name());
                System.out.println(" Факультет: " + d.facultyName());
                System.out.println("------------------------------------");
            });
        }
    }
public void listDTOforStudents(){
    List<StudentDTO> dtos = studentRepository.getCompactReport();
                if (dtos.isEmpty()) {
        System.out.println("Жодного студента поки не зареєстровано :(");
    } else {
        System.out.println("\n--------СТУДЕНТИ--------\n------------------------");
        dtos.forEach(s -> {
            System.out.println(" ID: " + s.id());
            System.out.println(" ПІБ: " + s.lastName() + " " + s.firstName() + " " + s.middleName());
            System.out.println("️ Факультет: " + s.faculty());
            System.out.println(" Курс: " + s.course());
            System.out.println("------------------------------------");
        });
    }
}
    public Faculty findFacultyInteractively() {
        while (true) {
            String name = UtilityValidation.askInput("Назва факультету (або '0' для скасування): ");
            if (name.equals("0")) return null;
            Faculty f = getUniversity().findFacultyByName(name);
            if (f != null) return f;
            System.out.println("Факультет не знайдено!");
        }
    }

    public Department findDepartmentInteractively(Faculty faculty) {
        listDTOforDepartments();
        while (true) {
            String name = UtilityValidation.askInput("Назва кафедри (або '0' для скасування): ");
            if (name.equals("0")) return null;
            Department d = faculty.departmentFindByName(name).orElse(null);
            if (d != null) return d;
            System.out.println("Кафедру не знайдено на цьому факультеті!");
        }
    }

    public Teacher findHeadInteractively() {
        listDTOforTeacher();
        while (true) {
            System.out.println("Дані завідувача (або введіть '0' в полі імені для виходу):");
            String fName = UtilityValidation.askInput("Ім'я: ");
            if (fName.equals("0")) return null;

            String lName = UtilityValidation.askInput("Прізвище: ");
            String mName = UtilityValidation.askInput("По батькові: ");

            Teacher head = teacherRepository.headFindByPIB(fName, lName, mName);
            if (head != null) return head;

            System.out.println("Викладача не знайдено! Спробуйте ще раз.");
        }
    }

    public Teacher findDeanInteractively() {
        listDTOforTeacher();
        while (true) {
            System.out.println("\nДані нового декана (або введіть '0' в полі імені для виходу):");
            String dName = UtilityValidation.askInput("Ім'я: ");
            if (dName.equals("0")) return null;

            String lName = UtilityValidation.askInput("Прізвище: ");
            String mName = UtilityValidation.askInput("По батькові: ");
            Teacher dean = teacherRepository.deanFindByPIB(dName, lName, mName);
            if (dean != null) return dean;

            System.out.println("Викладача не знайдено!");
        }
    }
    public Teacher findTeacherInteractively() {
        listDTOforTeacher();
        while (true) {
            String id = UtilityValidation.askInput("Введіть ID викладача для редагування (або '0' для виходу): ");
            if (id.equals("0")) return null;
            Teacher teacher = teacherRepository.teacherFindById(id).orElse(null);
            if (teacher != null) return teacher;
            System.out.println("Викладача з таким ID не знайдено! Спробуйте ще раз.");
        }
    }
    public Student findStudentInteractively() {
        listDTOforStudents();
        while (true) {
            String id = UtilityValidation.askInput("Введіть ID студента для редагування (або '0' для виходу): ");
            if (id.equals("0")) return null;
            Student student = studentRepository.studentFindById(id).orElse(null);
            if (student!= null) {return student;}
            System.out.println("Студента з таким ID не знайдено! Спробуйте ще раз.");
        }
    }
}

