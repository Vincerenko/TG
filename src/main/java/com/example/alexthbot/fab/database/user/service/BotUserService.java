package com.example.alexthbot.fab.database.user.service;

import com.example.alexthbot.fab.actions.router.ActionEnum;
import com.google.common.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Consumer;


@Service
public class BotUserService {

    @Autowired
    protected Cache<String, com.example.alexthbot.fab.database.user.model.BotUser> cache;

    public void setCommand(String chatId, ActionEnum actionEnum){
        changeUser(chatId,botUser -> botUser.setCommand(actionEnum.getCommand()));
    }

    public void setFirstName(String chatId, String name){
        changeUser(chatId,botUser -> botUser.setFirstName(name));
    }

    public void setSecondName(String chatId, String secondName){
        changeUser(chatId,botUser -> botUser.setSecondName(secondName));
    }

    public void setLogin(String chatId, String login){
        changeUser(chatId,botUser -> botUser.setLogin(login));
    }

    public void setPassword(String chatId, String password){
        changeUser(chatId,botUser -> botUser.setPassword(password));
    }

    public com.example.alexthbot.fab.database.user.model.BotUser user(String chatId){
        return cache.getIfPresent(chatId);
    }

    public void saveUser (String chatId , com.example.alexthbot.fab.database.user.model.BotUser botUser){
        cache.put(chatId,botUser);
    }

    private void changeUser(String chatId, Consumer<com.example.alexthbot.fab.database.user.model.BotUser> action){
        com.example.alexthbot.fab.database.user.model.BotUser botUser = user(chatId);
        action.accept(botUser);
        saveUser(chatId,botUser);
    }

    public String getFirstName(String chatId){
        return Objects.requireNonNull(cache.getIfPresent(chatId)).getFirstName();
    }

    public String getSecondName(String chatId){
        return Objects.requireNonNull(cache.getIfPresent(chatId)).getSecondName();
    }
}
