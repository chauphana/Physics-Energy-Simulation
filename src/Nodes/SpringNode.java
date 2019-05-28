package Nodes;

import java.awt.*;
import Engine.Engine;
import java.util.ArrayList;

public class SpringNode extends SpriteNode {

    public Engine e;

    public final int SPRING_CONSTANT = 30;
    public int maxSpringDisplacement = 100;
    public Point initialPosition;
    //public Boolean isDragging;
    public double angle;
    public final ArrayList<Node> projectileList;
    public double projectileMass;


    public SpringNode(String imagePath, int x, int y, Engine e) {
        super(imagePath, x, y);
        this.initialPosition = new Point(this.nodePosition.x + this.hitBox.width / 2, this.nodePosition.y + this.hitBox.height / 2);
        //this.isDragging = false;
        this.physicsBody.isStatic = false;
        this.e = e;
        this.angle = 0;

        projectileList = new ArrayList<Node>();
        projectileMass = 50;

    }

    public void launchProjectile(double velMagnitude) {
       double launchAngle = -this.angle;
       SpriteNode projectile = new SpriteNode("resources/rock.png",this.initialPosition.x - 15 , this.initialPosition.y + 15);
       projectile.hitBox.width = 30;
       projectile.hitBox.height = 30;
       projectile.color = null;
       projectile.drawHitbox = false;
       projectile.physicsBody.categoryID = 1;
       projectile.physicsBody.velocity = velMagnitude;
       //projectile.physicsBody.yVelocity = 90;
       System.out.println("xVel: " + projectile.physicsBody.velocity * Math.cos(3.14159 - this.angle));
       System.out.println("yVel: " + -projectile.physicsBody.velocity * Math.sin(3.14159 - this.angle));
       projectile.physicsBody.xVelocity = projectile.physicsBody.velocity * Math.cos(3.14159 - this.angle);
       projectile.physicsBody.yVelocity = -projectile.physicsBody.velocity * Math.sin(3.14159 - this.angle);
       projectile.physicsBody.mass = projectileMass;

       projectile.physicsBody.affectedByGravity = true;

       projectileList.add(projectile);

//       Node copy = projectile;
//       copy.physicsBody.affectedByGravity = false;
//       copy.physicsBody.isStatic = false;
//       copy.nodePosition.x = 100;
//        copy.nodePosition.y = 100;
//        copy.physicsBody.xVelocity = 0;
//        projectileList.add(copy);
//        e.nodeList.add(copy);

    }


    public void update(int ticks) {
        super.update(ticks);
        this.updateAngle();

        for (Node node : projectileList) {
            node.update(ticks);
            node.physicsBody.updatePosWithVelocity(node);
            if (node.nodeCenterPosition.y > e.spring.nodeCenterPosition.y) {
                projectileList.remove(node);

                break;
            }
        }
    }

    //@Override
    public void render(Graphics g) {
        super.render(g);
        g.setColor(Color.gray);
        g.drawLine(this.initialPosition.x, this.initialPosition.y, this.nodeCenterPosition.x, this.nodeCenterPosition.y);
        g.setColor(Color.black);
        g.drawString("Launch Angle: "+ Math.toDegrees(3.14 - this.angle), 22, 100);
        if (this.calcDistanceFromEquilibrium() > maxSpringDisplacement) {
            g.drawString("Displacement: "+ maxSpringDisplacement / 10 + "m", 22, 125);
        } else {
            g.drawString("Displacement: "+ this.calcDistanceFromEquilibrium() / 10 + "m", 22, 125);
        }

        for (Node node : projectileList) {
            node.render(g);
        }
    }

    public double calcDistanceFromEquilibrium() {
        if (e.inputHandler.selectedNode == this) {
            double xDifference = e.inputHandler.mouseClickPos.x - this.initialPosition.x;
            double yDifference = e.inputHandler.mouseClickPos.y - this.initialPosition.y;
            double distance = Math.sqrt(xDifference * xDifference + yDifference * yDifference);

            return distance;
        } else {
            return 0;
        }
    }

    public void updateAngle() {
        if (this.isDragging) {
            this.angle = Math.atan2(this.nodeCenterPosition.y - this.initialPosition.y, this.nodeCenterPosition.x - this.initialPosition.x);
        }
    }
}
