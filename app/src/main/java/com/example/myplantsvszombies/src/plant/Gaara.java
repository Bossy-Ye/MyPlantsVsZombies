package com.example.myplantsvszombies.src.plant;

import com.example.myplantsvszombies.Tools;
import com.example.myplantsvszombies.src.effect.AEffect;
import com.example.myplantsvszombies.src.zombie.Zombie;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import com.example.myplantsvszombies.R;
public class Gaara extends Plant {

    private Zombie zombie;
    private ArrayList<Zombie> zombies;
    private ArrayList<Zombie> group;
    private AEffect aEffect;

    public Gaara() {
        super("plant/gaara/stand%02d.png", 3);
        setPrice(20);
        setScale(2f);
    }

    public void start(Zombie zombie,ArrayList<Zombie> zombies) {
        this.zombie = zombie;
        this.zombies = zombies;
        CCDelayTime ccDelayTime = CCDelayTime.action(1.6f);
        CCCallFunc ccCallFunc = CCCallFunc.action(this, "spell");
        CCSequence ccSequence = CCSequence.actions(ccDelayTime, ccCallFunc);
        runAction(ccSequence);
        if (zombie == null || zombie.getHP() == 0) {
            remove();
        }
    }

    public void spell() {
        Tools.effectiveSound(R.raw.up);
        ArrayList<CCSpriteFrame> frames = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            CCSpriteFrame ccSpriteFrame = CCSprite.sprite(String.format(Locale.CHINA,
                    "plant/gaara/spell%02d.png", i)).displayedFrame();
            frames.add(ccSpriteFrame);
        }
        CCAnimation ccAnimation = CCAnimation.animationWithFrames(frames, 0.2f);
        CCAnimate ccAnimate = CCAnimate.action(ccAnimation, true);
        CCRepeatForever ccRepeatForever = CCRepeatForever.action(ccAnimate);
        runAction(ccRepeatForever);

        CCDelayTime ccDelayTime = CCDelayTime.action(1.8f);
        CCCallFunc ccCallFunc = CCCallFunc.action(this, "end");
        CCSequence ccSequence = CCSequence.actions(ccDelayTime, ccCallFunc);
        runAction(ccSequence);
        aEffect = new AEffect("eff/sand/sand2_%02d.png", 19, 19 * 0.1f, 0.1f);
        aEffect.setPosition(zombie.getPosition());
        aEffect.setScale(1.5);
        getParent().addChild(aEffect, 6);

        group = new ArrayList<>();
        group.add(zombie);
        zombie.stop("none", 4);
        Iterator<Zombie> zombieIterator = zombies.iterator();
        while (zombieIterator.hasNext()) {
            Zombie zombie = zombieIterator.next();
            if (CGPoint.ccpDistance(zombie.getPosition(), zombie.getPosition()) <= 200) {
                group.add(zombie);
                zombie.stop("none", 4);
            }
        }
        if (zombie == null || zombie.getHP() == 0) {
            remove();
        }

    }

    public void end() {

        CCDelayTime ccDelayTime = CCDelayTime.action(0.6f);
        CCCallFunc ccCallFunc = CCCallFunc.action(this, "remove");
        CCSequence ccSequence = CCSequence.actions(ccDelayTime, ccCallFunc);
        runAction(ccSequence);

        Tools.effectiveSound(R.raw.bomb);


        AEffect aEffect1 = new AEffect("eff/SetEff/Frame%02d.png", 4);
        aEffect1.setPosition(zombie.getPosition());
        getParent().addChild(aEffect1, 8);
        aEffect1.setScale(1.5f);

        zombie.hurtCompute(9999);
        if (zombie.getHP() == 0) {
            zombie.death(0);
            zombie.removeSelf();
            zombies.remove(zombie);
        }


        if (zombie == null || zombie.getHP() == 0) {
            remove();
        }


    }

    public void remove() {
        AEffect aEffect = new AEffect("eff/show/Frame%02d.png", 5);
        aEffect.setPosition(getPosition().x, getPosition().y);
        getParent().addChild(aEffect, 6);
        removeSelf();
    }


}
