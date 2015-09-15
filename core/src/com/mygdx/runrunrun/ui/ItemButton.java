package com.mygdx.runrunrun.ui;

/**
 * Created by 343076 on 13/09/2015.
 */
public class ItemButton extends Box{

    private Item item;
    private boolean hide;

    public ItemButton(float x, float y, float width, float height, Item item){

        super(x,y,width,height);
        this.item = item;
        hide = true;
    }

    public void setHide(boolean b){
        this.hide = b;
    }

    public void interact(){
        if(!hide) {
            switch (this.item){
                case SLEEP:
                    System.out.println("You picked sleep");
                    break;
                case BREAD:
                    System.out.println("You picked bread");
                    break;
                case SOUP:
                    System.out.println("You picked soup");
                    break;
                case SUSHI:
                    System.out.println("You picked sushi");
                break;
                case SODA:
                    System.out.println("You picked soda");
                break;
            }
        }
    }


}
