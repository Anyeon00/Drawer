import java.awt.*;

public class Line extends TwoPointFigure {
    private static final long serialVersionUID = -2787538065501750475L;

    Line(Color color) {
        super(color);
    }

    Line(Color color, int x, int y) {
        super(color, x, y);
    }

    Line(Color color, int x1, int y1, int x2, int y2) {
        super(color, x1, y1, x2, y2);
    }

    void draw(Graphics g) {
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
    }

    Figure copy() {
        Line newLine = new Line(color, x1, y1, x2, y2);
        newLine.popup = popup;
        newLine.move(MOVE_DX, MOVE_DY);
        return newLine;
    }

    void makeRegion() {
        int regionWidth = 6;
        int x = x1;
        int y = y1;
        int w = x2 - x1;
        int h = y2 - y1;

        int sign_h = 1;
        if (h < 0) sign_h = -1;
        double angle;
        double theta = (w != 0) ? Math.atan((double) (h) / (double) (w)) : sign_h * Math.PI / 2.;
        if (theta < 0) theta = theta + 2 * Math.PI;
        angle = (theta + Math.PI / 2.);
        int dx = (int) (regionWidth * Math.cos(angle));
        int dy = (int) (regionWidth * Math.sin(angle));
        int xpoints[] = new int[4];
        int ypoints[] = new int[4];
        xpoints[0] = x + dx;
        ypoints[0] = y + dy;
        xpoints[1] = x - dx;
        ypoints[1] = y - dy;
        xpoints[2] = x + w - dx;
        ypoints[2] = y + h - dy;
        xpoints[3] = x + w + dx;
        ypoints[3] = y + h + dy;
        region = new Polygon(xpoints, ypoints, 4);
    }
}






