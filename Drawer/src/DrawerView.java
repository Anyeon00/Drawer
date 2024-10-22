import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.event.*;
import java.awt.image.*;
import javax.imageio.*;

public class DrawerView extends JPanel
        implements MouseListener, MouseMotionListener {
    static class TextEditor extends JTextArea
            implements DocumentListener, MouseListener, KeyListener {
        private DrawerView canvas;
        private int INIT_WIDTH = 100;
        private int INIT_HEIGHT = 150;
        private int DELTA = 30;
        private Font font;
        private Font originFont = null;
        private Color originColor = null;
        private FontMetrics fm;
        int x;
        int y;
        int width;
        int height;

        /*
                private DrawerView canvas;
                private int INIT_WIDTH = 100;
                private int INIT_HEIGHT = 150;
                private int DELTA = 30;
                private Font font;
                private FontMetrics fm;
                int x;
                int y;
                int width;
                int height;
        */
        TextEditor(DrawerView canvas) {
            super();
            this.canvas = canvas;
            setBackground(canvas.getBackground());
            getDocument().addDocumentListener(this);
        }

        public void start(Text text) {
            font = text.getFont();

            originFont = text.getFont();
            originColor = text.getColor();

            setText(text.getText());

            fm = null;
            setFont(font);
            this.x = text.getX1();
            this.y = text.getY1();
            this.width = text.getX2() - text.getX1();
            this.height = text.getY2() - text.getY1();

            setBounds(x, y, width, height);
            Graphics g = canvas.getGraphics();
            g.setColor(Color.blue);
            g.drawRect(x, y, width - 2, height);
            setBorder(BorderFactory.createLineBorder(Color.blue));
            setCaretPosition(0);

            canvas.removeMouseListener(canvas);
            canvas.removeMouseMotionListener(canvas);

            canvas.add(this);
            requestFocus();

            canvas.addMouseListener(this);
            this.addKeyListener(this);

            this.repaint();
        }

        public void start(int x, int y) {
            font = canvas.selectedFont;

            originFont = null;
            originColor = null;

            setText("");
            fm = null;
            this.x = x;
            this.y = y;
            this.width = INIT_WIDTH;
            this.height = INIT_HEIGHT;

            setBounds(x, y, width, height);
            Graphics g = canvas.getGraphics();
            g.setColor(Color.blue);
            g.drawRect(x, y, INIT_WIDTH - 2, INIT_HEIGHT);
            setBorder(BorderFactory.createLineBorder(Color.blue));
            setCaretPosition(0);

            canvas.removeMouseListener(canvas);
            canvas.removeMouseMotionListener(canvas);

            canvas.add(this);
            requestFocus();

            canvas.addMouseListener(this);
//			this.addKeyListener(this);

            this.repaint();
			/*
			setText("");
			font = getFont();
			fm = canvas.getGraphics().getFontMetrics();
			this.x = x;
			this.y = y;
			this.width = INIT_WIDTH;
			this.height = INIT_HEIGHT;

			setBounds(x,y,width,height);
			Graphics g = canvas.getGraphics();
			g.setColor(Color.blue);
			g.drawRect(x,y,INIT_WIDTH-2,INIT_HEIGHT);
			setBorder(BorderFactory.createLineBorder(Color.blue));
			setCaretPosition(0);

			canvas.removeMouseListener(canvas);
			canvas.removeMouseMotionListener(canvas);

			canvas.add(this);
			requestFocus();

			canvas.addMouseListener(this);
			*/
        }

        public void insertUpdate(DocumentEvent e) {
            if (fm == null) {
                setFont(font);
                fm = getGraphics().getFontMetrics();
            }
            String text = getText();
            String[] lines = text.split("\n");

            int w;
            int maxWidth = 0;
            for (int i = 0; i < lines.length; i++) {
                String s = lines[i];
                w = fm.stringWidth(s);
                if (w > maxWidth) maxWidth = w;
            }
            if (maxWidth > width) {
                width = width + DELTA;
                setBounds(x, y, width, height);
            }
            int maxHeight = lines.length * fm.getHeight();
            if (maxHeight > height) {
                height = height + DELTA;
                setBounds(x, y, width, height);
            }
			/*
			String text = getText();
			String[] lines = text.split("\n");

			int w;
			int maxWidth = 0;
			for (int i = 0; i < lines.length; i++)
			{
				String s = lines[i];
				w = fm.stringWidth(s);
				if (w > maxWidth) maxWidth = w;
			}
			if (maxWidth > width)
			{
				width = width + DELTA;
				setBounds(x,y,width,height);
			}
			int maxHeight = lines.length * fm.getHeight();
			if (maxHeight > height)
			{
				height = height + DELTA;
				setBounds(x,y,width,height);
			}
			*/
        }

        public void removeUpdate(DocumentEvent e) {
        }

        public void changedUpdate(DocumentEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
            canvas.remove(this);

            canvas.removeMouseListener(this);

            canvas.addMouseListener(canvas);
            canvas.addMouseMotionListener(canvas);

            String text = getText();
            String[] lines = text.split("\n");
            int w;

            if (lines.length == 1 && lines[0].equals("")) return;

            int maxWidth = 0;
            for (int i = 0; i < lines.length; i++) {
                String s = lines[i];
                w = fm.stringWidth(s);
                if (w > maxWidth) maxWidth = w;
            }
            int maxHeight = lines.length * fm.getHeight();

            Text newFigure =
                    new Text(canvas.selectedColor, canvas.selectedFont, x, y, x + maxWidth, y + maxHeight, lines);
            newFigure.setPopup(canvas.textPopup());
            canvas.addFigure(newFigure);

            canvas.repaint();
        }

        public void stopEditor() {
            if (fm == null) {
                canvas.getGraphics().setFont(font);
                fm = canvas.getGraphics().getFontMetrics();
            }

            canvas.remove(this);

            canvas.removeMouseListener(this);
            this.removeKeyListener(this);

            canvas.addMouseListener(canvas);
            canvas.addMouseMotionListener(canvas);

            String text = getText();
            String[] lines = text.split("\n");
            int w;

            if (lines.length == 1 && lines[0].equals("")) {
                canvas.repaint();
                return;
            }

            int maxWidth = 0;
            for (int i = 0; i < lines.length; i++) {
                String s = lines[i];
                w = fm.stringWidth(s);
                if (w > maxWidth) maxWidth = w;
            }
            int maxHeight = lines.length * fm.getHeight();

            if (originColor == null) originColor = canvas.selectedColor;

            Text newFigure =
                    new Text(originColor, originFont, x, y, x + maxWidth, y + maxHeight, lines);
            newFigure.setPopup(canvas.textPopup());
            if (originFont == null) {
                newFigure.changeFont(canvas.getGraphics(), font);
            } else {
                newFigure.changeFont(canvas.getGraphics(), originFont);
            }

            originFont = null;
            originColor = null;

            canvas.addFigure(newFigure);

//			canvas.setWhatToDraw(ID_MOUSE);

            canvas.repaint();
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                stopEditor();
            }
        }

        public void keyTyped(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
        }
    }

    public static String[] figureType
            = {"Point", "Box", "Line", "Arrow", "Circle", "Triangle", "Pentagon",
            "Star", "TV", "Kite", "Text", "ImageObject"};
    public static ArrayList<String> figureTypeNames
            = new ArrayList<>();

    // static initializer
    static {
        for (int i = 0; i < figureType.length; i++) {
            figureTypeNames.add(figureType[i]);
        }
    }

    public static int INIT_WIDTH = 3000;
    public static int INIT_HEIGHT = 1500;
    public static int DELTA = 500;

    public static int ID_POINT = 0;
    public static int ID_BOX = 1;
    public static int ID_LINE = 2;
    public static int ID_ARROW = 3;
    public static int ID_CIRCLE = 4;
    public static int ID_TRIANGLE = 5;
    public static int ID_PENTAGON = 6;
    public static int ID_STAR = 7;
    public static int ID_TV = 8;
    public static int ID_KITE = 9;
    public static int ID_TEXT = 10;
    public static int ID_IMAGE = 11;

    public static int NOTHING = 0;
    public static int DRAWING = 1;
    public static int MOVING = 2;

    private int actionMode;
    private int whatToDraw;
    private Figure selectedFigure;
    private ArrayList<Figure> figures = new ArrayList<>();

    private int currentX;
    private int currentY;

    private Popup mainPopup;

    private Popup pointPopup;
    private Popup boxPopup;
    private Popup linePopup;
    private Popup arrowPopup;
    private Popup circlePopup;
    private Popup trianglePopup;
    private Popup pentagonPopup;
    private Popup starPopup;
    private Popup tvPopup;
    private Popup kitePopup;
    private Popup textPopup;
    private Popup imagePopup;
    private Popup[] popups = new Popup[figureType.length];

    private SelectAction pointAction;
    private SelectAction boxAction;
    private SelectAction lineAction;
    private SelectAction arrowAction;
    private SelectAction circleAction;
    private SelectAction triangleAction;
    private SelectAction pentagonAction;
    private SelectAction starAction;
    private SelectAction tvAction;
    private SelectAction kiteAction;
    private SelectAction textAction;
    private SelectAction imageAction;

    private DrawerFrame mainFrame;

    private double zoomRatio = 1.0;

    private int width = INIT_WIDTH;
    private int height = INIT_HEIGHT;

    private TextEditor textEditor;

    private Font selectedFont;
    private String fontFamily;
    private int fontStyle;
    private int fontSize;
    private FontDialog fontDialog = null;
    private JFileChooser imageChooser = null;

    private Color selectedColor = Color.black;

    DrawerView(DrawerFrame mainFrame) {
        setLayout(null);
        this.mainFrame = mainFrame;

        textEditor = new TextEditor(this);

/* Icon
		pointAction = new SelectAction("Point(P)",new ImageIcon("point.gif"),this,ID_POINT);
		boxAction = new SelectAction("Box(B)",new ImageIcon("box.gif"),this,ID_BOX);
		lineAction = new SelectAction("Line(L)",new ImageIcon("line.gif"),this,ID_LINE);
		circleAction = new SelectAction("Circle(C)",new ImageIcon("circle.gif"),this,ID_CIRCLE);
		tvAction = new SelectAction("TV(T)",new ImageIcon("tv.gif"),this,ID_TV);
		kiteAction = new SelectAction("Kite(K)",new ImageIcon("kite.gif"),this,ID_KITE);
		textAction = new SelectAction("Text(X)",new ImageIcon("text.gif"),this,ID_TEXT);
*/

// Icon
        pointAction = new SelectAction("Point(P)", new FigureIcon(figureType[0]), this, ID_POINT);
        boxAction = new SelectAction("Box(B)", new FigureIcon(figureType[1]), this, ID_BOX);
        lineAction = new SelectAction("Line(L)", new FigureIcon(figureType[2]), this, ID_LINE);
        arrowAction = new SelectAction("Arrow(A)", new FigureIcon(figureType[3]), this, ID_ARROW);
        circleAction = new SelectAction("Circle(C)", new FigureIcon(figureType[4]), this, ID_CIRCLE);
        triangleAction = new SelectAction("Triangle(T)", new FigureIcon(figureType[5]), this, ID_TRIANGLE);
        pentagonAction = new SelectAction("Pentagon(P)", new FigureIcon(figureType[6]), this, ID_PENTAGON);
        starAction = new SelectAction("Star(S)", new FigureIcon(figureType[7]), this, ID_STAR);
        tvAction = new SelectAction("TV(T)", new FigureIcon(figureType[8]), this, ID_TV);
        kiteAction = new SelectAction("Kite(K)", new FigureIcon(figureType[9]), this, ID_KITE);
        textAction = new SelectAction("Text(X)", new FigureIcon(figureType[10]), this, ID_TEXT);
        imageAction = new SelectAction("Image(I)", new FigureIcon(figureType[11]), this, ID_IMAGE);

        mainPopup = new MainPopup(this);
        pointPopup = new FigurePopup(this, "Point", false);
        boxPopup = new FigurePopup(this, "Box", true);
        linePopup = new FigurePopup(this, "Line", false);
        arrowPopup = new FigurePopup(this, "Arrow", true);
        circlePopup = new FigurePopup(this, "Circle", true);
        trianglePopup = new FigurePopup(this, "Triangle", true);
        pentagonPopup = new FigurePopup(this, "Pentagon", true);
        starPopup = new FigurePopup(this, "Star", true);
        tvPopup = new TVPopup(this);
        kitePopup = new FigurePopup(this, "Kite", true);
        textPopup = new TextPopup(this, "Text");
        imagePopup = new ImagePopup(this, "Image");

        int i = 0;
        popups[i++] = pointPopup;
        popups[i++] = boxPopup;
        popups[i++] = linePopup;
        popups[i++] = arrowPopup;
        popups[i++] = circlePopup;
        popups[i++] = trianglePopup;
        popups[i++] = pentagonPopup;
        popups[i++] = starPopup;
        popups[i++] = tvPopup;
        popups[i++] = kitePopup;
        popups[i++] = textPopup;
        popups[i++] = imagePopup;

        selectedFont = new Font(FontConstants.INIT_FONT_FAMILY,
                FontConstants.INIT_FONT_STYLE,
                FontConstants.INIT_FONT_SIZE);
        fontDialog = new FontDialog("Font dialog", this);
        imageChooser = createImageChooser();

        actionMode = NOTHING;
        setWhatToDraw(ID_BOX);
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(new Dimension(width, height));
    }

    private JFileChooser createImageChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    String filename = f.getName().toLowerCase();
                    return filename.endsWith(".jpg") || filename.endsWith(".jpeg") ||
                            filename.endsWith(".png") || filename.endsWith(".gif");
                }
            }

            public String getDescription() {
                return "Image files (*.jpg, *.jpeg, *.png, *.gif)";
            }
        });
        return chooser;
    }

    public ArrayList<Figure> getFigures() {
        return figures;
    }

    public void increaseHeight() {
        height = height + DELTA;
        setPreferredSize(new Dimension(width, height));
    }

    public void increaseWidth() {
        width = width + DELTA;
        setPreferredSize(new Dimension(width, height));
    }

    SelectAction getPointAction() {
        return pointAction;
    }

    SelectAction getBoxAction() {
        return boxAction;
    }

    SelectAction getLineAction() {
        return lineAction;
    }

    SelectAction getArrowAction() {
        return arrowAction;
    }

    SelectAction getCircleAction() {
        return circleAction;
    }

    SelectAction getTriangleAction() {
        return triangleAction;
    }

    SelectAction getPentagonAction() {
        return pentagonAction;
    }

    SelectAction getStarAction() {
        return starAction;
    }

    SelectAction getTVAction() {
        return tvAction;
    }

    SelectAction getKiteAction() {
        return kiteAction;
    }

    SelectAction getTextAction() {
        return textAction;
    }

    SelectAction getImageAction() {
        return imageAction;
    }

    Popup pointPopup() {
        return pointPopup;
    }

    Popup boxPopup() {
        return boxPopup;
    }

    Popup linePopup() {
        return linePopup;
    }

    Popup arrowPopup() {
        return arrowPopup;
    }

    Popup circlePopup() {
        return circlePopup;
    }

    Popup trianglePopup() {
        return trianglePopup;
    }

    Popup pentagonPopup() {
        return pentagonPopup;
    }

    Popup starPopup() {
        return starPopup;
    }

    Popup tvPopup() {
        return tvPopup;
    }

    Popup kitePopup() {
        return kitePopup;
    }

    Popup textPopup() {
        return textPopup;
    }

    Popup imagePopup() {
        return imagePopup;
    }

    public void setCurrentColor(Color color) {
        selectedColor = color;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public int getFontStyle() {
        return fontStyle;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setSelectedFont(Font font) {
        selectedFont = font;
        fontFamily = font.getFamily();
        fontStyle = font.getStyle();
        fontSize = font.getSize();
    }

    public void changeFontForText() {
        if (selectedFigure == null) return;
        if (!(selectedFigure instanceof Text)) return;
        Text text = (Text) selectedFigure;
        fontDialog.setFontSelection(text.getFont());
        fontDialog.setVisible(true);
        Font font = fontDialog.getFont();
        if (font == null) return;
        text.changeFont(getGraphics(), font);
        text.makeRegion();
        repaint();
    }

    public void editText() {
        if (selectedFigure == null) return;
        if (!(selectedFigure instanceof Text)) return;
        Text text = (Text) selectedFigure;
        textEditor.start(text);
        figures.remove(selectedFigure);
        selectedFigure = null;
    }


    void setWhatToDraw(int id) {
        whatToDraw = id;
        mainFrame.writeFigureType(figureType[id]);
    }

    public void doFileNew() {
        figures.clear();
        repaint();
    }

    public void doOpen(String fileName) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            figures = (ArrayList<Figure>) (ois.readObject());
            ois.close();
            fis.close();

			/*
			for(Figure ptr : figures) {
				if (ptr instanceof Point)
				{
					ptr.setPopup(pointPopup);
				} else if (ptr instanceof Box)
				{
					ptr.setPopup(boxPopup);
				} else if (ptr instanceof Line)
				{
					ptr.setPopup(linePopup);
				} else if (ptr instanceof Circle)
				{
					ptr.setPopup(circlePopup);
				} else if (ptr instanceof TV)
				{
					ptr.setPopup(tvPopup);
				} else if (ptr instanceof Kite)
				{
					ptr.setPopup(kitePopup);
				} else if (ptr instanceof Text)
				{
					ptr.setPopup(textPopup);
				}
			}
*/

            for (Figure ptr : figures) {
                String figureTypeName = ptr.getClass().getName();
                int index = figureTypeNames.indexOf(figureTypeName);
                ptr.setPopup(popups[index]);
            }

            repaint();
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        }
    }

    public void doSave(String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(figures);
            oos.flush();
            oos.close();
            fos.close();
        } catch (IOException ex) {
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        ((Graphics2D) g).scale(zoomRatio, zoomRatio);

        for (int i = 0; i < figures.size(); i++) {
            Figure pFigure = figures.get(i);
            pFigure.draw(g); // dynamic binding
        }
    }

    public void zoom(int ratio) {
        zoomRatio = (double) ratio / 100.0;
        repaint();
        removeMouseListener(this);
        removeMouseMotionListener(this);
        if (ratio == 100) {
            addMouseListener(this);
            addMouseMotionListener(this);
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (e.getButton() == MouseEvent.BUTTON3) {
            actionMode = NOTHING;
            return;
        }
        selectedFigure = find(x, y);
        if (selectedFigure != null) {
            actionMode = MOVING;
            currentX = x;
            currentY = y;
            figures.remove(selectedFigure);
            repaint();
            return;
        }

        if (whatToDraw == ID_POINT) {
            selectedFigure = new Point(selectedColor, x, y);
            selectedFigure.setPopup(pointPopup);
        } else if (whatToDraw == ID_BOX) {
            selectedFigure = new Box(selectedColor, x, y);
            selectedFigure.setPopup(boxPopup);
        } else if (whatToDraw == ID_LINE) {
            selectedFigure = new Line(selectedColor, x, y);
            selectedFigure.setPopup(linePopup);
        } else if (whatToDraw == ID_ARROW) {
            selectedFigure = new Arrow(selectedColor, x, y);
            selectedFigure.setPopup(arrowPopup);
        } else if (whatToDraw == ID_CIRCLE) {
            selectedFigure = new Circle(selectedColor, x, y);
            selectedFigure.setPopup(circlePopup);
        } else if (whatToDraw == ID_TRIANGLE) {
            selectedFigure = new Triangle(selectedColor, x, y);
            selectedFigure.setPopup(trianglePopup);
        } else if (whatToDraw == ID_PENTAGON) {
            selectedFigure = new Pentagon(selectedColor, x, y);
            selectedFigure.setPopup(pentagonPopup);
        } else if (whatToDraw == ID_STAR) {
            selectedFigure = new Star(selectedColor, x, y);
            selectedFigure.setPopup(starPopup);
        } else if (whatToDraw == ID_TV) {
            selectedFigure = new TV(selectedColor, x, y, true);
            selectedFigure.setPopup(tvPopup);
            addFigure(selectedFigure);
            selectedFigure = null;
            actionMode = NOTHING;
            return;
        } else if (whatToDraw == ID_KITE) {
            selectedFigure = new Kite(selectedColor, x, y);
            selectedFigure.setPopup(kitePopup);
        } else if (whatToDraw == ID_TEXT) {
            selectedFigure = null;
            actionMode = NOTHING;
            textEditor.start(x, y);
            return;
        } else if (whatToDraw == ID_IMAGE) {
            Image image = getImageFromImageChooser();
            if (image == null) {
                selectedFigure = null;
                actionMode = NOTHING;
                return;
            }
            selectedFigure = new ImageObject((BufferedImage) image, x, y);
            selectedFigure.setPopup(imagePopup);
            selectedFigure.makeRegion();
            addFigure(selectedFigure);
            selectedFigure = null;
            actionMode = NOTHING;
            return;
        }

        actionMode = DRAWING;
    }

    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (e.isPopupTrigger()) {
            selectedFigure = find(x, y);
            if (selectedFigure == null) {
                mainPopup.popup(this, x, y);
            } else {
                selectedFigure.popup(this, x, y);
            }
            return;
        }

        if (selectedFigure == null) return;

        Graphics g = getGraphics();
        if (actionMode == DRAWING) {
            selectedFigure.setXY2(x, y);
        }
        selectedFigure.draw(g);
        addFigure(selectedFigure);
        selectedFigure = null;
    }

    private Image getImageFromImageChooser() {
        Image newImage = null;
        int returnVal = imageChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = imageChooser.getSelectedFile();
            try {
                newImage = ImageIO.read(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newImage;
    }

    public void removeFromFigures(Figure ptr) {
        selectedFigure = null;
        figures.remove(ptr);
        repaint();
    }

    public void removeFromFigures(int index) {
        if (index < 0) return;
        selectedFigure = null;
        figures.remove(index);
        repaint();
    }

    public void addFigure(Figure newFigure) {
        newFigure.makeRegion();
        figures.add(newFigure);
        repaint();
    }

    public void copyFigure() {
        if (selectedFigure == null) return;
        Figure newFigure = selectedFigure.copy();
        addFigure(newFigure);
        selectedFigure = newFigure;
    }

    public void fillFigure() {
        if (selectedFigure == null) return;
        selectedFigure.setFill();
        repaint();
    }

    public void deleteFigure() {
        if (selectedFigure == null) return;
        figures.remove(selectedFigure);
        selectedFigure = null;
        repaint();
    }

    void setColorForSeletedFigure(Color color) {
        if (selectedFigure == null) return;
        selectedFigure.setColor(color);
        repaint();
    }

    public void showColorChooser() {
        Color color = JColorChooser.showDialog(null,
                "Color Chooser", Color.black);
        setColorForSeletedFigure(color);
    }

    public void onOffTV() {
        if (selectedFigure == null) return;
        if (selectedFigure instanceof TV) {
            TV tv = (TV) selectedFigure;
            tv.pressPowerButton();
            repaint();
        }
    }

    public void setAntenna() {
        if (selectedFigure == null) return;
        if (selectedFigure instanceof TV) {
            TV tv = (TV) selectedFigure;
            tv.setAntenna();
            repaint();
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Graphics g = getGraphics();
        g.setXORMode(getBackground());
        if (actionMode == DRAWING) {
            selectedFigure.drawing(g, x, y);
        } else if (actionMode == MOVING) {
            selectedFigure.move(g, x - currentX, y - currentY);
            currentX = x;
            currentY = y;
        }
    }

    private Figure find(int x, int y) {
        for (int i = 0; i < figures.size(); i++) {
            Figure pFigure = figures.get(i);
            if (pFigure.contains(x, y)) {
                return pFigure;
            }
        }
        return null;
    }

    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        selectedFigure = find(x, y);
        if (selectedFigure != null) {
            setCursor(Cursor.getPredefinedCursor
                    (Cursor.CROSSHAIR_CURSOR));
        } else {
            setCursor(Cursor.getDefaultCursor());
        }

        mainFrame.writePosition("[" + x + "," + y + "]");
    }
}
