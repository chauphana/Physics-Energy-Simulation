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
    public PhysicsBody physicsBody;
    public Rectangle hitBox;
    public String name = "";

    public Node(int x, int y) {
        //this.sprite = null;
        hitBox = new Rectangle(x, y, 0, 0);
        nodePosition = new Point(x, y);
        physicsBody = new PhysicsBody(10);

    }

    public void update(int ticks) {
        this.physicsBody.update();
        //this.spritePosition.y += this.physicsBody.yVelocity;
        //this.physicsBody.gravityForce(this.spritePosition);
        this.nodePosition.y = (int)this.physicsBody.gravityForce(this.nodePosition);
        this.hitBox.x = this.nodePosition.x;
        this.hitBox.y = this.nodePosition.y;
    }


    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(this.nodePosition.x - 10, this.nodePosition.y - 10,hitBox.width + 20, hitBox.height + 20);

        //g.dispose();

    }


}
