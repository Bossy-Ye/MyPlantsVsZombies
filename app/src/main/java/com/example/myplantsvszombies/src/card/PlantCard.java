package com.example.myplantsvszombies.src.card;

import org.cocos2d.nodes.CCSprite;

import java.util.Locale;

public class PlantCard {
    public int id;
    public final CCSprite light;
    public final CCSprite dark;

    public PlantCard(int id)
    {
        light = CCSprite.sprite(String.format(Locale.CHINA,"choose/p%02d.png",id));
        dark = CCSprite.sprite(String.format(Locale.CHINA,"choose/p%02d.png",id));
        dark.setOpacity(100);
        this.id = id;
    }

}
