package bai5;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFormattedTextField;

public class PlaceholderFormattedTextField extends JFormattedTextField {

    private String placeholder;

    public PlaceholderFormattedTextField() {
        super();
    }

    public PlaceholderFormattedTextField(AbstractFormatter formatter, String placeholder) {
        super(formatter);
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

        String raw = getText();
        if (raw != null && raw.replaceAll("[^0-9]", "").length() > 0) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setColor(new Color(160, 160, 160));
            g2.setFont(getFont().deriveFont(Font.ITALIC));

            FontMetrics fm = g2.getFontMetrics();
            int x = getInsets().left + 2;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(placeholder, x, y);
        } finally {
            g2.dispose();
        }
    }
}
