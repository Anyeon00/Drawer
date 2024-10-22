import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class FontDialog extends JDialog {
    static class DialogPanel extends JPanel
            implements ActionListener, ListSelectionListener {
        JDialog dialog;
        DrawerView view;
        JButton okButton, cancelButton;
        private JLabel sampleLabel = new JLabel("Sample...");
        private JLabel fontLabel, styleLabel, sizeLabel;
        private JPanel fontPanel, topPanel, samplePanel, buttonPanel, fontFamilyPanel, fontStylePanel, fontSizePanel;
        private JScrollPane styleScrollPane, fontScrollPane, fontSizeScrollPane;
        private JList<String> fontFamilyList, fontStyleList, fontSizeList;
        private String[] fontFamilies;
        private GraphicsEnvironment env = null;

        public Font font = null;
        public boolean okFlag = false;

        public void setFontSelection(Font font) {
            String family = font.getFamily();
            int style = font.getStyle();
            int size = font.getSize();
            fontFamilyList.setSelectedIndex(FontConstants.getIndexOf(family, fontFamilies));
            fontStyleList.setSelectedIndex(FontConstants.getStyleIndexOf(style));
            fontSizeList.setSelectedIndex(FontConstants.getSizeIndexOf("" + size));
        }

        private JPanel constructFontPanel(DrawerView view) {
            fontPanel = new JPanel(new BorderLayout());
            topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            env = GraphicsEnvironment.getLocalGraphicsEnvironment();

            //FamilyPanel
            fontFamilyPanel = new JPanel();
            fontFamilyPanel.setLayout(new BorderLayout());

            fontLabel = new JLabel("Fonts: ");
            fontFamilyPanel.add(fontLabel, BorderLayout.NORTH);

            fontFamilies = env.getAvailableFontFamilyNames();

            fontFamilyList = new JList<String>(fontFamilies);
            fontFamilyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            fontFamilyList.setSelectedIndex(0);
            fontFamilyList.addListSelectionListener(this);
            fontScrollPane = new JScrollPane(fontFamilyList);
            fontFamilyPanel.add(fontScrollPane, BorderLayout.CENTER);

            topPanel.add(fontFamilyPanel);

            //StylePanel
            fontStylePanel = new JPanel();
            fontStylePanel.setLayout(new BorderLayout());

            styleLabel = new JLabel("Style: ");
            fontStylePanel.add(styleLabel, BorderLayout.NORTH);

            fontStyleList = new JList<String>(FontConstants.StyleNames);
            fontStyleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            fontStyleList.setSelectedIndex(0);
            fontStyleList.addListSelectionListener(this);
            styleScrollPane = new JScrollPane(fontStyleList);
            fontStylePanel.add(styleScrollPane, BorderLayout.CENTER);

            topPanel.add(fontStylePanel);

            //SizePanel
            fontSizePanel = new JPanel();
            fontSizePanel.setLayout(new BorderLayout());

            sizeLabel = new JLabel("Sizes: ");
            fontSizePanel.add(sizeLabel, BorderLayout.NORTH);

            fontSizeList = new JList<String>(FontConstants.SizeNames);
            fontSizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            fontSizeList.setSelectedIndex(0);
            fontSizeList.addListSelectionListener(this);
            fontSizeScrollPane = new JScrollPane(fontSizeList);
            fontSizePanel.add(fontSizeScrollPane, BorderLayout.CENTER);

            topPanel.add(fontSizePanel);

            fontPanel.add(topPanel, BorderLayout.NORTH);
            return fontPanel;
        }

        private JPanel constructBottomPanel() {
            JPanel bottom = new JPanel(new BorderLayout());

            samplePanel = new JPanel();
            samplePanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), "Sample"));
            samplePanel.add(sampleLabel);
            bottom.add(samplePanel, BorderLayout.CENTER);

            buttonPanel = new JPanel();
            buttonPanel.add(okButton = new JButton("Ok"));
            okButton.addActionListener(this);
            buttonPanel.add(cancelButton = new JButton("Cancel"));
            cancelButton.addActionListener(this);
            bottom.add(buttonPanel, BorderLayout.SOUTH);

            return bottom;
        }

        public DialogPanel(JDialog dialog, DrawerView view) {
            this.view = view;
            this.dialog = dialog;
            setLayout(new BorderLayout());

            //Font info Panel
            JPanel fontPanel = constructFontPanel(view);
            add(fontPanel, BorderLayout.CENTER);

            //Sample, Button Panel
            JPanel bottomPanel = constructBottomPanel();
            add(bottomPanel, BorderLayout.SOUTH);
        }

        public void valueChanged(ListSelectionEvent e) {
            font = new Font(fontFamilyList.getSelectedValue(),
                    FontConstants.StyleValues[fontStyleList.getSelectedIndex()],
                    Integer.parseInt(fontSizeList.getSelectedValue()));
            sampleLabel.setFont(font);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == okButton) {
                okFlag = true;
            } else if (e.getSource() == cancelButton) {
                okFlag = false;
            }
            dialog.setVisible(false);
        }
    }

    DialogPanel panel;

    public Font getFont() {
        if (panel.okFlag == false) return null;
        return panel.font;
    }

    public void setFontSelection(Font font) {
        panel.setFontSelection(font);
        panel.updateUI();
    }

    public FontDialog(String title, DrawerView view) {
        super((JFrame) null, title);
        setModal(true);
        setLocation(200, 300);
        setResizable(false);
        setSize(400, 300);

        Container container = getContentPane();
        panel = new DialogPanel(this, view);
        container.add(panel);
    }
}
