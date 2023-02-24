package render;

import java.awt.*;

public interface Rasterizer {
    void drawLine(double x1, double y1, double x2, double y2, Color color);
}