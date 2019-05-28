package Engine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import Nodes.*;

public class Engine extends Canvas implements Runnable{

    private final int WIDTH = 1200;
    private final int HEIGHT = 600;

    public Boolean running = false;
    public Graphics g;
    public BufferStrategy bs;
    public JFrame frame;
    public InputHandler inputHandler;

    public final ArrayList<Node> nodeList;

    public SpringNode spring;
    public Node groundNode;
    public BufferedImage background;



    public Engine() {

        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        frame = new JFrame("Conservation of Energy Simulation");
        frame.setSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        inputHandler = new InputHandler(this);

        nodeList = new ArrayList<Node>();

        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            bs = getBufferStrategy();
        }
        g = bs.getDrawGraphics();
        createNodes();
    }




    public static void main(String[] args) {
        new Engine().start();
    }

    public synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    public synchronized void stop() {
        running = false;
    }

    public void createNodes() {


        try {
            background = ImageIO.read(new FileInputStream("resources/raw background2.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        groundNode = new Node(-300,550);
        groundNode.physicsBody.categoryID = 2;
        groundNode.color = Color.pink;
        groundNode.hitBox.height = 50;
        groundNode.hitBox.width = 1500;
        nodeList.add(groundNode);

        spring = new SpringNode("resources/Sling.png", 105, 499, this);
        spring.name = "spring";
        spring.color = null;
        spring .drawHitbox = false;
        //spring.hitBox.width = 0;
        //spring.hitBox.height = 0;
        nodeList.add(spring);

        SpriteNode springSprite = new SpriteNode("resources/Slingshot.png", 100, 490);
        springSprite.physicsBody.isStatic = true;
        springSprite.color = null;
        springSprite.drawHitbox = false;
        springSprite.hitBox.width = 0;
        springSprite.hitBox.height = 0;
        nodeList.add(springSprite);




//        SpriteNode testNode1 = new SpriteNode("resources/flower.png", 400, 300);
//        testNode1.name = "flower";
//        testNode1.physicsBody.isStatic = false;
//        nodeList.add(testNode1);
//
//        testNode1 = new SpriteNode("resources/Earth.jpg", 600, 100);
//        testNode1.name = "earth";
//        testNode1.physicsBody.isStatic = false;
//        nodeList.add(testNode1);

//        Node baseNode = new Node(400, 500);
//        baseNode.name = "base";
//        baseNode.hitBox.width = 10;
//        baseNode.hitBox.height  = 20;
//        baseNode.physicsBody.isStatic = false;
//        nodeList.add(baseNode);
//
//        baseNode = new Node(1100, 50);
//        baseNode.physicsBody.affectedByGravity = false;
//        baseNode.name = "base2";
//        baseNode.hitBox.width = 100;
//        baseNode.hitBox.height  = 20;
//        baseNode.physicsBody.isStatic = false;
//        nodeList.add(baseNode);




    }



    @Override
    public void run() {

        long lastTime = System.nanoTime();
        double nsecPerTick = 1000000000 / 20D; //  ticks per second as a double
        int ticks = 0;
        int frames = 0;
        long lastTimer = System.currentTimeMillis();
        double deltaTime = 0;
        while (running) {

            long now = System.nanoTime();
            deltaTime += (now - lastTime) / nsecPerTick;
            lastTime = now;
            boolean shouldRender = true;
            while (deltaTime >= 1) {
                ticks++;
                tick(ticks);
                deltaTime -= 1;
                shouldRender = true;
            }

            if (shouldRender) {
                frames++;
                render(ticks);
            }

            /*
             * THIS CONTROLS PER SECOND RATE
             * RESETTING FRAMES AND TICKS AFTER
             * 1000ms HAS PASSED FROM THE BEGINNING OF THE
             * LOOP
             */
            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                //System.out.println("Frames: " + frames + " Ticks: " + ticks);
                frames = 0;
                ticks = 0;
            }
        }
    }

    public void tick(int ticks) {
        for (Node item : nodeList) {
            item.update(ticks);

            if (item.name == "base2" && (item.nodePosition.y > this.getHeight())) {
                item.nodePosition.y = 50;
                item.physicsBody.yVelocity = 0;
            }
        }
        checkCollisions();
    }

    public void checkCollisions() {
        Node nodeA;
        Node nodeB;
        for(int indexA = 0; indexA < nodeList.size(); indexA++) {
            nodeA = nodeList.get(indexA);
            for(int indexB = indexA + 1; indexB < nodeList.size(); indexB++) {
                nodeB = nodeList.get(indexB);
                if (nodeA.hitBox.intersects(nodeB.hitBox)) {
                   //System.out.println("COLLIDED");
                }

            }
        }
    }

    public void render(int ticks) {
        g = bs.getDrawGraphics();
        //g.setColor(Color.cyan);
       // g.fillRect(0,0, WIDTH, HEIGHT);

        if (background != null) {
            g.drawImage(background,0,0, null);
        }


        renderEnergyBars();


        for (Node item : nodeList) {
            //System.out.println(test);
            item.render(g);
            //test++;
        }
       // repaint();

        g.dispose();
        bs.show();


    }

//    double maxPE = 0;
//    double maxKE = 0;
//    double maxHeight = 0;
//    double lowestKE = 999999999;

    public void renderEnergyBars() {
        double KE = 0;
        double PE = 0;
        double height = 0;
        

        g.setColor(Color.black);

        g.drawString("PEs: " + (int)spring.physicsBody.getSpringEnergy(spring), 22, 320);
        g.setColor(Color.pink);
        g.fillRect(30,300,50,-(int)spring.physicsBody.getSpringEnergy(spring) / 10);


        if (spring.projectileList.size() > 0) {
            int lastProjIndex = spring.projectileList.size() - 1;
            int mass = (int)spring.projectileList.get(lastProjIndex).physicsBody.mass;
            height = (spring.nodeCenterPosition.y - spring.projectileList.get(lastProjIndex).nodePosition.y) / 10; //CONVERTED TO METERS
            g.drawString("HEIGHT: " + height, 400, 126);
            PE =  mass * .49 * height;


            //g.fillRect(170,300,50,-(int)PE/10);

            double vMag = Math.sqrt(spring.projectileList.get(lastProjIndex).physicsBody.yVelocity * spring.projectileList.get(lastProjIndex).physicsBody.yVelocity + spring.projectileList.get(lastProjIndex).physicsBody.xVelocity * spring.projectileList.get(lastProjIndex).physicsBody.xVelocity);

            KE = (.5 * spring.projectileList.get(lastProjIndex).physicsBody.mass * (vMag / 10) * (vMag / 10));

            //PE -= KE;
            g.setColor(Color.magenta);
            g.fillRect(170,300,50,-(int)PE/10);
            g.setColor(Color.red);
            g.fillRect(100,300,50,-(int)KE/10);


        }
        //g.drawString("PE + KE: " + (PE + KE), 400, 150);

        String formattedKE = String.format("%.00f", KE);
        g.setColor(Color.black);
        g.drawString("KE: " + formattedKE, 100, 320);
        g.drawString("PEg: " + PE, 162, 320);

//        if (KE > maxKE) {
//            maxKE = KE;
//        }
//        if (KE < lowestKE && KE != 0) {
//            lowestKE = KE;
//        }
//
//        if (PE > maxPE) {
//            maxPE = PE;
//        }
//        if (height > maxHeight) {
//            maxHeight = height;
//        }

//        g.drawString("MAX KE" + maxKE, 800, 125);
//
//        g.drawString("MAX PE" + maxPE, 800, 150);
//        g.drawString("MAX HEIGHT: " + maxHeight, 800, 175);
//        g.drawString("LOWEST KE: " + lowestKE, 800, 200);



    }

}
