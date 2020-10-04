import processing.core.PVector;

import static org.junit.jupiter.api.Assertions.*;

public class MagnetTest {

    public void assertEqualsParticle(Particle part1, Particle part2){
        assertEquals(part1.coord.x, part2.coord.x,1E-5);
        assertEquals(part1.coord.y, part2.coord.y,1E-5);
        assertEquals(part1.coord.z, part2.coord.z,1E-5);

        assertEquals(part1.charge, part2.charge);
        assertEquals(part1.mass, part2.mass);
    }

    @org.junit.jupiter.api.Test
    public void newMonopole() {
        Magnet monopole = Magnet.newMonopole(1, 2);

        Particle particle = new Particle(new PVector(0,0, 0), 1 ,2);

        assertEquals(monopole.getParticle().length, 1);
        assertEqualsParticle(monopole.getParticle()[0], particle);
    }

    @org.junit.jupiter.api.Test
    public void newDipole() {
        Magnet dipole = Magnet.newDipole(3, 1, 2);

        Particle[] particle = {
                new Particle(new PVector(3,0, 0), 1 ,2),
                new Particle(new PVector(-3,0, 0), -1 ,2),
        };

        assertEquals(dipole.getParticle().length, 2);
        for(int i = 0; i < 2; i++) {
            assertEqualsParticle(dipole.getParticle()[i], particle[i]);
        }
    }

    @org.junit.jupiter.api.Test
    public void newMultipole() {
        Magnet multipole = Magnet.newMultipole(4,3, 1, 2);

        Particle[] particle = {
                new Particle(new PVector(3,0, 0), 1 ,2),
                new Particle(new PVector(0,3, 0), -1 ,2),
                new Particle(new PVector(-3,0, 0), 1 ,2),
                new Particle(new PVector(0,-3, 0), -1 ,2),
        };

        assertEquals(multipole.getParticle().length, 4);
        for(int i = 0; i < 4; i++) {
            assertEqualsParticle(multipole.getParticle()[i], particle[i]);
        }
    }
}