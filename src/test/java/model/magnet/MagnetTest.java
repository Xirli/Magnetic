package model.magnet;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import processing.core.PVector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MagnetTest {

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
    public void newMagnet() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(4,4,4),1,3),
                new Particle(new PVector(0,0,0),1,1)
        };

        Magnet magnet = new Magnet(particle, (s, m) -> { });

        Particle[] particleResult = new Particle[]{
            new Particle(new PVector(4,4,4),1,3),
            new Particle(new PVector(0,0,0),1,1)
        };

        assertEqualsParticle(magnet.getParticle(), particleResult);
        assertEqualsPVector(magnet.getCoord(), new PVector(3,3,3));
        assertEquals(magnet.getMomentOfInertia(), 36);
    }

    @Test
    public void setGetCoord() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(-1,-1,-1),1,1),
                new Particle(new PVector(+1,+1,+1),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.setCoord(5,5,5);

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(4,4,4),1,1),
                new Particle(new PVector(6,6,6),1,1)
        };

        assertEqualsPVector(magnet.getCoord(), new PVector(5, 5, 5));
        assertEqualsParticle(magnet.getParticle(), particleResult);
    }

    @Test
    public void setGetSpeed() {
        Magnet magnet = new Magnet(new Particle[]{}, (s, m) -> { });

        magnet.setSpeed(1,2,3);

        assertEqualsPVector(magnet.getSpeed(), new PVector(1,2,3));
    }

    @Test
    public void rotateX() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.rotate((float) Math.PI/2, 0,0);

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(+1,-1,+1),1,1),
                new Particle(new PVector(-1,+1,-1),1,1)
        };

        assertEqualsParticle(magnet.getParticle(), particleResult);
    }

    @Test
    public void rotateY() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.rotate(0,(float) Math.PI/2,0);

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(+1,+1,-1),1,1),
                new Particle(new PVector(-1,-1,+1),1,1)
        };

        assertEqualsParticle(magnet.getParticle(), particleResult);
    }

    @Test
    public void rotateZ() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.rotate(0,0,(float) Math.PI/2);

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(-1,+1,+1),1,1),
                new Particle(new PVector(+1,-1,-1),1,1)
        };

        assertEqualsParticle(magnet.getParticle(), particleResult);
    }

    @Test
    public void rotateXY() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,0),1,1),
                new Particle(new PVector(-1,-1,0),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.rotate((float) (-Math.PI/2/Math.sqrt(2)),(float) (Math.PI/2/Math.sqrt(2)),0);

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(0,0,(float) (-1.0 * Math.sqrt(2))),1,1),
                new Particle(new PVector(0,0,(float) (+1.0 * Math.sqrt(2))),1,1)
        };

        assertEqualsParticle(magnet.getParticle(), particleResult);
    }

    @Test
    public void rotateXYZ() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(-1,+1,0),1,1),
                new Particle(new PVector(+1,-1,0),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.rotate((float) (Math.PI/Math.sqrt(3)),(float) (Math.PI/Math.sqrt(3)),(float) (Math.PI/Math.sqrt(3)));

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(+1,-1,0),1,1),
                new Particle(new PVector(-1,+1,0),1,1)
        };

        assertEqualsParticle(magnet.getParticle(), particleResult);
    }

    @Test
    public void rotateZero() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.rotate(0, 0,0);

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };

        assertEqualsParticle(magnet.getParticle(), particleResult);
    }

    @Test
    public void setGetVelocity() {
        Magnet magnet = new Magnet(new Particle[]{}, (s, m) -> { });

        magnet.setVelocity(1,2,3);

        assertEqualsPVector(magnet.getVelocity(), new PVector(1,2,3));
    }

    @Test
    public void run1step() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.setSpeed(1,1,1);
        magnet.setVelocity((float) Math.PI/2,0,0);

        magnet.run();

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(2,0,2),1,1),
                new Particle(new PVector(0,2,0),1,1)
        };

        assertEqualsParticle(magnet.getParticle(), particleResult);
    }

    @Test
    public void run2stepVelocity() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.setVelocity((float) Math.PI/2,0,0);

        magnet.run();

        magnet.setVelocity(0,(float) Math.PI/2,0);

        magnet.run();

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(+1,-1,-1),1,1),
                new Particle(new PVector(-1,+1,+1),1,1)
        };

        assertEqualsParticle(magnet.getParticle(), particleResult);
    }

    @Test
    public void run2step() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.setSpeed(1,1,1);
        magnet.setVelocity((float) Math.PI/2,0,0);

        magnet.run();

        magnet.setSpeed(2,2,2);
        magnet.setVelocity(0,(float) Math.PI/2,0);

        magnet.run();

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(4,2,2),1,1),
                new Particle(new PVector(2,4,4),1,1)
        };

        assertEqualsParticle(magnet.getParticle(), particleResult);
    }

}
