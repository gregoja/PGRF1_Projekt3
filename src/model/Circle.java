package model;

import transforms.Mat4Transl;
import transforms.Point3D;

public class Circle extends Solid {

    public Circle(double r, int n) {
        if (n < 3) {
            throw new IllegalArgumentException("n is too small");
        }

        double help = 2 * Math.PI / n;
        for (int i = 0; i < n; i++) {
            double alpha = i * help;
            double vy = r * Math.cos(alpha);
            double vz = r * Math.sin(alpha);
            vertexBuffer.add(new Point3D(0, vy, vz));

            indexBuffer.add(i);
            indexBuffer.add((i + 1) % n);
        }
    }
}
