package Physics;
import java.awt.Point;
import java.util.ArrayList;


public class PhysicsBody extends PhysicsWorld {

    public double mass;
    public Boolean affectedByGravity;
    public Boolean allowRotation;
    public Boolean isPinned;
    public boolean isDynamic;

    public double xVelocity;
    public double yVelocity;

    public int categoryID = 999; //Numerical identifier for a node
    public ArrayList<Integer> contactID;
    public ArrayList<Integer> collision;

    public PhysicsBody (double mass) {
        super();
        this.mass = mass;
        this.affectedByGravity = true;
        this.allowRotation = true;
        this.isPinned = false;
        this.isDynamic = true;

        this.xVelocity = 0;
        this.yVelocity = 0;

    }

    public void update() {




    }

    public double gravityForce(Point initialPoint) {
        double finalY = initialPoint.y;
        if (this.affectedByGravity && isDynamic) {
            this.yVelocity += this.gravity;
            finalY = finalY + this.yVelocity;
            return finalY;
        } else {
            return initialPoint.y;
        }
    }

    public void updateVelocity() {

    }



}
