import processing.core.PVector;

import java.util.ArrayList;

public class MagnetGenerator {

    public static final float sceneZ = 100;

    public static Particle[] monopole(float charge, float particleMass){
        return multipole(1, 0, charge, particleMass);
    }

    public static Particle[] dipole(float radius, float charge, float particleMass){
        return multipole(2, radius, charge, particleMass);
    }

    public static Particle[] multipole(int countOfPole, float radius, float charge, float particleMass){
        Particle[] particle = new Particle[countOfPole];

        for(int i = 0; i < countOfPole; i++){
            particle[i] = new Particle(
                    PVector.fromAngle((float) (2*Math.PI * i/countOfPole)).mult(radius),
                    -(charge *=-1),
                    particleMass
            );
        }return particle;
    }

    public static Magnet MagnetConstCoord(Particle[] part){
        return new Magnet(part) {
            public void display() {
                //coord is const
                setAngle(PVector.add(angle, velocity));
                angle.mult(rotationSlowdown);
            }
        };
    }
    public static Magnet MagnetMouseCoord(Particle[] part){
        return new Magnet(part){
            public void display(){
                setCoord(Main.SKETCH.mouseX-Main.SKETCH.width/2 , Main.SKETCH.mouseY-Main.SKETCH.height/2, sceneZ);
                setSpeed(0,0,0);
            }
            public void draw(){
                for(Particle part : particle){

                    if(part.absoluteCoord.z < 1) continue;

                    if(part.charge > 0) Main.SKETCH.fill(255,0,0);
                    else Main.SKETCH.fill(0,0,255);

                    Main.SKETCH.ellipse(
                            (100*part.absoluteCoord.x)/(part.absoluteCoord.z),
                            (100*part.absoluteCoord.y)/(part.absoluteCoord.z),
                            drawRadiusOfParticle/(part.absoluteCoord.z),
                            drawRadiusOfParticle/(part.absoluteCoord.z)
                    );
                    Main.SKETCH.stroke(255);
                    Main.SKETCH.strokeWeight(5);
                    Main.SKETCH.line(
                            (100*part.absoluteCoord.x)/(part.absoluteCoord.z),
                            (100*part.absoluteCoord.y)/(part.absoluteCoord.z),
                            (100*part.absoluteCoord.x+ speed.x*1000000)/(part.absoluteCoord.z),
                            (100*part.absoluteCoord.y+ speed.y*1000000)/(part.absoluteCoord.z)
                    );
                    Main.SKETCH.strokeWeight(0);
                }
            }
        };
    }

    public static ArrayList<Magnet> generateMonopole(int count){
        ArrayList<Magnet> magnets = new ArrayList<>();
        float charge = 1;
        for(int i = 0; i < count; i++) {
            Magnet m = new Magnet( monopole( charge*=-1, 1) );
            m.setCoord(PVector.random2D().mult(200).add(0,0,sceneZ));
            m.setVelocity(0, (float) 0, (float) 0.0);
            magnets.add(m);
        }
        return magnets;
    }

    public static ArrayList<Magnet> generateDipole(){

        int count = 50;
        int radius = 10;

        ArrayList<Magnet> magnets = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Magnet m = new Magnet(dipole(radius, 1, 1));
            m.setCoord(PVector.random2D().mult(100).add(0,0,sceneZ));
            m.setVelocity(0, 0,  0);
            magnets.add(m);
        }
        return magnets;
    }

    public static ArrayList<Magnet> generateParaMagnetic(){

        int width = 10;
        int height = 10;
        float dist = 50;

        ArrayList<Magnet> magnets = new ArrayList<>();

        Magnet m = MagnetMouseCoord(monopole(4,2));
        m.setCoord(-50,-50,sceneZ);
        m.setVelocity(0, 0,  0);
        magnets.add(m);

        for(int i = -width/2; i < (width+1)/2; i++) {
            for(int j = -height/2; j < (height+1)/2; j++) {

                m = MagnetConstCoord(dipole(7, 5, 5));

                m.setCoord(i * dist,j * dist, sceneZ);
                m.setVelocity(0,  0,  0);
                //m.setAngle(0,0, (float) Math.random());//
                magnets.add(m);
            }
        }
        return magnets;
    }

    public static ArrayList<Magnet> generateEllipseMagnetic(){

        int colRadius = 40;
        int colPart = 1;
        float radius = 10;

        ArrayList<Magnet> magnets = new ArrayList<>();

        for(int i = 2; i <= colRadius; i++) {
            Magnet m = MagnetConstCoord(multipole(colPart++, radius * i, 5, 1));
            m.setCoord(0,0, sceneZ);
            m.setVelocity(0,  0, (float) 0.001*0);
            magnets.add(m);
        }
        return magnets;
    }

}