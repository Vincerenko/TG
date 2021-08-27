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
public class ActionChooseProcedureTooth extends Action {
    @Autowired
    BotAppointment botAppointment;

    @Override
    public void action(Update update, AbsSender absSender) {
    String id = update.getMessage().getChatId().toString();
    String text = update.getMessage().getText();



        botUserService.setCommand(id, ActionEnum.CHOOSE_DATE);

    SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        sendMessage.setText("Выберите процедуру: \n(В первый раз советуем выбрать консультацию)");
        //sendMessage.setReplyMarkup(keyboard());
        try {
        absSender.execute(sendMessage);
    } catch (
    TelegramApiException e) {
        e.printStackTrace();
    }
}

    public ReplyKeyboard keyboard() {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("Консультация (1час)");
        keyboardRow.add("Вырвать зуб");
        keyboardRow.add("Поставить пломбу");
        keyboardRow.add("Отбелить зубы");

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    @Override
    public ActionEnum getKey() {
        return ActionEnum.CHOOSE_PROCEDURE_TOOTH;
    }
}


