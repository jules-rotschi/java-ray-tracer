import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class MainLoop implements ActionListener {
  private SceneManager sceneManager;
  private RenderPanel panel;
  private Timer timer;

  MainLoop(SceneManager sceneManager, RenderPanel panel, int frameRate) {
    this.sceneManager = sceneManager;
    this.panel = panel;
    timer = new Timer(1000 / frameRate, this);
  }

  void start() {
    timer.start();
  }

  void stop() {
    timer.stop();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    sceneManager.updateScene();
    try {
      panel.image = panel.renderer.render();
    } catch (Exception exception) {
      System.err.println("Cannot render image: " + exception.getMessage());
    }
    panel.repaint();
  }
}
