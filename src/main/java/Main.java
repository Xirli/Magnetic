import processing.core.PApplet;

public class Main{

    public static final MyApplet SKETCH = new MyApplet();

    public static void main(String[] args){
        String[] processingArgs = {"MySketch"};
        PApplet.runSketch(processingArgs, SKETCH);
    }
}