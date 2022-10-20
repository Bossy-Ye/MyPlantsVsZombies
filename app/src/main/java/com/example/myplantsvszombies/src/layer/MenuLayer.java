package com.example.myplantsvszombies.src.layer;

import com.example.myplantsvszombies.R;
import com.example.myplantsvszombies.Tools;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGSize;

public class MenuLayer extends CCLayer {
    CCDirector director = CCDirector.sharedDirector();
    CGSize winSize = director.winSize();

    public MenuLayer()
    {
        Tools.bgmSound(R.raw.bgm);
        CCSprite ccSprite_menu = CCSprite.sprite("menu/main_menu_bg.png");
        this.addChild(ccSprite_menu);
        //ccSprite_menu.setPosition(winSize.width/2,winSize.height/2);
        ccSprite_menu.setAnchorPoint(0,0);//左上角！
        setAnchorPoint(0,0);
        ccSprite_menu.setScaleX(Tools.screen_width/ccSprite_menu.getTextureRect().size.getWidth());


        //游戏进入
        CCMenu ccMenu= CCMenu.menu();
        CCSprite ccSprite_start_adventure_default=
                CCSprite.sprite("menu/start_adventure_default.png");
        CCSprite ccSprite_start_adventure_press=
                CCSprite.sprite("menu/start_adventure_press.png");
        CCMenuItemSprite ccMenuItemSprite= CCMenuItemSprite.item(ccSprite_start_adventure_default,
                ccSprite_start_adventure_press,this,"startGame");
        this.setAnchorPoint(0,0);

        ccMenuItemSprite.setPosition(350,160);
        ccMenuItemSprite.setScale(1.2f);
        ccMenu.addChild(ccMenuItemSprite);
        this.addChild(ccMenu);


        //随便看看，图鉴
        CCMenu ccMenu2= CCMenu.menu();
        CCSprite ccSprite_start_adventure_default2=
                CCSprite.sprite("menu/look.png");
        CCSprite ccSprite_start_adventure_press2=
                CCSprite.sprite("menu/looked.png");
        CCMenuItemSprite ccMenuItemSprite2= CCMenuItemSprite.item(ccSprite_start_adventure_default2,
                ccSprite_start_adventure_press2,this,"almanac");
        ccMenuItemSprite2.setPosition(300,-70);
        ccMenuItemSprite2.setScale(1.7f);
        ccMenu.addChild(ccMenuItemSprite2);
        addChild(ccMenu2);

        //contentProvider
        /*CCMenu ccMenu3= CCMenu.menu();
        CCSprite ccSprite_contentPro_off=
                CCSprite.sprite("menu/contentprovider_off.png");
        CCSprite ccSprite_contentPro_on=
                CCSprite.sprite("menu/contentprovider_on.png");
        CCMenuItemSprite ccMenuItemSprite3= CCMenuItemSprite.item(ccSprite_contentPro_off,
                ccSprite_contentPro_on,this,"contentPro");
        ccMenuItemSprite3.setPosition(440,-300);
        ccMenuItemSprite3.setScale(0.4f);
        ccMenu.addChild(ccMenuItemSprite3);
        addChild(ccMenu3);*/

        //退出
        CCMenu ccMenuExit = CCMenu.menu();
        CCSprite ccSprite_exit_default =
                CCSprite.sprite("menu/exit.png");
        CCSprite ccSprite_exit_press =
                CCSprite.sprite("menu/exited.png");
        CCMenuItemSprite ccMenuItemExitSprite = CCMenuItemSprite.item(ccSprite_exit_default,
                ccSprite_exit_press,this,"exit");
        ccMenuItemExitSprite.setPosition(-300,-150);
        ccMenuItemExitSprite.setScale(0.6f);
        ccMenu.addChild(ccMenuItemExitSprite);
        addChild(ccMenuExit);

        //欢迎
        CCSprite ccSprite_welcome =
                CCSprite.sprite("interface/sanzhai.png");
        addChild(ccSprite_welcome);
        ccSprite_welcome.setScale(0.9f);
        ccSprite_welcome.setAnchorPoint(0,0);
        ccSprite_welcome.setPosition(0,380);
        ccSprite_welcome.setScale(0.8f);

    }

    public void exit(Object item)
    {
        System.exit(0);
    }
    public void contentPro(Object item)
    {
        CCScene ccScene = CCScene.node();
        ccScene.addChild(new ContentProLayer());
        CCFadeTransition ccFadeTransition = CCFadeTransition.transition(
                2f,ccScene
        );
        director.replaceScene(ccFadeTransition);
    }
    public void almanac(Object item)
    {
        CCScene ccScene= CCScene.node();
        ccScene.addChild(new AlmanacLayer(this));

        CCFadeTransition ccFadeDownTransition = CCFadeTransition.transition(
                2f, ccScene);
        //因为后面还要回来所以先移除
        CCDirector.sharedDirector().getRunningScene().removeAllChildren(false);
        director.replaceScene(ccFadeDownTransition);
    }
    public void startGame(Object item)
    {
        Tools.effectiveSound(R.raw.dight);
        CCScene ccScene = CCScene.node();
        ccScene.addChild(new GameLayer());
        CCFadeTransition ccFadeTransition = CCFadeTransition.transition(
                2f,ccScene
        );
        director.replaceScene(ccFadeTransition);
    }
}
