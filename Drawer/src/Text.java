import java.awt.*;
import java.util.*;

public class Text extends Box {
    private static final long serialVersionUID = -5526552132759568802L;
    String[] lines;
    Font font;

    Text(Color color, Font font) {
        super(color);
        this.font = font;
    }

    Text(Color color, Font font, int x, int y) {
        super(color, x, y);
        this.font = font;
    }

    Text(Color color, Font font, int x1, int y1, int x2, int y2, String lines[]) {
        super(color, x1, y1, x2, y2);
        this.lines = lines;
        this.font = font;
    }

    Font getFont() {
        return font;
    }

    public String getText() {
        String s = "";
        for (int i = 0; i < lines.length; i++) {
            s = s + lines[i] + "\n\r";
        }
        return s;
    }

    void setColor(Color color) {
        super.setColor(color);
    }

    void draw(Graphics g) {
        g.setColor(color);
        g.setFont(font);

        Font f = g.getFont();
        FontMetrics fm = g.getFontMetrics(f);
        int height = fm.getHeight();
        int ascent = fm.getAscent();
        for (int i = 0; i < lines.length; i++) {
            g.drawString(lines[i], x1, y1 + (ascent + i * height));
        }
    }

    Figure copy() {
        Text newText = new Text(color, font, x1, y1, x2, y2, lines);
        newText.popup = popup;
        newText.move(MOVE_DX, MOVE_DY);
        return newText;
    }

    void changeFont(Graphics g, Font font) {
        this.font = font;
        FontMetrics fm = g.getFontMetrics(font);
        int w;

        x2 = x1;
        y2 = y1;

        if (lines.length == 1 && lines[0].equals("")) return;

        int maxWidth = 0;
        for (int i = 0; i < lines.length; i++) {
            String s = lines[i];
            w = fm.stringWidth(s);
            if (w > maxWidth) maxWidth = w;
        }
        int maxHeight = lines.length * fm.getHeight();

        x2 = x1 + maxWidth;
        y2 = y1 + maxHeight;
    }
}
