package com.example.myplantsvszombies.src.bullet;

import com.example.myplantsvszombies.Tools;
import com.example.myplantsvszombies.src.effect.AEffect;
import com.example.myplantsvszombies.src.plant.Peashooter;
import com.example.myplantsvszombies.src.plant.ShooterPlant;
import com.example.myplantsvszombies.src.zombie.Zombie;

import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import com.example.myplantsvszombies.R;
public class PeaBullet extends Bullet{
    public PeaBullet(ShooterPlant peashooter) {
        super("bullet/bullet1.png",peashooter);
    }
    public PeaBullet(ShooterPlant shooterPlant, String path, Boolean isLeft){
        super(path, shooterPlant,isLeft);
    }

    @Override
    public void showBulletBlast(Zombie zombie) {
        if (isFire()){
            AEffect aEffect = new AEffect("eff/fireBomb/Frame%02d.png", 5,0.8f,0.16f);
            aEffect.setPosition(ccp(zombie.getPosition().x, zombie.getPosition().y-40));
            getParent().addChild(aEffect, 6);
            CCDelayTime ccDelayTime = CCDelayTime.action(0.8f);
            CCHide ccHide = CCHide.action();
            CCSequence ccSequence = CCSequence.actions(ccDelayTime, ccHide);
            aEffect.runAction(ccSequence);
            Tools.effectiveSound(R.raw.bomb1);
        }else {
            AEffect aEffect = new AEffect("eff/pea/eff%02d.png", 3,0.3f,0.1f);
            aEffect.setPosition(ccp(zombie.getPosition().x, zombie.getPosition().y));
            getParent().addChild(aEffect, 6);
            CCDelayTime ccDelayTime = CCDelayTime.action(1.0f);
            CCHide ccHide = CCHide.action();
            CCSequence ccSequence = CCSequence.actions(ccDelayTime, ccHide);
            aEffect.runAction(ccSequence);
            Tools.effectiveSound(R.raw.fight);
        }
    }
}
