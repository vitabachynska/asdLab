package domain;

import java.time.LocalDate;
import java.util.Objects;

public sealed abstract class Person permits Student, Teacher {
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String phone;

    protected Person(String id, String firstName, String lastName, String middleName,
                  LocalDate birthDate, String email, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
    }

    protected Person() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(firstName, person.firstName) && Objects.equals(middleName, person.middleName) && Objects.equals(lastName, person.lastName) && Objects.equals(birthDate, person.birthDate) && Objects.equals(email, person.email) && Objects.equals(phone, person.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, middleName, lastName, birthDate, email, phone);
    }

    public String toString() {
        return "ID : " + getId() + ", " + getFirstName() + getLastName() + getMiddleName() +
                ", " + getBirthDate() + ", email : " + getEmail() + ", тел. :" + getPhone();
    }
}
