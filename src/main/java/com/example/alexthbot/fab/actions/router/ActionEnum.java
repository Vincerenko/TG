package com.example.alexthbot.fab.actions.router;

public enum ActionEnum {

    START("/start"),
    REGISTRATION("Регистрация"),
    CHOSE_ROLE("/chose_role"),
    LOGIN("Логин"),
    REGISTRATION_WAITING_LOGIN("/wait login"),
    REGISTRATION_WAITING_PASSWORD("/wait password"),
    CHOOSE_DOCTOR("/choose doctor"),
    ANY(null);

    private String command;

    ActionEnum(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static ActionEnum interpret(String value) {
        if (value == null || value.isEmpty()) {
            return ANY;
        }

        value = value.trim();

        if (START.command.equals(value)) return START;
        if (REGISTRATION.command.equals(value)) return REGISTRATION;
        if (CHOSE_ROLE.command.equals(value)) return CHOSE_ROLE;
        if (LOGIN.command.equals(value)) return LOGIN;
        if (REGISTRATION_WAITING_LOGIN.command.equals(value)) return REGISTRATION_WAITING_LOGIN;
        if (REGISTRATION_WAITING_PASSWORD.command.equals(value)) return REGISTRATION_WAITING_PASSWORD;
        if (CHOOSE_DOCTOR.command.equals(value)) return CHOOSE_DOCTOR;

        return ANY;
    }

}
