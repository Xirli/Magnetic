package controller;

import model.magnet.Magnet;
import model.magnet.MagnetGenerator;
import model.service.Physics;
import view.GraphicGenerator;
import processing.core.PApplet;

import java.util.ArrayList;

import static controller.Param.msPerUpdateModel;
import static java.lang.Thread.sleep;

public class Main{

    public static void main(String[] args){

        ArrayList<Magnet> magnets = new ArrayList<>();
        MyApplet sketch = new MyApplet(magnets);

        magnets.addAll(MagnetGenerator.generateParaMagnetic(GraphicGenerator.blackWhiteCircle()));
        magnets.add(MagnetGenerator.generateMouseMagnet(sketch, GraphicGenerator.testCharge()));

        PApplet.runSketch(new String[]{"MySketch"}, sketch);
        updateLoop(magnets);
    }

    public static void updateLoop(ArrayList<Magnet> magnets) {
        while (true)
        {
            long start = System.currentTimeMillis();
            Physics.update(magnets);
            try {
                long timeSleep = start + msPerUpdateModel - System.currentTimeMillis();
                if(timeSleep > 0) sleep(timeSleep);
            } catch(InterruptedException ignored){}
        }
    }
}