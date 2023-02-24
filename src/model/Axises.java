package model;

import transforms.Point3D;

import java.awt.*;

public class Axises extends Solid {

    public Axises() {
        transformable = false;

        vertexBuffer.add(new Point3D(0,0,0));
        vertexBuffer.add(new Point3D(1,0,0));
        vertexBuffer.add(new Point3D(0,1,0));
        vertexBuffer.add(new Point3D(0,0,1));

        vertexBuffer.add(new Point3D(0.95,-0.05,0));
        vertexBuffer.add(new Point3D(0.95,0.05,0));
        vertexBuffer.add(new Point3D(0.95,0,0.05));
        vertexBuffer.add(new Point3D(0.95,0,-0.05));

        vertexBuffer.add(new Point3D(0.05, 0.95, 0));
        vertexBuffer.add(new Point3D(-0.05, 0.95, 0));
        vertexBuffer.add(new Point3D(0, 0.95, 0.05));
        vertexBuffer.add(new Point3D(0, 0.95, -0.05));

        vertexBuffer.add(new Point3D(0.05, 0, 0.95));
        vertexBuffer.add(new Point3D(-0.05, 0, 0.95));
        vertexBuffer.add(new Point3D(0, 0.05, 0.95));
        vertexBuffer.add(new Point3D(0, -0.05, 0.95));

        //X
        colorBufferAddXTimes(5,Color.RED);
        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(1);
        indexBuffer.add(4);
        indexBuffer.add(1);
        indexBuffer.add(5);
        indexBuffer.add(1);
        indexBuffer.add(6);
        indexBuffer.add(1);
        indexBuffer.add(7);

        //Y
        colorBufferAddXTimes(5,Color.GREEN);
        indexBuffer.add(0);
        indexBuffer.add(2);
        indexBuffer.add(2);
        indexBuffer.add(8);
        indexBuffer.add(2);
        indexBuffer.add(9);
        indexBuffer.add(2);
        indexBuffer.add(10);
        indexBuffer.add(2);
        indexBuffer.add(11);

        //Z
        colorBufferAddXTimes(5,Color.BLUE);
        indexBuffer.add(0);
        indexBuffer.add(3);
        indexBuffer.add(3);
        indexBuffer.add(12);
        indexBuffer.add(3);
        indexBuffer.add(13);
        indexBuffer.add(3);
        indexBuffer.add(14);
        indexBuffer.add(3);
        indexBuffer.add(15);

    }
}
