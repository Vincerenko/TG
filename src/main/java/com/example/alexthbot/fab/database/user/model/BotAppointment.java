package com.example.alexthbot.fab.database.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BotAppointment {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String doctor;
    private String procedure;
    private String date;
    private String time;
    private String duration;
    private String numberRoom;
    private String timeBook;

    public BotAppointment(String doctor, String procedure, String date, String time, String duration, String numberRoom, String timeBook) {
        this.doctor = doctor;
        this.procedure = procedure;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.numberRoom = numberRoom;
        this.timeBook = timeBook;
    }


}
