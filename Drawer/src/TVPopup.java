import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class TVPopup extends FigurePopup {
    private static final long serialVersionUID = 1311846301252431755L;

    TVPopup(DrawerView view) {
        super(view, "TV", false);

        JMenuItem powerItem = new JMenuItem("ON/OFF");
        powerItem.addActionListener((evt) -> view.onOffTV());
        popupPtr.add(powerItem);

        JMenuItem antennaItem = new JMenuItem("Antenna");
        antennaItem.addActionListener((evt) -> view.setAntenna());
        popupPtr.add(antennaItem);
    }
}
