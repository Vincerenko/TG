package com.example.alexthbot.fab.actions.router;

import com.example.alexthbot.fab.exeptions.RoleNotFoundException;

import java.util.Locale;

public enum Role {


    PATIENT("пациент"),
    DOCTOR("доктор"),
    ADMIN("админ");
    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Role interpret(String value) {
        if (value == null || value.isEmpty()) {
            throw new RoleNotFoundException(value);
        }
        value = value.trim().toLowerCase(Locale.ROOT);

        if (PATIENT.value.equals(value)) return PATIENT;
        if (DOCTOR.value.equals(value)) return DOCTOR;
        if (ADMIN.value.equals(value)) return ADMIN;
        throw new RoleNotFoundException(value);
    }
}
