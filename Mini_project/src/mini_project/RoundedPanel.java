/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mini_project;

/**
 *
 * @author ACER
 */
import java.awt.*;
import javax.swing.*;

public class RoundedPanel extends JPanel {
    private Image profileImage;
    private int cornerRadius;

    public RoundedPanel(Image profileImage, int cornerRadius) {
        this.profileImage = profileImage;
        this.cornerRadius = cornerRadius;
        setOpaque(false); // Make the panel transparent
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw rounded rectangle
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);
        
        // Draw profile image
        if (profileImage != null) {
            int imgWidth = profileImage.getWidth(this);
            int imgHeight = profileImage.getHeight(this);
            int x = (width - imgWidth) / 2;
            int y = (height - imgHeight) / 2;
            graphics.drawImage(profileImage, x, y, imgWidth, imgHeight, this);
        }
        
        graphics.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        // Adjust preferred size based on image size
        if (profileImage != null) {
            return new Dimension(profileImage.getWidth(this), profileImage.getHeight(this));
        } else {
            return super.getPreferredSize();
        }
    }

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
        repaint(); // Repaint the panel to reflect the change
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint(); // Repaint the panel to reflect the change
    }
}
