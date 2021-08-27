package com.example.alexthbot.fab.actions;

import com.example.alexthbot.fab.actions.parent.Action;
import com.example.alexthbot.fab.actions.router.ActionEnum;
import com.example.alexthbot.fab.configuration.ConfigurationAppointment;
import com.example.alexthbot.fab.database.repository.BotAppointmentRepository;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ActionBooked extends Action {
    @Autowired
    BotAppointment botAppointment;
    @Autowired
    BotAppointmentRepository botAppointmentRepository;
    @Autowired
    ConfigurationAppointment configurationAppointment;


    @Override
    public void action(Update update, AbsSender absSender) {
        String id = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText();
        botAppointment.setTime(text);
        String s = LocalDateTime.now().toString();
        botAppointment.setTimeBook(s.split("T")[0]);
        botUserService.setCommand(id, ActionEnum.SHOW_APPOINTMENTS);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        botAppointmentRepository.save(botAppointment);
        BotAppointment botAppointment2 = new BotAppointment();
        botAppointment2.setTimeBook(botAppointment.getTimeBook());
        botAppointment2.setDoctor(botAppointment.getDoctor());
        botAppointment2.setNumberRoom(botAppointment.getNumberRoom());
        botAppointment2.setProcedure(botAppointment.getProcedure());
        botAppointment2.setDate(botAppointment.getDate());
        botAppointment2.setTime(botAppointment.getTime());
        botAppointment2.setDuration(botAppointment.getDuration());
        configurationAppointment.appointmentList.add(botAppointment2);


        sendMessage.setText("Ваша запись от "+botAppointment.getTimeBook()+ " числа"+ "\n"
                + "Доктор: " + botAppointment.getDoctor() + "\n"
                + "Кабинет: " + botAppointment.getNumberRoom() + "\n"
                + "Процедура: " + botAppointment.getProcedure() + "\n"
                + "День: " + botAppointment.getDate() + "\n"
                + "Время: " + botAppointment.getTime() + "\n"
                + "Длительность процедуры: " + botAppointment.getDuration() + "\n"
        );
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
        keyboardRow.add("Мои записи");

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }


    @Override
    public ActionEnum getKey() {
        return ActionEnum.MIDDLE_BOOKED;
    }
}
