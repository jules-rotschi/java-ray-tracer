import java.util.Optional;

public class Plane extends Object {
  Point origin;
  Vector3 vector1;
  Vector3 vector2;

  Plane(Point origin, Vector3 vector1, Vector3 vector2, WorldColor albedo) {
    super(albedo);
    this.origin = origin;
    this.vector1 = vector1;
    this.vector2 = vector2;
  }

  @Override
  Vector3 getNormalVector(Point point) {
    return vector1.getVectorProduct(vector2);
  }

  Optional<Point> getClosestIntersection(Ray ray) {

    // Ensemble des points du rayon
    // Tous les points P tels que :
    // P = O + (D - O) * t
    // O est l'origine du rayon
    // D est la direction du rayon
    // la variable t est le coeficient d'échelle
    // Donc tous les points P = (Ox + (Dx - Ox) * t, Oy + (Dx - Ox) * t, Oz + (Dz - Ox) * t)

    // Ensemble de tous les points du plan
    // Tous les points P tels que :
    // a * Px + b * Py + c * Pz + d = 0
    // a, b et c sont respectivement les coordonnées x, y et z du vector normal au plan
    // d est le décalage du plan à l'origine

    // La potentielle intersection entre le rayon et le plan
    // est le point du plan dont les coordonnées
    // correspondent à un point du rayon pour un certain coefficient t

    // Cela nous donne l'équation suivante :
    // a * (Ox + (Dx - Ox) * t) + b * (Oy + (Dy - Oy) * t) + c * (Oz + (Dz - Oz) * t) + d = 0

    // Donc
    // t = -(a * Ox + b * Oy + c * Oz + d) / (a * (Dx - Ox) + b * (Dy - Oy) + c * (Dz - Oz))

    // On remarque donc que si Dx = Ox ou a = 0 et Dy = Oy ou b = 0 et Dz = Oz ou c = 0, il n'y a pas de solution

    Vector3 normal = getNormalVector(origin);

    double a = normal.x;
    double b = normal.y;
    double c = normal.z;
    double d = -(a * origin.x + b * origin.y + c * origin.z);

    Vector3 rayVector = new Vector3(ray.origin, ray.direction);

    if (a * rayVector.x + b * rayVector.y + c * rayVector.z == 0) {
      return Optional.empty();
    }

    double t = -(a * ray.origin.x + b * ray.origin.y + c * ray.origin.z + d) / (a * rayVector.x + b * rayVector.y + c * rayVector.z);

    // Pour trouver x, y et z, on remplace les coordonnées du rayon dans l'expresion du rayon

    double x = ray.origin.x + rayVector.x * t;
    double y = ray.origin.y + rayVector.y * t;
    double z = ray.origin.z + rayVector.z * t;

    return Optional.of(new Point(x, y, z));
  }

  boolean contains(Point point) {
    Vector3 normal = getNormalVector(origin);

    double a = normal.x;
    double b = normal.y;
    double c = normal.z;
    double d = -(a * origin.x + b * origin.y + c * origin.z);

    // true if approximatively equals 0
    return a * point.x + b * point.y + c * point.z + d >= -0.000001 && a * point.x + b * point.y + c * point.z + d <= 0.000001;
  }
}
