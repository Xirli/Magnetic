package model.magnet;

import org.junit.jupiter.api.Test;
import processing.core.PVector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParticleTest {

    @Test
    public void sqDist() {
        Particle part1 = new Particle(new PVector(0,0,0), 0 ,0);
        Particle part2 = new Particle(new PVector(3,4,12), 0 ,0);
        assertEquals(Particle.sqDist(part1, part2), 169);
    }

    @Test
    public void sqZeroDist() {
        Particle part1 = new Particle(new PVector(0,0,0), 0 ,0);
        Particle part2 = new Particle(new PVector(0,0,0), 0 ,0);
        assertEquals(Particle.sqDist(part1, part2), 0);
    }

}
