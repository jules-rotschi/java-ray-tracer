import java.awt.Dimension;

import javax.swing.JFrame;

public class RayTracer {
  public static void main(String[] args) throws Exception {
    final int FRAME_RATE = 25;

    final Scene scene = new Scene(new Sky(new WorldColor(0.01, 0.05, 0.1), 1000));

    final WorldColor ALMOST_WHITE = new WorldColor(0.9, 0.9, 0.9);

    final Plane floor = new Plane(
      new Point(0, -1, 0),
      new Vector3(0, 0, 1),
      new Vector3(1, 0, 0),
      ALMOST_WHITE
    );
    scene.add(floor);

    final Sphere sphere1 = new Sphere(new Point(-1, 0, -5), 1, ALMOST_WHITE);
    scene.add(sphere1);

    final Sphere sphere2 = new Sphere(new Point(1, -0.75, -5), 0.25, ALMOST_WHITE);
    scene.add(sphere2);

    // Cuboid cuboid = new Cuboid(new Point(0, -1, -4.5), 1, 1, 1, Math.PI / 4, Math.PI / 4, almostWhite);
    // scene.add(cuboid);

    final Light keyLight = new Light(
      new Point(-2, 0.5, -3),
      0.25,
      1000,
      new WorldColor(1, 0.5, 0.2)
    );
    scene.add(keyLight);

    final Light fillLight = new Light(
      new Point(2, 0.5, -4),
      0.25,
      1000,
      new WorldColor(0.1, 0.7, 1)
    );
    scene.add(fillLight);

    final Light backLight = new Light(
      new Point(0, 2, -6),
      0.25,
      1000,
      new WorldColor(0.8, 0.95, 1)
    );
    scene.add(backLight);

    final ImageDefinition sensorDefinition = new ImageDefinition(1280, 720);
    final Sensor sensor = new Sensor(sensorDefinition, 0.036, 800);
    final Lens lens = new Lens(0.024, 0.07, 0.05, 5.6);
    final Camera camera = new Camera(sensor, lens, FRAME_RATE, 180, new Point(0, 0, 3));
    scene.setCamera(camera);

    final Renderer renderer = new Renderer(scene);
    
    final RenderPanel renderPanel = new RenderPanel(new Dimension(1280, 720), renderer);
    
    final JFrame frame = new JFrame("Ray Tracer");
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setLocationByPlatform(true);
    frame.add(renderPanel);
    frame.pack();
    renderPanel.requestFocus();
    frame.setVisible(true);
    
    final CameraControl cameraControl = new CameraControl(camera, renderPanel);
    final SceneManager sceneManager = new SceneManager(scene, cameraControl);
    final MainLoop mainLoop = new MainLoop(sceneManager, renderPanel, FRAME_RATE);
    mainLoop.start();
  }
}
