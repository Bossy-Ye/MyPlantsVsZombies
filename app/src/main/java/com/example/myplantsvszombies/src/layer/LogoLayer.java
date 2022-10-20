package com.example.myplantsvszombies.src.layer;

import android.view.MotionEvent;

import com.example.myplantsvszombies.R;
import com.example.myplantsvszombies.Tools;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.transitions.CCJumpZoomTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;
import java.util.Locale;

public class LogoLayer extends CCLayer{
    CCDirector director = CCDirector.sharedDirector();
    CGSize winSize = director.winSize();

    public LogoLayer()
    {
        logo1();

        //单列获取
        Tools.bgm = SoundEngine.sharedEngine();
        Tools.bgmSound(R.raw.bgm);
    }

    private void logo1()
    {
        CCSprite ccSprite1 = new CCSprite("logo/logo3.png");
        ccSprite1.setAnchorPoint(0,0);
        CGSize size2 = ccSprite1.getContentSize();
        ccSprite1.setScaleX(Tools.screen_width/size2.width);
        ccSprite1.setScaleY(Tools.screen_height/size2.height);
        this.addChild(ccSprite1);

        CCDelayTime delayTime = CCDelayTime.action(2);
        CCHide hide = CCHide.action();
        CCCallFunc callFunc = CCCallFunc.action(this,"logo2");

        CCSequence sequence = CCSequence.actions(delayTime,hide,callFunc);

        ccSprite1.runAction(sequence);
    }
    public void logo2()//public?!!!
    {
        CCSprite ccSprite = new CCSprite("logo/logo2.png");
        ccSprite.setPosition(winSize.getWidth()/2, winSize.getHeight()/2);
        this.addChild(ccSprite);

        CCDelayTime delayTime = CCDelayTime.action(3);
        CCHide hide = CCHide.action();
        CCCallFunc callFunc = CCCallFunc.action(this,"background");
        CCSequence sequence = CCSequence.actions(delayTime,hide,callFunc);

        ccSprite.runAction(sequence);
    }

    public void background()
    {
        CCSprite ccSpriteBg = new CCSprite("cg/bg.jpg");
        this.addChild(ccSpriteBg);

        ccSpriteBg.setPosition(winSize.getWidth()/2, winSize.getHeight()/2);
        ccSpriteBg.setScaleX(winSize.getWidth()/ccSpriteBg.getTextureRect().size.getWidth());
        ccSpriteBg.setScaleY(winSize.getHeight()/ccSpriteBg.getTextureRect().size.getHeight());

       CCSprite ccSpriteLoad1 = new CCSprite("cg/loading_01.png");
       this.addChild(ccSpriteLoad1);
       ccSpriteLoad1.setPosition(winSize.getWidth()/2, 80);
       ccSpriteLoad1.setScale(1.9f);


        ArrayList<CCSpriteFrame> loadingFrames = new ArrayList<>();

        ArrayList<CCSpriteFrame> frames=new ArrayList<>();
        for (int i = 1; i <11 ; i++) {
            CCSpriteFrame ccSpriteFrame= CCSprite.sprite(String.format(Locale.CHINA,
                    "cg/loading_%02d.png",i)).displayedFrame();
            frames.add(ccSpriteFrame);
        }

        CCAnimation ccAnimation= CCAnimation.animation("loading",0.2f,frames);

        CCAnimate ccAnimate = CCAnimate.action(ccAnimation,false);
        CCCallFunc callFunc = CCCallFunc.action(this,"setTouch");
        CCSequence sequence = CCSequence.actions(ccAnimate,callFunc);

        ccSpriteLoad1.runAction(sequence);
    }

    public void setTouch()
    {
        setIsTouchEnabled(true);
    }

    @Override
    public boolean ccTouchesBegan(MotionEvent event)
    {
        CGPoint point = convertTouchToNodeSpace(event);
        CCSprite ccSpriteLoad = new CCSprite("cg/loading_10.png");
        this.addChild(ccSpriteLoad);
        ccSpriteLoad.setPosition(winSize.getWidth()/2, 80);
        ccSpriteLoad.setScale(1.9f);
        CGRect rect = ccSpriteLoad.getBoundingBox();

        boolean containsPoint = CGRect.containsPoint(rect, point);
        if(containsPoint){
            CCScene ccScene= CCScene.node();
            ccScene.addChild(new MenuLayer());
            CCJumpZoomTransition ccJumpZoomTransition= CCJumpZoomTransition.transition(2,ccScene);
            director.replaceScene(ccJumpZoomTransition);
        }

        return false;
    }
}
