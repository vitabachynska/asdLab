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
        else throw new IllegalArgumentException("Курс має бути в межах від 1 до 4");
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
        BUDGET("бюджет"),
        CONTRACT("контракт");

        private final String label;

        TuitionForm(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public static TuitionForm fromString(String text) {
            for (TuitionForm form : TuitionForm.values()) {
                if (form.label.equalsIgnoreCase(text.trim()))
                    return form;
            }
            throw new IllegalArgumentException("Введіть правильно форму навчання");
        }
    }

    public enum StudentStatus {
        STUDYING("навчається"),
        ACADEMIC_LEAVE("в академ відпустці"),
        EXPELLED("відрахований");

        private final String label;

        StudentStatus(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public static StudentStatus fromString(String text) {
            for (StudentStatus status : StudentStatus.values()) {
                if (status.label.equalsIgnoreCase(text.trim()))
                    return status;
            }
            throw new IllegalArgumentException("Введіть правильно статус навчання");
        }
    }


    @Override
    public String toString() {
        return "ID студента : " + getStudentCardId() +",\nПІБ : " + getFirstName()
                + " " +getLastName()+ " "+ getMiddleName()+
                ",\nДата народження : " + getBirthDate() + ",\nemail : " + getEmail() + ", тел. : " + getPhone()+",\nКурс " + getCourse() +", група " + getGroup()
                + ", рік вступу " + getAdmissionYear() + ",\nФорма навчання : " +
                getTuitionForm().getLabel() +" , ідентифікатор студента : " + getStudentCardId()
                + ", статус студента : " + getStatus().getLabel() + ".\n------------------------";
    }



}
