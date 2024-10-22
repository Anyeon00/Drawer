import java.awt.*;

public class Pentagon extends TwoPointFigure {
    private static final long serialVersionUID = -5352317005542173451L;
    protected boolean fillFlag;

    Pentagon(Color color) {
        super(color);
        fillFlag = false;
    }

    Pentagon(Color color, int x, int y) {
        super(color, x, y);
        fillFlag = false;
    }

    Pentagon(Color color, int x1, int y1, int x2, int y2) {
        super(color, x1, y1, x2, y2);
        fillFlag = false;
    }

    void setFill() {
        fillFlag = !fillFlag;
    }

    void draw(Graphics g) {
        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);

        int minLen = width;
        if (minLen > height) minLen = height;

        int xpoints[] = new int[6];
        int ypoints[] = new int[6];

        makePentaPolygon(minX, minY, minLen, minLen, xpoints, ypoints);

        g.setColor(color);
        g.drawPolygon(xpoints, ypoints, 6);

        if (fillFlag == true) {
            g.fillPolygon(xpoints, ypoints, 6);
        }
    }

    public static void makePentaPolygon(int x, int y, int width, int height,
                                        int xp[], int yp[]) {
        int cx = x + width / 2;
        int cy = y + height / 2;
        double r1 = width / 2;
        double delta = 2 * Math.PI / 6;
        int pos = 0;

        double theta = 0.0;

        for (int i = 0; i < 6; i++) {
            int px1 = cx + (int) (r1 * Math.cos(theta - Math.PI / 2));
            int py1 = cy + (int) (r1 * Math.sin(theta - Math.PI / 2));
            xp[pos] = px1;
            yp[pos] = py1;
            pos++;

            theta = theta + delta;
        }
    }

    public void makeRegion() {
        if (x1 > x2) {
            int tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
        if (y1 > y2) {
            int tmp = y1;
            y1 = y2;
            y2 = tmp;
        }

        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);

        int minLen = width;
        if (minLen > height) minLen = height;

        x2 = x1 + minLen;
        y2 = y1 + minLen;

        int xpoints[] = new int[6];
        int ypoints[] = new int[6];

        makePentaPolygon(minX, minY, minLen, minLen, xpoints, ypoints);

        region = new Polygon(xpoints, ypoints, 6);
    }

    Figure copy() {
        Pentagon newPentagon = new Pentagon(color, x1, y1, x2, y2);
        newPentagon.popup = popup;
        newPentagon.fillFlag = fillFlag;
        newPentagon.move(MOVE_DX, MOVE_DY);
        return newPentagon;
    }
}
