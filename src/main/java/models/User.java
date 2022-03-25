package models;


public class User {
    private Integer id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private Integer roleId; // 1 -> Employee, 2 -> Manager

    public User(Integer id, String username, String password, String firstname, String lastname, String email, Integer roleId) {
        // all args
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.roleId = roleId;
    }

    public User() {
        // no args
    }

    public User(String username, String password) {
        // constructor to be used when validating credentials
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, Integer roleId) {
        // constructor to be used when validating credentials
        this.username = username;
        this.password = password;
        this.roleId = roleId;
    }

    public User(String username, String password, String firstname, String lastname, String email, Integer roleId) {
        // constructor to be used when creating a new user
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.roleId = roleId;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", roleId=" + roleId +
                '}';
    }

    public Boolean isManager (){
        return (this.getRoleId() == 2);
    } // Checks if the user is a manager
}
