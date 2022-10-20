package com.example.myplantsvszombies.src.layer;

import android.view.MotionEvent;

import androidx.collection.CircularArray;

import com.example.myplantsvszombies.R;
import com.example.myplantsvszombies.Tools;
import com.example.myplantsvszombies.src.bullet.Sun;
import com.example.myplantsvszombies.src.card.PlantCard;
import com.example.myplantsvszombies.src.card.SelectedCard;
import com.example.myplantsvszombies.src.effect.AEffect;
import com.example.myplantsvszombies.src.plant.CabbagePitcher;
import com.example.myplantsvszombies.src.plant.CornPitcher;
import com.example.myplantsvszombies.src.plant.DoubleShooter;
import com.example.myplantsvszombies.src.plant.Gaara;
import com.example.myplantsvszombies.src.plant.Kakashi;
import com.example.myplantsvszombies.src.plant.Peashooter;
import com.example.myplantsvszombies.src.plant.Plant;
import com.example.myplantsvszombies.src.plant.QuadrupleShooter;
import com.example.myplantsvszombies.src.plant.SunFlower;
import com.example.myplantsvszombies.src.plant.TallNut;
import com.example.myplantsvszombies.src.plant.TorchWood;
import com.example.myplantsvszombies.src.plant.WallNut;
import com.example.myplantsvszombies.src.plant.WatermelonPitcher;
import com.example.myplantsvszombies.src.zombie.NewspaperZombie;
import com.example.myplantsvszombies.src.zombie.PoleVaultingZombie;
import com.example.myplantsvszombies.src.zombie.Zombie;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class GameLayer extends CCLayer {
    private CGSize winSize = CCDirector.sharedDirector().winSize();
    private CCTMXTiledMap cctmxTiledMap;
    private CCSprite background;
    private ArrayList<CCSprite> ccSprites;
    private ArrayList<CCSprite> ccSpritesShow;
    private ArrayList<Zombie> zombies;
    private CCSprite ccSprite_SeedBank;
    private CCLabel ccLabel;
    private CCSprite ccSprite_SeedChooser;
    private int currentSunNumber = 9999;
    private ArrayList<PlantCard> plantCards;
    private ArrayList<PlantCard> selectedCards;
    private int cardNum = 27;
    private CCSprite ccSprite_mainMenu;
    private CCSprite ccSprite_almanac;
    private CCSprite ccSprite_showPlant;
    private CCSprite ccSprite_showLabel;
    private CCSprite ccSprite_showText;
    private ArrayList<CCSpriteFrame> showFrames;
    private boolean shovelSelected = false;
    private CCSprite ccSprite_shovel;
    private CCSprite ccSprite_shovelBackground;
    private boolean newCardIsTouch=false;
    private PlantCard newCard;
    private CCSprite light;
    private CCSprite down;
    private CCSprite newCardLight;
    private ArrayList<CombatLine> combatLines;
    private PlantCard selectCard;
    private Plant selectPlant;
    private boolean setClicked = false;
    private ArrayList<ArrayList<CGPoint>> cgPointsTowers;
    private CCSprite colShowad;
    private CCSprite rowShowad;
    private CCSprite ccSprite_SeedChooser_Button;
    private CCSprite ccSprite_startReady;
    private ArrayList<CGPoint> cgPointsPath;
    private Random random;
    private ArrayList<Sun> suns;
    private Zombie lastZombie;
    private AEffect aEffect;
    private int help=0;
    private int helpMax=4;

    public  GameLayer() {
        Tools.bgmSound(R.raw.bgmfight);
        loadMap();
    }
    public void loadMap()
    {
        if(!Tools.isNight()){
            cctmxTiledMap = CCTMXTiledMap.tiledMap("combat/map1.tmx");
        }else
        {
            cctmxTiledMap = CCTMXTiledMap.tiledMap("combat/map2.tmx");
        }

        this.addChild(cctmxTiledMap);

        CCTMXObjectGroup objectGroupShow = cctmxTiledMap.objectGroupNamed("show");
        ArrayList<HashMap<String, String>> objects = objectGroupShow.objects;
        ccSpritesShow = new ArrayList<>();

        for(HashMap<String, String> object : objects)
        {
            float x = Float.parseFloat(object.get("x"));
            float y = Float.parseFloat(object.get("y"));
            CCSprite ccSpriteShake = CCSprite.sprite("zombies/zombies_1/shake/Frame00.png");
            ccSpriteShake.setPosition(x,y);
            cctmxTiledMap.addChild(ccSpriteShake);
            ccSpritesShow.add(ccSpriteShake);
            ArrayList<CCSpriteFrame> frames = new ArrayList<>();
            Random random = new Random();
            float range = random.nextFloat();//0.0-1.0
            int index = 0;
            String path = Tools.zombieStand;
            int num = 22;


            for(int i = 0;i < num;i++)
            {
                CCSpriteFrame ccSpriteFrame = CCSprite.sprite(
                        String.format(Locale.CHINA,path,i)
                ).displayedFrame();
                frames.add(ccSpriteFrame);
            }

            CCAnimation ccAnimation = CCAnimation.animation("ZombieWalk",0.1f,frames);
            CCAnimate ccAnimate = CCAnimate.action(ccAnimation);
            CCRepeatForever ccRepeatForever = CCRepeatForever.action(ccAnimate);
            ccSpriteShake.runAction(ccRepeatForever);
        }
        winSize = CCDirector.sharedDirector().getWinSize();
        CCDelayTime ccDelayTime = CCDelayTime.action(2.0f);
        CCMoveBy ccMoveBy = CCMoveBy.action(2,
                ccp(winSize.getWidth()-cctmxTiledMap.getContentSize().getWidth(),0));
        CCCallFunc callFunc = CCCallFunc.action(this,"loadChoose");
        CCSequence ccSequence = CCSequence.actions(ccDelayTime,ccMoveBy,callFunc);
        cctmxTiledMap.runAction(ccSequence);


        zombies = new ArrayList<>();
        Tools.shooterLists = new ArrayList<>();
        Tools.zombieLists = new ArrayList<>();
    }

    public void loadChoose()
    {
        ccSprite_SeedBank = CCSprite.sprite("choose/SeedBank.png");
        ccSprite_SeedBank.setAnchorPoint(0, 1);
        ccSprite_SeedBank.setPosition(0,winSize.height);
        this.addChild(ccSprite_SeedBank);

        //阳光数量
        ccLabel = CCLabel.makeLabel(currentSunNumber + "", "", 20);
        ccLabel.setColor(ccColor3B.ccBLACK);
        ccLabel.setPosition(40, 695);
        addChild(ccLabel);

        //植物选择
        ccSprite_SeedChooser = CCSprite.sprite("choose/SeedChooser.png");
        this.addChild(ccSprite_SeedChooser);
        //左下角
        ccSprite_SeedChooser.setAnchorPoint(0,0);

        //按钮
        CCSprite ccSprite_SeedChooser_Button_Disabled = CCSprite.
                sprite("choose/SeedChooser_Button_Disabled.png");
        ccSprite_SeedChooser_Button_Disabled.setPosition(ccSprite_SeedChooser.getContentSize().getWidth() / 2, 80);
        ccSprite_SeedChooser.addChild(ccSprite_SeedChooser_Button_Disabled);

        ccSprite_SeedChooser_Button = CCSprite.sprite("choose/SeedChooser_Button.png");
        ccSprite_SeedChooser_Button.
                setPosition(ccSprite_SeedChooser.getContentSize().getWidth() / 2, 80);
        ccSprite_SeedChooser.addChild(ccSprite_SeedChooser_Button);
        ccSprite_SeedChooser_Button.setVisible(false);



        //放置植物吧
        plantCards = new ArrayList<>();
        selectedCards = new ArrayList<>();

        int bias;
        for(int i = 0;i < cardNum;i++)
        {
            bias = i;
            PlantCard plantCard = new PlantCard(i);
            plantCards.add(plantCard);
            plantCard.dark.setPosition(50 + 60 * (i % 9), 590 - (bias / 9) * 100);
            ccSprite_SeedChooser.addChild(plantCard.dark);
            plantCard.light.setPosition(50 + 60 * (i % 9), 590 - (bias / 9) * 100);
            ccSprite_SeedChooser.addChild(plantCard.light);
        }

        ccSprite_mainMenu = CCSprite.sprite("choose/mainMenu.png");
        ccSprite_mainMenu.setAnchorPoint(0,1);
        ccSprite_mainMenu.setScale(1.3f);
        ccSprite_mainMenu.setPosition(winSize.getWidth()-150, winSize.getHeight());
        this.addChild(ccSprite_mainMenu);

        setAlmanac();
        setShovel();
        setIsTouchEnabled(true);
    }


    public void setAlmanac()
    {
        ccSprite_almanac = CCSprite.sprite("choose/Almanac_PlantCard.png");
        ccSprite_almanac.setAnchorPoint(0,1);
        ccSprite_almanac.setPosition(572,winSize.height-85);
        this.addChild(ccSprite_almanac);


        //初始化显示豌豆射手图鉴
        ccSprite_showPlant = CCSprite.sprite("plant/Peashooter/Frame00.png");
        ccSprite_showPlant.setAnchorPoint(0,1);
        ccSprite_showPlant.setPosition(ccSprite_almanac.getPosition().x+70,winSize.height-120);
        this.addChild(ccSprite_showPlant);

        ccSprite_showLabel = CCLabel.makeLabel("小小豌豆射手！", "hkbd.ttf", 25);
        ccSprite_showLabel.setAnchorPoint(0,1);
        ccSprite_showLabel.setColor(ccColor3B.ccBLACK);
        ccSprite_showLabel.setPosition(ccSprite_showPlant.getPosition().x, winSize.getHeight() - 280);
        addChild(ccSprite_showLabel);
        //描述信息
        ccSprite_showText = CCSprite.sprite("text/t00.png");
        ccSprite_showText.setAnchorPoint(0,1);
        ccSprite_showText.setPosition(ccSprite_showPlant.getPosition().x-30, winSize.getHeight() - 300);
        addChild(ccSprite_showText);

        //CCHide
        //先把介绍说明给隐藏了！
        CCHide ccHide = CCHide.action();
        ccSprite_almanac.runAction(ccHide);
        ccSprite_showPlant.runAction(ccHide);
        ccSprite_showLabel.runAction(ccHide);
        ccSprite_showText.runAction(ccHide);
        showFrames = new ArrayList<>();
    }
    public void setShovel() {
        shovelSelected = false;
        ccSprite_shovelBackground = CCSprite.sprite("choose/ShovelBack.png");
        ccSprite_shovelBackground.setAnchorPoint(0,1);
        ccSprite_shovelBackground.setPosition(580,winSize.height);
        ccSprite_shovelBackground.setScaleX(1.3f);
        ccSprite_shovelBackground.setScaleY(2.4f);
        this.addChild(ccSprite_shovelBackground);


        ccSprite_shovel = CCSprite.sprite("choose/Shovel.png");
        ccSprite_shovel.setAnchorPoint(0,1);
        ccSprite_shovel.setPosition(590,winSize.height-30);
        this.addChild(ccSprite_shovel);

    }

    private boolean isStart = false;
    private boolean isLongTouch = false;
    private CGPoint longPoint;
    private boolean flag = false;
    private Plant showadPlant;



    @Override
    public boolean ccTouchesBegan(MotionEvent event)
    {
        return false;
    }

    public void longTouchCallBack(float t)
    {

    }

    public void toBig()
    {

    }
    public void cardLight()
    {

    }

    private boolean isPause = false;
    private boolean isCardIsMove = false;
    @Override
    public boolean ccTouchesEnded(MotionEvent event)
    {
        CGPoint cgPoint;
        cgPoint = convertTouchToNodeSpace(event);
        if(CGRect.containsPoint(ccSprite_mainMenu.getBoundingBox(),cgPoint))
        {
            if(!isPause)
            {
                this.onExit();
                this.getParent().addChild(new PauseLayer(this));
                Tools.effectiveSound(R.raw.dight);
            }
        }
        if(isStart)
        {
            touchEventsWhileStart(cgPoint);
        }else {
            touchEventsWhileSelect(cgPoint);
        }

        return false;
    }

    public void touchEventsWhileStart(CGPoint cgPoint) {
        if(CGRect.containsPoint(ccSprite_SeedBank.getBoundingBox(),cgPoint)) {
            if (!shovelSelected)//shovel is not selected
            {

                if (selectCard != null) {//如果之前选中了卡片，则重置卡片
                    if (CGRect.containsPoint(selectCard.light.getBoundingBox(), cgPoint)) {
                        selectCard.light.setOpacity(255);
                        selectCard=null;
                        return;
                    }

                    selectCard.light.setOpacity(255);
                    selectCard = null;
                }
                for (PlantCard plantCard : selectedCards) {
                    if (CGRect.containsPoint(plantCard.light.getBoundingBox(), cgPoint)) {
                        if (plantCard.light.getOpacity() == 255) {
                            selectCard = plantCard;
                            selectCard.light.setOpacity(100);
                            Tools.effectiveSound(R.raw.click);
                            switch (selectCard.id) {
                                case 0:
                                    selectPlant = new Peashooter();
                                    break;
                                case 1:
                                    selectPlant = new SunFlower();
                                    break;
                                case 3:
                                    selectPlant = new WallNut();
                                    break;
                                case 7:
                                    selectPlant = new DoubleShooter();
                                    break;
                                case 8:
                                    selectPlant = new TorchWood();
                                    break;
                                case 12:
                                    selectPlant = new QuadrupleShooter();
                                    break;
                                case 15:
                                    selectPlant = new TallNut();
                                    break;
                                case 21:
                                    selectPlant = new CabbagePitcher();
                                    break;
                                case 22:
                                    selectPlant = new CornPitcher();
                                    break;
                                case 23:
                                    selectPlant = new WatermelonPitcher();
                                    break;
                                case 26:
                                    help++;
                                    if(help>helpMax){
                                        help=0;
                                    }
                                    if (help == 1){
                                        selectPlant = new Kakashi();
                                    }else if (help == 2){
                                        selectPlant = new Gaara();
                                    }/*else if (help == 2) {
                                        selectPlant = new RockLee();
                                    }else if(help == 3){
                                        selectPlant = new Sasuke();
                                    }else if(help == 4){
                                        selectPlant = new Kahu();
                                    }*/
                                    break;
                            /*case 2:
                                selectPlant = new CherryBomb();
                                break;
                            case 3:
                                selectPlant = new WallNut();
                                break;
                            case 4:
                                selectPlant = new PotatoMine();
                                break;
                            case 5:
                                selectPlant = new SnowPea();
                                break;
                            case 6:
                                selectPlant = new Chomper();
                                break;
                            case 7:
                                selectPlant = new Repeater();
                                break;

                            case 9:
                                selectPlant = new Squash();
                                break;
                            case 10:
                                selectPlant = new Jalapeno();
                                break;
                            case 11:
                                selectPlant = new Threepeater();
                                break;
                            case 12:
                                selectPlant = new GatlingPea();
                                break;
                            case 13:
                                selectPlant = new TwinSunflower();
                                break;
                            case 14:
                                selectPlant = new DoomShroom();
                                break;
                            case 15:
                                selectPlant = new TallNut();
                                break;
                            case 16:
                                selectPlant = new Spikeweed();
                                break;
                            case 17:
                                selectPlant = new Spikerock();
                                break;
                            case 18:
                                selectPlant = new StarFruit();
                                break;
                            case 19:
                                selectPlant = new SplitPea();
                                break;
                            case 20:
                                selectPlant = new Garlic();
                                break;
                            case 21:
                                selectPlant = new CabbagePult();
                                break;
                            case 22:
                                selectPlant = new KernelPult();
                                break;
                            case 23:
                                selectPlant = new MelonPult();
                                break;
                            case 24:
                                selectPlant = new Cactus();
                                break;
                            case 25:
                                selectPlant = new Plantern();
                                break;
                            case 26:
                                help++;
                                if(help>helpMax){
                                    help=0;
                                }
                                if (help == 0){
                                    selectPlant = new Kakashi();
                                }else if (help == 1){
                                    selectPlant = new Gaara();
                                }else if (help == 2) {
                                    selectPlant = new RockLee();
                                }else if(help == 3){
                                    selectPlant = new Sasuke();
                                }else if(help == 4){
                                    selectPlant = new Kahu();
                                }*/

                                default:
                                    break;
                            }
                        }
                    }
                }


            }else{
                shovelSelected = false;
                ccSprite_shovel.setOpacity(255);
            }
        }else if(selectCard!=null&&selectPlant!=null)
        {
            int col = (int) (cgPoint.x - 220) / 105;
            int row = (int) (cgPoint.y - 40) / 120;

            int colx = (int) (cgPoint.x - 220 - 10) / 105;
            int rowy = (int) (cgPoint.y - 40 - 50) / 120;

            if (col >= 0 && col < 9 && row >= 0 && row < 5) {
                CombatLine combatLine = combatLines.get(row);
                combatLine.setCurrent(row);
                if (!combatLine.isContainPlant(col)) {



                    // 安放植物
                    combatLine.addPlant(col, selectPlant);
                    selectPlant.setPosition(cgPointsTowers.get(row).get(col));
                    cctmxTiledMap.addChild(selectPlant);
                    addSunNumber(-selectPlant.getPrice());

                    // 安放特效
                    if (selectCard.id == 3) {
                        aEffect = new AEffect("eff/cap/Frame%02d.png", 10);
//                        combatLine.addPlant(col,aEffect);
                        aEffect.setPosition(cgPointsTowers.get(row).get(col));
                        cctmxTiledMap.addChild(aEffect);
                    } else {
                        aEffect = new AEffect("eff/show/Frame%02d.png", 5);
//                        combatLine.addPlant(col,aEffect);
                        aEffect.setPosition(cgPointsTowers.get(row).get(col));
                        cctmxTiledMap.addChild(aEffect);
                    }

                    Tools.effectiveSound(R.raw.click);

                    /*if (selectCard.id == 11) {
                        Threepeater threepeater = (Threepeater) selectPlant;
                        threepeater.setCurrerCol(col);
                        threepeater.setAng(col, row);
                    }*/

                    selectPlant = null;
//                        SelectCard.getLight().setOpacity(255);
                    selectCard = null;
                    colShowad.setVisible(false);
                    rowShowad.setVisible(false);
                }
            }
        }else if (shovelSelected) {
            int col = (int) (cgPoint.x - 220) / 105;
            int row = (int) (cgPoint.y - 40) / 120;
            if (col >= 0 && col < 9 && row >= 0 && row < 5) {
                CombatLine combatLine = combatLines.get(row);
                combatLine.setCurrent(row);
                if (combatLine.isContainPlant(col)) {
                    Plant plant = combatLine.getPlants().get(col);
                    plant.safe(combatLine.getZombies());
                    plant.setHP(0);
                    combatLine.getPlants().remove(col);
                    plant.setRemove(true);
                    plant.removeSelf();
                    // 铲除特效
                    aEffect = new AEffect("eff/show/Frame%02d.png", 5);
                    aEffect.setPosition(cgPointsTowers.get(row).get(col));
                    cctmxTiledMap.addChild(aEffect);
                    shovelSelected = false;
                    Tools.effectiveSound(R.raw.click);
                    ccSprite_shovel.setOpacity(255);

                }
            }

            if (CGRect.containsPoint(ccSprite_shovelBackground.getBoundingBox(), cgPoint) || shovelSelected) {
                shovelSelected = false;
                ccSprite_shovel.setOpacity(255);
            }

        } else if (CGRect.containsPoint(ccSprite_shovelBackground.getBoundingBox(), cgPoint)) {
            // 选择铲除植物
            if (selectCard == null && selectCard == null) {
                shovelSelected = true;
                ccSprite_shovel.setOpacity(100);
            }
        } else {
            for (Sun sun :
                    suns) {
                if (CGRect.containsPoint(sun.getBoundingBox(), cgPoint)) {
                    sun.collect();
                }

            }
        }

    }
    public void touchEventsWhileSelect(CGPoint cgPoint)
    {
        if(CGRect.containsPoint(ccSprite_SeedChooser.getBoundingBox(),cgPoint))
        {
            if(selectedCards.size()<8) {
                for (PlantCard plantCard :
                        plantCards) {
                    if (CGRect.containsPoint(plantCard.light.getBoundingBox(), cgPoint)) {
                        if (!selectedCards.contains(plantCard)) {
                            selectedCards.add(plantCard);
                            CCMoveTo ccMoveTo = CCMoveTo.action(0.1f,
                                    CGPoint.ccp(60 * selectedCards.size() + 50, ccSprite_SeedBank.getPosition().y - 40));
                            plantCard.light.runAction(ccMoveTo);
                            if (selectedCards.size() == 8) {
                                ccSprite_SeedChooser_Button.setVisible(true);
                            }
                        }
                    }
                }
            }
        }

        if(CGRect.containsPoint(ccSprite_SeedBank.getBoundingBox(),cgPoint))
        {
            for (PlantCard plantCard:
                    plantCards) {
                if(CGRect.containsPoint(plantCard.light.getBoundingBox(), cgPoint))
                {
                    CCMoveTo ccMoveTo = CCMoveTo.action(0.1f,
                            plantCard.dark.getPosition());
                    plantCard.light.runAction(ccMoveTo);
                    isCardIsMove = true;
                    selectedCards.remove(plantCard);
                    ccSprite_SeedChooser_Button.setVisible(false);
                    break;
                }
            }
        }
        if(isCardIsMove)
        {
            cardMove();
            isCardIsMove=false;
        }

        if(ccSprite_SeedChooser_Button.getVisible())
        {
            if(CGRect.containsPoint(ccSprite_SeedChooser_Button.getBoundingBox(),cgPoint))
            {
                ccSprite_SeedChooser.removeAllChildren(false);
                for (PlantCard plantCard:
                        selectedCards) {
                    addChild(plantCard.light);
                }
                ccSprite_SeedChooser.removeSelf();
                CCMoveTo ccMoveTo = CCMoveTo.action(2f,
                        CGPoint.ccp(-100,0));
                CCCallFunc ccCallFunc = CCCallFunc.action(this,"startReady");
                CCSequence ccSequence = CCSequence.actions(ccMoveTo,ccCallFunc);
                this.cctmxTiledMap.runAction(ccSequence);
            }

        }
    }


    public void startReady()
    {

        setIsKeyEnabled(false);
        ccSprite_startReady = CCSprite.sprite("startready/startReady_00.png");
        ccSprite_startReady.setPosition(winSize.getWidth() / 2, winSize.getHeight() / 2);
        addChild(ccSprite_startReady);



        //放置准备动画效果
        ArrayList<CCSpriteFrame> frames = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            CCSpriteFrame ccSpriteFrame = CCSprite.sprite(String.format(Locale.CHINA,
                    "startready/startReady_%02d.png", i)).displayedFrame();
            frames.add(ccSpriteFrame);
        }

        CCAnimation readyAnimation = CCAnimation.animationWithFrames(frames,0.2f);
        CCAnimate readyAnimate = CCAnimate.action(readyAnimation,false);
        CCCallFunc ccCallFunc = CCCallFunc.action(this,"start");
        CCSequence ccSequence = CCSequence.actions(readyAnimate,ccCallFunc);
        this.setIsTouchEnabled(false);//可触碰为否
        ccSprite_startReady.runAction(ccSequence);
    }
    public void cardMove()
    {
        for (PlantCard plantCard:
                selectedCards) {
            CCMoveTo ccMoveTo = CCMoveTo.action(0.1f,
                    CGPoint.ccp((selectedCards.indexOf(plantCard)+1)*60+50,
                            ccSprite_SeedBank.getPosition().y-40));
            plantCard.light.runAction(ccMoveTo);
        }
    }

    public void createLawnMower(CombatLine combatLine, int i)
    {
        CCSprite lawnMower = CCSprite.sprite("plant/LawnMower.png");
        lawnMower.setScale(1.5);
        lawnMower.setPosition(i * 5, i * ((winSize.getHeight() - 80) / 5) + 80);
        addChild(lawnMower);
        combatLine.setLawnMower(lawnMower);
        ccSprites.add(lawnMower);
        combatLine.setLawnMowerPoint(lawnMower.getPosition());
    }
    private int lawnMowerNum = 0;
    public void lawnMowerAct(float t) {
        if (lawnMowerNum < 5) {
            CCMoveTo ccMoveTo = CCMoveTo.action(0.5f, CGPoint.ccp(ccSprites.get(lawnMowerNum).getPosition().x + 150, ccSprites.get(lawnMowerNum).getPosition().y));
            ccSprites.get(lawnMowerNum).runAction(ccMoveTo);
        } else {
            CCScheduler.sharedScheduler().unschedule("lawnMowerAct", this);
        }
        lawnMowerNum++;
    }
    public void start()
    {
        //CCHide hide = CCHide.action();
        //ccSprite_startReady.runAction(hide);
        isStart = true;
        setIsTouchEnabled(true);

        ccSprite_startReady.removeSelf();

        CCScheduler.sharedScheduler().schedule("lawnMowerAct",
                this,0.01f,false);


        cgPointsTowers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ArrayList<CGPoint> cgPoints_tower = new ArrayList<>();
            CCTMXObjectGroup objectGroup_tower = cctmxTiledMap.objectGroupNamed("tower" + i);
            ArrayList<HashMap<String, String>> objects = objectGroup_tower.objects;
            for (HashMap<String, String> object : objects) {
                float x = Float.parseFloat(object.get("x"));
                float y = Float.parseFloat(object.get("y"));
                cgPoints_tower.add(ccp(x, y));
            }
            cgPointsTowers.add(cgPoints_tower);
        }

        combatLines = new ArrayList<>();
        ccSprites = new ArrayList<>();
        for(int i = 0;i < 5;i++)
        {
            CombatLine line = new CombatLine();
            line.setCurrent(i);
            combatLines.add(line);
            createLawnMower(line,i);
        }
        Tools.combatLines = combatLines;

        cgPointsPath = new ArrayList<>();
        CCTMXObjectGroup objectGroup_path = cctmxTiledMap.objectGroupNamed("path");
        ArrayList<HashMap<String, String>> objects = objectGroup_path.objects;
        for (HashMap<String, String> object : objects) {
            float x = Float.parseFloat(object.get("x"));
            float y = Float.parseFloat(object.get("y"));
            cgPointsPath.add(ccp(x, y));
        }

        random = new Random();
        //10s
        CCScheduler.sharedScheduler().schedule("addZombie",this,10f,false);
        suns = new ArrayList<>();
        update();
        startZombie();
        progress();

        // 影子
        rowShowad = CCSprite.sprite("choose/row.png");
        CGPoint cgPoint = cgPointsTowers.get(0).get(0);
        rowShowad.setPosition(winSize.getWidth()/2,cgPoint.y);
        addChild(rowShowad);

        colShowad = CCSprite.sprite("choose/col.png");
        colShowad.setPosition(cgPoint.x,winSize.getHeight()/2);
        addChild(colShowad);

        //不透明度设置为0 不可见目前
        colShowad.setOpacity(0);
        rowShowad.setOpacity(0);

        startAddSun();
    }



    public void progress() {
    }

    public void startZombie() {
        // 向阳花
        CCDelayTime ccDelayTime = CCDelayTime.action(1f);
        CCCallFunc ccCallFunc = CCCallFunc.action(this, "startAddSun");

        // 第一轮僵尸
        CCDelayTime ccDelayTime1 = CCDelayTime.action(30f);
        CCCallFunc ccCallFunc1 = CCCallFunc.action(this, "startAddZombie1");
        //第二轮僵尸
        CCDelayTime ccDelayTime2 = CCDelayTime.action(60f);
        CCCallFunc ccCallFunc2 = CCCallFunc.action(this, "startAddZombie2");
        //第三轮僵尸
        CCDelayTime ccDelayTime3 = CCDelayTime.action(80f);
        CCCallFunc ccCallFunc3 = CCCallFunc.action(this, "startAddZombie3");
        CCSequence ccSequence = CCSequence.actions(ccDelayTime, ccCallFunc, ccDelayTime1, ccCallFunc1, ccDelayTime2,
                ccCallFunc2, ccDelayTime3, ccCallFunc3);
        runAction(ccSequence);
    }
    private int ZombiesNum = 0;
    private int ZombiesMax = 40;
    private int death;
    public void addZombie(float t)
    {
        if(ZombiesNum < ZombiesMax)
        {
            if(ZombiesNum==0){//加入旗帜僵尸
                int i = random.nextInt(5);
                Zombie zombie = new Zombie(this, cgPointsPath.get(2 * i),
                        cgPointsPath.get(2 * i + 1), 190);
                cctmxTiledMap.addChild(zombie,5-i);
                combatLines.get(i).addZombie(zombie);
                zombies.add(zombie);
            }else if(ZombiesNum<9&&ZombiesNum>0){
                //普通僵尸
                int i = random.nextInt(5);
                Zombie zombie = new Zombie(this, cgPointsPath.get(2 * i),
                        cgPointsPath.get(2 * i + 1));
                cctmxTiledMap.addChild(zombie, 5 - i);
                combatLines.get(i).addZombie(zombie);
                if (ZombiesNum == ZombiesMax) {
                    lastZombie = zombie;
                    zombie.setLast(true);
                }
                zombies.add(zombie);
            }else if(ZombiesNum<19&&ZombiesNum>=9){
                //报纸僵尸
                int i = random.nextInt(5);
                NewspaperZombie zombie = new NewspaperZombie(this, cgPointsPath.get(2 * i),
                        cgPointsPath.get(2 * i + 1));
                cctmxTiledMap.addChild(zombie, 5 - i);
                combatLines.get(i).addZombie(zombie);
                if (ZombiesNum == ZombiesMax) {
                    lastZombie = zombie;
                    zombie.setLast(true);
                }
                zombies.add(zombie);
            }else if(ZombiesNum<29&&ZombiesNum>=19){
                //铁桶子
                int i = random.nextInt(5);
                Zombie zombie = new Zombie(this, cgPointsPath.get(2 * i),
                        cgPointsPath.get(2 * i + 1),600);
                cctmxTiledMap.addChild(zombie, 5 - i);
                combatLines.get(i).addZombie(zombie);
                if (ZombiesNum == ZombiesMax) {
                    lastZombie = zombie;
                    zombie.setLast(true);
                }
                zombies.add(zombie);
            }else{
                //橄榄球
                int i = random.nextInt(5);
                PoleVaultingZombie zombie = new PoleVaultingZombie(this, cgPointsPath.get(2 * i),
                        cgPointsPath.get(2 * i + 1));
                cctmxTiledMap.addChild(zombie, 5 - i);
                combatLines.get(i).addZombie(zombie);
                if (ZombiesNum == ZombiesMax) {
                    lastZombie = zombie;
                    zombie.setLast(true);
                }
                zombies.add(zombie);
            }

        }
        ZombiesNum++;
        //进度条
        //progressTimer.setPercentage(zombiesAll * (100 / checkPoint.getZombiesCount()));
    }

    public void startAddSun()
    {
        //15s 一次
        CCScheduler.sharedScheduler().schedule("randomSun", this, 15.0f, false);
    }
    public void randomSun(float t)
    {
        Random random = new Random();
        int row = random.nextInt(5);
        int col = random.nextInt(9);
        CGPoint location = cgPointsTowers.get(row).get(col);
        CGPoint standLocation = new CGPoint();
        standLocation.set(location.x, location.y);

        Sun sun = new Sun(suns);
        suns.add(sun);
        sun.setPosition(standLocation.x, standLocation.y);
        cctmxTiledMap.getParent().addChild(sun, 7);
        CCMoveTo ccMoveTo = CCMoveTo.action(6f, location);
        sun.runAction(ccMoveTo);
    }
    public void startAddZombie1(){
        CCScheduler.sharedScheduler().schedule("addZombie",this,2f,false);
    }
    public void startAddZombie2(){
        CCScheduler.sharedScheduler().schedule("addZombie",this,1.5f,false);
    }
    public void startAddZombie3(){
        CCScheduler.sharedScheduler().schedule("addZombie",this,1f,false);

    }
    public void addSunNumber(int sunNumber) {
        currentSunNumber += sunNumber;
        ccLabel.setString(currentSunNumber + "");
        update();
    }
    public void update()
    {
        for (PlantCard plantCard : selectedCards) {
            int price = 0;
            switch (plantCard.id) {
                case 0:
                    price = 100;
                    break;
                case 1:
                    price = 50;
                    break;
                case 2:
                    price = 150;
                    break;
                case 3:
                    price = 50;
                    break;
                case 4:
                    price = 25;
                    break;
                case 5:
                    price = 175;
                    break;
                case 6:
                    price = 150;
                    break;
                case 7:
                    price = 200;
                    break;
                case 8:
                    price = 175;
                    break;
                case 9:
                    price = 50;
                    break;
                case 10:
                    price = 125;
                    break;
                case 11:
                    price = 125;
                    break;
                case 12:
                    price = 250;
                    break;
                case 13:
                    price = 150;
                    break;
                case 14:
                    price = 125;
                    break;
                case 15:
                    price = 125;
                    break;
                case 16:
                    price = 100;
                    break;
                case 17:
                    price = 125;
                    break;
                case 18:
                    price = 125;
                    break;
                case 19:
                    price = 125;
                    break;
                case 20:
                    price = 50;
                    break;
                case 21:
                    price = 100;
                    break;
                case 22:
                    price = 100;
                    break;
                case 23:
                    price = 300;
                    break;
                case 24:
                    price = 120;
                    break;
                case 25:
                    price = 120;
                    break;
                case 26:
                    price = 20;
                    break;
                case 27:
                    price = 20;
                    break;
                case 28:
                    price = 20;
                    break;
                default:
                    break;
            }
            if (currentSunNumber >= price) {
                plantCard.light.setOpacity(255);
            } else {
                plantCard.light.setOpacity(100);
            }
        }
    }
    public ArrayList<Zombie> getZombies()
    {
        return zombies;
    }
    public void end()
    {

    }

    public void addSun(Sun sun) {
        suns.add(sun);
    }

    public void removeSun(Sun sun) {
        suns.remove(sun);
    }
}
