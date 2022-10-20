package com.example.myplantsvszombies.src.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGSize;

public class PauseLayer extends CCLayer {
    private  GameLayer layer;
    private CCSprite back;
    private CGSize winSize;
    private CCMenu comeMainMenu;
    private CCMenu comeBackMenu;
    private CCSprite pushDefault;
    private CCSprite pushPress;
    public PauseLayer(GameLayer layer)
    {
        this.layer = layer;
        load();
    }

    public void load()
    {
        back = CCSprite.sprite("menu/OptionsMenuback32.png");
        winSize= CCDirector.sharedDirector().getWinSize();
        back.setPosition(winSize.width/2,winSize.height/2);
        this.addChild(back);

        //come back game layer
        comeBackMenu= CCMenu.menu();
        CCSprite pushDefault=
                CCSprite.sprite("menu/push.png");
        CCSprite pushPress=
                CCSprite.sprite("menu/pushed.png");
        CCMenuItemSprite ccMenuItemSprite= CCMenuItemSprite.item(pushDefault,
                pushPress,this,"goBack");
        ccMenuItemSprite.setPosition(getPosition().x,getPosition().y-190);
        comeBackMenu.addChild(ccMenuItemSprite);
        this.addChild(comeBackMenu);


        //go to menu layer
        comeMainMenu = CCMenu.menu();
        pushDefault = CCSprite.sprite("menu/remain.png");
        pushPress = CCSprite.sprite("menu/remained.png");
        CCMenuItemSprite remainMenuItemSprite= CCMenuItemSprite.item(pushDefault,
                pushPress,this,"goMenuLayer");
        remainMenuItemSprite.setPosition(getPosition().x,getPosition().y-100);
        remainMenuItemSprite.setScale(0.5f);
        comeMainMenu.addChild(remainMenuItemSprite);
        this.addChild(comeMainMenu);



    }
    public void goBack(Object object)
    {
        layer.onEnter();
        this.removeSelf();
    }
    public void goMenuLayer(Object object)
    {
        layer.onEnter();
        layer.end();
        CCScene ccScene = CCScene.node();
        ccScene.addChild(new MenuLayer());
        CCFadeTransition ccFadeTransition = CCFadeTransition.transition(
                2f,ccScene
        );
        CCDirector.sharedDirector().replaceScene(ccFadeTransition);
        this.removeSelf();
    }
}
