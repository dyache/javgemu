package org.dyache.Javgemu.controller;

import org.dyache.Javgemu.dto.ProfileResponseDto;
import org.dyache.Javgemu.dto.ProfileUpdateDto;
import org.dyache.Javgemu.entity.UserEntity;
import org.dyache.Javgemu.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfile(@AuthenticationPrincipal UserEntity currentUser) {
        System.out.println(currentUser.getEmail());
        System.out.println("getting prof");
        ProfileResponseDto profile = profileService.getProfile(currentUser.getEmail());
        return ResponseEntity.ok(profile);
    }


    @PatchMapping
    public ResponseEntity<?> updateProfile(@RequestBody ProfileUpdateDto dto,
                                           @AuthenticationPrincipal UserEntity currentUser) {
        profileService.updateProfile(currentUser.getEmail(), dto);
        return ResponseEntity.ok().body("Профиль обновлён");
    }
}