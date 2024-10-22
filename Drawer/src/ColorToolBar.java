import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class ColorToolBar extends JToolBar {
    ColorToolBar(DrawerView canvas) {
        add(new ColorAction("Black", Color.black, canvas));
        add(new ColorAction("Red", Color.red, canvas));
        add(new ColorAction("Green", Color.green, canvas));
        add(new ColorAction("Blue", Color.blue, canvas));
        add(new ColorAction("Color", Color.yellow, canvas));
    }
}
