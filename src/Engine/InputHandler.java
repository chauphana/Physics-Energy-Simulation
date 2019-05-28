package Engine;

//import Utility.Point;
import Engine.Engine;
import Nodes.*;

import javax.swing.*;
import java.awt.Point;
import java.awt.event.*;


public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

    public boolean facingRight;
    public boolean isWalking;
    public Point mouseClickPos;
    public Point mousePos;
    public Point playerPos;
    public Point slopePoint;
    public Point cMoneyLoc;
    public Point vaultLoc;
    public Node selectedNode;
    public Boolean isDragging = false;

    public int index;



    private Engine engine;
    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key space = new Key();

    public InputHandler(Engine e) {
        engine = e;
        engine.addKeyListener(this);
        engine.addMouseListener(this);
        engine.addMouseMotionListener(this);

        this.mouseClickPos = new Point();

        System.out.println("crated input handler");

    }

    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        //toggleKey(e.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent e) {
        //engine.player.isWalking = false;
        //toggleKey(e.getKeyCode(), false);
    }

    public void keyTyped(KeyEvent arg0) {
    }



    public void mouseClicked(MouseEvent e) {
        mouseClickPos = new Point(e.getX(), e.getY());
        System.out.println("Mouse Clicked at: " + e.getX() + ", " + e.getY());





    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Point getMouseClickPos() {
        return mouseClickPos;
    }

    public void setMouseClickPos(Point mouseClickPos) {
        this.mouseClickPos = mouseClickPos;
    }

    // 992, 157
    public Engine getGame() {
        return engine;
    }

    public void setGame(Engine game) {
        this.engine = game;
    }

    public void mousePressed(MouseEvent e) {
        mouseClickPos = new Point(e.getX(), e.getY());
        System.out.println("Mouse Clicked at: " + e.getX() + ", " + e.getY());
        //this.isDragging = true;
        //clickedSpring(mouseClickPos);

        for (Node node : engine.nodeList) {
            if (node.hitBox.contains(mouseClickPos) && !node.physicsBody.isStatic) {
                this.selectedNode = node;
                System.out.println("selected node " + selectedNode.name);
            }
            if (!node.physicsBody.isStatic) {
                node.isDragging = true;
            }
        }

    }

    public void mouseReleased(MouseEvent e) {
        for (Node node : engine.nodeList) {
            node.isDragging = false;
            if (node.name == "spring" && this.selectedNode == node) {
                Point adjustedPoint = new Point();
                adjustedPoint.x = engine.spring.initialPosition.x - (engine.spring.sprite.getWidth() / 2);
                adjustedPoint.y = engine.spring.initialPosition.y - (engine.spring.sprite.getHeight() / 2);
                engine.spring.nodePosition = adjustedPoint;
                if (node instanceof SpringNode) {
                    //double x = ((SpringNode) node).calcDistanceFromEquilibrium();
                    double velMagnitude = node.physicsBody.vFromPEsToKEPEg(node, engine.groundNode);
                    System.out.println("velMag: " + velMagnitude);
                    ((SpringNode) node).launchProjectile(velMagnitude);

                }
            }
        }
        this.selectedNode = null;
    }

    public void mouseEntered(MouseEvent e) {
        System.out.println("entered");
    }

    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseClickPos = new Point(e.getX(), e.getY());

        for (Node node : engine.nodeList) {
            if ((node.isDragging || node.containsPoint(mouseClickPos)) && node.equals(selectedNode) ) {
                node.isDragging = true;
                Point adjustedPoint = new Point();
                adjustedPoint.x = mouseClickPos.x - (node.hitBox.width / 2);
                adjustedPoint.y = mouseClickPos.y - (node.hitBox.height / 2);

                if (node instanceof SpringNode) {
                    if (((SpringNode) node).calcDistanceFromEquilibrium() <= 120) {
                        node.nodePosition = adjustedPoint;
                    }
                } else {
                    node.nodePosition = adjustedPoint;
                }
            }
        }




    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.mousePos = new Point(e.getX(), e.getY());
    }




}


