package controller;

import model.magnet.Magnet;
import view.Analyzer;
import processing.core.PApplet;

import static controller.ParamController.*;
import static view.ParamView.frameHeight;
import static view.ParamView.frameWidth;

public class MyApplet extends PApplet{

    PhysicsController physicsController;

    MyApplet(){ }

    public void setPhysicsController(PhysicsController physicsController) {
        this.physicsController = physicsController;
    }

    public void settings(){
        size(frameWidth, frameHeight);
    }
    public void setup() {
        frameRate(FPS);
    }

    public void draw(){
        background(0);
        Analyzer.potential(this, physicsController.getMagnets());
        for(Magnet mag : physicsController.getMagnets()) {
            mag.draw(this);
        }
    }

    public void keyPressed(){
        switch (key) {
            case ' ' -> physicsController.changeRunPause();
            case ESC -> physicsController.stop();
            case '1' -> physicsController.start(1);
            case '2' -> physicsController.start(2);
        }
    }

}