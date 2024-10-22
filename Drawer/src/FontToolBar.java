import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FontToolBar extends JToolBar {
    DrawerView canvas;
    String family;
    int style;
    int size;

    String[] fontFamilies;

    JComboBox families, styles, sizes;

    public FontToolBar(DrawerView canvas) {
        this.canvas = canvas;
        GraphicsEnvironment env =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        fontFamilies = env.getAvailableFontFamilyNames();

        family = FontConstants.INIT_FONT_FAMILY;
        style = FontConstants.INIT_FONT_STYLE;
        size = FontConstants.INIT_FONT_SIZE;

        families = new JComboBox(fontFamilies);
        families.setMaximumSize(new Dimension(30, 30));
        families.setPreferredSize(new Dimension(150, 30));
        // size does not matter if smaller that actual size
        families.setSelectedIndex(FontConstants.getIndexOf(family, fontFamilies));
        families.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                setFontFamily(fontFamilies[families.getSelectedIndex()]);
            }
        });
        add(families);

        styles = new JComboBox(FontConstants.StyleNames);
        styles.setMaximumSize(new Dimension(30, 30));
        styles.setPreferredSize(new Dimension(70, 30));
        // size does not matter if smaller that actual size
        styles.setSelectedIndex(FontConstants.getStyleIndexOf(style));
        styles.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                setFontStyle(FontConstants.StyleValues[styles.getSelectedIndex()]);
            }
        });
        add(styles);

        sizes = new JComboBox(FontConstants.SizeNames);
        sizes.setMaximumSize(new Dimension(30, 30));
        sizes.setSelectedIndex(FontConstants.getSizeIndexOf("" + size));
        sizes.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                setFontSize(Integer.parseInt(FontConstants.SizeNames[sizes.getSelectedIndex()]));
            }
        });
        add(sizes);
    }

    public void setFontBoxSelection(Font font) {
        setFontFamilySelection(font.getFamily());
        setFontStyleSelection(font.getStyle());
        setFontSizeSelection(font.getSize());
    }

    public void setFontFamilySelection(String name) {
        family = name;
        families.setSelectedIndex(FontConstants.getIndexOf(family, fontFamilies));
    }

    public void setFontStyleSelection(int style) {
        this.style = style;
        styles.setSelectedIndex(FontConstants.getStyleIndexOf(style));
    }

    public void setFontSizeSelection(int size) {
        this.size = size;
        sizes.setSelectedIndex(FontConstants.getSizeIndexOf("" + size));
    }

    public void setFontFamily(String name) {
        family = name;
        changeFont();
    }

    public void setFontStyle(int style) {
        this.style = style;
        changeFont();
    }

    public void setFontSize(int size) {
        this.size = size;
        changeFont();
    }

    protected void changeFont() {
        canvas.setSelectedFont(new Font(family, style, size));
    }
}
