import java.awt.Color;
import java.awt.image.*;
// import java.util.ArrayList;
// import java.util.List;
import java.util.Optional;

public class Renderer {
  final int MAX_VISIBLE_DISTANCE = 50;

  Scene scene;
  boolean showLights;
  ImageDefinition imageDefinition;
  BufferedImage image;

  Renderer(Scene scene) {
    this.scene = scene;
    this.imageDefinition = scene.camera.sensor.definition;
    image = new BufferedImage(imageDefinition.widthPixel, imageDefinition.heightPixel, BufferedImage.TYPE_INT_RGB);
  }

  BufferedImage render() throws Exception {
    long startTime = System.nanoTime();
    for (int line = 0; line < imageDefinition.heightPixel; line++) {
      for (int linePosition = 0; linePosition < imageDefinition.widthPixel; linePosition++) {
        Color pixelColor = computePixel(linePosition, line);
        image.setRGB(linePosition, line, pixelColor.getRGB());
      }
    }
    long endTime = System.nanoTime();
    System.out.println("Render time = " + (endTime - startTime) / 1000000 + "ms");
    return image;
  }

  private class IntersectionPayload {
    Point position;
    Vector3 normal;
    Object object;

    IntersectionPayload(Point position, Vector3 normal, Object object) {
      this.position = position;
      this.normal = normal;
      this.object = object;
    }
  }

  private Color computePixel(int linePosition, int line) throws Exception {
    Camera camera = scene.camera;
    Ray ray = new Ray(camera.position, camera.getPixelPosition(linePosition, line));

    double redLuminance = 0;
    double greenLuminance = 0;
    double blueLuminance = 0;

    IntersectionPayload intersection = traceRay(ray);

    if (intersection.position.z == Double.POSITIVE_INFINITY) {
      redLuminance += scene.sky.color.red * scene.sky.intensity / Math.PI;
      greenLuminance += scene.sky.color.green * scene.sky.intensity / Math.PI;
      blueLuminance += scene.sky.color.blue * scene.sky.intensity / Math.PI;

      return getPixelFromLuminance(camera, redLuminance, greenLuminance, blueLuminance);
    }

    double redIllumination = 0;
    double greenIllumination = 0;
    double blueIllumination = 0;

    for (int j = 0; j < scene.lights.size(); j++) {
      Light light = scene.lights.get(j);
      if (!isInShadow(intersection.position, light)) {
        Vector3 lightDirection = new Vector3(intersection.position, light.position);
        double orientationFactor = Math.max(Math.cos(lightDirection.getAngleTo(intersection.normal)), 0);
        double lightDistance = lightDirection.getNorm();
        double squaredDistance = lightDistance * lightDistance;

        redIllumination += (light.getRedIntensity() / squaredDistance) * orientationFactor;
        greenIllumination += (light.getGreenIntensity() / squaredDistance) * orientationFactor;
        blueIllumination += (light.getBlueIntensity() / squaredDistance) * orientationFactor;
      }
    }

    redLuminance += (redIllumination * intersection.object.albedo.red) / Math.PI;
    greenLuminance += (greenIllumination * intersection.object.albedo.green) / Math.PI;
    blueLuminance += (blueIllumination * intersection.object.albedo.blue) / Math.PI;

    return getPixelFromLuminance(camera, redLuminance, greenLuminance, blueLuminance);
  }

  private IntersectionPayload traceRay(Ray ray) {

    Point closestIntersection = new Point(0, 0, Double.POSITIVE_INFINITY);
    Object visibleObject = null;

    for (int i = 0; i < scene.objects.size(); i++) {
      Object object = scene.objects.get(i);
      Optional<Point> optionalIntersection = object.getClosestIntersection(ray);
      if (optionalIntersection.isEmpty()) continue;
      Point intersection = optionalIntersection.get();
      double intersectionDistance = intersection.getDistanceFrom(ray.origin);
      if (!ray.isInPositiveDirection(intersection) || intersectionDistance > MAX_VISIBLE_DISTANCE) continue;
      if (intersectionDistance < closestIntersection.getDistanceFrom(ray.origin)) {
        closestIntersection = intersection;
        visibleObject = object;
      }
    }

    if (closestIntersection.z == Double.POSITIVE_INFINITY) {
      return miss(ray);
    }

    return closestIntersection(ray, closestIntersection, visibleObject);
  }

  private IntersectionPayload miss(Ray ray) {
    return new IntersectionPayload(new Point(0, 0, Double.POSITIVE_INFINITY), null, null);
  }

  private IntersectionPayload closestIntersection(Ray ray, Point intersection, Object object) {
    return new IntersectionPayload(intersection, object.getNormalVector(intersection), object);
  }

  private boolean isInShadow(Point point, Light light) {
    Ray lightRay = new Ray(point, light.position);
    double lightDistance = light.position.getDistanceFrom(point);
    for (int i = 0; i < scene.objects.size(); i++) {
      Object object = scene.objects.get(i);
      Optional<Point> optionalIntersection = object.getClosestIntersection(lightRay);
      if (optionalIntersection.isEmpty()) continue;
      Point intersection = optionalIntersection.get();
      double intersectionDistance = intersection.getDistanceFrom(point);
      if (lightRay.isInPositiveDirection(intersection) && intersectionDistance < lightDistance && intersectionDistance > 0.000001) {
        return true;
      }
    }
    return false;
  }

  private Color getPixelFromLuminance(Camera camera, double redLuminance, double greenLuminance, double blueLuminance) {
    double pixelRedValue = Math.min(redLuminance * Math.PI * camera.getExposure(), 1);
    double pixelGreenValue = Math.min(greenLuminance * Math.PI * camera.getExposure(), 1);
    double pixelBlueValue = Math.min(blueLuminance * Math.PI * camera.getExposure(), 1);

    return new Color(digitalize(pixelRedValue), digitalize(pixelGreenValue), digitalize(pixelBlueValue));
  }

  private int digitalize(double value) {
    return Long.valueOf(Math.round(value * 255)).intValue();
  }
}
