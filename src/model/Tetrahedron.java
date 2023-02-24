package model;

import transforms.*;

public class Tetrahedron extends Solid {

    public Tetrahedron() {
        vertexBuffer.add(new Point3D(-1, -1, -1));
        vertexBuffer.add(new Point3D(1, -1, 1));
        vertexBuffer.add(new Point3D(-1, 1, 1));
        vertexBuffer.add(new Point3D(1, 1, -1));


        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(0);
        indexBuffer.add(2);
        indexBuffer.add(0);
        indexBuffer.add(3);
        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(1);
        indexBuffer.add(3);
        indexBuffer.add(2);
        indexBuffer.add(3);
    }
}