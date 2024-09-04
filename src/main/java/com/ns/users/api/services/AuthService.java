package com.ns.users.api.services;

import com.ns.users.api.model.User;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    String login(String email, String password);

    User checkToken(HttpServletRequest req);

    // String refreshToken(String email);
}
