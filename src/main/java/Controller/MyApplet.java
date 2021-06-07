package Controller;

import Model.Magnet;
import View.Analyzer;
import processing.core.PApplet;

import java.util.ArrayList;

import static Controller.Param.*;

public class MyApplet extends PApplet{

    ArrayList<Magnet> magnets;

    MyApplet(ArrayList<Magnet> magnets){
        this.magnets = magnets;
    }

    public void settings(){
        size(frameWidth, frameHeight);
    }
    public void setup() {
        frameRate(msPerUpdateFrame);
    }

    public void draw(){
        background(0);
        Analyzer.potential(this, magnets);
        for(Magnet mag : magnets) {
            mag.draw(this);
        }
    }

}