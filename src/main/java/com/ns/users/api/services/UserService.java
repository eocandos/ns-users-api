package com.ns.users.api.services;

import com.ns.users.api.model.User;
import java.util.List;

public interface UserService {

    List<User> getAll();
    User register(User user);
    void updateStatusUser(String email, String token);
}
