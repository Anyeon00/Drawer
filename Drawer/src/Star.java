import java.awt.*;

public class Star extends TwoPointFigure {
    private static final long serialVersionUID = -5377717005542173451L;
    protected boolean fillFlag;

    Star(Color color) {
        super(color);
        fillFlag = false;
    }

    Star(Color color, int x, int y) {
        super(color, x, y);
        fillFlag = false;
    }

    Star(Color color, int x1, int y1, int x2, int y2) {
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

        int xpoints[] = new int[10];
        int ypoints[] = new int[10];

        makeStarPolygon(minX, minY, minLen, minLen, xpoints, ypoints);

        g.setColor(color);
        g.drawPolygon(xpoints, ypoints, 10);

        if (fillFlag == true) {
            g.fillPolygon(xpoints, ypoints, 10);
        }
    }

    public static void makeStarPolygon(int x, int y, int width, int height,
                                       int xp[], int yp[]) {
        int cx = x + width / 2;
        int cy = y + height / 2;
        double r1 = width / 2;
        double r2 = 0.3819660113 * r1; // sin(54)-tan(36)*cos(54)
        double delta = 2 * Math.PI / 5;
        int pos = 0;

        double theta = 0.0;

        for (int i = 0; i < 5; i++) {
            int px1 = cx + (int) (r1 * Math.cos(theta - Math.PI / 2));
            int py1 = cy + (int) (r1 * Math.sin(theta - Math.PI / 2));
            xp[pos] = px1;
            yp[pos] = py1;
            pos++;

            int px2 = cx + (int) (r2 * Math.cos(theta + Math.PI / 2 + 3 * 2 * Math.PI / 5));
            int py2 = cy + (int) (r2 * Math.sin(theta + Math.PI / 2 + 3 * 2 * Math.PI / 5));
            xp[pos] = px2;
            yp[pos] = py2;
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

        int xpoints[] = new int[10];
        int ypoints[] = new int[10];

        makeStarPolygon(minX, minY, minLen, minLen, xpoints, ypoints);

        region = new Polygon(xpoints, ypoints, 10);
    }

    Figure copy() {
        // upcasting: ����Ŭ���� -> ����Ŭ����
        Star newStar = new Star(color, x1, y1, x2, y2);
        newStar.popup = popup;
        newStar.fillFlag = fillFlag;
        newStar.move(MOVE_DX, MOVE_DY);
        return newStar;
    }
}
