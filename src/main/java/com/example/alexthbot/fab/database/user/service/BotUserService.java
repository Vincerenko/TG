package com.example.alexthbot.fab.database.user.service;

import com.example.alexthbot.fab.actions.router.ActionEnum;
import com.example.alexthbot.fab.database.user.model.BotUser;
import com.google.common.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class BotUserService {

    @Autowired
    protected Cache<String, BotUser> cache;

    public void setCommand(String chatId, ActionEnum actionEnum){
        changeUser(chatId,botUser -> botUser.setCommand(actionEnum.getCommand()));
    }

    public void setLogin(String chatId, String login){
        changeUser(chatId,botUser -> botUser.setLogin(login));
    }

    public void setPassword(String chatId, String password){
        changeUser(chatId,botUser -> botUser.setPassword(password));
    }

    private BotUser user(String chatId){
        return cache.getIfPresent(chatId);
    }

    private void saveUser (String chatId , BotUser botUser){
        cache.put(chatId,botUser);
    }

    private void changeUser(String chatId, Consumer<BotUser> action){
        BotUser botUser = user(chatId);
        action.accept(botUser);
        saveUser(chatId,botUser);
    }
}
