package Engine;

//import Utility.Point;
import Engine.Engine;

import java.awt.Point;
        import java.awt.event.*;


public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

    public boolean facingRight;
    public boolean isWalking;
    public Point mouseClickPos;
    public Point playerPos;
    public Point slopePoint;
    public Point cMoneyLoc;
    public Point vaultLoc;
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

        System.out.println("crated input handler");

    }

    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        toggleKey(e.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent e) {
        //engine.player.isWalking = false;
        toggleKey(e.getKeyCode(), false);
    }

    public void keyTyped(KeyEvent arg0) {
    }

    // public void checkInput() {
    // /*
    // * All if statements so it can handle
    // * diagonal movement.
    // */
    // if (game.input.up.isPressed()) {
    // game.player.y -= game.player.speed;
    // }
    // if (game.input.down.isPressed()) {
    // game.player.y += game.player.speed;
    // }
    // if (game.input.left.isPressed()) {
    // game.player.x -= game.player.speed;
    // }
    // if (game.input.right.isPressed()) {
    // game.player.x += game.player.speed;
    //
    // }
    // }

    public void checkInput() {
        //System.out.println(game.level.levelStage);
        //System.out.println("pos; " + game.player.x + ", " + game.player.y);
        if (this.up.isPressed()) {

        }
        if (this.down.isPressed()) {

        }
        if (this.left.isPressed()) {

        }

    }

    public void toggleKey(int keyCode, boolean state) {

        if (keyCode == KeyEvent.VK_W) {


        }
        if (keyCode == KeyEvent.VK_S) {

        }
        if (keyCode == KeyEvent.VK_A) {

        }
        if (keyCode == KeyEvent.VK_D) {

        }
        if (keyCode == KeyEvent.VK_SPACE) {

        }
    }


    public void mouseClicked(MouseEvent e) {
//        mouseClickPos = new Point(e.getX(), e.getY());
//        System.out.println("Mouse Clicked at: " + e.getX() + ", " + e.getY());
//        this.isDragging = true;
//        clickedSpring(mouseClickPos);




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

    }

    public void mouseReleased(MouseEvent e) {
        if (engine.spring.isDragging) {
            engine.spring.isDragging = false;
            engine.spring.nodePosition = engine.spring.initialPosition;

        }
    }

    public void mouseEntered(MouseEvent e) {
        System.out.println("entered");
    }

    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseClickPos = new Point(e.getX(), e.getY());
        //System.out.println("draggggg");
        if (springContainsPoint(mouseClickPos) || engine.spring.isDragging) {
            engine.spring.isDragging = true;
            if (engine.spring.calcDistanceFromEquilibrium() <= 500) {
                Point adjustedPoint = new Point();
                adjustedPoint.x = mouseClickPos.x - (engine.spring.sprite.getWidth() / 2);
                adjustedPoint.y = mouseClickPos.y - (engine.spring.sprite.getHeight() / 2);
                engine.spring.nodePosition = adjustedPoint;
            }



        }




    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("movingggg");
    }

    public boolean springContainsPoint(Point mouseClickPos) {
        return engine.spring.hitBox.contains(mouseClickPos);
    }


}


