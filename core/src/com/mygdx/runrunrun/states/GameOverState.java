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

import org.w3c.dom.css.Rect;

/**
 * Created by 343076 on 12/08/2015.
 */
public class GameOverState extends State {

    private TextureRegion gameOver;
    private TextureRegion play;
    private Rectangle playRect;
    private TextureRegion menu;
    private Rectangle menuRect;

    //private TextImage game_over_text;
    private TextImage highScore;
    private TextImage currentScore;

    //private TextImage menu;
    //private TextImage play;

    private boolean fState; // 0 play again, 1 main menu;

    public GameOverState(GSM gsm, int score){
        super(gsm);

        enterTransition = true;
        enterTransitionVal = 1f;
        getEnterTransitionValHelper = 1f;

        exitTransition = false;

        currentScore = new TextImage("YOUR SCORE:" + score, 0, 0, 0.5f );
        highScore = new TextImage("HIGH SCORE:" + Main.pref.getHighScore(), 0, 0, 0.5f);


        //game_over_text = new TextImage("GAME OVER!",Main.WIDTH/2, Main.HEIGHT/2 + 100, 1f);
        gameOver = new TextureRegion(Main.resource.getAtlas("assets").findRegion("gameover"));
        play = new TextureRegion(Main.resource.getAtlas("assets").findRegion("restart"));
        playRect = new Rectangle(Main.WIDTH/2 - play.getRegionWidth()/2,Main.HEIGHT/2,play.getRegionWidth(),play.getRegionHeight());
        menu = new TextureRegion(Main.resource.getAtlas("assets").findRegion("mainmenu"));
        menuRect = new Rectangle(Main.WIDTH/2 - menu.getRegionWidth()/2,Main.HEIGHT/2 - playRect.getHeight(),menu.getRegionWidth(),menu.getRegionHeight());

        currentScore.update(Main.WIDTH/2 + currentScore.getWidth()/2, Main.HEIGHT/2 - 130);
        highScore.update(Main.WIDTH/2 + highScore.getWidth()/2, Main.HEIGHT/2 - 160);

        if(score > Main.pref.getHighScore()) {
            Main.pref.setHighScore(score);
            currentScore.update("NEW HIGH SCORE: " + Main.pref.getHighScore(), Main.WIDTH/2, Main.HEIGHT/2, 1f);
            currentScore.setTextHide(false);
        }else{
            currentScore.setTextHide(false);
            highScore.setTextHide(false);
        }

        //game_over_text.setTextHide(false);
        //menu = new TextImage("MENU", Main.WIDTH/2 + 100, Main.HEIGHT/2 - 150, 1f);
        //play = new TextImage("PLAY", Main.WIDTH/2 - 100, Main.HEIGHT/2 - 150, 1f);
        //menu.setTextHide(false);
        //play.setTextHide(false);

    }

    public void handleInput(){
        if(Gdx.input.isTouched() && !exitTransition && !enterTransition){

            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(mouse);
            if(menuRect.contains(mouse.x, mouse.y)) {
                Main.sounds.playSound("select");
                fState = true;
                exitTransition = true;
                exitTransitionVal = 0f;
            }
            else if (playRect.contains(mouse.x, mouse.y)) {
                Main.sounds.playSound("select");
                fState = false;
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
                if(!fState)
                    gsm.set(new PlayState(gsm, 5));
                else if(fState)
                    gsm.set(new MenuState(gsm));
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

        currentScore.render(sb);
        highScore.render(sb);
        //game_over_text.render(sb);
        sb.draw(gameOver,Main.WIDTH/2 - gameOver.getRegionWidth()/2, Main.HEIGHT/2 + 100);
        sb.draw(play,playRect.getX(),playRect.getY());
        sb.draw(menu,menuRect.getX(),menuRect.getY());
        //menu.render(sb);
        //play.render(sb);

        sb.end();
    }

    public void shapeRender(ShapeRenderer sr){
        super.shapeRender(sr);
    }
}
