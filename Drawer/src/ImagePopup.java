import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ImagePopup extends Popup {
    public ImagePopup(DrawerView view, String title) {
        super(title);

        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener((evt) -> view.deleteFigure());
        popupPtr.add(deleteItem);

        JMenuItem copyItem = new JMenuItem("Copy");
        copyItem.addActionListener((evt) -> view.copyFigure());
        popupPtr.add(copyItem);
    }
}

