package controller;

import model.magnet.Magnet;
import model.magnet.MagnetGenerator;
import model.service.Physics;
import view.GraphicGenerator;

import java.util.ArrayList;

import static controller.ParamController.msPerUpdateModel;
import static java.lang.Thread.sleep;

public class PhysicsController {

    private MyApplet sketch;
    Physics physics;
    private final ArrayList<Magnet> magnets;
    private boolean doing;
    private Thread thread;

    public PhysicsController(){
        magnets = new ArrayList<>();
        physics = new Physics(magnets);
        Runnable task = this::updateLoop;
        thread = new Thread(task);
    }

    public void setSketch(MyApplet sketch){
        this.sketch = sketch;
    }

    public void updateLoop() {
        while (doing)
        {
            long start = System.currentTimeMillis();
            physics.multiUpdate();
            //physics.update();
            try {
                long timeSleep = start + msPerUpdateModel - System.currentTimeMillis();
                if(timeSleep > 0) sleep(timeSleep);
            } catch(InterruptedException ignored){}
        }
    }

    public void start(int type){
        stop();
        magnets.clear();
        switch (type) {
            case 1 -> magnets.addAll(new ArrayList<>(MagnetGenerator.generateDipole(GraphicGenerator.blackWhiteCircle())));
            case 2 -> magnets.addAll(new ArrayList<>(MagnetGenerator.generateParaMagnetic(GraphicGenerator.blackWhiteCircle())));
        }
        run();
    }

    public void changeRunPause(){
        if(doing){
            stop();
        }
        else{
            run();
        }
    }

    public void run(){
        if(doing) return;
        doing = true;

        Runnable task = this::updateLoop;
        thread = new Thread(task);
        thread.start();
    }

    public void stop(){
        doing = false;
        try{
          thread.join();
        } catch(InterruptedException ignored){}
    }

    public ArrayList<Magnet> getMagnets(){
        return magnets;
    }
}
