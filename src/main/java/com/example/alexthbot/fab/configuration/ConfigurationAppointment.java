package com.example.alexthbot.fab.configuration;

import com.example.alexthbot.fab.database.user.model.BotAppointment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConfigurationAppointment {
    public List<BotAppointment> appointmentList = new ArrayList<>();
}
