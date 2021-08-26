package com.example.alexthbot.fab.database.user.model;

import com.example.alexthbot.fab.actions.router.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BotUser {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String secondName;
    @Column
    private Role role;
    @Column
    private String login;
    @Column
    private String password;
    @Column
    private String command;

    public BotUser(String firstName, String secondName, Role role, String login, String password, String command) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.role = role;
        this.login = login;
        this.password = password;
        this.command = command;
    }
}