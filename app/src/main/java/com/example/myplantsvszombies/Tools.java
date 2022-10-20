package com.example.myplantsvszombies;

import com.example.myplantsvszombies.src.layer.CombatLine;
import com.example.myplantsvszombies.src.layer.GameLayer;
import com.example.myplantsvszombies.src.plant.ShooterPlant;
import com.example.myplantsvszombies.src.zombie.Zombie;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.sound.SoundEngine;

import java.util.ArrayList;

public class Tools {
    public static SoundEngine bgm = null;
    public static float screen_width = 1280;
    public static  float screen_height = 768;



    public static  boolean isBgmSound = true;
    public static boolean isEffectiveSound = true;
    //是否为黑夜
    private static boolean isNight = false;
    public  static boolean isNight(){return isNight;}

    public static ArrayList<ArrayList<ShooterPlant>> shooterLists;
    public static ArrayList<ArrayList<Zombie>> zombieLists;
    public static ArrayList<CombatLine> combatLines;
    public static GameLayer currentGameLayer;
    public static void bgmSound(int id)
    {
        if(isBgmSound)
        {
            bgm.playSound(CCDirector.sharedDirector().getActivity(),id,true);
        }
    }
    public static void effectiveSound(int id)
    {
        if(isEffectiveSound)
        {
            SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(),id);
        }
    }


    // 设置植物卡片图鉴效果
    public static String[] cardPath = {
            "plant/Peashooter/Frame%02d.png",
            "plant/SunFlower/Frame%02d.png",
            "plant/CherryBomb/Frame%02d.png",
            "plant/WallNut/high/Frame%02d.png",
            "plant/PotatoMine/Frame%02d.png",
            "plant/SnowPea/Frame%02d.png",
            "plant/ChomperAttack/Frame%02d.png",
            "plant/Repeater/Frame%02d.png",
            "plant/Torchwood/Frame%02d.png",
            "plant/Squash/Frame%02d.png",
            "plant/Jalapeno/Frame%02d.png",
            "plant/Threepeater/Frame%02d.png",
            "plant/GatlingPea/frame%02d.png",
            "plant/TwinSunflower/Frame%02d.png",
            "plant/DoomShroom/Frame%02d.png",
            "plant/TallNut/high/Frame%02d.png",
            "plant/Spikeweed/Frame%02d.png",
            "plant/Spikerock/Frame%02d.png",
            "plant/Starfruit/Frame%02d.png",
            "plant/SplitPea/Frame%02d.png",
            "plant/Garlic/Frame%02d.png",
            "plant/CabbagePult/Frame%02d.png",
            "plant/KernelPult/Frame%02d.png",
            "plant/MelonPult/Frame%02d.png",
            "plant/Cactus/Frame%02d.png",
            "plant/Plantern/Frame%02d.png",
            "plant/en%02d.png",
    };

    //植物帧数
    public static int[] cardInt = {
            13,
            18,
            7,
            16,
            8,
            15,
            9,
            15,
            9,
            17,
            8,
            16,
            13,
            20,
            13,
            14,
            19,
            8,
            13,
            14,
            12,
            18,
            9,
            9,
            11,
            19,
            1
    };

    public static String[] name = {
            "小小豌豆射手！","向阳花","樱桃炸弹","坚果墙","土豆雷","寒冰射手","食人花","双枪射杀","火炬树桩"
            ,"窝瓜","火爆辣椒","三管射手","机枪射手","双生向阳花","毁灭蘑菇","巨型坚果墙","地刺",
            "地刺王","杨桃","精分射手","大蒜","卷心菜投手","玉米投手","西瓜投手","仙人掌","强化路灯","应援卡"
    };


    public static String zombieStand = "zombies/zombies_1/walk/Frame%02d.png";
    public static String zombieStands = "zombies/zombies_1/walk/Frame00.png";
    public static int zombiStandInt = 22;
    public static String zombieMove = "zombies/zombies_1/walk/Frame%02d.png";
    public static int zombieMoveInt = 22;
    public static String zombieAttack = "zombies/zombies_1/attack/Frame%02d.png";
    public static int zombieAttackInt = 21;

    // 水桶僵尸 400
    public static String bucketheadZombieStand = "zombies/zombies_1/BucketheadZombie/stand%02d.png";
    public static int bucketheadZombieStandInt = 6;
    public static String bucketheadZombieMove = "zombies/zombies_1/BucketheadZombie/Frame%02d.png";
    public static int bucketheadZombieInt = 15;
    public static String bucketheadZombieAttack = "zombies/zombies_1/BucketheadZombieAttack/Frame%02d.png";
    public static int bucketheadZombieAttackInt = 11;

    // 路障僵尸 200 coneheadZombie
    public static String coneheadZombieStand = "zombies/zombies_1/ConeheadZombie/Frame%02d.png";
    public static int coneheadZombieStandInt = 8;
    public static String coneheadZombieMove = "zombies/zombies_1/ConeheadZombie/Frame%02d.png";
    public static int coneheadZombieInt = 21;
    public static String coneheadZombieAttack = "zombies/zombies_1/ConeheadZombieAttack/Frame%02d.png";
    public static int coneheadZombieAttackInt = 11;


    // 旗帜僵尸 190 flagZombie
    public static String flagZombieStand = "zombies/zombies_1/FlagZombie/Frame%02d.png";
    public static int flagZombieStandInt = 12;
    public static String flagZombieMove = "zombies/zombies_1/FlagZombie/Frame%02d.png";
    public static int flagZombieInt = 12;
    public static String flagZombieAttack = "zombies/zombies_1/FlagZombieAttack/Frame%02d.png";
    public static int flagZombieAttackInt = 11;

    // 读报僵尸-读报模式 500
    public static String newspaperZombieStandB = "zombies/zombies_1/NewspaperZombie/stand%02d.png";
    public  static int newspaperZombieStandBInt = 19;
    public static String newspaperZombieMoveB = "zombies/zombies_1/NewspaperZombie/walkb%02d.png";
    public static int newspaperZombieIntB = 19;
    public static String newspaperZombieAttackB = "zombies/zombies_1/NewspaperZombie/attackb%02d.png";
    public static int newspaperZombieAttackIntB = 8;

    // 读报僵尸-丢报模式 200
    public static String newspaperZombieStandA = "zombies/zombies_1/NewspaperZombie/walka00.png";
    public static String newspaperZombieMoveA = "zombies/zombies_1/NewspaperZombie/walka%02d.png";
    public static int newspaperZombieIntA = 14;
    public static String newspaperZombieAttackA = "zombies/zombies_1/NewspaperZombie/attacka%02d.png";
    public static int newspaperZombieAttackIntA = 7;

    // 撑杆跳僵尸 400
    public static String poleVaultingZombieStand = "zombies/zombies_1/PoleVaultingZombie/stand%02d.png";
    public static int poleVaultingZombieStandInt = 9;
    public static String poleVaultingZombieMoveA = "zombies/zombies_1/PoleVaultingZombie/walka%02d.png";
    public static int poleVaultingZombieIntA = 10;
    public static String poleVaultingZombieMoveB = "zombies/zombies_1/PoleVaultingZombie/walk%02d.png";
    public static int poleVaultingZombieIntB = 25;
    public static String poleVaultingZombieAttack = "zombies/zombies_1/PoleVaultingZombie/attack%02d.png";
    public static int poleVaultingZombieAttackInt = 14;
}
