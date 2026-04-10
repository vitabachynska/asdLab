package domain;

import java.util.ArrayList;
import java.util.List;

public class University {
    private String fullName;
    private String shortName;
    private String city;
    private String address;

    private List<Faculty> faculties = new ArrayList<>();

    public University(String fullName, String shortName, String city, String address) {
        this.fullName = fullName;
        this.shortName = shortName;
        this.city = city;
        this.address = address;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getShortName() { return shortName; }
    public void setShortName(String shortName) { this.shortName = shortName; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public void addFaculty(Faculty faculty){
        if(faculty != null)
            faculties.add(faculty);
    }
    public  List<Faculty> getFaculties(){
        return faculties;
    }

    public boolean facultiesExists(){
        if(faculties == null || faculties.isEmpty()){
           return false;
        }return true;
    }


    public Faculty findFacultyByName(String facultyName) {
        for (Faculty faculty : faculties) {
            if (faculty.getName().equalsIgnoreCase(facultyName)) {
                return faculty;
            }
        }
        return null;
    }

    public boolean removeFacultyByName(String name) {
        return faculties.removeIf(f -> f.getName().equalsIgnoreCase(name));
    }


    @Override
    public String toString (){
        return "Повна назва : " + getFullName() +",\nСкорочена назва : " + getShortName()
                + "\nМісто : " +getCity()+ "\nАдрес : "+ getAddress()+"\n------------------------";
    }
}
