import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class FigurePopup extends Popup {
    private static final long serialVersionUID = 1311846301252431755L;

    FigurePopup(DrawerView view, String title, boolean fillFlag) {
        super(title);

        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener((evt) -> view.deleteFigure());
        popupPtr.add(deleteItem);

        JMenuItem copyItem = new JMenuItem("Copy");
        copyItem.addActionListener((evt) -> view.copyFigure());
        popupPtr.add(copyItem);

        JMenu colorMenu = new JMenu("Colors");
        popupPtr.add(colorMenu);

        JMenuItem blackItem = new JMenuItem("Black");
        blackItem.addActionListener(
                (evt) -> view.setColorForSeletedFigure(Color.black));
        colorMenu.add(blackItem);

        JMenuItem redItem = new JMenuItem("Red");
        redItem.addActionListener(
                (evt) -> view.setColorForSeletedFigure(Color.red));
        colorMenu.add(redItem);

        JMenuItem greenItem = new JMenuItem("Green");
        greenItem.addActionListener(
                (evt) -> view.setColorForSeletedFigure(Color.green));
        colorMenu.add(greenItem);

        JMenuItem blueItem = new JMenuItem("Blue");
        blueItem.addActionListener(
                (evt) -> view.setColorForSeletedFigure(Color.blue));
        colorMenu.add(blueItem);

        JMenuItem chooserItem = new JMenuItem("Chooser");
        chooserItem.addActionListener((evt) -> view.showColorChooser());
        colorMenu.add(chooserItem);

        if (fillFlag == true) {
            JMenuItem fillItem = new JMenuItem("Fill");
            fillItem.addActionListener((evt) -> view.fillFigure());
            popupPtr.add(fillItem);
        }
    }
}

