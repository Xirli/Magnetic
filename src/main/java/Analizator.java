import processing.core.PApplet;

import java.util.ArrayList;

public class Analizator {

    public static float SIZE = 10;

    public static void potential(ArrayList<Magnet> magnets){

        for(int x = 0; x < Main.SKETCH.width; x += SIZE){
            for(int y = 0; y < Main.SKETCH.height; y += SIZE){
                float potential = potential(x, y, magnets)*200;
                Main.SKETCH.colorMode(PApplet.HSB);
                //Main.SKETCH.fill(PApplet.abs(potential)%100+150,255,255);
                Main.SKETCH.fill(potential%100+200,255,155+potential%100);
                //System.out.println((potential*1000)%255);
                Main.SKETCH.rect(x, y, SIZE, SIZE);
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
