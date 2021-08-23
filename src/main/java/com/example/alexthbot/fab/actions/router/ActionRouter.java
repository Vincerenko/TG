package com.example.alexthbot.fab.actions.router;

import com.example.alexthbot.fab.actions.parent.Action;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ActionRouter {

    private final Map<ActionEnum, Action> driveMap = new HashMap<>();

    public void put(ActionEnum key, Action value) {
        driveMap.put(key, value);
    }

    public Action get(ActionEnum key){
        return driveMap.get(key);
    }
}