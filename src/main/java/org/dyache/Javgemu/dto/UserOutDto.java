package org.dyache.Javgemu.dto;

import org.dyache.Javgemu.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOutDto {
    private Long id;
    private String email;
    private String nickname;
    private String bio;

    public static UserOutDto fromEntity(UserEntity user) {
        return new UserOutDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getBio()
        );
    }
}
