import java.awt.*;

public class Triangle extends TwoPointFigure {
    private static final long serialVersionUID = -5352327405542174751L;
    protected boolean fillFlag;

    Triangle(Color color) {
        super(color);
        fillFlag = false;
    }

    Triangle(Color color, int x, int y) {
        super(color, x, y);
        fillFlag = false;
    }

    Triangle(Color color, int x1, int y1, int x2, int y2) {
        super(color, x1, y1, x2, y2);
        fillFlag = false;
    }

    void setFill() {
        fillFlag = !fillFlag;
    }

    void draw(Graphics g) {
        int width;
        int x = x1;
        int y = y1;
        int w = x2 - x1;
        int h = y2 - y1;
        double len = Math.sqrt((w * w) + (h * h));
        width = (int) (len * Math.tan(Math.PI / 6.0));

        int sign_h = 1;
        if (h < 0) sign_h = -1;
        double angle;
        double theta = (w != 0) ? Math.atan((double) (h) / (double) (w)) : sign_h * Math.PI / 2.;
        if (theta < 0) theta = theta + 2 * Math.PI;
        angle = (theta + Math.PI / 2.);
        int dx = (int) (width * Math.cos(angle));
        int dy = (int) (width * Math.sin(angle));
        int xpoints[] = new int[3];
        int ypoints[] = new int[3];
        xpoints[0] = x + dx;
        ypoints[0] = y + dy;
        xpoints[1] = x - dx;
        ypoints[1] = y - dy;
        xpoints[2] = x2;
        ypoints[2] = y2;

        g.setColor(color);
        g.drawPolygon(xpoints, ypoints, 3);

        if (fillFlag == true) {
            g.fillPolygon(xpoints, ypoints, 3);
        }
    }

    public void makeRegion() {
        int width;
        int x = x1;
        int y = y1;
        int w = x2 - x1;
        int h = y2 - y1;
        double len = Math.sqrt((w * w) + (h * h));
        width = (int) (len * Math.tan(Math.PI / 6.0));

        int sign_h = 1;
        if (h < 0) sign_h = -1;
        double angle;
        double theta = (w != 0) ? Math.atan((double) (h) / (double) (w)) : sign_h * Math.PI / 2.;
        if (theta < 0) theta = theta + 2 * Math.PI;
        angle = (theta + Math.PI / 2.);
        int dx = (int) (width * Math.cos(angle));
        int dy = (int) (width * Math.sin(angle));
        int xpoints[] = new int[3];
        int ypoints[] = new int[3];
        xpoints[0] = x + dx;
        ypoints[0] = y + dy;
        xpoints[1] = x - dx;
        ypoints[1] = y - dy;
        xpoints[2] = x2;
        ypoints[2] = y2;
        region = new Polygon(xpoints, ypoints, 3);
    }

    Figure copy() {
        Triangle newTriangle = new Triangle(color, x1, y1, x2, y2);
        newTriangle.popup = popup;
        newTriangle.fillFlag = fillFlag;
        newTriangle.move(MOVE_DX, MOVE_DY);
        return newTriangle;
    }
}
