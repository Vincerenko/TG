package com.example.alexthbot.fab.actions.router;

public enum ActionEnum {

    START("/start"),
    REGISTRATION("Регистрация"),
    CHOSE_ROLE("/chose_role"),
    LOGIN("Логин"),
    REGISTRATION_WAITING_LOGIN("/wait login"),
    REGISTRATION_WAITING_PASSWORD("/wait password"),
    CHOOSE_DOCTOR("/choose doctor"),
    CHOOSE_PROCEDURE("/choose procedure"),
    CHOOSE_TIME("/choose time"),
    CHOOSE_FIRST_NAME("/choose first name"),
    CHOOSE_LAST_NAME("/choose last name"),
    CHOOSE_DATE("/choose date"),
    BOOKED("/booked"),
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
        if (CHOOSE_FIRST_NAME.command.equals(value)) return CHOOSE_FIRST_NAME;
        if (CHOOSE_LAST_NAME.command.equals(value)) return CHOOSE_LAST_NAME;
        if (CHOOSE_PROCEDURE.command.equals(value)) return CHOOSE_PROCEDURE;
        if (CHOOSE_TIME.command.equals(value)) return CHOOSE_TIME;
        if (CHOOSE_DOCTOR.command.equals(value)) return CHOOSE_DOCTOR;
        if (CHOOSE_DATE.command.equals(value)) return CHOOSE_DATE;
        if (BOOKED.command.equals(value)) return BOOKED;

        return ANY;
    }

}
