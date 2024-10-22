import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.awt.print.*;

public class DrawerFrame extends JFrame {
    static class ZoomBox extends JComboBox implements ActionListener {
        DrawerView canvas;
        static String[] size = {"100", "80", "50"};

        ZoomBox(DrawerView canvas) {
            super(size);
            this.canvas = canvas;
            setMaximumSize(new Dimension(60, 30));
//			setMaximumSize(new Dimension(1500,200));
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            JComboBox box = (JComboBox) e.getSource();
            String ratio = (String) box.getSelectedItem();
            canvas.zoom(Integer.parseInt(ratio));
        }
    }

    static class PrintableView implements Printable {
        DrawerView canvas;
        String fileName;

        PrintableView(DrawerView canvas, String fileName) {
            this.canvas = canvas;
            this.fileName = fileName;
        }

        public int print(Graphics g, PageFormat format, int pagenum) {
            if (pagenum > 0) return Printable.NO_SUCH_PAGE;

            Graphics2D g2 = (Graphics2D) g;
            double pageX = format.getImageableX() + 1;
            double pageY = format.getImageableY() + 1;
            g2.translate(pageX, pageY);

            int pageWidth = (int) format.getImageableWidth() - 2;
            int pageHeight = (int) format.getImageableHeight() - 2;

            g2.drawRect(-1, -1, pageWidth + 2, pageHeight + 2);

            g2.setClip(0, 0, pageWidth, pageHeight);
            g2.scale(0.5, 0.5);

            canvas.paint(g);

            g2.scale(2.0, 2.0);
            g2.drawString(fileName, 0, pageHeight);
            return Printable.PAGE_EXISTS;
        }
    }

    SelectToolBar selectToolBar;
    ColorToolBar colorToolBar;
    FontToolBar fontToolBar;

    DrawerView canvas;
    StatusBar statusBar;
    FigureDialog dialog = null;
    TableDialog tableDialog = null;
    TreeDialog treeDialog = null;
    String fileName = "noname.jdr";

    public void writePosition(String s) {
        statusBar.writePosition(s);
    }

    public void writeFigureType(String s) {
        statusBar.writeFigureType(s);
    }

    public void doOpen() {
        JFileChooser chooser =
                new JFileChooser(System.getProperty("user.dir"));
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.setFileFilter(new FileNameExtensionFilter("JDrawer file", "jdr"));
        int value = chooser.showOpenDialog(null);
        if (value != JFileChooser.APPROVE_OPTION) return;
        fileName = chooser.getSelectedFile().getPath();
        canvas.doOpen(fileName);
        setTitle("Drawer - [" + fileName + "]");
    }

    public void doSaveAs() {
        JFileChooser chooser =
                new JFileChooser(System.getProperty("user.dir"));
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        chooser.setFileFilter(new FileNameExtensionFilter("JDrawer file", "jdr"));
        int value = chooser.showSaveDialog(null);
        if (value != JFileChooser.APPROVE_OPTION) return;
        fileName = chooser.getSelectedFile().getPath();
        if (fileName.endsWith(".jdr") == false) {
            fileName = fileName + ".jdr";
        }
        setTitle("Drawer - [" + fileName + "]");
        canvas.doSave(fileName);
    }

    public void doPrint() {
        PrinterJob job = PrinterJob.getPrinterJob();

        PageFormat page = job.defaultPage();
        page.setOrientation(PageFormat.LANDSCAPE);

        Printable printable = new PrintableView(canvas, fileName);
        job.setPrintable(printable, page);

        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, ex.toString(),
                        "PrinterException", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    DrawerFrame() {
        setTitle("Drawer - [noname.jdr]");
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screenWidth = d.width;
        setSize(screenWidth * 2 / 3, screenHeight * 2 / 3);
        setLocation(screenWidth / 6, screenHeight / 6);
        Image img = tk.getImage("ball.gif");
        setIconImage(img);

        Container container = this.getContentPane();
        statusBar = new StatusBar();
        container.add(statusBar, "South");
        canvas = new DrawerView(this);
        JScrollPane sp = new JScrollPane(canvas);
        container.add(sp, "Center");

        sp.registerKeyboardAction(new ActionListener() {
                                      public void actionPerformed(ActionEvent evt) {
                                          JScrollBar scrollBar = sp.getVerticalScrollBar();
                                          scrollBar.setValue(scrollBar.getValue()
                                                  + scrollBar.getBlockIncrement());
                                      }
                                  }
                , KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0)
                , JComponent.WHEN_IN_FOCUSED_WINDOW
        );
        sp.registerKeyboardAction(new ActionListener() {
                                      public void actionPerformed(ActionEvent evt) {
                                          JScrollBar scrollBar = sp.getVerticalScrollBar();
                                          scrollBar.setValue(scrollBar.getValue()
                                                  - scrollBar.getBlockIncrement());
                                      }
                                  }
                , KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0)
                , JComponent.WHEN_IN_FOCUSED_WINDOW
        );
        sp.registerKeyboardAction((evt) -> {
                    JScrollBar scrollBar = sp.getHorizontalScrollBar();
                    scrollBar.setValue(scrollBar.getValue()
                            + scrollBar.getBlockIncrement());
                }
                , KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, InputEvent.CTRL_MASK)
                , JComponent.WHEN_IN_FOCUSED_WINDOW
        );
        sp.registerKeyboardAction((evt) -> {
                    JScrollBar scrollBar = sp.getHorizontalScrollBar();
                    scrollBar.setValue(scrollBar.getValue()
                            - scrollBar.getBlockIncrement());
                }
                , KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, InputEvent.CTRL_MASK)
                , JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        sp.registerKeyboardAction((evt) -> canvas.increaseHeight()
                , KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, InputEvent.ALT_MASK)
                , JComponent.WHEN_IN_FOCUSED_WINDOW
        );
        sp.registerKeyboardAction((evt) -> canvas.increaseWidth()
                , KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, InputEvent.ALT_MASK)
                , JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        javax.swing.Box toolBarPanel
                = javax.swing.Box.createHorizontalBox();

        selectToolBar = new SelectToolBar(canvas);
        selectToolBar.add(new ZoomBox(canvas));
