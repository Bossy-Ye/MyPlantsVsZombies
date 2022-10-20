package com.example.myplantsvszombies.src.layer;

import android.view.MotionEvent;

import com.example.myplantsvszombies.Tools;
import com.example.myplantsvszombies.src.card.PlantCard;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import java.util.ArrayList;
import java.util.Locale;

public class AlmanacLayer extends CCLayer {
    private CCLayer preLayer;
    private CGSize winSize;
    private CCDirector director;
    private CCSprite background;
    private CCSprite cardBack;
    private CCMenu exitMenu;
    private int cardNum = 27;
    private ArrayList<PlantCard> plantCards = new ArrayList<PlantCard>();
    private ArrayList<PlantCard> selected_plantCards = new ArrayList<PlantCard>();
    private ArrayList<CCSpriteFrame> cardEffectFrames = new ArrayList<>();
    private ArrayList<CCSpriteFrame> cardSurroundFrames = new ArrayList<>();
    private CCLabel showLabel;
    private CCSprite showText;
    private CCSprite showFrame;
    private CCSprite almanac;
    private CCSprite light;

    public AlmanacLayer(CCLayer ccLayer)
    {
        this.preLayer = ccLayer;
        this.director = CCDirector.sharedDirector();
        this.winSize = CCDirector.sharedDirector().winSize();
        load();
        loadCard();
        showCardFrame();
        setIsTouchEnabled(true);
    }
    public void load()
    {
        background = CCSprite.sprite("interface/background3.jpg");
        background.setAnchorPoint(0,0);
        background.setPosition(0,0);
        background.setScale(1.25f);
        this.addChild(background);

        cardBack = CCSprite.sprite("interface/Almanac_PlantBack.jpg");
        cardBack.setAnchorPoint(0,0);
        cardBack.setPosition(winSize.width/5,winSize.getHeight()-cardBack.getContentSize().getHeight());
        addChild(cardBack);

        //exit
        exitMenu = CCMenu.menu();
        CCSprite exit_default =
                CCSprite.sprite("menu/exit.png");
        CCSprite exit_press =
                CCSprite.sprite("menu/exited.png");
        CCMenuItemSprite ccMenuItemExitSprite = CCMenuItemSprite.item(exit_default,
                exit_press,this,"exit");
        ccMenuItemExitSprite.setPosition(-500,-300);
        ccMenuItemExitSprite.setScale(0.6f);
        exitMenu.addChild(ccMenuItemExitSprite);
        this.addChild(exitMenu);

        setIsTouchEnabled(true);
    }
    public void exit(Object item)
    {
        if(preLayer instanceof  MenuLayer)
        {
            CCScene ccScene = CCScene.node();
            ccScene.addChild(preLayer);

            CCFadeTransition ccFadeDownTransition = CCFadeTransition.transition(
                    2f, ccScene);
            director.replaceScene(ccFadeDownTransition);
        }else if(preLayer instanceof GameLayer) {
            preLayer.onEnter();
            removeSelf();
        }
    }
    @Override
    public boolean ccTouchesBegan(MotionEvent event)
    {
        CGPoint cgPoint = convertTouchToNodeSpace(event);

        for (PlantCard card:
             plantCards) {
            boolean containsPoint = CGRect.containsPoint(card.light.getBoundingBox(), cgPoint);
            if(containsPoint)
            {
                ShowCardDesc(plantCards.indexOf(card));
            }
        }
        return false;
    }

    public void loadCard()
    {
        int biasX = 306;
        int biasY = 650;
        for(int i = 0;i < cardNum;i++)
        {
            int bias = i;
            PlantCard plantCard = new PlantCard(i);
            plantCards.add(plantCard);
//            plantCard.getDark().setPosition(biasX + 52 * (i % 8), biasY + 590 - (bias / 8) * 80);
//            cardBack.addChild(plantCard.getDark());
            plantCard.light.setPosition(biasX + 52 * (bias % 8), biasY - (bias / 8) * 80);
            addChild(plantCard.light);
        }

        light = CCSprite.sprite("eff/cardlight/taqing_round_anim_00.png");
        light.setPosition(plantCards.get(0).light.getPosition().x, plantCards.get(0).light.getPosition().y);
        addChild(light);
        light.setScaleX(0.7f);
        //卡片特效动画
        for(int i = 0;i < 10;i++)
        {
            cardSurroundFrames.add(CCSprite.sprite(String.format(Locale.CHINA,
                    "eff/cardlight/taqing_round_anim_%02d.png",i)).displayedFrame());
        }

        CCAnimation animation = CCAnimation.animation("cardEffect",0.1f,cardSurroundFrames);
        CCAnimate cardSurAnimate = CCAnimate.action(animation);
        CCRepeatForever repeatSurEffect = CCRepeatForever.action(cardSurAnimate);

        light.runAction(repeatSurEffect);
    }
    public void showCardFrame()
    {
        //加入图鉴
        almanac = CCSprite.sprite("choose/Almanac_PlantCard.png");
        almanac .setPosition(886,450);
        this.addChild(almanac);


        //初始化显示豌豆射手图鉴
        showFrame = CCSprite.sprite("plant/Peashooter/Frame00.png");
        showFrame.setPosition(894,600);
        this.addChild(showFrame);

        showLabel = CCLabel.makeLabel("小小豌豆射手！", "hkbd.ttf", 25);
        showLabel.setColor(ccColor3B.ccBLACK);
        showLabel.setPosition(showFrame.getPosition().x + 65, winSize.getHeight() - 280);
        addChild(showLabel);
        //描述信息
        showText = CCSprite.sprite("text/t00.png");
        showText.setPosition(showFrame.getPosition().x, winSize.getHeight() - 440);
        addChild(showText);

        ShowCardDesc(0);
    }
    public void ShowCardDesc(int id)
    {
        cardEffectFrames.clear();

        //展现植物的动画
        for(int i = 0; i < Tools.cardInt[id]; i++)
        {
            CCSpriteFrame frame = CCSprite.sprite(String.format(
                  Locale.CHINA,Tools.cardPath[id],i)).displayedFrame();
            cardEffectFrames.add(frame);
        }
        CCAnimation ccAnimation = CCAnimation.animation("plantAnimation",0.1f,cardEffectFrames);
        CCAnimate ccAnimate = CCAnimate.action(ccAnimation,true);
        CCRepeatForever ccRepeatForever = CCRepeatForever.action(ccAnimate);

        showFrame.setPosition(894,600);
        //对窝瓜位置做特别调整
        if(id==9) showFrame.setPosition(894,650);
        showFrame.runAction(ccRepeatForever);

        showLabel.setString(Tools.name[id]);

        showText.removeSelf();
        showText = CCSprite.sprite(String.format(Locale.CHINA,"text/t%02d.png",id));
        showText.setPosition(showFrame.getPosition().x, winSize.getHeight() - 440);
        addChild(showText);

        //调整卡片环绕光圈效果
        light.setPosition(plantCards.get(id).light.getPosition().x, plantCards.get(id).light.getPosition().y);
    }
}
