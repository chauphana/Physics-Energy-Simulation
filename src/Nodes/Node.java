package Nodes;


import Physics.PhysicsBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Node {

    //public BufferedImage sprite;
    public Point nodePosition;
    public Point nodeCenterPosition;
    public PhysicsBody physicsBody;
    public Rectangle hitBox;
    public Boolean isDragging;

    public String name = "";

    public Node(int x, int y) {
        //this.sprite = null;
        this.hitBox = new Rectangle(x, y, 0, 0);
        this.nodePosition = new Point(x, y);
        this.nodeCenterPosition = new Point(this.nodePosition.x + hitBox.width / 2, this.nodePosition.y + this.hitBox.height / 2);
        this.physicsBody = new PhysicsBody(10, this.nodeCenterPosition);
        this.isDragging = false;
    }

    public Boolean containsPoint(Point point) {
        return this.hitBox.contains(point);
    }


    public void update(int ticks) {
        this.physicsBody.update(ticks);
        this.physicsBody.updateVelocity(this, ticks);
        //this.spritePosition.y += this.physicsBody.yVelocity;
        //this.physicsBody.gravityForce(this.spritePosition);

        this.nodePosition.y = (int)this.physicsBody.gravityForce(this.nodePosition);
        this.hitBox.x = this.nodePosition.x;
        this.hitBox.y = this.nodePosition.y;
        this.nodeCenterPosition.x = this.nodePosition.x + hitBox.width / 2;
        this.nodeCenterPosition.y = this.nodePosition.y + this.hitBox.height / 2;

    }


    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(this.nodePosition.x - 10, this.nodePosition.y - 10,hitBox.width + 20, hitBox.height + 20);
        //change fillRect to just Rect to have invis rectangle
        //g.dispose();

    }


}
