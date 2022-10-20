package com.example.myplantsvszombies.src.plant;

import com.example.myplantsvszombies.src.zombie.Zombie;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;

import java.util.ArrayList;
import java.util.Locale;

public class Plant extends CCSprite {
    private int HP=100;
    private int price;

    private int curCol;
    private boolean isRemove;

    private boolean doNotAttack=false;//默认可以吃

    public Plant(String format,int number)
    {
        super(String.format(Locale.CHINA,format,0));
        setAnchorPoint(0.5f,0);
        ArrayList<CCSpriteFrame> frames = new ArrayList<>();
        for(int i = 0;i < number;i++)
        {
            CCSpriteFrame frame = CCSprite.sprite(String.format(Locale.CHINA,format,i)).displayedFrame();
            frames.add(frame);

            CCAnimation animation = CCAnimation.animationWithFrames(frames,0.1f);
            CCAnimate animate = CCAnimate.action(animation,true);
            CCRepeatForever ccRepeatForever = CCRepeatForever.action(animate);
            this.runAction(ccRepeatForever);
        }
    }

    public void death(Zombie zombie)
    {

    }
    public void hurtCompute(int hurt)
    {
        HP -= hurt;
        if(HP < 0) HP = 0;
    }

    public void safe(ArrayList<Zombie> zombies){
        for (Zombie zombie:zombies){
            if (CGPoint.ccpDistance(zombie.getPosition(),getPosition())<=40){
                zombie.move();
            }
        }
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCurrerCol() {
        return curCol;
    }

    public void setCurrerCol(int currerCol) {
        this.curCol = currerCol;
    }

    public boolean isRemove() {
        return isRemove;
    }

    public void setRemove(boolean remove) {
        isRemove = remove;
    }

    public boolean isDontAttack() {
        return doNotAttack;
    }

    public void setDontAttack(boolean dontAttack) {
        this.doNotAttack = dontAttack;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }
}
