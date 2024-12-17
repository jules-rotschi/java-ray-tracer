import java.util.Optional;

// Work in progress : does not work & too slow

public class Cuboid extends Object {
  Point origin;
  double width;
  double height;
  double depth;
  double phiAngle;
  double thetaAngle;
  

  Cuboid(Point origin, double width, double height, double depth, double phiAngle, double thetaAngle, WorldColor albedo) {
    super(albedo);
    this.origin = origin;
    this.width = width;
    this.height = height;
    this.depth = depth;
    this.phiAngle = phiAngle;
    this.thetaAngle = thetaAngle;
  }

  @Override
  Vector3 getNormalVector(Point point) {
    final Face[] FACES = {
      getFrontFace(),
      getRightFace(),
      getBackFace(),
      getLeftFace(),
      getTopFace(),
      getBottomFace()
    };

    Vector3 normal = null;

    for (int i = 0; i < FACES.length; i++) {
      Face face = FACES[i];
      if (!face.contains(point)) continue;
      normal = face.getPlane().getNormalVector(point);
    }

    return normal;
  }

  @Override
  Optional<Point> getClosestIntersection(Ray ray) {
    final Face[] FACES = {
      getFrontFace(),
      getRightFace(),
      getBackFace(),
      getLeftFace(),
      getTopFace(),
      getBottomFace()
    };

    Point closestIntersection = new Point(0, 0, Double.POSITIVE_INFINITY);

    for (int i = 0; i < FACES.length; i++) {
      Face face = FACES[i];
      Plane facePlane = face.getPlane();
      Optional<Point> optionalIntersection = facePlane.getClosestIntersection(ray);
      if (optionalIntersection.isEmpty()) continue;
      Point intersection = optionalIntersection.get();
      if (
        face.contains(intersection)
        && intersection.getDistanceFrom(ray.origin) < closestIntersection.getDistanceFrom(ray.origin)
        && ray.isInPositiveDirection(intersection)
        ) {
        closestIntersection = intersection;
      }
    }

    if (closestIntersection.z == Double.POSITIVE_INFINITY) return Optional.empty();
    return Optional.of(closestIntersection);
  }

  private class Face {
    Point origin;
    Vector3 width;
    Vector3 height;

    Face(Point origin, Vector3 width, Vector3 height) {
      this.origin = origin;
      this.width = width;
      this.height = height;
    }

    Plane getPlane() {
      return new Plane(origin, width, height, albedo);
    }

    boolean contains(Point point) {
      boolean planeContains = getPlane().contains(point);
      boolean x = point.x + 0.000001 >= origin.x && point.x - 0.000001 <= origin.addVector(width).addVector(height).x;
      boolean y = point.y + 0.000001 >= origin.y && point.y - 0.000001 <= origin.addVector(width).addVector(height).y;
      boolean z = point.z + 0.000001 >= origin.z && point.z - 0.000001 <= origin.addVector(width).addVector(height).z;
      return planeContains && x && y && z;
    }
  }

  private Face getFrontFace() {
    return new Face(
      origin,
      new Vector3(width, 0, 0).rotate(phiAngle, thetaAngle),
      new Vector3(0, height, 0).rotate(phiAngle, thetaAngle)
    );
  }

  private Face getRightFace() {
    return new Face(
      origin.addVector(new Vector3(width, 0, 0).rotate(phiAngle, thetaAngle)),
      new Vector3(0, 0, -depth).rotate(phiAngle, thetaAngle),
      new Vector3(0, height, 0).rotate(phiAngle, thetaAngle)
    );
  }

  private Face getBackFace() {
    return new Face(
      origin.addVector(new Vector3(width, 0, -depth).rotate(phiAngle, thetaAngle)),
      new Vector3(-width, 0, 0).rotate(phiAngle, thetaAngle),
      new Vector3(0, height, 0).rotate(phiAngle, thetaAngle)
    );
  }

  private Face getLeftFace() {
    return new Face(
      origin.addVector(new Vector3(0, 0, -depth).rotate(phiAngle, thetaAngle)),
      new Vector3(0, 0, depth).rotate(phiAngle, thetaAngle),
      new Vector3(0, height, 0).rotate(phiAngle, thetaAngle)
    );
  }

  private Face getTopFace() {
    return new Face(
      origin.addVector(new Vector3(0, height, 0).rotate(phiAngle, thetaAngle)),
      new Vector3(width, 0, 0).rotate(phiAngle, thetaAngle),
      new Vector3(0, 0, -depth).rotate(phiAngle, thetaAngle)
    );
  }

  private Face getBottomFace() {
    return new Face(
      origin.addVector(new Vector3(0, 0, -depth).rotate(phiAngle, thetaAngle)),
      new Vector3(width, 0, 0).rotate(phiAngle, thetaAngle),
      new Vector3(0, 0, depth).rotate(phiAngle, thetaAngle)
    );
  }
}
