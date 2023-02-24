package render;

import rasterOper.Raster;

import java.awt.*;

public class RasterizerLine implements Rasterizer {
    private Graphics gr;
    private Raster raster;

    public RasterizerLine(Graphics gr, Raster r) {
        this.gr = gr;
        this.raster = r;
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2,Color color) {

        int ix1 = (int)(((x1+1)/2)* raster.getWidth() -1);
        int ix2 = (int)(((x2+1)/2)* raster.getWidth() -1);

        int iy1 = (int)(((-y1+1)/2)* raster.getHeight() -1);
        int iy2 = (int)(((-y2+1)/2)* raster.getHeight() -1);
        
        gr.setColor(color);
        gr.drawLine(ix1,iy1,ix2,iy2);
    }
}
