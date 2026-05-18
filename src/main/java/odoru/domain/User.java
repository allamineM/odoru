package odoru.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String lastName;
    private String firstName;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String username;

    private String password;
    private Address address;
    private int expertiseLevel; // 1 à 5
    private Set<Role> roles = new HashSet<>();

    public enum Role {
        MEMBER, SECRETARY, TEACHER, PRESIDENT
    }

    public User() {}

    public String getId() { return id; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public int getExpertiseLevel() { return expertiseLevel; }
    public void setExpertiseLevel(int expertiseLevel) { this.expertiseLevel = expertiseLevel; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
}