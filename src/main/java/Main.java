import processing.core.PApplet;

public class Main{

    private static final MyApplet SKETCH = new MyApplet();

    public static void main(String[] args){
        String[] processingArgs = {"MySketch"};
        PApplet.runSketch(processingArgs, SKETCH);
    }
}