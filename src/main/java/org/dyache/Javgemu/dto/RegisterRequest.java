package org.dyache.Javgemu.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String nickname;
    private boolean isAdmin = false;
}
