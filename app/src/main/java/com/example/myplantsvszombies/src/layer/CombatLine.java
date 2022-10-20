package com.example.myplantsvszombies.src.layer;

import android.os.Build;
import android.util.SparseArray;

import androidx.annotation.RequiresApi;

import com.example.myplantsvszombies.Tools;
import com.example.myplantsvszombies.src.plant.CabbagePitcher;
import com.example.myplantsvszombies.src.plant.Cactus;
import com.example.myplantsvszombies.src.plant.CornPitcher;
import com.example.myplantsvszombies.src.plant.Gaara;
import com.example.myplantsvszombies.src.plant.Kakashi;
import com.example.myplantsvszombies.src.plant.Peashooter;
import com.example.myplantsvszombies.src.plant.Plant;
import com.example.myplantsvszombies.src.plant.ShooterPlant;
import com.example.myplantsvszombies.src.plant.SunFlower;
import com.example.myplantsvszombies.src.plant.TallNut;
import com.example.myplantsvszombies.src.plant.TorchWood;
import com.example.myplantsvszombies.src.plant.WatermelonPitcher;
import com.example.myplantsvszombies.src.zombie.PoleVaultingZombie;
import com.example.myplantsvszombies.src.zombie.Zombie;
import com.example.myplantsvszombies.src.bullet.Bullet;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class CombatLine {
    private CCSprite lawnMower;
    private CGPoint lawnMowerPoint;
    private boolean isLawnMowerAct = false;
    private boolean isLawnMowerEnd = false;
    private ArrayList<Zombie> zombies;
    private SparseArray<Plant> plants;
    private ArrayList<ShooterPlant> shooterPlants;
    private ArrayList<SunFlower> sunFlowers;
    private ArrayList<CabbagePitcher> cabbagePitchers;
    private ArrayList<Kakashi> kakashis;
    private ArrayList<CornPitcher> cornPitchers;
    private ArrayList<WatermelonPitcher> watermelonPitchers;
    private ArrayList<TorchWood> torchWoods;
    private ArrayList<TallNut> tallNuts;
    private ArrayList<Gaara> gaaras;
    //private ArrayList<Peashooter> peaShooters;
    Zombie target = null;
    float tagetDis = 1000;
    private int row;

    public CombatLine(){
        this.zombies = new ArrayList<>();
        this.plants = new SparseArray<>();
        sunFlowers = new ArrayList<>();
        shooterPlants = new ArrayList<>();
        cabbagePitchers = new ArrayList<>();
        kakashis = new ArrayList<>();
        cornPitchers = new ArrayList<>();
        watermelonPitchers = new ArrayList<>();
        torchWoods = new ArrayList<>();
        tallNuts = new ArrayList<>();
        gaaras = new ArrayList<>();
        Tools.shooterLists.add(shooterPlants);//
        Tools.zombieLists.add(zombies);//加入总僵尸队列中
        CCScheduler.sharedScheduler().schedule("lawnMowerAct",this,
                0.3f,false);
        CCScheduler.sharedScheduler().schedule("plantAttackZombie",this,1f,false);
        CCScheduler.sharedScheduler().schedule("zombieAttackPlant",this,1f,false);
        CCScheduler.sharedScheduler().schedule("bulletHurtCal",this,0.05f,false);
    }
    public void addPlant(int col,Plant plant)
    {
        plants.put(col,plant);
        plant.setCurrerCol(col);
        if(plant instanceof SunFlower)
        {
            sunFlowers.add((SunFlower) plant);
        }
        if(plant instanceof ShooterPlant)
        {
            shooterPlants.add((ShooterPlant) plant);
        }
        if(plant instanceof CabbagePitcher)
        {
            cabbagePitchers.add((CabbagePitcher) plant);
        }
        if(plant instanceof Kakashi)
        {
            kakashis.add((Kakashi) plant);
        }
        if(plant instanceof CornPitcher)
        {
            cornPitchers.add((CornPitcher) plant);
        }
        if(plant instanceof WatermelonPitcher)
        {
            watermelonPitchers.add((WatermelonPitcher) plant);
        }
        if(plant instanceof TorchWood)
        {
            torchWoods.add((TorchWood) plant);
        }
        if(plant instanceof TallNut)
        {
            tallNuts.add((TallNut) plant);
        }
        if(plant instanceof Gaara)
        {
            gaaras.add((Gaara) plant);
        }
    }
    public void lawnMowerAct(float t)
    {
        if (!isLawnMowerAct) {
            if (!zombies.isEmpty()) {
                Iterator<Zombie> iterator = zombies.iterator();
                while (iterator.hasNext()) {
                    Zombie zombie = iterator.next();

                    if (CGPoint.ccpDistance(lawnMower.getPosition(), zombie.getPosition()) <= 180) {
                        isLawnMowerAct = true;
                        CCMoveTo ccMoveTo = CCMoveTo.action(0.8f, CGPoint.ccp(lawnMower.getPosition().x + 1600, lawnMower.getPosition().y));
                        lawnMower.runAction(ccMoveTo);
                        break;
                    }
                }
            }
        }

        if(!isLawnMowerEnd)
        {
            if(isLawnMowerAct)
            {
                if(CGPoint.ccpDistance(lawnMowerPoint,lawnMower.getPosition()) >= 1400)
                {
                    isLawnMowerEnd=true;
                    lawnMower.removeSelf();
                    for (Zombie zombie:
                         zombies) {
                        zombie.hurtCompute(9999);
                        if(zombie.getHP()==0)
                        {
                            zombie.death(0);
                            //zombies.remove(zombie);
                            zombie.removeSelf();
                        }

                    }
                }
            }
        }
    }
    public void zombieAttackPlant(float t)
    {
        if(plants.size()!=0&&zombies.size()!=0)
        {
            for (Zombie zombie:
                 zombies) {
                int col = (int) (zombie.getPosition().x - 280) / 105;
                if(isContainPlant(col))
                {
                    //开吃！
                    if(!plants.get(col).isDontAttack())
                    {
                        switch (zombie.getState())
                        {
                            case MOVE:
                                zombie.stopAllActions();
                                zombie.attack();
                                break;
                            case ATTACK:
                                Plant plant = plants.get(col);
                                plant.hurtCompute(zombie.getAttack());
                                if(plant.getHP()==0)
                                {
                                    plant.death(zombie);
                                    plants.remove(col);
                                    plant.removeSelf();
                                    zombie.stopAllActions();
                                    zombie.move();
                                }
                                break;
                            default:
                                break;
                        }
                    }else
                    {
                        //不能吃，特殊事件处理

                    }

                    if (zombie instanceof PoleVaultingZombie) {
                        if (!((PoleVaultingZombie) zombie).isJump()) {
                            Plant plant = plants.get(col);
                            CGPoint cgPoint = CGPoint.ccp(plant.getPosition().x - 100, plant.getPosition().y);
                            ((PoleVaultingZombie) zombie).jump(cgPoint, plant);
                        }
                    }
                }else if (zombie.getState() == Zombie.State.ATTACK) {
                    zombie.stopAllActions();
                    zombie.move();
                }
            }
        }
    }
    public void plantAttackZombie(float t)
    {
        if (!shooterPlants.isEmpty()) {
            for (ShooterPlant shooterPlant : shooterPlants) {
                /*if (shooterPlant instanceof Threepeater) {
                    Threepeater threepeater = (Threepeater) shooterPlant;
                    if (ToolsSet.combatLines.contains(this)) {
                        currt = ToolsSet.combatLines.indexOf(this);
//                        System.out.println("存在:"+currt);
                    }
                    boolean isAtt = false;
                    if (currt == 0) {
                        isAtt = zombies.isEmpty() && ToolsSet.shooterPlansArrays.get(currt + 1).isEmpty();
                    } else if (currt == 4) {
                        isAtt = zombies.isEmpty() && ToolsSet.shooterPlansArrays.get(currt - 1).isEmpty();
                    } else {
                        isAtt = zombies.isEmpty() &&
                                ToolsSet.shooterPlansArrays.get(currt + 1).isEmpty() &&
                                ToolsSet.shooterPlansArrays.get(currt - 1).isEmpty();
                    }
                    if (isAtt) {
                        shooterPlant.stopAttackZombie();
                    } else {
                        shooterPlant.attackZombie();
                    }
                } */{
                    if (zombies.isEmpty()) {
                        shooterPlant.stopAttack();
                    } else {
                        shooterPlant.attack();
                    }
                }
            }
        }


    }
    public void bulletHurtCal(float t)
    {
        if(!shooterPlants.isEmpty()&&!zombies.isEmpty())
        {
            for (ShooterPlant shootPlant:
                 shooterPlants) {
                for (Bullet bullet:
                     shootPlant.getBullets()) {
                    Iterator<Zombie> iterator = zombies.iterator();
                    while (iterator.hasNext()) {
                        Zombie zombie = iterator.next();
                        if (bullet.getVisible() && bullet.getPosition().x > zombie.getPosition().x - 20
                                && bullet.getPosition().x < zombie.getPosition().x + 20) {
                            {
                                bullet.showBulletBlast(zombie);
                                bullet.setVisible(false);
                                zombie.hurtCompute(bullet.getAttack());
                                if (zombie.getHP() == 0) {
                                    zombie.death(0);
                                    zombie.removeSelf();
                                    iterator.remove();
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!cabbagePitchers.isEmpty()) {
            if (!zombies.isEmpty()) {
                for (CabbagePitcher cabbagePitcher : cabbagePitchers) {
                    if (!cabbagePitcher.isNoAttack()) {
                        Iterator<Zombie> zombieIterator = zombies.iterator();
                        target = zombies.get(0);
                        while (zombieIterator.hasNext()) {
                            Zombie zombie = zombieIterator.next();
                            float dis = CGPointUtil.distance(cabbagePitcher.getPosition(), zombie.getPosition());
                            if (dis <= tagetDis) {
                                tagetDis = dis;
                                target = zombie;
                            }
                        }
                        if (tagetDis >= 10 && tagetDis <= 900 && target.getPosition().x > cabbagePitcher.getPosition().x) {
                            cabbagePitcher.setZombies(zombies);
                            cabbagePitcher.ready(target);
                        }

                    }
                    if (cabbagePitcher.getHP() == 0) {
                        cabbagePitchers.remove(cabbagePitcher);
                    }
                }
            }
        }
        // 玉米投手
        if (!cornPitchers.isEmpty()) {
            if (!zombies.isEmpty()) {
                for (CornPitcher cornPitcher : cornPitchers) {
                    if (!cornPitcher.isNoAttack()) {
                        Iterator<Zombie> zombieIterator = zombies.iterator();
                        target = zombies.get(0);
                        while (zombieIterator.hasNext()) {
                            Zombie zombie = zombieIterator.next();
                            float dis = CGPointUtil.distance(cornPitcher.getPosition(), zombie.getPosition());
                            if (dis <= tagetDis) {
                                tagetDis = dis;
                                target = zombie;
                            }
                        }
                        if (tagetDis >= 10 && tagetDis <= 900 && target.getPosition().x > cornPitcher.getPosition().x) {
                            Random random = new Random();
                            int i = random.nextInt(10);
                            if (i <= 3) {
                                cornPitcher.setYellow(true);
                                cornPitcher.setBuPath("bullet/yellow.png");
                            }
                            cornPitcher.setZombies(zombies);
                            cornPitcher.ready(target);
                        }
                    }
                    if (cornPitcher.getHP() == 0) {
                        cornPitchers.remove(cornPitcher);
                    }
                }
            }
        }

        //西瓜投手
        if (!watermelonPitchers.isEmpty()) {
            if (!zombies.isEmpty()) {
                for (WatermelonPitcher watermelonPitcher : watermelonPitchers) {
                    if (!watermelonPitcher.isNoAttack()) {
                        Iterator<Zombie> zombieIterator = zombies.iterator();
                        target = zombies.get(0);
                        while (zombieIterator.hasNext()) {
                            Zombie zombie = zombieIterator.next();
                            float dis = CGPointUtil.distance(watermelonPitcher.getPosition(), zombie.getPosition());
                            if (dis <= tagetDis) {
                                tagetDis = dis;
                                target = zombie;
                            }
                        }
                        if (tagetDis >= 10 && tagetDis <= 900 && target.getPosition().x > watermelonPitcher.getPosition().x) {
                            watermelonPitcher.setZombies(zombies);
                            watermelonPitcher.ready(target);
                        }

                    }
                    if (watermelonPitcher.getHP() == 0) {
                        watermelonPitchers.remove(watermelonPitcher);
                    }
                }
            }
        }
        //火炬
        if(!torchWoods.isEmpty())
        {
            if(!shooterPlants.isEmpty())
            {
                for (TorchWood torchwood : torchWoods) {
                    for (ShooterPlant shooterPlant : shooterPlants) {
                        if (!(shooterPlant instanceof Cactus)) {
                            for (Bullet bullet : shooterPlant.getBullets()) {
                                float dis = getDisBetweenTwoUnit(torchwood.getPosition().x, torchwood.getPosition().y,
                                        bullet.getPosition().x, bullet.getPosition().y) / 105;
                                if (dis < 0.6) {
                                    System.out.println("diss : " + dis);
                                    if (!bullet.isFire()) {
                                        bullet.fire();
                                    }
                                }
                            }
                        }
                    }
                    if (torchwood.getHP() == 0 || torchwood.isRemove()) {
                        torchWoods.remove(torchwood);
                    }
                }
            }
        }
        // kakaxi
        if (!kakashis.isEmpty()) {
            if (!zombies.isEmpty()) {
                for (Kakashi kakashi : kakashis) {
                    Iterator<Zombie> zombieIterator = zombies.iterator();
                    while (zombieIterator.hasNext()) {
                        Zombie zombie = zombieIterator.next();
                        float dis = CGPointUtil.distance(kakashi.getPosition(), zombie.getPosition());
                        if (dis <= 700) {
                            if (dis <= tagetDis) {
                                tagetDis = dis;
                                target = zombie;
                            }
                            kakashis.remove(kakashi);
                            kakashi.start(target,zombies);
                            plants.remove(kakashi.getCurrerCol());
                        }

                    }
                    if (kakashi.getHP() == 0) {
                        kakashis.remove(kakashi);
                    }
                }
            }
        }


        // 我爱罗
        if (!gaaras.isEmpty()) {
            if (!zombies.isEmpty()) {
                for (Gaara gaara : gaaras) {
                    Iterator<Zombie> zombieIterator = zombies.iterator();
                    while (zombieIterator.hasNext()) {
                        Zombie zombie = zombieIterator.next();
                        float dis = CGPointUtil.distance(gaara.getPosition(), zombie.getPosition());
                        if (dis <= 700) {
                            if (dis <= tagetDis) {
                                tagetDis = dis;
                                target = zombie;
                            }
                            gaaras.remove(gaara);
                            gaara.start(target,zombies);
                            plants.remove(gaara.getCurrerCol());
                        }

                    }
                    if (gaara.getHP() == 0) {
                        gaaras.remove(gaara);
                    }
                }
            }
        }
    }

    public float getDisBetweenTwoUnit(float x1, float y1, float x2, float y2) {
        float dis = (float) Math.sqrt(
                Math.pow(x1 - x2, 2) +
                        Math.pow(y1 - y2, 2));
        return dis;
    }

    public  SparseArray<Plant> getPlants(){return plants;}
    public ArrayList<Zombie> getZombies(){return zombies;}
    public void addZombie(Zombie zombie) {
        zombies.add(zombie);
    }
    public void setCurrent(int row) {
        this.row = row;
    }

    public boolean isContainPlant(int col) {
        if(plants.get(col)==null) return false;
        return true;
    }

    public void setLawnMower(CCSprite lawnMower) {
        this.lawnMower = lawnMower;
    }

    public void setLawnMowerPoint(CGPoint position) {
        this.lawnMowerPoint = position;
    }

}