//		selectToolBar.add(javax.swing.Box.createGlue());

        colorToolBar = new ColorToolBar(canvas);
        fontToolBar = new FontToolBar(canvas);
        fontToolBar.add(javax.swing.Box.createGlue());

        toolBarPanel.add(selectToolBar);
        toolBarPanel.add(colorToolBar);
        toolBarPanel.add(fontToolBar);

        container.add(toolBarPanel, BorderLayout.NORTH);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Dimension sz = canvas.getSize();
                String s = "" + sz.width + " X " + sz.height + " px";
                statusBar.writeSize(s);
            }
        });

        JMenuBar menus = new JMenuBar();
        setJMenuBar(menus);

        JMenu fileMenu = new JMenu("File(F)");
        menus.add(fileMenu);

        JMenuItem newFile = new JMenuItem("New File(N)");
        fileMenu.add(newFile);
        newFile.setMnemonic('N');
        newFile.setIcon(new ImageIcon("new.gif"));
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                InputEvent.CTRL_MASK));
        newFile.addActionListener((e) -> canvas.doFileNew());

        JMenuItem openFile = new JMenuItem("Open(O)");
        fileMenu.add(openFile);
        openFile.setMnemonic('O');
        openFile.setIcon(new ImageIcon("open.gif"));
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                InputEvent.CTRL_MASK));
        openFile.addActionListener((e) -> doOpen());

        JMenuItem saveFile = new JMenuItem("Save(S)");
        fileMenu.add(saveFile);
        saveFile.setMnemonic('S');
        saveFile.setIcon(new ImageIcon("save.gif"));
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                InputEvent.CTRL_MASK));
        saveFile.addActionListener((e) -> canvas.doSave(fileName));

        JMenuItem anotherFile = new JMenuItem("Another Name Save(A)");
        fileMenu.add(anotherFile);
        anotherFile.setMnemonic('A');
        anotherFile.addActionListener((e) -> doSaveAs());

        fileMenu.addSeparator();

        JMenuItem printFile = new JMenuItem("Print(P)");
        fileMenu.add(printFile);
        printFile.addActionListener((e) -> doPrint());

        fileMenu.addSeparator();

        JMenuItem exit = new JMenuItem("Exit(X)");
        fileMenu.add(exit);
        exit.addActionListener(e -> System.exit(0));
