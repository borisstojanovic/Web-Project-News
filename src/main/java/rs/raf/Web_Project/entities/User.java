package rs.raf.Web_Project.entities;

import rs.raf.Web_Project.entities.enums.Type;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class User {

    private Integer id;

    private boolean status;

    @NotNull(message = "Type field is required")
    @NotEmpty(message = "Type field is required")
    private Type type;

    @NotNull(message = "Email field is required")
    @NotEmpty(message = "Email field is required")
    private String email;

    @NotNull(message = "First_Name field is required")
    @NotEmpty(message = "First_Name field is required")
    private String firstName;

    @NotNull(message = "Last_Name field is required")
    @NotEmpty(message = "Last_Name field is required")
    private String lastName;

    @NotNull(message = "Password field is required")
    @NotEmpty(message = "Password field is required")
    private String password;

    public User() {
    }

    public User(boolean status, Type type, String email, String firstName, String lastName, String password) {
        this.status = status;
        this.type = type;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public User(Integer id, boolean status, Type type, String email, String firstName, String lastName, String password) {
        this.id = id;
        this.status = status;
        this.type = type;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
