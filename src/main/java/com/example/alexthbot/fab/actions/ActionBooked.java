package com.example.alexthbot.fab.actions;

import com.example.alexthbot.fab.actions.parent.Action;
import com.example.alexthbot.fab.actions.router.ActionEnum;
import com.example.alexthbot.fab.database.repository.BotAppointmentRepository;
import com.example.alexthbot.fab.database.user.model.BotAppointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;

@Component
public class ActionBooked extends Action {
    @Autowired
    BotAppointment botAppointment;
    @Autowired
    BotAppointmentRepository botAppointmentRepository;


    @Override
    public void action(Update update, AbsSender absSender) {
        String id = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText();
        botAppointment.setTime(text);
        String s = LocalDateTime.now().toString();
        botAppointment.setTimeBook(s.split("T")[0]);
        botUserService.setCommand(id, ActionEnum.BOOKED);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        botAppointmentRepository.save(botAppointment);


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



    @Override
    public ActionEnum getKey() {
        return ActionEnum.BOOKED;
    }
}
