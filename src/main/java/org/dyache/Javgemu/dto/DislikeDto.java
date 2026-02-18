package org.dyache.Javgemu.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DislikeDto {
    @JsonAlias("review_id")
    private Long reviewId;
}