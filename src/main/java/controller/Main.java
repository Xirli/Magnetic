package controller;

import processing.core.PApplet;

public class Main{

    public static void main(String[] args){
        PhysicsController physicsController = new PhysicsController();
        MyApplet sketch = new MyApplet();

        physicsController.setSketch(sketch);
        sketch.setPhysicsController(physicsController);

        PApplet.runSketch(new String[]{"MySketch"}, sketch);
        physicsController.start(1);
    }

}