package model.magnet;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import processing.core.PVector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MagnetGeneratorTest {

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
    public void newMonopole() {
        Particle[] monopole = MagnetGenerator.monopole(2, 3);

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(0,0,0),2,3)
        };

        assertEqualsParticle(monopole, particleResult);
    }

    @Test
    public void newDipole() {
        Particle[] dipole = MagnetGenerator.dipole(1,2, 3);

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(+1,0,0),+2,3),
                new Particle(new PVector(-1,0,0),-2,3)
        };

        assertEqualsParticle(dipole, particleResult);
    }

    @Test
    public void newMultipole() {
        Particle[] dipole = MagnetGenerator.multipole(4,1, 2, 3);

        Particle[] particleResult = {
                new Particle(new PVector( 1, 0, 0), +2 ,3),
                new Particle(new PVector( 0, 1, 0), -2 ,3),
                new Particle(new PVector(-1, 0, 0), +2 ,3),
                new Particle(new PVector( 0,-1, 0), -2 ,3)
        };

        assertEqualsParticle(dipole, particleResult);
    }
}
