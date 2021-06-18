package model.magnet;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import processing.core.PApplet;
import processing.core.PVector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MouseMagnetTest {

    public void assertEqualsPVector(PVector vector1, PVector vector2){
        try {
            assertEquals(vector1.x, vector2.x, 1E-5);
            assertEquals(vector1.y, vector2.y, 1E-5);
            assertEquals(vector1.z, vector2.z, 1E-5);
        }catch(AssertionFailedError e){
            System.out.println("Expected:" + vector1);
            System.out.println("Actual  :" + vector2);
            System.out.println();
            throw e;
        }
    }

    public void assertEqualsParticle(Particle part1, Particle part2){
        assertEqualsPVector(part1.coord, part2.coord);
        assertEqualsPVector(part1.absoluteCoord, part2.absoluteCoord);
        assertEquals(part1.charge, part2.charge);
        assertEquals(part1.mass, part2.mass);
    }

    public void assertEqualsParticle(Particle[] part1, Particle[] part2){
        assertEquals(part1.length, part2.length);
        for (int i = 0; i < part1.length; i++) {
            assertEqualsParticle(part1[i], part2[i]);
        }
    }

    @Test
    public void run() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };

        PApplet sketch = new PApplet();
        Magnet magnet = new MouseMagnet(particle, sketch, (s, m) -> { });

        magnet.setSpeed(1,1,1);
        sketch.mouseX = 1;
        sketch.mouseY = 1;

        magnet.run();

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };
        particleResult[0].absoluteCoord.set(+2,+2,+1);
        particleResult[1].absoluteCoord.set( 0, 0,-1);

        assertEqualsParticle(magnet.getParticle(), particleResult);
    }

    @Test
    public void runWidthVelocity() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };

        PApplet sketch = new PApplet();
        Magnet magnet = new MouseMagnet(particle, sketch, (s, m) -> { });

        magnet.setSpeed(-1,-1,1);
        magnet.setVelocity((float) Math.PI/2,0,0);
        sketch.mouseX = 1;
        sketch.mouseY = 1;

        magnet.run();

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };
        particleResult[0].absoluteCoord.set(2,0, 1);
        particleResult[1].absoluteCoord.set(0,2,-1);

        assertEqualsParticle(magnet.getParticle(), particleResult);
    }

}
