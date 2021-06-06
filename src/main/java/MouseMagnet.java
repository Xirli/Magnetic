import processing.core.PApplet;

public class MouseMagnet extends Magnet{

    PApplet sketch;

    public MouseMagnet(Particle[] particle, PApplet sketch, GraphicObject graphic) {
        super(particle, graphic);
        this.sketch = sketch;
    }

    public void run(){
        setCoord(sketch.mouseX, sketch.mouseY, getCoord().z);
        setSpeed(0,0,0);
    }
    public void draw(PApplet sketch){
        for(Particle part : getParticle()){

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
                    (100*part.absoluteCoord.x + getSpeed().x*1000000)/(part.absoluteCoord.z),
                    (100*part.absoluteCoord.y + getSpeed().y*1000000)/(part.absoluteCoord.z)
            );
            sketch.strokeWeight(0);
        }
    }
}
