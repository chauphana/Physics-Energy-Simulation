package Nodes;

import java.awt.*;
import Engine.Engine;

public class SpringNode extends SpriteNode {

    public Engine e;

    public final int SPRING_CONSTANT = 30;
    public int maxSpringDisplacement = 200;
    public Point initialPosition;
    public Boolean isDragging;
    public double angle;


    public SpringNode(String imagePath, int x, int y, Engine e) {
        super(imagePath, x, y);
        this.initialPosition = new Point(this.nodePosition.x + this.hitBox.width / 2, this.nodePosition.y + this.hitBox.height / 2);
        //System.out.println("changechangechangechangechangechangechangechangechangechangechangechangechangechangechangechangechangechangechangechangechangechangechangechangechangechangechange");
        this.isDragging = false;
        this.e = e;
        this.angle = 0;
    }

    public void update(int ticks) {
        super.update(ticks);
        this.updateAngle();
    }

    //@Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.drawString("angle: "+ this.angle, 700, 100);
        super.render(g);
        //g.setColor(Color.white);

        //g.fillRect(400, 400, 300, 300);
        //System.out.println("he");
        //g.dispose();

    }

    public double calcDistanceFromEquilibrium() {
        double xDifference = e.inputHandler.mouseClickPos.x - this.initialPosition.x;
        double yDifference = e.inputHandler.mouseClickPos.y - this.initialPosition.y;
        double distance = Math.sqrt(xDifference * xDifference + yDifference * yDifference);

        return distance;
    }

    public void updateAngle() {
        if (this.isDragging || !this.isDragging) {
            //System.out.println("mousePos: " + e.inputHandler.mouseClickPos.toString() + "initialPos: " + this.initialPosition.toString());
            System.out.println("mousePos: " + this.nodeCenterPosition.toString() + "initialPos: " + this.initialPosition.toString());
            this.angle = Math.toDegrees(Math.atan2(this.nodeCenterPosition.y + this.initialPosition.y, this.nodeCenterPosition.x - this.initialPosition.x));
            System.out.println("dif: " + (this.nodeCenterPosition.y - this.initialPosition.y) + " " + (this.nodeCenterPosition.x - this.initialPosition.x));
            //System.out.println(Math.toDegrees(this.angle));
            System.out.println(this.angle);
        }

    }


}
