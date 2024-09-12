package com.ns.users.api.services;

import com.ns.users.api.dto.UserDataDTO;
import java.util.List;

public interface UserService {

    List<UserDataDTO> getAll();
    UserDataDTO register(UserDataDTO user);
    void updateStatusUser(String email, String token);
}
