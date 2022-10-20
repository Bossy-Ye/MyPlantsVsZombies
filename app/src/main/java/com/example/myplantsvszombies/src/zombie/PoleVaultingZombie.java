package com.example.myplantsvszombies.src.zombie;

import com.example.myplantsvszombies.Tools;
import com.example.myplantsvszombies.src.effect.AEffect;
import com.example.myplantsvszombies.src.layer.GameLayer;
import com.example.myplantsvszombies.src.plant.Plant;
import com.example.myplantsvszombies.src.plant.TallNut;

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
import java.util.Locale;

public class PoleVaultingZombie extends Zombie{

    private boolean isJump;
    private CGPoint cgPoint;
    private Plant plant;
    private int temp;

    public PoleVaultingZombie(GameLayer combatLayer, CGPoint start, CGPoint end) {
        super(combatLayer, start, end);
        setMoveInt(Tools.poleVaultingZombieIntA);
        setMove(Tools.poleVaultingZombieMoveA);
        setAttackInt(Tools.poleVaultingZombieAttackInt);
        setAttacka(Tools.poleVaultingZombieAttack);
        setHP(400);
        setAttack(15);
        setSpeed(25);
        setNoAttack(true);
        setSp(true);
        move();
        setNoHurt(true);
    }

    public void jump(CGPoint cgPoint, Plant plant){
        this.plant = plant;
        this.cgPoint = cgPoint;
        setNoHurt(false);
        temp = getHP();
        setHP(100000);
        setSp(false);
        isJump = true;
        if (getHP()!=0){
            ArrayList<CCSpriteFrame> frames = new ArrayList<>();
            for (int i = 0;i<10;i++){
                CCSpriteFrame ccSpriteFrame= CCSprite.sprite(String.format(Locale.CHINA,
                        "zombies/zombies_1/PoleVaultingZombie/jump%02d.png",i)).displayedFrame();
                frames.add(ccSpriteFrame);
            }
            CCAnimation ccAnimation= CCAnimation.animationWithFrames(frames,0.08f);
            CCAnimate ccAnimate= CCAnimate.action(ccAnimation,true);
            CCRepeatForever ccRepeatForever= CCRepeatForever.action(ccAnimate);
            runAction(ccRepeatForever);

            CCDelayTime ccDelayTime = CCDelayTime.action(0.8f);
            CCCallFunc ccCallFunc = CCCallFunc.action(this,"jumped");

            CCDelayTime ccDelayTime1 = CCDelayTime.action(0.7f);
            CCCallFunc ccCallFunc1 = CCCallFunc.action(this,"endJump");

            CCSequence ccSequence = CCSequence.actions(ccDelayTime , ccCallFunc);

            runAction(ccSequence);
        }
    }

    public void jumped(){
        setHP(temp);
        System.out.println("jumped");
        ArrayList<CCSpriteFrame> frames = new ArrayList<>();
        for (int i = 0;i<7;i++){
            CCSpriteFrame ccSpriteFrame= CCSprite.sprite(String.format(Locale.CHINA,
                    "zombies/zombies_1/PoleVaultingZombie/jumped%02d.png",i)).displayedFrame();
            frames.add(ccSpriteFrame);
        }
        CCAnimation ccAnimation= CCAnimation.animationWithFrames(frames,0.07f);
        CCAnimate ccAnimate= CCAnimate.action(ccAnimation,true);
        CCRepeatForever ccRepeatForever= CCRepeatForever.action(ccAnimate);
        runAction(ccRepeatForever);

        setMove(Tools.poleVaultingZombieMoveB);
        setMoveInt(Tools.poleVaultingZombieIntB);
        if (!(plant instanceof TallNut)){
            setPosition(cgPoint);
        }
        //setPosition(cgPoint);
        setSpeed(15);
        move();
        setNoAttack(false);
    }




    // 僵尸死亡效果
    @Override
    public void death(int dieState) {
        switch (dieState) {
            case 0:
                AEffect aEffect = new AEffect("zombies/zombies_1/PoleVaultingZombie/die%02d.png", 8, 2, 0.25f);
                aEffect.setPosition(ccp(getPosition().x, getPosition().y));
                getParent().addChild(aEffect, 6);

                AEffect head = new AEffect("zombies/zombies_1/PoleVaultingZombie/head%02d.png", 8, 1.2f, 0.15f);
                head.setPosition(ccp(getPosition().x + 20, getPosition().y - 30));
                getParent().addChild(head, 6);

                break;

            case 1:
                AEffect aEffect2 = new AEffect("zombies/zombies_1/BoomDie/Frame%02d.png", 20, 4, 0.2f);
                aEffect2.setPosition(ccp(getPosition().x, getPosition().y));
                getParent().addChild(aEffect2, 6);

            default:
                break;
        }
    }


    public boolean isJump() {
        return isJump;
    }

    public void setJump(boolean jump) {
        isJump = jump;
    }

    public CGPoint getCgPoint() {
        return cgPoint;
    }

    public void setCgPoint(CGPoint cgPoint) {
        this.cgPoint = cgPoint;
    }
}
