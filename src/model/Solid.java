package model;

import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Solid {
    final List<Point3D> vertexBuffer = new ArrayList<>();
    final List<Integer> indexBuffer = new ArrayList<>();
    private final List<Color> colorBuffer = new ArrayList<>();

    protected Mat4 transforms = new Mat4Identity();
    boolean transformable = true;

    public List<Point3D> getVertexBuffer() {
        return vertexBuffer;
    }

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public List<Color> getColorBuffer() {
        return colorBuffer;
    }

    void colorBufferAddXTimes(int times,Color color){
        for (int i = 0; i < times; i++) {
            colorBuffer.add(color);
        }
    }

    public Mat4 getTransforms() {
        return transforms;
    }

    public void setTransforms(Mat4 transforms){
        if(transformable) this.transforms = transforms;
    }
}
