public class Point {
  double x;
  double y;
  double z;

  Point(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  Point(SphericalCoordinates sphericalCoordinates) {
    x = sphericalCoordinates.r * Math.sin(sphericalCoordinates.theta) * Math.sin(sphericalCoordinates.phi);
    y = sphericalCoordinates.r * Math.cos(sphericalCoordinates.theta);
    z = sphericalCoordinates.r * Math.sin(sphericalCoordinates.theta) * Math.cos(sphericalCoordinates.phi);
  }

  double getDistanceFrom(Point otherPoint) {
    return new Vector3(otherPoint, this).getNorm();
  }

  Point rotate(double phiRotation, double thetaRotation, Point center) {
    Point relativePosition = getRelativePosition(center);
    SphericalCoordinates sphericalCoordinates = relativePosition.new SphericalCoordinates();
    sphericalCoordinates.phi += phiRotation;
    sphericalCoordinates.theta += thetaRotation;
    Point rotatedRelativePosition = new Point(sphericalCoordinates);
    return rotatedRelativePosition.getAbsolutePosition(center);
  }

  Point addVector(Vector3 vector) {
    return new Point(x + vector.x, y + vector.y, z + vector.z);
  }

  class SphericalCoordinates {
    double r;
    double theta;
    double phi;

    SphericalCoordinates() {
      r = getDistanceFrom(new Point(0, 0, 0));
      theta = Math.acos(y / r);
      if (z != 0) {
        phi = Math.atan2(x, z);
      } else if (x != 0) {
        phi = (x / Math.abs(x)) * (Math.PI / 2);
      } else {
        phi = 0;
      }
    }
  }

  private Point getRelativePosition(Point origin) {
    return new Point(x - origin.x, y - origin.y, z - origin.z);
  }

  private Point getAbsolutePosition(Point relativeOrigin) {
    return new Point(x + relativeOrigin.x, y + relativeOrigin.y, z + relativeOrigin.z);
  }
}