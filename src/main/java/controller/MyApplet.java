package controller;

import model.magnet.Magnet;
import view.Analyzer;
import processing.core.PApplet;

import java.util.ArrayList;

import static controller.ParamController.*;
import static view.ParamView.frameHeight;
import static view.ParamView.frameWidth;

public class MyApplet extends PApplet{

    ArrayList<Magnet> magnets;

    MyApplet(ArrayList<Magnet> magnets){
        this.magnets = magnets;
    }

    public void settings(){
        size(frameWidth, frameHeight);
    }
    public void setup() {
        frameRate(FPS);
    }

    public void draw(){
        background(0);
        Analyzer.potential(this, magnets);
        for(Magnet mag : magnets) {
            mag.draw(this);
        }
    }

}