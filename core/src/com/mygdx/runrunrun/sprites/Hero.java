package com.mygdx.runrunrun.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.runrunrun.Main;

/**
 * Created by 343076 on 31/07/2015.
 */
public class Hero extends MoveableObject {

    private static float MAX_SPEED = 100f;

    // Jump mechanics
    private float jump_acceleration;
    private boolean inAir;

    // Moving animations
    private float height_var;
    private boolean height_anim_interval_status;

    // Hit mechanics
    private boolean hide;
    private int health_counter;

    // Moving mechanics
    private TextureRegion bg_reference;
    private float speed;
    private boolean isStopped;

    // Items
    private int coins;

    // This constructor provides the Background reference, so that MoveableObject can determine at what x position Hero resets
    public Hero(float x, float y, TextureRegion image, TextureRegion bg_reference){

        super(x, y, image);

        // Moving animations
        height_var = 0f;
        height_anim_interval_status = true;

        // Hit mechanics
        health_counter = 3;
        hide = false;

        // Moving mechanics
        speed = MAX_SPEED;
        this.bg_reference = bg_reference;

        // Items
        coins = 0;

    }


    public void addCoin(int value){
        coins += value;
    }

    public int getCoins(){
        return coins;
    }

    public int getHealth_counter(){
        return health_counter;
    }

    public void reduceHealth(){
        health_counter--;
    }

    public void addHealth(){
        health_counter++;
    }

    public boolean isJumping(){
        return inAir;
    }

    public float getSpeed(){
        return speed;
    }

    public void toggleStop(){
        if(isStopped){
            speed = MAX_SPEED;
            isStopped = false;
        }else{
            speed = 0;
            isStopped = true;
        }

    }

    public void hit_animation(float t){
        if(t%2 == 0){
            hide = false;
        }
        else{
            hide = true;
        }
    }

    public void jump(){
        if(inAir == false && isStopped == false){
            jump_acceleration = 200f;
        }
    }

    private void updateAnimation(){
        if(!inAir) {
            if (height_anim_interval_status) {
                height_var++;
                if (height_var > 5f) {
                    height_anim_interval_status = false;
                }
            } else{
                height_var--;
                if (height_var < 0f) {
                    height_anim_interval_status = true;
                }
            }
        }
        else {
            height_var = 0;
        }

        if(isStopped){
            height_var = 0;
        }
    }

    public void update(float dt){

        super.update(dt);

        float init_x = this.position.x;
        float init_y = this.position.y;

        updateAnimation();

        float final_x = init_x + (speed) * dt;
        if(final_x >= bg_reference.getRegionWidth()){
            final_x = 0;
        }

        float final_y = init_y + (jump_acceleration) * dt;
        if(final_y > 0f){
            inAir = true;
            jump_acceleration -= 100f * dt;
        }
        else{
            jump_acceleration = 0;
            final_y = 0;
            inAir = false;
        }

        this.position.set(final_x,final_y);

    }

    public void render(SpriteBatch sb){

        if(hide == false)
            sb.draw(image, position.x, position.y, width, height - height_var);

    }


}
