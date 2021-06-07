package view;

import model.magnet.Magnet;
import processing.core.PApplet;

public interface GraphicObject {
    void draw(PApplet sketch, Magnet magnet);
}
