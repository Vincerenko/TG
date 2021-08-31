package com.example.alexthbot;

import com.example.alexthbot.fab.actions.router.ActionEnum;
import com.example.alexthbot.fab.actions.router.ActionRouter;
import com.example.alexthbot.fab.database.user.model.BotUser;
import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class MainTelegramBot extends TelegramLongPollingBot {



    @Value("${telegram.bot.name}")
    private String name;

    @Value("${telegram.bot.token}")
    private String token;

    @Autowired
    private ActionRouter router;

    @Autowired
    private Cache<String, BotUser> cache;

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()){
            final Message message = update.getMessage();
            if (message.hasText()){

                String chatId = message.getChatId().toString();

                BotUser user = cache.getIfPresent(chatId);
                if (user == null){
                    user = new BotUser();
                    cache.put(chatId, user);
                }

                String command = user.getCommand();
                if (command == null){
                    command = message.getText();
                }


                final ActionEnum action = ActionEnum.interpret(command);
                router.get(action).action(update,this);
            }
        }
        if (update.hasCallbackQuery()){
        }
    }

}
