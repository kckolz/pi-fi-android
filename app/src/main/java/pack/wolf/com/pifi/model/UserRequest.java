package pack.wolf.com.pifi.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserRequest implements Serializable {

    @SerializedName(value = "id")
    private Long id;

    @SerializedName(value = "firstName")
    private String firstName;

    @SerializedName(value = "lastName")
    private String lastName;

    @SerializedName(value = "email")
    private String emailAddress;

    @SerializedName(value = "admin")
    private Boolean admin;

    @SerializedName(value="password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
