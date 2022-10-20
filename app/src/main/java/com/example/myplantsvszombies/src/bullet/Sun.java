package com.example.myplantsvszombies.src.bullet;
import com.example.myplantsvszombies.R;
import com.example.myplantsvszombies.Tools;
import com.example.myplantsvszombies.src.layer.GameLayer;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

import java.util.ArrayList;
import java.util.Locale;

public class Sun extends CCSprite{
    private boolean isNowCollect;
    private ArrayList<Sun> suns;
    public Sun() {
        super("sun/Frame00.png");
        ArrayList<CCSpriteFrame> frames = new ArrayList<>();
        for (int i = 0;i<22;i++){
            CCSpriteFrame ccSpriteFrame = CCSprite.sprite(String.format(Locale.CHINA,
                    "sun/Frame%02d.png",i)).displayedFrame();
            frames.add((ccSpriteFrame));
        }
        CCAnimation ccAnimation = CCAnimation.animationWithFrames(frames,0.2f);
        CCAnimate ccAnimate = CCAnimate.action(ccAnimation,true);
        CCRepeatForever ccRepeatForever = CCRepeatForever.action(ccAnimate);
        runAction(ccRepeatForever);
    }

    public Sun(ArrayList<Sun> suns) {
        super("sun/Frame00.png");
        this.suns = suns;
        ArrayList<CCSpriteFrame> frames = new ArrayList<>();
        for (int i = 0;i<22;i++){
            CCSpriteFrame ccSpriteFrame = CCSprite.sprite(String.format(Locale.CHINA,
                    "sun/Frame%02d.png",i)).displayedFrame();
            frames.add((ccSpriteFrame));
        }
        CCAnimation ccAnimation = CCAnimation.animationWithFrames(frames,0.2f);
        CCAnimate ccAnimate = CCAnimate.action(ccAnimation,true);
        CCRepeatForever ccRepeatForever = CCRepeatForever.action(ccAnimate);
        runAction(ccRepeatForever);

        CCDelayTime ccDelayTime = CCDelayTime.action(14);
        CCCallFunc ccCallFunc = CCCallFunc.action(this,"remove");
        CCSequence ccSequence = CCSequence.actions(ccDelayTime,ccCallFunc);
        runAction(ccSequence);
    }

    public void remove(){
        suns.remove(this);
        if(!isNowCollect()){
            removeSelf();
        }
    }

    public void collect(){
        isNowCollect = true;
        CGPoint end = ccp(50,720);
        float t = CGPointUtil.distance(getPosition(),end)/1000;
        CCMoveTo ccMoveTo = CCMoveTo.action(t,end);
        CCCallFunc ccCallFunc = CCCallFunc.action(this,"addSunNumber");
        CCSequence ccSequence = CCSequence.actions(ccMoveTo,ccCallFunc);
        runAction(ccSequence);
        Tools.effectiveSound(R.raw.get);
    }

    public void addSunNumber(){
        ((GameLayer)getParent()).addSunNumber(25);
        removeSelf();
    }

    public boolean isNowCollect() {
        return isNowCollect;
    }
}

