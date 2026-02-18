package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Faculty {
    private String code;
    private String name;
    private String shortName;
    private Teacher dean;
    private String contacts;

    private List<Department> departments = new ArrayList<>();
    public List<Department> getDepartment() {
        return departments;
    }



    public Faculty(String code, String name, String shortName, Teacher dean, String contacts) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;
        this.dean = dean;
        this.contacts = contacts;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getShortName() { return shortName; }
    public void setShortName(String shortName) { this.shortName = shortName; }

    public Teacher getDean() { return dean; }
    public void setDean(Teacher dean) { this.dean = dean; }

    public String getContacts() { return contacts; }
    public void setContacts(String contacts) { this.contacts = contacts; }

    public void addDepartment(String code, String name, Faculty faculty, Teacher head, String location){
        departments.add(new Department(code, name, faculty, head, location));
    }

    public Department findDepartmentByName(String deptName) {
        for (Department dept : departments) {
            if (dept.getName().equalsIgnoreCase(deptName)) {
                return dept;
            }
        }
        return null;
    }
    public void addDepartment(Department department){
        if(department != null)
            departments.add(department);
    }
    public  List<Department> getDepartments(){
        return departments;
    }
    public Optional<Department> departmentFindByName(String name) {
        return departments.stream().filter(d -> d.getName().equalsIgnoreCase(name)).findFirst();
    }
    public boolean departmentExists(){
        if(departments == null || departments.isEmpty()){
            return false;
        }return true;
    }
    public boolean deleteDepartment(String name){
        Optional<Department> optionalDepartment = departmentFindByName(name);
        if(optionalDepartment.isPresent()){
            Department t = optionalDepartment.get();
            departments.remove(t);
            return true;
        }
        return false;
    }

    public Optional<Department> departmentByName(String code, String name) {
        return departments.stream().filter(d -> d.getName().equals(name)&&
                d.getCode().equals(code)).findFirst();
    }

    //public boolean removeFacultyByName(String name) {
    //    return faculties.removeIf(f -> f.getName().equalsIgnoreCase(name));
    //}


    @Override
    public String toString (){
        String deanName = (dean != null)
                ? dean.getLastName() + " " + dean.getFirstName() + " " + dean.getMiddleName() + " "
                : "не призначено";
        return "Код : " + getCode() +",\nПовна назва : " +getName()+"\nСкорочена назва "+ getShortName()
                + "\nКонтакти : " +getContacts()+ "\nДекан : "+ deanName +"\n------------------------";
    }
}
