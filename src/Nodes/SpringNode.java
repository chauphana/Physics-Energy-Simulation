package Nodes;

import java.awt.*;
import Engine.Engine;
import java.util.ArrayList;

public class SpringNode extends SpriteNode {

    public Engine e;

    public final int SPRING_CONSTANT = 30;
    public int maxSpringDisplacement = 200;
    public Point initialPosition;
    //public Boolean isDragging;
    public double angle;
    public final ArrayList<Node> projectileList;


    public SpringNode(String imagePath, int x, int y, Engine e) {
        super(imagePath, x, y);
        this.initialPosition = new Point(this.nodePosition.x + this.hitBox.width / 2, this.nodePosition.y + this.hitBox.height / 2);
        //this.isDragging = false;
        this.physicsBody.isStatic = false;
        this.e = e;
        this.angle = 0;

        projectileList = new ArrayList<Node>();

    }

    public void launchProjectile() {
       double launchAngle = -this.angle;
       Node projectile = new Node(this.nodeCenterPosition.x , this.nodeCenterPosition.y);
       projectile.hitBox.width = 30;
       projectile.hitBox.height = 30;
       projectile.color = Color.blue;

       //projectile.physicsBody.yVelocity = 90;
       System.out.println("xVel: " + projectile.physicsBody.velocity * Math.cos(3.14159 - this.angle));
       System.out.println("yVel: " + projectile.physicsBody.velocity * Math.sin(3.14159 - this.angle));
       projectile.physicsBody.xVelocity = projectile.physicsBody.velocity * Math.cos(3.14159 - this.angle);
       projectile.physicsBody.yVelocity = -projectile.physicsBody.velocity * Math.sin(3.14159 - this.angle);
       projectile.physicsBody.mass = 50;
       projectile.physicsBody.affectedByGravity = true;

       projectileList.add(projectile);

    }


    public void update(int ticks) {
        super.update(ticks);
        this.updateAngle();

        for (Node node : projectileList) {
            node.update(ticks);
            node.physicsBody.updatePosWithVelocity(node);
        }
    }

    //@Override
    public void render(Graphics g) {
        super.render(g);
        g.setColor(Color.GREEN);
        g.drawLine(this.initialPosition.x, this.initialPosition.y, this.nodeCenterPosition.x, this.nodeCenterPosition.y);
        g.setColor(Color.red);
        g.drawString("angle: "+ Math.toDegrees(this.angle), 700, 100);


        for (Node node : projectileList) {
            node.render(g);
        }


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
        if (this.isDragging) {
            //System.out.println("mousePos: " + e.inputHandler.mouseClickPos.toString() + "initialPos: " + this.initialPosition.toString());
            //System.out.println("mousePos: " + this.nodeCenterPosition.toString() + "initialPos: " + this.initialPosition.toString());
            this.angle = Math.atan2(this.nodeCenterPosition.y - this.initialPosition.y, this.nodeCenterPosition.x - this.initialPosition.x);
//            System.out.println("dif: " + (this.nodeCenterPosition.y - this.initialPosition.y) + " " + (this.nodeCenterPosition.x - this.initialPosition.x));
//            System.out.println( "deg: " + Math.toDegrees(this.angle));
//            System.out.println(this.angle);
        }

    }


}
