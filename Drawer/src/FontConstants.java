import java.awt.*;

public class FontConstants {
    public static String INIT_FONT_FAMILY = "Courier New";
    public static int INIT_FONT_STYLE = Font.PLAIN;
    public static int INIT_FONT_SIZE = 16;

    public static final String[] StyleNames = new String[]{
            "Plain", "Italic", "Bold", "Bold Italic"
    };
    public static final int[] StyleValues = {
            Font.PLAIN, Font.ITALIC, Font.BOLD, Font.BOLD + Font.ITALIC
    };
    public static final String[] SizeNames = new String[]{
            "8", "9", "10", "11", "12", "14", "16", "18", "20",
            "22", "24", "26", "28", "36", "48", "72"
    };

    public static int getIndexOf(String name, String names[]) {
        for (int i = 0; i < names.length; i++) {
            if (name.equals(names[i])) return i;
        }
        return 0;
    }

    public static int getStyleIndexOf(int value) {
        for (int i = 0; i < StyleValues.length; i++) {
            if (value == StyleValues[i]) return i;
        }
        return 0;
    }

    public static int getSizeIndexOf(String size) {
        for (int i = 0; i < SizeNames.length; i++) {
            if (size.equals(SizeNames[i])) return i;
        }
        return 0;
    }
}
