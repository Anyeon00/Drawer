import java.awt.*;

class TV extends TwoPointFigure {
    private static final long serialVersionUID = -5242971533927480700L;
    private boolean antennaFlag;
    private Box frame;
    private Box screen;
    private Circle channelButton;
    private Circle volumnButton;
    private Circle menuButton;
    private Box powerButton;
    private Line antenna1;
    private Line antenna2;
    private static int FRAME_WIDTH = 150;
    private static int FRAME_HEIGHT = 90;
    private static int ANTENNA_WIDTH = 30;
    private static int ANTENNA_HEIGHT = 40;
    private static int FRAME_GAP = 12;
    private static int SCREEN_WIDTH = 105;
    private static int SCREEN_HEIGHT = FRAME_HEIGHT - 2 * FRAME_GAP;
    private static int SWITCH_GAP = 17;
    private static int SWITCH_SIZE = 10;
    private static int POWER_SWITCH_WIDTH = 15;
    private static int POWER_SWITCH_HEIGHT = 8;
    private static int TOTAL_WIDTH = FRAME_WIDTH;
    private static int TOTAL_HEIGHT = FRAME_HEIGHT + ANTENNA_HEIGHT;

    TV(Color color, int x, int y, boolean antennaOption) {
        super(Color.black, x, y, x + TOTAL_WIDTH, y + TOTAL_HEIGHT);

        int x1, y1, x2, y2;
        x1 = x;
        y1 = y + ANTENNA_HEIGHT;
        x2 = x + FRAME_WIDTH;
        y2 = y + TOTAL_HEIGHT;
        frame = new Box(Color.black, x1, y1, x2, y2);
        x1 = x1 + FRAME_GAP;
        y1 = y1 + FRAME_GAP;
        x2 = x1 + SCREEN_WIDTH;
        y2 = y1 + SCREEN_HEIGHT;
        screen = new Box(color, x1, y1, x2, y2);
        x1 = x2 + FRAME_GAP;
        y1 = y1 + FRAME_GAP / 2;
        x2 = x1 + SWITCH_SIZE;
        y2 = y1 + SWITCH_SIZE;
        channelButton = new Circle(Color.black, x1, y1, x2, y2);
        y1 = y1 + SWITCH_GAP;
        y2 = y1 + SWITCH_SIZE;
        volumnButton = new Circle(Color.black, x1, y1, x2, y2);
        y1 = y1 + SWITCH_GAP;
        y2 = y1 + SWITCH_SIZE;
        menuButton = new Circle(Color.black, x1, y1, x2, y2);
        x1 = x1 - FRAME_GAP / 3 + 2;
        y1 = y1 + SWITCH_GAP + 2;
        x2 = x1 + POWER_SWITCH_WIDTH;
        y2 = y1 + POWER_SWITCH_HEIGHT;
        powerButton = new Box(Color.black, x1, y1, x2, y2);
        antennaFlag = antennaOption;
        if (antennaOption == true) {
            int cx = x + TOTAL_WIDTH / 2;
            x1 = cx - ANTENNA_WIDTH;
            y1 = y;
            x2 = cx;
            y2 = y + ANTENNA_HEIGHT;
            antenna1 = new Line(Color.black, x1, y1, x2, y2);
            x1 = cx + ANTENNA_WIDTH;
            antenna2 = new Line(Color.black, x1, y1, x2, y2);
        } else {
            antenna1 = null;
            antenna2 = null;
        }
    }

    void setAntenna() {
        if (antennaFlag == true) {
            antenna1 = null;
            antenna2 = null;
        } else {
            int cx = this.x1 + TOTAL_WIDTH / 2;
            int x1 = cx - ANTENNA_WIDTH;
            int y1 = this.y1;
            int x2 = cx;
            int y2 = this.y1 + ANTENNA_HEIGHT;
            antenna1 = new Line(Color.black, x1, y1, x2, y2);
            x1 = cx + ANTENNA_WIDTH;
            antenna2 = new Line(Color.black, x1, y1, x2, y2);
        }
        antennaFlag = !antennaFlag;
    }

    void pressPowerButton() {
        screen.setFill();
        powerButton.setFill();
    }

    void move(int dx, int dy) {
        super.move(dx, dy);
        frame.move(dx, dy);
        screen.move(dx, dy);
        channelButton.move(dx, dy);
        volumnButton.move(dx, dy);
        menuButton.move(dx, dy);
        powerButton.move(dx, dy);
        if (antenna1 != null) {
            antenna1.move(dx, dy);
        }
        if (antenna2 != null) {
            antenna2.move(dx, dy);
        }
    }

    void setColor(Color color) {
        super.setColor(color);
        screen.setColor(color);
    }

    void draw(Graphics g) {
        frame.draw(g);
        screen.draw(g);
        channelButton.draw(g);
        volumnButton.draw(g);
        menuButton.draw(g);
        powerButton.draw(g);
        if (antenna1 != null) {
            antenna1.draw(g);
        }
        if (antenna2 != null) {
            antenna2.draw(g);
        }
    }

    Figure copy() {
        TV newTV = new TV(color, x1, y1, antennaFlag);
        newTV.popup = popup;
        newTV.setColor(color);
        newTV.move(MOVE_DX, MOVE_DY);
        return newTV;
    }
}
