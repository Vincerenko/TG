package com.example.alexthbot.fab.database.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BotUser {

    private String name;
    private String role;
    private String login;
    private String password;
    private String command;

}