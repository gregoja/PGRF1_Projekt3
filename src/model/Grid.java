package model;

import transforms.Point3D;

public class Grid extends Solid {

    public Grid(int n, int m) {
        if(m < 2 && n < 2){
            throw new IllegalArgumentException("Grid size is too small");
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                vertexBuffer.add(new Point3D(0, i * (1. / (n - 1)), j * (1. / (m - 1))));

                if (j != m - 1) {
                    indexBuffer.add(j + i * m);
                    indexBuffer.add(j + i * m + 1);
                }
                if (i != n - 1) {
                    indexBuffer.add(j + i * m);
                    indexBuffer.add(j + i * m + m);
                }
            }
        }

        System.out.println(getVertexBuffer().size());
        System.out.println(getIndexBuffer().size());

    }
}
