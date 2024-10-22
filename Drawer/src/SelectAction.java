import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class SelectAction extends AbstractAction {
    private DrawerView view;

    public SelectAction(String name, Icon icon, DrawerView view, int id) {
        putValue(Action.NAME, name);
        putValue(Action.SMALL_ICON, icon);
        putValue("id", id);
        this.view = view;
    }

    public void actionPerformed(ActionEvent e) {
        int id = (int) getValue("id");
        view.setWhatToDraw(id);
    }
}

