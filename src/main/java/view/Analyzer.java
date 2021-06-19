package view;

import model.magnet.Magnet;
import model.magnet.Particle;
import processing.core.PApplet;

import java.util.ArrayList;

import static view.ParamView.analysisSize;

public class Analyzer {

    public static void potential(PApplet sketch, ArrayList<Magnet> magnets){
        sketch.colorMode(PApplet.HSB);
        for(int x = 0; x < sketch.width; x += analysisSize){
            for(int y = 0; y < sketch.height; y += analysisSize){
                double potential = potential(x, y, magnets)*200;
                //float brightness = (float) Math.sqrt(Math.abs(potential))*100;
                float brightness = 255;
                float hue = (float) Math.max(potential,-50) + 206;

                sketch.fill(hue, 255, brightness);
                sketch.stroke(hue, 255, brightness);
                sketch.rect(x, y, analysisSize, analysisSize);
            }
        }
    }

    public static float potential(float x, float y, ArrayList<Magnet> magnets){
        float potential = 0;
        for(Magnet magnet : magnets){
            for(Particle part : magnet.getParticle()){
                potential += potential(x, y, part);
            }
        }
        return potential;
    }

    public static float potential(float x, float y, Particle part){
        return part.charge / PApplet.dist(part.absoluteCoord.x, part.absoluteCoord.y, x, y);
    }

}
