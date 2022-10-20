package com.example.myplantsvszombies.src.plant;

import com.example.myplantsvszombies.Tools;
import com.example.myplantsvszombies.src.effect.AEffect;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import com.example.myplantsvszombies.R;
public class CornPitcher extends PitcherPlant{
    private boolean isYellow;

    public CornPitcher() {
        super("plant/KernelPult/Frame%02d.png", 9,"plant/KernelPult/Attack%02d.png",4,"bullet/kernelBullet.png");
        setPrice(100);
        setHurt(18);
    }




    @Override
    public void launchEnd(){
        getBullet().removeSelf();
        CCDelayTime ccDelayTime = CCDelayTime.action(7f);
        CCCallFunc ccCallFunc = CCCallFunc.action(this,"noAtt");
        CCSequence ccSequence = CCSequence.actions(ccDelayTime,ccCallFunc);
        runAction(ccSequence);

        AEffect head = new AEffect("eff/boom3/effect_f01_%02d.png",9,9*0.1f,0.1f);
        head.setPosition(getIntentPoint());
        getParent().addChild(head,6);
        Tools.effectiveSound(R.raw.bomb1);
        if (getZombie().getHP()!=0 && isYellow) {
            setHurt(80);
            getZombie().stop("bullet/yelloed.png", 4);
        }
        getZombie().hurtCompute(getHurt());
        if (getZombie().getHP() == 0) {
            getZombie().death(0);
            getZombie().removeSelf();
            getZombies().remove(getZombie());
        }





    }

    @Override
    public void noAtt(){
        isYellow = false;
        setNoAttack(false);
    }

    public boolean isYellow() {
        return isYellow;
    }

    public void setYellow(boolean yellow) {
        isYellow = yellow;
    }
}
