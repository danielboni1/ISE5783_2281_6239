package geometries;
import primitives.*;

import java.util.List;

/**
 * Helper class that should represent a table in a scene
 */
public class Table {
    /************* Color of the table       *************************/
    private Color color;
    /************* Size of the table        *************************/
    private double size;

    /************* Center point of the table*************************/
    private Point center;
    /************* Vector of the table      *************************/
    private Vector dir;

    /************* Surface of the table     *************************/
    private Cylinder surface;

    /************* First right leg          *************************/
    private Cylinder rightLeg1;

    /************* Second right leg         *************************/
    private Cylinder rightLeg2;

    /************* First left leg           *************************/
    private Cylinder leftLeg1;

    /************* Second left leg          *************************/
    private Cylinder leftLeg2;

    /************* Builder Pattern Setters  *************************/ //should delete this...
    public Table setSize(double size) {
        this.size = size;
        return this;
    }

    public Table setSurface(Cylinder surface) {
        this.surface = surface;
        return this;
    }

    public Table setRightLeg1(Cylinder rLeg1) {
        this.rightLeg1 = rLeg1;
        return this;
    }

    public Table setRightLeg2(Cylinder rLeg2) {
        this.rightLeg2 = rLeg2;
        return this;
    }

    public Table setLeftLeg1(Cylinder lLeg1) {
        this.leftLeg1 = lLeg1;
        return this;
    }

    public Table setLeftLeg2(Cylinder lLeg2) {
        this.leftLeg2 = lLeg2;
        return this;
    }


    public Table(Point cenPoint, Vector normalToSurface, double sizeOfTable, Color colorOfTable){
        center = cenPoint;
        dir = normalToSurface;
        size = sizeOfTable;
        color = colorOfTable; // maybe not necessary field...todo
        surface = new Cylinder(size, new Ray(center, dir), size/20);
        surface.emission = color;

        //creating the center points of the legs of the table
        Point p = new Point(center.getX(), center.getY()+(size/20), center.getZ());
        Point p1, p2, p3, p4;

        p1 = new Point(
                p.getX() + size*Math.cos(45) ,//- size/10,
                p.getY(),
                p.getZ()+size*Math.sin(45)  //- size/10
        );
        p2 = new Point(
                p.getX() + size*Math.cos(135) ,//- size/10,
                p.getY(),
                p.getZ()+size*Math.sin(135) //- size/10
        );
        p3 = new Point(
                p.getX() + size*Math.cos(225), //- size/10,
                p.getY(),
                p.getZ()+size*Math.sin(225) //- size/10
        );
        p4 = new Point(
                p.getX() + size*Math.cos(315),// - size/10,
                p.getY(),
                p.getZ()+size*Math.sin(315) //- size/10
        );

        Vector mDir = dir.scale(-1);
        //generating the legs:
        rightLeg1 = new Cylinder(size/11, new Ray(p1, mDir), size*1.5);
        rightLeg2 = new Cylinder(size/11, new Ray(p2, mDir), size*1.5);
        leftLeg1 = new Cylinder(size/11, new Ray(p3, mDir), size*1.5);
        leftLeg2 = new Cylinder(size/11, new Ray(p4, mDir), size*1.5);

        //color of the table:
        rightLeg1.emission = colorOfTable;
        rightLeg2.emission = colorOfTable;
        leftLeg1.emission = colorOfTable;
        leftLeg2.emission = colorOfTable;

    }

    /**
     * getter.
     *
     * @return the final table
     */
    public List<Cylinder> getTable(){
        return List.of(surface, rightLeg1, rightLeg2, leftLeg1, leftLeg2);
    }
}
