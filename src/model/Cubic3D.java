package model;

import transforms.Cubic;
import transforms.Mat4;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Cubic3D extends Solid {
    public Cubic3D(Mat4 type, List<Point3D> controlPoints, double increments) {
        if (type.eEquals(Cubic.FERGUSON) || type.eEquals(Cubic.BEZIER) || type.eEquals(Cubic.COONS)) {
            List<Cubic> cubics = new ArrayList<>();

            // cubic properties are ignored. Curve joints have to be "encoded" in next 4 controlPoints
            for (int i = 0; i < controlPoints.size(); i += 4) {
                cubics.add(new Cubic(type, controlPoints.get(i), controlPoints.get(i + 1), controlPoints.get(i + 2), controlPoints.get(i + 3)));
            }

            int counter = 0;
            for (Cubic cubic : cubics) {
                Point3D p1 = cubic.compute(0);
                vertexBuffer.add(p1);
                for (double i = increments; i <= 1+increments; i += increments) {
                    Point3D p2 = cubic.compute(i);
                    vertexBuffer.add(p2);
                    indexBuffer.add(counter);
                    indexBuffer.add(++counter);
                }
            }
        } else {
            throw new IllegalArgumentException("Unsupported Cubic type");
        }
    }
}