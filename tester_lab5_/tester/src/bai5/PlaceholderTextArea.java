package bai5;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextArea;

public class PlaceholderTextArea extends JTextArea {

    private String placeholder;

    public PlaceholderTextArea() {
        super();
    }

    public PlaceholderTextArea(int rows, int columns, String placeholder) {
        super(rows, columns);
        this.placeholder = placeholder;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (placeholder == null || placeholder.isEmpty()) {
            return;
        }

        String txt = getText();
        if (txt != null && !txt.isEmpty()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setColor(new Color(160, 160, 160));
            g2.setFont(getFont().deriveFont(Font.ITALIC));

            FontMetrics fm = g2.getFontMetrics();
            int x = getInsets().left + 2;
            int y = getInsets().top + fm.getAscent();
            g2.drawString(placeholder, x, y);
        } finally {
            g2.dispose();
        }
    }
}
