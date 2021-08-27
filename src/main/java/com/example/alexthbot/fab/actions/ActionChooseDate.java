package com.example.alexthbot.fab.actions;

import com.example.alexthbot.fab.actions.parent.Action;
import com.example.alexthbot.fab.actions.router.ActionEnum;
import com.example.alexthbot.fab.database.user.model.BotAppointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActionChooseDate extends Action {
    @Autowired
    BotAppointment botAppointment;

    @Override
    public void action(Update update, AbsSender absSender) {
        String id = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText();
        if (text.equals("Консультация (1час)")) {
            botAppointment.setProcedure(text);
            botAppointment.setDuration("1 час");
        } else {
            botAppointment.setProcedure(text);
            botAppointment.setDuration("Назначает врач");
        }


        botUserService.setCommand(id, ActionEnum.CHOOSE_TIME);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        sendMessage.setText("Выберите дату: ");
        sendMessage.setReplyMarkup(keyboard());
        try {
            absSender.execute(sendMessage);
        } catch (
                TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public ReplyKeyboard keyboard() {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("27 августа");
        keyboardRow.add("28 августа");
        keyboardRow.add("29 августа");
        keyboardRow.add("30 августа");

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    @Override
    public ActionEnum getKey() {
        return ActionEnum.CHOOSE_DATE;
    }
}
