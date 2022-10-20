package com.example.myplantsvszombies.src.plant;

import com.example.myplantsvszombies.Tools;
import com.example.myplantsvszombies.src.effect.AEffect;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import com.example.myplantsvszombies.R;

public class CabbagePitcher extends PitcherPlant{
    public CabbagePitcher() {
        super("plant/CabbagePult/Frame%02d.png", 18,"plant/CabbagePult/Attack%02d.png",8,"bullet/cabbage.png");
        setPrice(100);
        setHurt(25);
    }

    @Override
    public void launchEnd(){
        getBullet().removeSelf();
        CCDelayTime ccDelayTime = CCDelayTime.action(2.9f);
        CCCallFunc ccCallFunc = CCCallFunc.action(this,"noAtt");
        CCSequence ccSequence = CCSequence.actions(ccDelayTime,ccCallFunc);
        runAction(ccSequence);

        AEffect head = new AEffect("eff/boom3/effect_f01_%02d.png",9,9*0.1f,0.1f);
        head.setPosition(getIntentPoint());
        getParent().addChild(head,6);
        Tools.effectiveSound(R.raw.bomb1);
        getZombie().hurtCompute(getHurt());
        if (getZombie().getHP() == 0) {
            getZombie().death(0);
            getZombie().removeSelf();
            getZombies().remove(getZombie());
        }

    }
}
