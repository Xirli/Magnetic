import processing.core.PApplet;

import java.util.ArrayList;

    public class MyApplet extends PApplet{

    public static final float drawRadiusOfParticle = 1000;

    ArrayList<Magnet> magnets;

    MyApplet(ArrayList<Magnet> magnets){
        this.magnets = magnets;
    }

    //TODO определить отдельный класс (интерфейс), в котором описать константы
    public void settings(){
        //TODO Выделить в отдельный класс как константы
        size(800, 600);
    }
    public void setup() {
        //TODO Выделить в отдельный класс как константу
        frameRate(60);
    }


    public void draw(){
        background(0);
        Analizator.potential(this, magnets);
        for(Magnet mag : magnets) {
            mag.draw(this);
        }
        fill(0);
        text(frameRate, 10,10);
    }

    public void mouseDragged(){

    }

}