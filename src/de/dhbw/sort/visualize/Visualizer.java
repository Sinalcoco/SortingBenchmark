package de.dhbw.sort.visualize;

import de.dhbw.sort.util.AbstractAlgorithmHelper;
import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * Created by jbi on 13.06.2017.
 */
public class Visualizer extends PApplet {
    int fullScreen;

    private int width = 200;
    private int height = 600;
    private int grid1 = 3;
    private int grid2 = 3;
    private int fWidth;
    private int fHeight;

    private PGraphics[] gs;

    public Visualizer(int width, int height, int grid1, int grid2) {
        this.width = width;
        this.height = height;
        this.grid1 = grid1;
        this.grid2 = grid2;

        this.fWidth = width / grid1;
        this.fHeight = height / grid2;

        gs = new PGraphics[grid1 * grid2];
        fullScreen = -1;

    }
    public PGraphics getScreen(int index) {
        return gs[index];
    }
    public void addAlgorithemHelper(AbstractAlgorithmHelper helper) {
    }


    public void initScreens() {

        for (int i = 0; i < gs.length; i++)
            {
                gs[i] = createGraphics(fWidth, fHeight);
            }
    }

    public void setup() {
        surface.setResizable(true);
        surface.setSize(width, height);
        initScreens();
    }

    public void settings() {
        size(width, height);
    }

    public void mousePressed() {
        //Ordne dem Mausclick ein Fenster zu und setzte es auf fullscreen.
        if (fullScreen == -1)
            {
                if (mouseX < fWidth / 2)
                    {
                        if (mouseY < fHeight)
                            {
                                fullScreen = 0;
                            } else
                            {
                                fullScreen = 2;
                            }
                    } else
                    {
                        if (mouseY < fHeight / 2)
                            {
                                fullScreen = 1;
                            } else
                            {
                                fullScreen = 3;
                            }
                    }
            } else
            {
                //War schon eins Vollbild setze die ansicht wieder zurück
                fullScreen = -1;
                initScreens();
            }
    }

    public void draw() {
        //Wenn keines im fullScreen Modus ist zeichne einfach alle normal
        if (fullScreen == -1)
            {
                for (int i = 0; i < gs.length; i++)
                    {
                        image(gs[i], (i % grid2) * (fWidth), (i / grid1) * (fHeight));
                    }
            } else
            {
                //Eines wurde als fullScreen ausgewählt
                //Ist es der erste Frame nach der Auswahl, erstelle eine fullscreen-Grafik
                if (gs[fullScreen].width < width || gs[fullScreen].height < height)
                    {
                        gs[fullScreen] = createGraphics(width, height);
                    } else
                    {
                        //In jedem Folgeframe zeichne nur das fullscreen fenster
                        image(gs[fullScreen], 0, 0);
                    }
            }
    }
}
