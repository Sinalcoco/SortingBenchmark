package de.dhbw.sort.visualize;

import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * Created by jbi on 20.06.2017.
 */
public class Graphics {

    PGraphics graphics;

    public Graphics(PGraphics graphics) {
        this.graphics = graphics;

    }

    public void setGraphics(PGraphics graphics) {

        this.graphics = graphics;

    }
    public void drawRect(int x, int y, int width, int height){
        this.graphics.beginDraw();
        this.graphics.rect(x,y,width,height);
        this.graphics.endDraw();

    }

    public PGraphics getGraphics() {
        return this.graphics;
    }
}
