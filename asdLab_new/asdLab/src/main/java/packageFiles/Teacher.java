package packageFiles;

import java.time.LocalDate;
public class Teacher extends Person{
    private String position;
    private String degree;
    private String academicTitle;
    private LocalDate hireDate;
    private double workload;

    public Teacher(String id, String firstName, String lastName, String middleName,
                   LocalDate birthDate, String email, String phone,
                   String position, String degree, String academicTitle,
                   LocalDate hireDate, double workload) {
        super(id, firstName, lastName, middleName, birthDate, email, phone);
        this.position = position;
        this.degree = degree;
        this.academicTitle = academicTitle;
        this.hireDate = hireDate;
        this.workload = workload;
    }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }

    public String getAcademicTitle() { return academicTitle; }
    public void setAcademicTitle(String academicTitle) { this.academicTitle = academicTitle; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public double getWorkload() { return workload; }
    public void setWorkload(double workload) { this.workload = workload; }
}
