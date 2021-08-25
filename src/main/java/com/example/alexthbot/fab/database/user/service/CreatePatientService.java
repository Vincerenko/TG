package com.example.alexthbot.fab.database.user.service;

import com.example.alexthbot.fab.actions.router.ActionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class CreatePatientService {
    @Autowired
    BotUserService botUserService;
    public String setPerson(Update update, String value, String text, ReplyKeyboard keyboard){
        String id = update.getMessage().getChatId().toString();
        String name = update.getMessage().getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        sendMessage.setText("Напишите свою фамилию:");
        sendMessage.setReplyMarkup(keyboard);
        botUserService.setFirstName(id,name);
        botUserService.setCommand(id, ActionEnum.CHOOSE_LAST_NAME);
        return id;
    }
}
