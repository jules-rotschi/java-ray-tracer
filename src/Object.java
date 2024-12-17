import java.util.Optional;

public abstract class Object {
  WorldColor albedo;
  double roughness; // not used for now
  double opacity; // not used for now
  double refractiveIndex; // not used for now

  Object(WorldColor albedo) {
    this.albedo = albedo;
  }

  abstract Vector3 getNormalVector(Point point);
  abstract Optional<Point> getClosestIntersection(Ray ray);
}
