package com.example.alexthbot.fab.exeptions;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String name) {
        super("Role with name '" + name + "' doesn't exist");
    }
}
