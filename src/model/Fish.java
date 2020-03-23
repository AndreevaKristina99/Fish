package model;

import javafx.scene.canvas.GraphicsContext;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.ArrayList;

public class Fish  implements  Runnable {
    private Thread thread;
    private GraphicsContext gr;
    private boolean flagX = true;
    private boolean flagY = true;
    private Image bgImage;
    private int i = 0;
    private ArrayList<FishFood> fishFoods;

    public Fish(Thread thread, GraphicsContext gr, boolean flagX, boolean flagY, Image bgImage, int i, ArrayList<FishFood> fishFoods) {
        thread = new Thread(this);
        this.gr = gr;
        this.flagX = flagX;
        this.flagY = flagY;
        this.bgImage = bgImage;
        this.i = i;
        this.fishFoods = fishFoods;
    }

    public Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double canW, canH;
            synchronized (gr) {
                canH = gr.getCanvas().getHeight();
                canW = gr.getCanvas().getWidth();
                if (i == 25) {
                    i = 0;
                    flagY = !flagY;
                }
                gr.setFill(super.getFill());
                gr.fillOval(getX(), getY(), getWidth(), getHeight());
            }
            synchronized (fishFoods) {
                for (int j = 0; j < fishFoods.size(); j++) {
                    foodX = fishFoods.get(j).getX();
                    foodY = fishFoods.get(j).getY();
                    synchronized (fishFoods) {
                        foodWidth = fishFoods.get(j).getWidth();
                        foodHeight = fishFoods.get(j).getHeight();
                    }
                    if (this.getY() <= foodY + foodHeight &&
                            this.getY() + this.getWidth() >= foodY &&
                            this.getX() <= foodX + foodWidth &&
                            this.getX() + this.getHeight() >= foodX
                    ) {
                        if ((this.getWidth() < canWidth) && (this.getHeight() < canHeigth)) {
                            this.setWidth(this.getWidth() * 1.1);
                            this.setHeight(this.getHeight() * 1.1);
                        }
                        synchronized (fishFoods) {
                            fishFoods.get(j).stop();
                            fishFoods.remove(j);
                        }
                    }
                }
            }
            if (flagX) {
                if (super.getX() <= canWidth - super.getWidth())
                    super.setX(super.getX() + 1);
                else
                    flagX = false;
            }
            if (!flagX) {
                if (super.getX() >= 0)
                    super.setX(super.getX() - 1);
                else
                    flagX = true;
            }
            if (flagY) {
                if (super.getY() <= canHeigth - super.getHeight())
                    super.setY(super.getY() + 1);
                else
                    flagY = false;
            }
            if (!flagY) {
                if (super.getY() >= 0)
                    super.setY(super.getY() - 1);
                else
                    flagY = true;
            }
            i++;
        }
    }


    public void startFish() {
        thread.start();
    }

    public void stopFish() {
        thread.stop();
    }

    public void addFood(ArrayList<FishFood> fishFoods) {
        this.fishFoods = fishFoods;
    }
}
