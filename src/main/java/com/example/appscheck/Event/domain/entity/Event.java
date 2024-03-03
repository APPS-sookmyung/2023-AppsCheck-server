package com.example.appscheck.Event.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name="postevent")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int EventId;

    @Column
    private String eventTime;

    @Column
    private String eventPlace;
    @Column
    private String eventDetail;
}
