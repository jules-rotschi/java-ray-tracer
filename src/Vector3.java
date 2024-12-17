public class Vector3 {
  double x;
  double y;
  double z;

  Vector3(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  Vector3(Point origin, Point direction) {
    x = direction.x - origin.x;
    y = direction.y - origin.y;
    z = direction.z - origin.z;
  }

  double getNorm() {
    return Math.sqrt(x * x + y * y + z * z);
  }

  double getDotProduct(Vector3 otherVector) {
    return x * otherVector.x + y * otherVector.y + z * otherVector.z;
  }

  Vector3 getVectorProduct(Vector3 otherVector) {
    return new Vector3(
      y * otherVector.z - z * otherVector.y,
      z * otherVector.x - x * otherVector.z,
      x * otherVector.y - y * otherVector.x
    );
  }

  double getAngleTo(Vector3 otherVector) {
    double cosinus = getDotProduct(otherVector) / (getNorm() * otherVector.getNorm());
    return Math.acos(cosinus);
  }

  Vector3 rotate(double phiAngle, double thetaAngle) {
    Point origin = new Point(0, 0, 0);
    Point baseDirection = new Point(x, y, z);
    Point newDirection = baseDirection.rotate(phiAngle, thetaAngle, origin);
    return new Vector3(origin, newDirection);
  }
}
