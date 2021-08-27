package com.example.alexthbot.fab.database.repository;

import com.example.alexthbot.fab.database.user.model.BotAppointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotAppointmentRepository extends CrudRepository<BotAppointment,Long> {

}
