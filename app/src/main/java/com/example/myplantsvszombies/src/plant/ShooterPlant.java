package com.example.myplantsvszombies.src.plant;

import com.example.myplantsvszombies.src.bullet.Bullet;

import org.cocos2d.actions.CCScheduler;

import java.util.ArrayList;

public abstract class ShooterPlant extends Plant{
    private ArrayList<Bullet> bullets;
    private boolean isAttack;
    public ShooterPlant(String format, int number) {
        super(format, number);
        this.bullets = new ArrayList<>();
        this.isAttack = false;
    }
    public void attack(){
        if (!isAttack){
            System.out.println("攻击：发射");
            createBullet(0);
            CCScheduler.sharedScheduler().schedule("createBullet",this,
                    5,false);
            isAttack=true;
        }
    }

    public void stopAttack(){
        if (isAttack){
            System.out.println("攻击：停止发射");
            CCScheduler.sharedScheduler().unschedule("createBullet",this);
            isAttack=false;
        }
    }

    public abstract void createBullet(float t);
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }


}
