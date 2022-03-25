package repositories;

import models.User;

public interface UserDAO {
    User getUserGivenUsername(String username); // used to validate credentials and allow user to log in
    void createUser (User userToCreate);
    User getUserGivenEmail(String email);
    User getUserGivenId(Integer id);
}