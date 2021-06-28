package model.service;

import model.magnet.Magnet;
import model.magnet.Particle;
import processing.core.PVector;

import java.util.ArrayList;

import static controller.ParamController.countThread;
import static model.ParamModel.*;

public class Physics {

    Thread[] threads;
    Runnable task = this::getTask;

    private final ArrayList<Magnet> magnets;
    private int incrementA, incrementB;

    public Physics(ArrayList<Magnet> magnets){
        threads = new Thread[countThread];
        this.magnets = magnets;
    }

    public void update(){
        for(Magnet mag : magnets) {
            mag.run();
            mag.setVelocity(PVector.mult(mag.getVelocity(), rotationSlowdown));
        }

        for(Magnet mag1 : magnets) {
            for(Magnet mag2 : magnets) {
                if(mag1 == mag2)break;
                gravity(mag1, mag2);
            }
        }
    }

    public void multiUpdate() {
        for(Magnet mag : magnets) {
            mag.run();
            mag.setVelocity(PVector.mult(mag.getVelocity(), rotationSlowdown));
        }

        incrementA = 0;
        incrementB = 0;

        for (int i = 0; i < countThread; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }
        try {
            for (int i = 0; i < countThread; i++) {
                threads[i].join();
            }
        } catch(InterruptedException ignored){}
    }

    public void getTask(){
        while(true){
            int valA, valB;
            synchronized (this){
                incrementB++;
                if(incrementB == magnets.size()){
                    incrementA++;
                    incrementB = incrementA+1;
                }
                if(incrementA == magnets.size()-1) return;
                valA = incrementA;
                valB = incrementB;
            }
            //System.out.println("A = " + valA + "; B = " + valB + ";");
            gravity(magnets.get(valA), magnets.get(valB));
        }
    }

    public static void gravity(Magnet mag1, Magnet mag2){
        for(Particle part1 : mag1.getParticle()){
            for(Particle part2 : mag2.getParticle()){
                PVector force = forceGravity(part1, part2);
                forceDisplay(mag1, force, part1);
                force.mult(-1);
                forceDisplay(mag2, force, part2);
            }
        }
    }

    public static PVector forceGravity(Particle part1, Particle part2){
        PVector vector = new PVector();
        vector.set(part2.absoluteCoord).mult(-1).add(part1.absoluteCoord);
        float F = kulon * part1.charge * part2.charge / Math.max(vector.magSq(), minRadiusOfParticle*minRadiusOfParticle);
        vector.normalize();
        vector.mult(F);
        return vector;
    }

    public static void forceDisplay(Magnet mag, PVector force, Particle part){
        PVector acceleration = PVector.div(force, mag.getMass());
        synchronized (mag) {
            mag.setSpeed(PVector.add(mag.getSpeed(), acceleration));
        }
        if(mag.getMomentOfInertia() < accuracy) return;

        PVector moment = force.cross(PVector.add(part.absoluteCoord, PVector.mult(mag.getCoord(),-1)));
        synchronized (mag) {
            mag.setVelocity( PVector.add(mag.getVelocity(), moment.div(-mag.getMomentOfInertia())) );
        }
    }

}
