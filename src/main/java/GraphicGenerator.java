import processing.core.PApplet;

public class GraphicGenerator {

    public static GraphicObject blackWhiteCircle(){
        return (sketch, magnet) -> {
            for(Particle part : magnet.getParticle()){

                if(part.absoluteCoord.z < 1) continue;

                if(part.charge > 0) sketch.fill(255,0,0);
                else sketch.fill(0,0,255);
                sketch.ellipse(
                        100*part.absoluteCoord.x/(part.absoluteCoord.z),
                        100*part.absoluteCoord.y/(part.absoluteCoord.z),
                        MyApplet.drawRadiusOfParticle/(part.absoluteCoord.z),
                        MyApplet.drawRadiusOfParticle/(part.absoluteCoord.z)
                );
            }
        };
    }

    public static GraphicObject testCharge(){
        return (sketch, magnet) -> {
            for(Particle part : magnet.getParticle()){

                if(part.absoluteCoord.z < 1) continue;

                if(part.charge > 0) sketch.fill(255,0,0);
                else sketch.fill(0,0,255);

                sketch.ellipse(
                        (100*part.absoluteCoord.x)/(part.absoluteCoord.z),
                        (100*part.absoluteCoord.y)/(part.absoluteCoord.z),
                        MyApplet.drawRadiusOfParticle/(part.absoluteCoord.z),
                        MyApplet.drawRadiusOfParticle/(part.absoluteCoord.z)
                );
                sketch.stroke(255);
                sketch.strokeWeight(5);
                sketch.line(
                        (100*part.absoluteCoord.x)/(part.absoluteCoord.z),
                        (100*part.absoluteCoord.y)/(part.absoluteCoord.z),
                        (100*part.absoluteCoord.x + magnet.getSpeed().x*1000000)/(part.absoluteCoord.z),
                        (100*part.absoluteCoord.y + magnet.getSpeed().y*1000000)/(part.absoluteCoord.z)
                );
                sketch.strokeWeight(0);
            }
        };
    }
}
