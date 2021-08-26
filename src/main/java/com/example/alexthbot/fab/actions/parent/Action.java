package com.example.alexthbot.fab.actions.parent;

import com.example.alexthbot.fab.actions.router.ActionEnum;
import com.example.alexthbot.fab.actions.router.ActionRouter;
import com.example.alexthbot.fab.database.user.service.BotUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.bots.AbsSender;

public abstract class Action {

    @Autowired
    protected BotUserService botUserService;

    public abstract void action(Update update, AbsSender absSender);

    public abstract ActionEnum getKey();

    @Autowired
    void add(ActionRouter actionRouter){
        actionRouter.put(getKey(), this);
    }

    public ReplyKeyboard keyboard() {
        return new ReplyKeyboardRemove(true);
    }


}
