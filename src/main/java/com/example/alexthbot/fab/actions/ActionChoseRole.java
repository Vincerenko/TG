package com.example.alexthbot.fab.actions;

import com.example.alexthbot.fab.actions.parent.Action;
import com.example.alexthbot.fab.actions.router.ActionEnum;
import com.example.alexthbot.fab.actions.router.Role;
import com.example.alexthbot.fab.database.user.model.BotUser;
import com.example.alexthbot.fab.exeptions.RoleNotFoundException;
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
public class ActionChoseRole extends Action {

    @Override
    public void action(Update update, AbsSender absSender) {
        Role role = null;

        SendMessage sendMessage = new SendMessage();
        String chatId = update.getMessage().getChatId().toString();
        sendMessage.setChatId(chatId);
        try {
            role = Role.interpret(update.getMessage().getText());
            sendMessage.setText("Роль успешно сохранена.\nВыберите действие, которые хотите сделать далее.");
            BotUser user = botUserService.user(chatId);
            user.setRole(role);
            user.setCommand(null);
            botUserService.saveUser(chatId, user);
            sendMessage.setReplyMarkup(getKeyboard());
        } catch (RoleNotFoundException e) {
            sendMessage.setText("Роль указана неверно!");
        }



        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public ReplyKeyboard getKeyboard() {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("Логин");
        keyboardRow.add("Регистрация");

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    @Override
    public ActionEnum getKey() {
        return ActionEnum.CHOSE_ROLE;
    }
}
