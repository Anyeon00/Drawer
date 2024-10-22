import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextPopup extends Popup {
    public TextPopup(DrawerView view, String title) {
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

        JMenuItem fontItem = new JMenuItem("Font");
        fontItem.addActionListener((evt) -> view.changeFontForText());
        popupPtr.add(fontItem);

        JMenuItem editItem = new JMenuItem("Edit");
        editItem.addActionListener((evt) -> view.editText());
        popupPtr.add(editItem);
//////
    }
}

