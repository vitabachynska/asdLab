package domain;

import java.time.LocalDate;
public class Teacher extends Person{
    private TeachersPosition position; //enum
    private TeachersDegree degree; //enum
    private TeachersAcademicTitle academicTitle; //enum
    private LocalDate hireDate;
    private double workload;
    private Department department;
    private Faculty faculty;

    public Teacher(String id, String firstName, String lastName, String middleName,
                   LocalDate birthDate, String email, String phone,
                   TeachersPosition position, TeachersDegree degree, TeachersAcademicTitle academicTitle,
                   LocalDate hireDate, double workload, Faculty faculty, Department department) {
        super(id, firstName, lastName, middleName, birthDate, email, phone);
        this.position = position;
        this.degree = degree;
        this.academicTitle = academicTitle;
        this.hireDate = hireDate;
        this.workload = workload;
        this.faculty = faculty;
        this.department = department;
    }

    public TeachersPosition getPosition() { return position; }
    public void setPosition(TeachersPosition position) { this.position = position; }

    public TeachersDegree getDegree() { return degree; }
    public void setDegree(TeachersDegree degree) { this.degree = degree; }

    public TeachersAcademicTitle getAcademicTitle() { return academicTitle; }
    public void setAcademicTitle(TeachersAcademicTitle academicTitle) { this.academicTitle = academicTitle; }





    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public double getWorkload() { return workload; }
    public void setWorkload(double workload) { this.workload = workload; }

    public Department getDepartment(){return department;}
    public void setDepartment(Department department){this.department = department; }

    public Faculty getFaculty(){return faculty;}
    public void setFaculty(Faculty faculty){this.faculty = faculty;}



    public enum TeachersAcademicTitle {
        PROFESSOR("професор"),
        ASSOCIATE_PROFESSOR("доцент"),
        SENIOR_RESEARCHER ("старший дослідник"),
        NONE("не має");

        private final String label;

        TeachersAcademicTitle(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public static TeachersAcademicTitle fromString(String text) {
            for (TeachersAcademicTitle form : TeachersAcademicTitle.values()) {
                if (form.label.equalsIgnoreCase(text.trim()))
                    return form;
            }
            throw new IllegalArgumentException("Введіть правильно науковий ступінь");
        }
    }


    public enum TeachersDegree {
        PHD("доктор філософії"),
        CANDIDATE_OF_SCIENCES("доктор наук"),
        NONE("не має");

        private final String label;

        TeachersDegree(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public static Teacher.TeachersDegree fromString(String text) {
            for (Teacher.TeachersDegree form : Teacher.TeachersDegree.values()) {
                if (form.label.equalsIgnoreCase(text.trim()))
                    return form;
            }
            throw new IllegalArgumentException("Введіть правильно науковий ступінь");
        }
    }

    public enum TeachersPosition {
        PROFESSOR("професор"),
        ASSOCIATE_PROFESSOR("доцент"),
        SENIOR_LECTURER("старший викладач"),
        LECTURER("викладач"),
        TEACHING_ASSISTANT("асистент"),
        TRAINEE_LECTURER("викладач-стажист");

        private final String label;

        TeachersPosition(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public static Teacher.TeachersPosition fromString(String text) {
            for (Teacher.TeachersPosition form : Teacher.TeachersPosition.values()) {
                if (form.label.equalsIgnoreCase(text.trim()))
                    return form;
            }
            throw new IllegalArgumentException("Введіть правильно посаду з поданих");
        }
    }



    @Override
    public String toString (){
        return "ID викладача : " + getId() +",\nПІБ : " + getFirstName()
                + " " +getLastName()+ " "+ getMiddleName()+ ", Факультет : " + getFaculty().getName() + ", Кафедра : "+getDepartment().getName() +
                ",\nДата народження : " + getBirthDate() + ",\nemail : " + getEmail() + ", тел. : " + getPhone()+",\nПосада "
                + getPosition().getLabel() +", науковий ступінь " + getDegree().getLabel()
                + ", вчене звання " + getAcademicTitle().getLabel() + ",\nДата прийняття на роботу : " +
                getHireDate() + ", навантаження : " + getWorkload() + "годин на рік.\n------------------------";
    }
}





