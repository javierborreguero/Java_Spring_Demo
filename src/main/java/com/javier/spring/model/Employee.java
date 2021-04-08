package com.javier.spring.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class Employee {

    @Min(value = 0, message = "{empleado.id.mayorquecero}")
    // El mínimo valor del id es 0 (es decir no puede ser negativo)
    private long id;
    @NotEmpty // No puede estar vacío
    private String name;
    @Email // Tiene que tener formato email
    private String email;
    private String phoneNumber;
    // Guardar la imagen
    private String image;

    public Employee() {
    }

    public Employee(long id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Employee(@Min(value = 0, message = "{empleado.id.mayorquecero}") long id, @NotEmpty String name, @Email String email, String phoneNumber, String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && name.equals(employee.name) && email.equals(employee.email) && phoneNumber.equals(employee.phoneNumber) && image.equals(employee.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, phoneNumber, image);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
