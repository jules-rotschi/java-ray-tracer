import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.*;

import javax.swing.JPanel;

public class RenderPanel extends JPanel {
  Dimension size;
  Renderer renderer;
  BufferedImage image;

  RenderPanel(Dimension size, Renderer renderer) throws Exception {
    this.size = size;
    this.renderer = renderer;
    setPreferredSize(size);
    setFocusable(true);
    image = renderer.render();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    int fontSize = 32;
    int paddingTop = 8;
    int paddingLeft = 16;
    int gap = 8;
    g.drawImage(image, 0, 0, getDrawnImageDimension().width, getDrawnImageDimension().height, null);
    g.setColor(Color.WHITE);
    g.setFont(new Font("Figtree", Font.BOLD, fontSize));
    g.drawString(renderer.scene.camera.frameRate + "fps", paddingLeft, fontSize + paddingTop);
    g.drawString("f/" + renderer.scene.camera.lens.getAperture(), paddingLeft, 2 * fontSize + gap + paddingTop);
    g.drawString(renderer.scene.camera.shutterAngle + "°", paddingLeft, 3 * fontSize + 2 * gap + paddingTop);
    g.drawString(renderer.scene.camera.sensor.sensivity + " ISO", paddingLeft, 4 * fontSize + 3 * gap + paddingTop);
    g.drawString(renderer.scene.camera.lens.focalDistance * 1000 + "mm", paddingLeft, 5 * fontSize + 4 * gap + paddingTop);
  }

  private double getAspectRatio() {
    return Double.valueOf(size.width) / size.height;
  }

  private Dimension getDrawnImageDimension() {
    int width = 0;
    int height = 0;

    double imageRatio = renderer.imageDefinition.getAspectRatio();

    if (imageRatio <= getAspectRatio()) {
      height = size.height;
      width = Long.valueOf(Math.round(height * imageRatio)).intValue();
    } else {
      width = size.width;
      height = Double.valueOf(width / imageRatio).intValue();
    }

    return new Dimension(width, height);
  }
}
