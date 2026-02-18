package org.dyache.Javgemu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subscribe")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscribeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "subscriber_id", nullable = false)
    private UserEntity userId;

    @ManyToOne()
    @JoinColumn(name = "target_id", nullable = false)
    private UserEntity subscriber;
}
