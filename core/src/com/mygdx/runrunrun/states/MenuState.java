package com.mygdx.runrunrun.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.runrunrun.Main;
import com.mygdx.runrunrun.ui.TextImage;


/**
 * Created by 343076 on 21/09/2015.
 */
public class MenuState extends State {

    //private TextImage name;
    private TextImage gold;
    private TextImage highScore;

    //private TextImage title;
    //private TextImage startButton;

    private TextureRegion title;
    private TextureRegion startButton;

    private Rectangle startRect;

    public MenuState(GSM gsm){
        super(gsm);

        initPrefs();

        enterTransition = true;
        enterTransitionVal = 1f;
        getEnterTransitionValHelper = 1f;

        exitTransition = false;

        //name = new TextImage("" + Main.pref.getName(), Main.WIDTH/2, Main.HEIGHT/2 - 50, 1);
        //title = new TextImage("KOBE", Main.WIDTH/2, Main.HEIGHT/2 + 100,1);
        //startButton = new TextImage("START", Main.WIDTH/2, Main.HEIGHT/2 + 50,1);

        title = new TextureRegion(Main.resource.getAtlas("assets").findRegion("title"));
        startButton = new TextureRegion(Main.resource.getAtlas("assets").findRegion("start"));
        startRect = new Rectangle(Main.WIDTH/2 - startButton.getRegionWidth()/2, Main.HEIGHT/2 - startButton.getRegionHeight()/2,startButton.getRegionWidth(),startButton.getRegionHeight());

        highScore = new TextImage("HIGH SCORE:" + Main.pref.getHighScore(), 0, 0,0.5f);
        gold = new TextImage("GOLD:" + Main.pref.getGold(), 0,0, 0.5f);

        highScore.update(Main.WIDTH/2 + highScore.getWidth()/2, Main.HEIGHT/2 - 160);
        gold.update(Main.WIDTH/2 + gold.getWidth()/2, Main.HEIGHT/2 - 130);

        highScore.setTextHide(false);
        gold.setTextHide(false);

        //name.setTextHide(false);
        //title.setTextHide(false);
        //startButton.setTextHide(false);


    }

    private void initPrefs(){

        if(!Main.pref.getPrefs().contains("Name"))
            Main.pref.setName("PLAYER");

        if(!Main.pref.getPrefs().contains("Gold"))
           Main.pref.setGold(0);

        if(!Main.pref.getPrefs().contains("High Score"))
            Main.pref.setHighScore(0);
    }

    public void handleInput(){
        if(Gdx.input.justTouched() && !exitTransition && !enterTransition){
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(mouse);

            if(startRect.contains(mouse.x,mouse.y)){
                Main.sounds.playSound("select");
                exitTransition = true;
                exitTransitionVal = 0f;
            }
        }
    }

    @Override
    protected void onExitTransition(float dt){
        if(exitTransition){

            exitTransitionVal += 1f * dt;
            if(exitTransitionVal >= 1f){
                gsm.set(new PlayState(gsm, 5));
            }
        }
    }

    public void update(float dt){

        handleInput();
        onEnterTransition(dt);
        onExitTransition(dt);

    }

    public void render(SpriteBatch sb){
        sb.setProjectionMatrix((cam.combined));
        sb.begin();


        gold.render(sb);
        sb.draw(title, Main.WIDTH/2 - title.getRegionWidth()/2, Main.HEIGHT/2 + 60);
        sb.draw(startButton, startRect.getX(), startRect.getY());
        highScore.render(sb);
        //name.render(sb);
        //title.render(sb);
        //startButton.render(sb);

        sb.end();
    }

    public void shapeRender(ShapeRenderer sr){
        super.shapeRender(sr);
    }
}
