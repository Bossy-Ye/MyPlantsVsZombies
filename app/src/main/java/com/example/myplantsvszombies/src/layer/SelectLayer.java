package com.example.myplantsvszombies.src.layer;

import com.example.myplantsvszombies.Tools;
import com.example.myplantsvszombies.src.card.SelectedCard;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;

public class SelectLayer extends CCLayer {
    private ArrayList<SelectedCard>  cards;
    private CGSize winSize = CCDirector.sharedDirector().winSize();
    private CCSprite background;

    public SelectLayer()
    {
        this.cards = new ArrayList<>();
        load();
    }

    public void load()
    {
        if(Tools.isNight())
        {
            background = CCSprite.sprite("interface/selectCheck.png");
        }else
        {
            background = CCSprite.sprite("interface/checkpointLight.png");
        }
        background.setPosition(winSize.width/2,winSize.height/2);
        addChild(background,0);
        background.setScaleX(winSize.width/background.getTextureRect().size.getWidth());
        background.setScaleY(winSize.height/background.getTextureRect().size.getHeight());

        //
    }


}
