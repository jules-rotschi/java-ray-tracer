public class Light {
  Point position;
  double radius;
  double intensity;
  WorldColor color;

  Light(Point position, double radius, double intensity, WorldColor color) {
    this.position = position;
    this.radius = radius;
    this.intensity = intensity;
    this.color = color;
  }

  double getRedIntensity() {
    return color.red * intensity;
  }

  double getGreenIntensity() {
    return color.green * intensity;
  }

  double getBlueIntensity() {
    return color.blue * intensity;
  }
}
