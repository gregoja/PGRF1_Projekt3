package render;

import model.Solid;
import transforms.Mat4;
import transforms.Point3D;
import transforms.Vec3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Renderer {

    private Mat4 view;
    private Mat4 proj;
    private Rasterizer rasterizer;

    public Renderer(Rasterizer rasterizer) {
        this.rasterizer = rasterizer;
    }

    public void drawSolid(List<Solid> solids){
        for(Solid s: solids){
            drawSolid(s);
        }
    }

    public void drawSolid(Solid solid){
        List<Point3D> points = new ArrayList<>();
        // M V P
        Mat4 transforms = solid.getTransforms().mul(view).mul(proj);
        for (Point3D p: solid.getVertexBuffer()) {
            points.add(p.mul(transforms));
        }
        int counter = 0;    //for color
        for (int i = 0; i < solid.getIndexBuffer().size(); i+=2) {
            int i1 = solid.getIndexBuffer().get(i);
            int i2 = solid.getIndexBuffer().get(i+1);
            int colBuffSize = solid.getColorBuffer().size();
            renderLine(points.get(i1), points.get(i2));
            if(counter < colBuffSize){
                renderLine(points.get(i1), points.get(i2),solid.getColorBuffer().get(counter++));
            }else {
                renderLine(points.get(i1), points.get(i2));
            }
        }
    }

    private void renderLine (Point3D a, Point3D b) {
        renderLine(a,b, Color.WHITE);
    }

    private void renderLine (Point3D a, Point3D b, Color color){
        if(clip(a) || clip(b)) return;

        if(!a.dehomog().isPresent()) return;
        Vec3D va = a.dehomog().get();

        if(!b.dehomog().isPresent()) return;
        Vec3D vb = b.dehomog().get();


        double x1 = va.getX();
        double y1 = va.getY();

        double x2 = vb.getX();
        double y2 = vb.getY();

        rasterizer.drawLine(x1,y1,x2,y2,color);
    }

    private boolean clip(Point3D p) {
        if (-p.getW() > p.getX() || p.getX() > p.getW()) return true;
        if (-p.getW() > p.getY() || p.getY() > p.getW()) return true;
        return p.getZ() < 0 || p.getZ() > p.getW();
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public void setProj(Mat4 proj) {
        this.proj = proj;
    }
}