package model.magnet;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import processing.core.PVector;

import static model.ParamModelTest.accuracy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MagnetGeneratorTest {

    public void assertEqualsPVector(PVector expected, PVector actual){
        try {
            assertEquals(expected.x, actual.x, accuracy);
            assertEquals(expected.y, actual.y, accuracy);
            assertEquals(expected.z, actual.z, accuracy);
        }catch(AssertionFailedError e){
            System.out.println("Expected:" + expected);
            System.out.println("Actual  :" + actual);
            System.out.println();
            throw e;
        }
    }

    public void assertEqualsParticle(Particle expected, Particle actual){
        assertEqualsPVector(expected.absoluteCoord, actual.absoluteCoord);
        assertEquals(expected.charge, actual.charge);
        assertEquals(expected.mass, actual.mass);
    }

    public void assertEqualsParticle(Particle[] expected, Particle[] actual){
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertEqualsParticle(expected[i], actual[i]);
        }
    }

    @Test
    public void newMonopole() {
        Particle[] monopole = MagnetGenerator.monopole(2, 3);

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(0,0,0),2,3)
        };

        assertEqualsParticle(particleResult, monopole);
    }

    @Test
    public void newDipole() {
        Particle[] dipole = MagnetGenerator.dipole(1,2, 3);

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(+1,0,0),+2,3),
                new Particle(new PVector(-1,0,0),-2,3)
        };

        assertEqualsParticle(particleResult, dipole);
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

        assertEqualsParticle(particleResult, dipole);
    }
}
