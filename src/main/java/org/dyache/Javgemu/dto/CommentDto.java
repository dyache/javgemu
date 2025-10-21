package org.dyache.Javgemu.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String authorNickname;
    private String content;
    private LocalDateTime createdAt;
}