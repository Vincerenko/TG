package com.example.alexthbot.fab.actions;

import com.example.alexthbot.fab.actions.parent.Action;
import com.example.alexthbot.fab.actions.router.ActionEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class ActionLogin extends Action {

    @Override
    public void action(Update update, AbsSender absSender) {
        String text = update.getMessage().getText();

    }

    @Override
    public ActionEnum getKey() {
        return ActionEnum.LOGIN;
    }
}
