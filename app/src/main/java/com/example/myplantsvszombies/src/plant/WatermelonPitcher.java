package com.example.myplantsvszombies.src.plant;

import com.example.myplantsvszombies.Tools;
import com.example.myplantsvszombies.src.effect.AEffect;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import com.example.myplantsvszombies.R;
public class WatermelonPitcher extends PitcherPlant{
    public WatermelonPitcher() {
        super("plant/MelonPult/Frame%02d.png", 9,"plant/MelonPult/Attack%02d.png",4,"bullet/melon.png");
        setHurt(80);
        setPrice(300);
        setRotate(true);
    }

    @Override
    public void launchEnd(){
        getBullet().removeSelf();
        CCDelayTime ccDelayTime = CCDelayTime.action(3f);
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
        }else {
            getZombie().stop("none",1);
        }



    }

}
