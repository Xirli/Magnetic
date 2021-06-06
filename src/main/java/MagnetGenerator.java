import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class MagnetGenerator {

    public static float sceneX = 400;
    public static float sceneY = 300;
    public static float sceneZ = 100;

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
            public void run() {
                //coord is const
                setAngle(PVector.add(getAngle(), getVelocity()));
            }
        };
    }
    public static Magnet generateMouseMagnet(PApplet sketch){
        Magnet m = new MouseMagnet(monopole((float) 0.01, (float) 0.01), sketch);
        m.setCoord(sceneX, sceneY, sceneZ);
        return m;
    }

    public static ArrayList<Magnet> generateMonopole(int count){
        ArrayList<Magnet> magnets = new ArrayList<>();
        float charge = 1;
        for(int i = 0; i < count; i++) {
            Magnet m = new Magnet( monopole( charge*=-1, 1) );
            m.setCoord(new PVector().add(sceneX + (float)Math.random()*400-200, sceneY + (float)Math.random()*400-200, sceneZ));
            magnets.add(m);
        }
        return magnets;
    }

    public static ArrayList<Magnet> generateDipole(){

        int count = 50;
        int radius = 10;

        ArrayList<Magnet> magnets = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            Magnet m = new Magnet(dipole(radius, 1, 1));
            m.setCoord(PVector.random2D().mult(100).add(sceneX, sceneY, sceneZ));
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

        for(int i = -width/2; i < (width+1)/2; i++) {
            for(int j = -height/2; j < (height+1)/2; j++) {

                Magnet m = MagnetConstCoord(dipole(10, 5, 5));

                m.setCoord(i * dist + sceneX,j * dist + sceneY, sceneZ);
                //m.setVelocity(0,  0, (float) ((float) 0.0*((PApplet.abs(j)%2)-0.5)));
                //m.setAngle(0,0, j%2 * PApplet.PI );//
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
            m.setCoord(sceneX, sceneY, sceneZ);
            m.setVelocity(0,  0, (float) 0.001*0);
            magnets.add(m);
        }
        return magnets;
    }

}