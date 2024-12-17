public class Ray {
  Point origin;
  Point direction;

  Ray(Point origin, Point direction) {
    this.origin = origin;
    this.direction = direction;
  }

  boolean isInPositiveDirection(Point point) {
    Vector3 rayVector = new Vector3(origin, direction);
    return (point.x - origin.x) / rayVector.x >= 0;
  }

  Ray getReflectedRay(Point reflectionPoint, Vector3 normal) {
    Vector3 rayVector = new Vector3(origin, direction);
    Point cartesianCoordinates = new Point(rayVector.x, rayVector.y, rayVector.z);
    Point.SphericalCoordinates sphericalCoordinates = cartesianCoordinates.new SphericalCoordinates();

    Point normalCartesianCoordinates = new Point(normal.x, normal.y, normal.z);
    Point.SphericalCoordinates normalSphericalCoordinates = normalCartesianCoordinates.new SphericalCoordinates();

    Point origin = new Point(reflectionPoint.x + normal.x * 0.001, reflectionPoint.y + normal.y * 0.001, reflectionPoint.z + normal.z * 0.001);
    Point direction = origin.rotate(
      2 * (normalSphericalCoordinates.phi - sphericalCoordinates.phi),
      2 * (normalSphericalCoordinates.theta - sphericalCoordinates.theta),
      reflectionPoint
    );
    return new Ray(origin, direction);
  }
}