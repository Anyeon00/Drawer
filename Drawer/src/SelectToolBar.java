import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class SelectToolBar extends JToolBar {
    SelectToolBar(DrawerView canvas) {
        add(canvas.getPointAction());
        add(canvas.getBoxAction());
        add(canvas.getLineAction());
        add(canvas.getArrowAction());
        add(canvas.getCircleAction());
        add(canvas.getTriangleAction());
        add(canvas.getPentagonAction());
        add(canvas.getStarAction());
        add(canvas.getTVAction());
        add(canvas.getKiteAction());
        add(canvas.getTextAction());
        add(canvas.getImageAction());
    }
}
