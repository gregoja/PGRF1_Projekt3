
import model.*;
import rasterOper.RasterBufferedImage;
import render.RasterizerLine;
import render.Renderer;
import transforms.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class CanvasMouse {
    private Renderer renderer;
    private RasterBufferedImage rasterBufferedImage;

    private int oldX;
    private int newX;
    private int oldY;
    private int newY;
    private Cursor moveCursor = new Cursor(Cursor.MOVE_CURSOR);
    private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);

    private Camera cam;
    private Mat4 persp = new Mat4PerspRH(Math.PI/4,1,0.1,200);
    private Mat4 orth = new Mat4OrthoRH(5,5,0.1,200);
    private double step = 1;

    private List<Solid> scene = new ArrayList<>();
    private Tetrahedron tetrahedron;
    private Cube cube;
    private Circle circle;
    private Cubic3D cubicBez;
    private List<Point3D> pointsBez = new ArrayList<>();
    private Cubic3D cubicFerg;
    private List<Point3D> pointsFer = new ArrayList<>();
    private Cubic3D cubicCoon;
    private List<Point3D> pointsCoon = new ArrayList<>();
    private Grid grid;

    private JCheckBox chkCube = new JCheckBox("Cube",true);
    private JCheckBox chkTetrahedron = new JCheckBox("Tetrahedron");
    private JCheckBox chkCircle = new JCheckBox("Circle");
    private JCheckBox chkGrid = new JCheckBox("Grid");

    private JCheckBox chkBezier = new JCheckBox("Bezier");
    private JCheckBox chkCoons = new JCheckBox("Coons");
    private JCheckBox chkFerguson = new JCheckBox("Ferguson");

    private JPanel panel;
    private BufferedImage img;

    public CanvasMouse(int width, int height) {
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };
        panel.setFocusable(true);
        panel.grabFocus();
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel);
        frame.setVisible(true);

        rasterBufferedImage = new RasterBufferedImage(img);
        RasterizerLine rasterizerLine =  new RasterizerLine(img.getGraphics(),rasterBufferedImage);
        renderer = new Renderer(rasterizerLine);
        renderer.setProj(persp);

        pointsBez.add(new Point3D(-1, -1, -1));
        pointsBez.add(new Point3D(-1, -1, 1));
        pointsBez.add(new Point3D(1, -1, -1));
        pointsBez.add(new Point3D(1, -1, 1));

        Point3D a = new Point3D(1, 1, -1);
        Point3D b = new Point3D(-1, 1, 1);
        Point3D c = new Point3D(1, 1, 1);
        Point3D d = new Point3D(-1, 1, -1);

        pointsCoon.add(a);pointsCoon.add(a);pointsCoon.add(a);pointsCoon.add(b);
        pointsCoon.add(a);pointsCoon.add(a);pointsCoon.add(b);pointsCoon.add(c);
        pointsCoon.add(a);pointsCoon.add(b);pointsCoon.add(c);pointsCoon.add(d);
        pointsCoon.add(b);pointsCoon.add(c);pointsCoon.add(d);pointsCoon.add(d);
        pointsCoon.add(c);pointsCoon.add(d);pointsCoon.add(d);pointsCoon.add(d);

        pointsFer.add(new Point3D(1,-1,-1));
        pointsFer.add(new Point3D(1,1,1));
        pointsFer.add(new Point3D(0,5,2));
        pointsFer.add(new Point3D(0,2,5));

        JToolBar tlbNorth = new JToolBar();
        tlbNorth.setFloatable(false);
        JButton btnReset = new JButton("Reset");
        JRadioButton btnPersp = new JRadioButton("Perspective",true);
        JRadioButton btnOrth = new JRadioButton("Orthogonal");
        ButtonGroup bgProjection = new ButtonGroup();
        bgProjection.add(btnPersp);
        bgProjection.add(btnOrth);
        tlbNorth.add(btnReset);
        tlbNorth.add(btnPersp);
        tlbNorth.add(btnOrth);
        frame.add(tlbNorth,BorderLayout.NORTH);

        tlbNorth.addSeparator();

        tlbNorth.add(chkCube);
        tlbNorth.add(chkTetrahedron);
        tlbNorth.add(chkCircle);
        tlbNorth.add(chkGrid);

        tlbNorth.addSeparator();

        tlbNorth.add(chkBezier);
        tlbNorth.add(chkCoons);
        tlbNorth.add(chkFerguson);

        btnReset.addActionListener(e -> reset());
        btnOrth.addActionListener(e -> {renderer.setProj(orth);draw();});
        btnPersp.addActionListener(e -> {renderer.setProj(persp);draw();});

        chkCube.addActionListener(e -> {
            if(chkCube.isSelected()){
                cube = new Cube();
                scene.add(cube);
            }else{
                scene.remove(cube);
                cube = null;
            }
            draw();
        });
        chkTetrahedron.addActionListener(e -> {
            if(chkTetrahedron.isSelected()){
                tetrahedron = new Tetrahedron();
                scene.add(tetrahedron);
            }else{
                scene.remove(tetrahedron);
                tetrahedron = null;
            }
            draw();
        });
        chkCircle.addActionListener(e -> {
            if(chkCircle.isSelected()){
                circle = new Circle(0.5,25);
                scene.add(circle);
            }else{
                scene.remove(circle);
                circle = null;
            }
            draw();
        });
        chkBezier.addActionListener(e -> {
            if(chkBezier.isSelected()){
                cubicBez = new Cubic3D(Cubic.BEZIER,pointsBez,0.01);
                scene.add(cubicBez);
            }else{
                scene.remove(cubicBez);
                cubicBez = null;
            }
            draw();
        });
        chkCoons.addActionListener(e -> {
            if(chkCoons.isSelected()){
                cubicCoon = new Cubic3D(Cubic.COONS,pointsCoon,0.01);
                scene.add(cubicCoon);
            }else{
                scene.remove(cubicCoon);
                cubicCoon = null;
            }
            draw();
        });
        chkFerguson.addActionListener(e -> {
            if(chkFerguson.isSelected()){
                cubicFerg = new Cubic3D(Cubic.FERGUSON,pointsFer,0.01);
                scene.add(cubicFerg);
            }else{
                scene.remove(cubicFerg);
                cubicFerg = null;
            }
            draw();
        });
        chkGrid.addActionListener(e -> {
            if(chkGrid.isSelected()){
                grid = new Grid(15,15);
                scene.add(grid);
            }else{
                scene.remove(grid);
                grid = null;
            }
            draw();
        });


        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                panel.requestFocus();
                oldX = e.getX();
                oldY = e.getY();
                if(!SwingUtilities.isMiddleMouseButton(e)) frame.setCursor(moveCursor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                frame.setCursor(defaultCursor);
            }
        });
        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                newX = e.getX();
                newY = e.getY();

                int dx = oldX - newX;
                int dy = oldY - newY;

                if(e.isShiftDown()){
                   for(Solid solid : scene){
                       solid.setTransforms(solid.getTransforms().mul(new Mat4Transl(0,dx/10.0,dy/10.0)));
                   }
                }else
                if(SwingUtilities.isLeftMouseButton(e)){
                    cam = cam.addAzimuth((Math.PI * (dx)) / rasterBufferedImage.getWidth());
                    cam = cam.addZenith((Math.PI * (dy)) / rasterBufferedImage.getHeight());
                }else
                if(SwingUtilities.isRightMouseButton(e)){
                    for(Solid solid: scene){
                        // doesn't have anything to do with variables dx, dy
                        double ddx = solid.getTransforms().get(3,0);
                        double ddy = solid.getTransforms().get(3,1);
                        double ddz = solid.getTransforms().get(3,2);
                        if(ddx == 0 && ddy == 0 && ddz == 0){
                            solid.setTransforms(solid.getTransforms().mul(new Mat4RotXYZ(0,
                                    Math.PI * (dy) / rasterBufferedImage.getHeight()
                                    ,-(Math.PI * (dx) / rasterBufferedImage.getWidth()))));
                        }else{
                            solid.setTransforms(solid.getTransforms().mul(new Mat4Transl(-ddx,-ddy,-ddz)));
                            solid.setTransforms(solid.getTransforms().mul(new Mat4RotXYZ(0,
                                    Math.PI * (dy) / rasterBufferedImage.getHeight()
                                    ,-(Math.PI * (dx) / rasterBufferedImage.getWidth()))));
                            solid.setTransforms(solid.getTransforms().mul(new Mat4Transl(ddx,ddy,ddz)));
                        }
                    }
                }
                oldX = newX;
                oldY = newY;

                draw();
            }
        });
        panel.addMouseWheelListener(e -> {
            if(e.getWheelRotation() > 0){
                for(Solid solid : scene){
                    solid.setTransforms(solid.getTransforms().mul(new Mat4Scale(0.8)));
                }
            }else{
                for(Solid solid : scene){
                    solid.setTransforms(solid.getTransforms().mul(new Mat4Scale(1.2)));
                }
            }
            draw();
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_W : cam = cam.forward(step); break;
                    case KeyEvent.VK_A : cam = cam.left(step); break;
                    case KeyEvent.VK_S : cam = cam.backward(step); break;
                    case KeyEvent.VK_D : cam = cam.right(step); break;
                    case KeyEvent.VK_R : reset(); break;
                }
                draw();
            }
        });

        frame.pack();
    }

    private void reset() {
        scene = new ArrayList<>();
        scene.add(new Axises());
        cube = new Cube();
        scene.add(cube);
        cam = new Camera().withZenith(0).addAzimuth(0).withPosition(new Vec3D(-10,0,0));

        chkCube.setSelected(true);
        chkTetrahedron.setSelected(false);
        chkCircle.setSelected(false);
        chkGrid.setSelected(false);

        chkBezier.setSelected(false);
        chkCoons.setSelected(false);
        chkFerguson.setSelected(false);

        draw();
    }

    private void draw(){
        clear();
        img.getGraphics().drawString("r = reset,shift+mouse = translation, mouse wheel = scaling, rmb = rotation, lmb = looking",
                5, img.getHeight() - 5);
        renderer.setView(cam.getViewMatrix());
        renderer.drawSolid(scene);
        panel.repaint();
    }

    public void clear() {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0x2f2f2f));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    public void present(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }

    public void start() {
        reset();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CanvasMouse(800, 600).start());
    }
}
