package com.example.myplantsvszombies.src.plant;

import com.example.myplantsvszombies.Tools;
import com.example.myplantsvszombies.src.effect.AEffect;
import com.example.myplantsvszombies.src.zombie.Zombie;

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import com.example.myplantsvszombies.R;

public class Kakashi extends Plant{
    private Zombie zombie;
    private CGPoint cgPoint;
    private ArrayList<Zombie> zombies;
    public Kakashi(){
        super("plant/kakashi/stand%02d.png",3);
        setPrice(20);
        setScale(2f);

    }

    public void start(Zombie zombie,ArrayList<Zombie> zombies){
        this.zombie = zombie;
        this.zombies = zombies;
        CCDelayTime ccDelayTime = CCDelayTime.action(1.6f);
        CCCallFunc ccCallFunc = CCCallFunc.action(this,"spell");
        CCSequence ccSequence = CCSequence.actions(ccDelayTime,ccCallFunc);
        runAction(ccSequence);
        cgPoint = zombie.getPosition();
    }

    public void spell(){
        ArrayList<CCSpriteFrame> frames = new ArrayList<>();
        for (int i = 0; i< 8;i++){
            CCSpriteFrame ccSpriteFrame;
            ccSpriteFrame = CCSprite.sprite(String.format(Locale.CHINA,
                    "plant/kakashi/spell%02d.png",i)).displayedFrame();
            frames.add(ccSpriteFrame);
        }
        CCAnimation ccAnimation = CCAnimation.animationWithFrames(frames,0.125f);
        CCAnimate ccAnimate = CCAnimate.action(ccAnimation,true);
        CCRepeatForever ccRepeatForever = CCRepeatForever.action(ccAnimate);
        runAction(ccRepeatForever);

        CCDelayTime ccDelayTime = CCDelayTime.action(1.0f);
        CCCallFunc ccCallFunc = CCCallFunc.action(this,"hold");
        CCSequence ccSequence = CCSequence.actions(ccDelayTime,ccCallFunc);
        runAction(ccSequence);

        if(zombie==null || zombie.getHP()==0){
            end();
        }

    }

    public void hold(){
        Tools.effectiveSound(R.raw.ele);
        ArrayList<CCSpriteFrame> frames = new ArrayList<>();
        for (int i = 0; i< 4;i++){
            CCSpriteFrame ccSpriteFrame= CCSprite.sprite(String.format(Locale.CHINA,
                    "plant/kakashi/hold%02d.png",i)).displayedFrame();
            frames.add(ccSpriteFrame);
        }
        CCAnimation ccAnimation = CCAnimation.animationWithFrames(frames,0.2f);
        CCAnimate ccAnimate = CCAnimate.action(ccAnimation,true);
        CCRepeatForever ccRepeatForever = CCRepeatForever.action(ccAnimate);
        runAction(ccRepeatForever);

        CCDelayTime ccDelayTime = CCDelayTime.action(1.6f);
        CCCallFunc ccCallFunc = CCCallFunc.action(this,"go");
        CCSequence ccSequence = CCSequence.actions(ccDelayTime,ccCallFunc);
        runAction(ccSequence);
        if(zombie==null || zombie.getHP()==0){
            end();
        }

    }

    public void go(){
        ArrayList<CCSpriteFrame> frames = new ArrayList<>();
        for (int i = 0; i< 3;i++){
            CCSpriteFrame ccSpriteFrame= CCSprite.sprite(String.format(Locale.CHINA,
                    "plant/kakashi/walk%02d.png",i)).displayedFrame();
            frames.add(ccSpriteFrame);
        }
        CCAnimation ccAnimation = CCAnimation.animationWithFrames(frames,0.2f);
        CCAnimate ccAnimate = CCAnimate.action(ccAnimation,true);
        CCRepeatForever ccRepeatForever = CCRepeatForever.action(ccAnimate);
        runAction(ccRepeatForever);
        CCMoveTo ccMoveTo = CCMoveTo.action(0.6f,ccp(cgPoint.x + 50,cgPoint.y));
        runAction(ccMoveTo);

        CCDelayTime ccDelayTime = CCDelayTime.action(0.4f);
        CCCallFunc ccCallFunc = CCCallFunc.action(this,"attack");
        CCSequence ccSequence = CCSequence.actions(ccDelayTime,ccCallFunc);
        runAction(ccSequence);
        if(zombie==null || zombie.getHP()==0){
            end();
        }
    }

    public void attack(){
        Tools.effectiveSound(R.raw.boom3);
        ArrayList<CCSpriteFrame> frames = new ArrayList<>();
        for (int i = 0; i< 6;i++){
            CCSpriteFrame ccSpriteFrame= CCSprite.sprite(String.format(Locale.CHINA,
                    "plant/kakashi/attack%02d.png",i)).displayedFrame();
            frames.add(ccSpriteFrame);
        }
        CCAnimation ccAnimation = CCAnimation.animationWithFrames(frames,0.05f);
        CCAnimate ccAnimate = CCAnimate.action(ccAnimation,false);
        CCRepeatForever ccRepeatForever = CCRepeatForever.action(ccAnimate);
        runAction(ccRepeatForever);

        CCDelayTime ccDelayTime = CCDelayTime.action(0.5f);
        CCCallFunc ccCallFunc = CCCallFunc.action(this,"end");
        CCSequence ccSequence = CCSequence.actions(ccDelayTime,ccCallFunc);
        runAction(ccSequence);
        Iterator<Zombie> zombieIterator = zombies.iterator();
        while (zombieIterator.hasNext()) {
            Zombie zombie = zombieIterator.next();
            float dis = zombie.getPosition().x - getPosition().x;
            if (Math.abs(dis)<=150 ) {
                AEffect head = new AEffect("eff/Ele02/Frame%02d.png",4,4*0.1f,0.1f);
                head.setPosition(ccp(zombie.getPosition().x+20,zombie.getPosition().y-30));
                getParent().addChild(head,6);
                zombie.hurtCompute(999);
                if (zombie.getHP() == 0) {
                    zombie.death(0);
                    zombies.remove(zombie);
                    zombie.removeSelf();
                }
            }
        }

    }

    public void end(){
        AEffect aEffect = new AEffect("eff/show/Frame%02d.png", 5);
        aEffect.setPosition(getPosition().x,getPosition().y);
        getParent().addChild(aEffect,6);
        removeSelf();
    }

}
