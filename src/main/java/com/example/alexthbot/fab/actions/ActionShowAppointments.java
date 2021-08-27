package com.example.alexthbot.fab.actions;

import com.example.alexthbot.fab.actions.parent.Action;
import com.example.alexthbot.fab.actions.router.ActionEnum;
import com.example.alexthbot.fab.configuration.ConfigurationAppointment;
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
public class ActionShowAppointments extends Action {
    @Autowired
    ConfigurationAppointment configurationAppointment;
    @Override
    public void action(Update update, AbsSender absSender) {
        String s = "";
        String id = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText();
        botUserService.setCommand(id, ActionEnum.CHOOSE_LAST_NAME);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        sendMessage.setText("Ваша запись учтена, вы так же можете записаться к другому врачу.");
        sendMessage.setReplyMarkup(keyboard());
        for (int i = 0; i < configurationAppointment.appointmentList.size(); i++) {
            s+=
                    "Ваша запись от "+configurationAppointment.appointmentList.get(i).getTimeBook()+ " числа"+ "\n"
                            + "Доктор: " + configurationAppointment.appointmentList.get(i).getDoctor() + "\n"
                            + "Кабинет: " + configurationAppointment.appointmentList.get(i).getNumberRoom() + "\n"
                            + "Процедура: " + configurationAppointment.appointmentList.get(i).getProcedure() + "\n"
                            + "День: " + configurationAppointment.appointmentList.get(i).getDate() + "\n"
                            + "Время: " + configurationAppointment.appointmentList.get(i).getTime() + "\n"
                            + "Длительность процедуры: " + configurationAppointment.appointmentList.get(i).getDuration() + "\n"
                            + "\n"
                    ;
        }
        sendMessage.setText(s);
        try {
            absSender.execute(sendMessage);
        } catch (
                TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public ReplyKeyboard keyboard() {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("Записаться к другому врачу");

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }
    @Override
    public ActionEnum getKey() {
        return ActionEnum.SHOW_APPOINTMENTS;
    }
}
