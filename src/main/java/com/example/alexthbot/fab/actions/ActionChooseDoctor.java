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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActionChooseDoctor extends Action {
    @Autowired
    BotAppointment botAppointment;

    @Override
    public void action(Update update, AbsSender absSender) {
        SendMessage sendMessage = new SendMessage();
        String id = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText();
        botAppointment.setDoctor(text);
        botAppointment.setNumberRoom("Кабинет 302");
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove());
        if (text.equals("Зубной техник")) {
            botUserService.setCommand(id, ActionEnum.CHOOSE_DATE);

            sendMessage.setReplyMarkup(keyboard());
        }
        else if (text.equals("Врач нарколог")){
            botUserService.setCommand(id, ActionEnum.CHOOSE_DATE);
            sendMessage.setReplyMarkup(keyboardDrag());
        }







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

    public ReplyKeyboard keyboardDrag() {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("Консультация (1час)");
        keyboardRow.add("Бросить курить");
        keyboardRow.add("Бросить пить");
        keyboardRow.add("Лечение игромании");

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    @Override
    public ActionEnum getKey() {
        return ActionEnum.CHOOSE_DOCTOR;
    }
}
