package com.example.alexthbot.fab.database.repository;

import com.example.alexthbot.fab.database.user.model.BotUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotUserRepository extends CrudRepository<BotUser, Long> {

}
