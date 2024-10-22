import java.awt.*;

public class Arrow extends TwoPointFigure {
    private static final long serialVersionUID = -5352327499942174751L;
    protected boolean fillFlag;

    Arrow(Color color) {
        super(color);
        fillFlag = false;
    }

    Arrow(Color color, int x, int y) {
        super(color, x, y);
        fillFlag = false;
    }

    Arrow(Color color, int x1, int y1, int x2, int y2) {
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
        width = (int) (len * Math.tan(Math.PI / 6));

        int sign_h = 1;
        if (h < 0) sign_h = -1;
        double angle;
        double theta = (w != 0) ? Math.atan((double) (h) / (double) (w)) : sign_h * Math.PI / 2.;
        if (theta < 0) theta = theta + 2 * Math.PI;
        angle = (theta + Math.PI / 2.);
        int dx = (int) (width * Math.cos(angle));
        int dy = (int) (width * Math.sin(angle));

        int xpoints[] = new int[7];
        int ypoints[] = new int[7];
//////
        xpoints[0] = x + dx / 2;
        ypoints[0] = y + dy / 2;
        xpoints[1] = x - dx / 2;
        ypoints[1] = y - dy / 2;
        xpoints[2] = x + w / 2 - dx / 2;
        ypoints[2] = y + h / 2 - dy / 2;
        xpoints[3] = x + w / 2 - dx;
        ypoints[3] = y + h / 2 - dy;
        xpoints[4] = x2;
        ypoints[4] = y2;
        xpoints[5] = x + w / 2 + dx;
        ypoints[5] = y + h / 2 + dy;
        xpoints[6] = x + w / 2 + dx / 2;
        ypoints[6] = y + h / 2 + dy / 2;
//////
        g.drawPolygon(xpoints, ypoints, 7);

        if (fillFlag == true) {
            g.fillPolygon(xpoints, ypoints, 7);
        }
    }

    public void makeRegion() {
        int width;
        int x = x1;
        int y = y1;
        int w = x2 - x1;
        int h = y2 - y1;
        double len = Math.sqrt((w * w) + (h * h));
        width = (int) (len * Math.tan(Math.PI / 6));

        int sign_h = 1;
        if (h < 0) sign_h = -1;
        double angle;
        double theta = (w != 0) ? Math.atan((double) (h) / (double) (w)) : sign_h * Math.PI / 2.;
        if (theta < 0) theta = theta + 2 * Math.PI;
        angle = (theta + Math.PI / 2.);
        int dx = (int) (width * Math.cos(angle));
        int dy = (int) (width * Math.sin(angle));
        int xpoints[] = new int[7];
        int ypoints[] = new int[7];

//////
        xpoints[0] = x + dx / 2;
        ypoints[0] = y + dy / 2;
        xpoints[1] = x - dx / 2;
        ypoints[1] = y - dy / 2;
        xpoints[2] = x + w / 2 - dx / 2;
        ypoints[2] = y + h / 2 - dy / 2;
        xpoints[3] = x + w / 2 - dx;
        ypoints[3] = y + h / 2 - dy;
        xpoints[4] = x2;
        ypoints[4] = y2;
        xpoints[5] = x + w / 2 + dx;
        ypoints[5] = y + h / 2 + dy;
        xpoints[6] = x + w / 2 + dx / 2;
        ypoints[6] = y + h / 2 + dy / 2;
//////

        region = new Polygon(xpoints, ypoints, 7);
    }

    Figure copy() {
        // upcasting: ����Ŭ���� -> ����Ŭ����
        Arrow newArrow = new Arrow(color, x1, y1, x2, y2);
        newArrow.popup = popup;
        newArrow.fillFlag = fillFlag;
        newArrow.move(MOVE_DX, MOVE_DY);
        return newArrow;
    }
}
