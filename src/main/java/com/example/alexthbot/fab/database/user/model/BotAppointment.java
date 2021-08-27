package com.example.alexthbot.fab.database.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BotAppointment {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column
    private String doctor;
    @Column
    private String procedure;
    @Column
    private String date;
    @Column
    private String time;
    @Column
    private String duration;
    @Column
    private String numberRoom;


    public BotAppointment(String doctor, String procedure, String date, String duration, String numberRoom) {
        this.doctor = doctor;
        this.procedure = procedure;
        this.date = date;
        this.duration = duration;
        this.numberRoom = numberRoom;
    }
}
