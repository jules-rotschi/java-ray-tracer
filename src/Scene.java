import java.util.ArrayList;
import java.util.List;

public class Scene {
  Sky sky;
  List<Object> objects;
  List<Light> lights;
  Camera camera;

  Scene(Sky sky) {
    this.sky = sky;
    objects = new ArrayList<Object>();
    lights = new ArrayList<Light>();
  }

  void add(Object object) {
    this.objects.add(object);
  }

  void add(Light light) {
    this.lights.add(light);
  }

  void setCamera(Camera camera) {
    this.camera = camera;
  }
}
