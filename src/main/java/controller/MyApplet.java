package controller;

import model.magnet.Magnet;
import view.Analyzer;
import processing.core.PApplet;

import java.util.ArrayList;

import static controller.Param.*;
import static view.Param.frameHeight;
import static view.Param.frameWidth;

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