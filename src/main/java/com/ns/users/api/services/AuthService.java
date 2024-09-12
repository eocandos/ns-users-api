package com.ns.users.api.services;

import com.ns.users.api.dto.UserDataDTO;

public interface AuthService {

    String login(UserDataDTO userDataDTO);

}
