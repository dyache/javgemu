package org.dyache.Javgemu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.dyache.Javgemu.dto.UserOutDto;
import org.dyache.Javgemu.service.UserService ;
import org.dyache.Javgemu.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public ResponseEntity<List<UserOutDto>> getAllUsers() {
        List<UserOutDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUserByEmail(@PathVariable String email,
                                               @AuthenticationPrincipal UserEntity currentUser) {

        if (!currentUser.getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Можно удалить только свой аккаунт");
        }

        userService.deleteUserByEmail(email);
        return ResponseEntity.ok("Ваш аккаунт удалён");
    }


    @GetMapping("/profile/{nickname}")
    public ResponseEntity<UserOutDto> getUserProfile(@PathVariable String nickname) {
        UserOutDto user = userService.getUserProfile(nickname);
        return ResponseEntity.ok(user);
    }
}
