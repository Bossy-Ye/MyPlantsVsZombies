package com.example.myplantsvszombies.src.plant;

import com.example.myplantsvszombies.src.bullet.PeaBullet;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;

public class DoubleShooter extends ShooterPlant{
    public DoubleShooter() {
        super("plant/Repeater/Frame%02d.png", 15);
        setPrice(200);
    }

    @Override
    public void createBullet(float t) {
        PeaBullet DoubleShooter=new PeaBullet(this);
        CCDelayTime ccDelayTime=CCDelayTime.action(0.3f);
        CCCallFunc ccCallFunc=CCCallFunc.action(this,"createBulletTwo");
        CCSequence ccSequence= CCSequence.actions(ccDelayTime,ccCallFunc);
        runAction(ccSequence);
    }

    public void createBulletTwo(){
        PeaBullet peaBullet=new PeaBullet(this);
    }
}
