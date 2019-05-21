package Nodes;

import java.awt.*;
import Engine.Engine;

public class SpringNode extends SpriteNode {

    public final int SPRING_CONSTANT = 30;
    public int maxSpringDisplacement = 200;
    public Point initialPosition;
    public Boolean isDragging;
    public Engine e;

    public SpringNode(String imagePath, int x, int y, Engine e) {
        super(imagePath, x, y);
        this.initialPosition = new Point(x, y);
        this.isDragging = false;
        this.e = e;
    }

    public void update(int ticks) {
        super.update(ticks);
    }

    public void render(Graphics g) {
        super.render(g);
    }

    public double calcDistanceFromEquilibrium() {
        double xDifference = e.inputHandler.mouseClickPos.x - this.initialPosition.x;
        double yDifference = e.inputHandler.mouseClickPos.y - this.initialPosition.y;

        double distance = Math.sqrt(xDifference * xDifference + yDifference * yDifference);

        return distance;

    }


}
