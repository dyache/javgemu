package org.dyache.Javgemu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dyache.Javgemu.dto.ReviewOutDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDto {
    private UserOutDto user;
    private List<ReviewOutDto> reviews;
}
