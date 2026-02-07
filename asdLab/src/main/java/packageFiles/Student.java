package packageFiles;

import java.time.LocalDate;

public class Student extends Person {
    private String studentCardId;
    private int course;
    private int group;
    private int admissionYear;
    private TuitionForm tuitionForm; // Використовуємо enum
    private StudentStatus status;    // Використовуємо enum

    public Student(String id, String firstName, String lastName, String middleName,
                   LocalDate birthDate, String email, String phone,
                   String studentCardId, int course, int group,
                   int admissionYear, TuitionForm tuitionForm, StudentStatus status) {
        super(id, firstName, lastName, middleName, birthDate, email, phone);
        this.studentCardId = studentCardId;
        this.course = course;
        this.group = group;
        this.admissionYear = admissionYear;
        this.tuitionForm = tuitionForm;
        this.status = status;
    }

    public String getStudentCardId() {return studentCardId;}
    public void setStudentCardId(String studentCardId) {this.studentCardId = studentCardId;}

    public int getCourse() {return course;}
    public void setCourse(int course) {
        if(course>=1 && course<=4)
            this.course = course;
        else throw new IllegalArgumentException("Кур має бути в межах від 1 до 4");
    }

    public int getGroup() {return group;}
    public void setGroup(int group) {
        if(group>=1 && group<=6)
            this.group = group;
        else throw new IllegalArgumentException("Група має бути в межах від 1 до 6");
    }

    public int getAdmissionYear() {return admissionYear;}
    public void setAdmissionYear(int admissionYear) {
        int currentYear = LocalDate.now().getYear();
        if (admissionYear <= currentYear)
            this.admissionYear = admissionYear;
        else throw new IllegalArgumentException("Некоректний рік вступу");
    }

    public TuitionForm getTuitionForm() {return tuitionForm;}
    public void setTuitionForm(TuitionForm tuitionForm) {this.tuitionForm = tuitionForm;}

    public StudentStatus getStatus() {return status;}
    public void setStatus(StudentStatus status) {this.status = status;}

    public enum TuitionForm {
        BUDGET("Бюджет"),
        CONTRACT("Контракт");

        private final String label;

        TuitionForm(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
    public enum StudentStatus {
        STUDYING("Навчається"),
        ACADEMIC_LEAVE("Академвідпустка"),
        EXPELLED("Відрахований");

        private final String label;

        StudentStatus(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
}