//        exit.addActionListener(new ActionListener() {
//                                   public void actionPerformed(ActionEvent e) {
//                                       System.exit(0);
//                                   }
//                               }
//        );

        JMenu figureMenu = new JMenu("Figure(F)");
        menus.add(figureMenu);

        JMenuItem figurePoint = new JMenuItem(canvas.getPointAction());
        figureMenu.add(figurePoint);

        JMenuItem figureBox = new JMenuItem(canvas.getBoxAction());
        figureMenu.add(figureBox);

        JMenuItem figureLine = new JMenuItem(canvas.getLineAction());
        figureMenu.add(figureLine);

        JMenuItem figureArrow = new JMenuItem(canvas.getArrowAction());
        figureMenu.add(figureArrow);

        JMenuItem figureCircle = new JMenuItem(canvas.getCircleAction());
        figureMenu.add(figureCircle);

        JMenuItem figureTriangle = new JMenuItem(canvas.getTriangleAction());
        figureMenu.add(figureTriangle);

        JMenuItem figurePentagon = new JMenuItem(canvas.getPentagonAction());
        figureMenu.add(figurePentagon);

        JMenuItem figureStar = new JMenuItem(canvas.getStarAction());
        figureMenu.add(figureStar);

        JMenuItem figureTV = new JMenuItem(canvas.getTVAction());
        figureMenu.add(figureTV);

        JMenuItem figureKite = new JMenuItem(canvas.getKiteAction());
        figureMenu.add(figureKite);

        JMenuItem figureText = new JMenuItem(canvas.getTextAction());
        figureMenu.add(figureText);

        JMenuItem figureImage = new JMenuItem(canvas.getImageAction());
        figureMenu.add(figureImage);

        JMenu toolMenu = new JMenu("Tool(T)");
        menus.add(toolMenu);

        JMenuItem modalTool = new JMenuItem("Modal (M)");
        toolMenu.add(modalTool);
        modalTool.addActionListener((e) -> {
            if (dialog == null) {
                dialog =
                        new FigureDialog("Figure Dialog", canvas);
                dialog.setModal(true);
            }
            dialog.setVisible(true);
        });

        JMenuItem modalessTool = new JMenuItem("Modaless (S)");
        toolMenu.add(modalessTool);
        modalessTool.addActionListener((e) -> {
            if (dialog == null) {
                dialog =
                        new FigureDialog("Figure Dialog", canvas);
                dialog.setModal(false);
            }
            dialog.setVisible(true);
        });

        JMenuItem tableTool = new JMenuItem("Table (T)");
        toolMenu.add(tableTool);
        tableTool.addActionListener((e) -> {
            if (tableDialog == null) {
                tableDialog =
                        new TableDialog("Table Dialog", canvas);
                tableDialog.setModal(true);
            }
            tableDialog.setVisible(true);
        });

        JMenuItem treeTool = new JMenuItem("Tree (R)");
        toolMenu.add(treeTool);
        treeTool.addActionListener((e) -> {
            if (treeDialog == null) {
                treeDialog =
                        new TreeDialog("Tree Dialog", canvas);
                treeDialog.setModal(true);
            }
            treeDialog.setVisible(true);
        });

        JMenu zoomMenu = new JMenu("Zoom");
        toolMenu.add(zoomMenu);

        JMenuItem zoom100 = new JMenuItem("100%");
        zoomMenu.add(zoom100);
        zoom100.addActionListener((e) -> canvas.zoom(100));

        JMenuItem zoom80 = new JMenuItem("80%");
        zoomMenu.add(zoom80);
        zoom80.addActionListener((e) -> canvas.zoom(80));

        JMenuItem zoom50 = new JMenuItem("50%");
        zoomMenu.add(zoom50);
        zoom50.addActionListener((e) -> canvas.zoom(50));

        JMenu helpMenu = new JMenu("Help(H)");
        menus.add(helpMenu);

        JMenuItem infoHelp = new JMenuItem("Drawer Info(I)");
        helpMenu.add(infoHelp);
        infoHelp.addActionListener((e) ->
                {
                    JOptionPane.showMessageDialog(null,
                            "Author: LeeHyeongSeok\r\nCompany: BUFS",
                            "Drawer Info",
                            JOptionPane.INFORMATION_MESSAGE);
                }
        );

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
/*
		JMenuItem printFile = new JMenuItem("����Ʈ(P)");
		fileMenu.add(printFile);
		printFile.addActionListener((e) -> doPrint());

		fileMenu.addSeparator();

	static class PrintableView implements Printable
	{
		DrawerView canvas;
		String fileName;
		PrintableView(DrawerView canvas,String fileName) {
			this.canvas = canvas;
			this.fileName = fileName;
		}
		public int print(Graphics g, PageFormat format, int pagenum) {
			System.out.println("HERE" + pagenum);
			if (pagenum > 0) return Printable.NO_SUCH_PAGE;

			Graphics2D g2 = (Graphics2D)g;
			double pageX = format.getImageableX()+1;
			double pageY = format.getImageableY()+1;
			g2.translate(pageX,pageY);

			Dimension size = canvas.getSize();
			double pageWidth = format.getImageableWidth()-2;
			double pageHeight = format.getImageableHeight()-2;

			System.out.println("x,y = " + format.getImageableX() 
				+ "," + format.getImageableY());
			System.out.println("w,h = " + pageWidth + "," + pageHeight);
			System.out.println("w,h = " + size.width + "," + size.height);
			System.out.println("HERE" + pagenum);

			g2.drawRect(-1,-1,(int)pageWidth+2,(int)pageHeight+2);
			g2.setClip(0,0,(int)pageWidth,(int)pageHeight);
			
			g2.scale(0.5,0.5);
			canvas.paint(g);

			g2.scale(2.0,2.0);
			g2.drawString(fileName,0,(int)pageHeight);
			
			return Printable.PAGE_EXISTS;
		}
	}
	public void doPrint() {
		PrinterJob job = PrinterJob.getPrinterJob();

		PageFormat page = job.defaultPage();
		page.setOrientation(PageFormat.LANDSCAPE);

		Printable printable = new PrintableView(canvas,fileName);
		job.setPrintable(printable,page);

		if (job.printDialog())
		{
			try
			{
				job.print();
			}
			catch (PrinterException e)
			{
				JOptionPane.showMessageDialog(this,e.toString(),
					"PrinterException",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
*/