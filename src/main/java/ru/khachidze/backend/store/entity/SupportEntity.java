package ru.khachidze.backend.store.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "support")
public class SupportEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "response")
    private String response;

    @Column(name = "recording_time_question", nullable = false)
    private Date recordingTimeQuestion;

    @Column(name = "recording_time_response")
    private Date recordingTimeResponse;


}
