import java.util.Optional;

public class Sphere extends Object {
  Point origin;
  double radius;

  Sphere(Point origin, double radius, WorldColor albedo) {
    super(albedo);
    this.origin = origin;
    this.radius = radius;
  }

  @Override
  Vector3 getNormalVector(Point point) {
    return new Vector3(origin, point);
  }

  Optional<Point> getClosestIntersection(Ray ray) {

    // Ensemble des points du rayon
    // Tous les points P tels que :
    // P = O + (D - O) * t
    // O est l'origine du rayon
    // D est la direction du rayon
    // la variable t est le coeficient d'échelle
    // Donc tous les points P(Ox + (Dx - Ox) * t, Oy + (Dy - Oy) * t, Oz + (Dz - Oz) * t)

    // Ensemble des points de la sphère
    // Tous les points dont les coordonnées x, y, z sont telles que :
    // r^2 - (x - Sx)^2 - (y - Sy)^2 - (z - Sz)^2 = 0
    // r est le rayon de la sphère
    // Sx, Sy, Sz sont les coordonnées du centre de la sphère

    // Les potentielles intersections entre le rayon
    // sont les points de la sphère dont les coordonnées
    // correspondent à un point du rayon pour un certain coefficient t

    // En remplaçant x et y par l'expression des coordonnées d'un point du rayon
    // dans l'équation de la sphère, on obtient :
    // r^2 - (Ox + (Dx - Ox) * t - Sx)^2 - (Oy + (Dy - Ox) * t - Sy)^2 - (Oz + (Dz - Oz) * t - Sz)^2 = 0

    // Ce qui donne :
    // ((Dx - Ox)^2 + (Dy - Oy)^2 + (Dz - Oz)^2)t^2 + 2 * ((Dx - Ox) * (Ox - Sx) + (Dy - Oy) * (Oy - Sy) + (Dz - Oz) * (Oz - Sz)) * t + Ox^2 - 2 * Ox * Sx + Sx^2 + Oy^2 - 2 * Oy * Sy + Sy^2 + Oz^2 - 2 * Oz * Sz + Sz^2 - r^2 = 0

    // On a donc une équation d'un polynôme du deuxième degré, de la forme
    // a(t^2) + bt + c = 0

    Vector3 rayVector = new Vector3(ray.origin, ray.direction);
    
    double a = rayVector.x * rayVector.x + rayVector.y * rayVector.y + rayVector.z * rayVector.z;
    
    double b =
    2 * (
      rayVector.x * (ray.origin.x - origin.x)
      + rayVector.y * (ray.origin.y - origin.y)
      + rayVector.z * (ray.origin.z - origin.z)
      );
      
    double c =
      ray.origin.x * ray.origin.x - 2 * ray.origin.x * origin.x + origin.x * origin.x
      + ray.origin.y * ray.origin.y - 2 * ray.origin.y * origin.y + origin.y * origin.y
      + ray.origin.z * ray.origin.z - 2 * ray.origin.z * origin.z + origin.z * origin.z
      - radius * radius;
      
    // On peut donc la résoudre en calculant le discriminant
    // d = a^2 - 4ac
    // Qui donnera le nombre de solutions (aucune si d < 0, une seule si d = 0, deux si d > 0)
    
    double discriminant = b * b - 4 * a * c;
    
    if (discriminant < 0) {
      return Optional.empty();
    }

    // Les solutions sont de la forme
    // s = (-b +- sqrt(d)) / 2a

    // Elles correspondent aux facteurs d'échelle (t) des points du rayon qui sont sur la sphère

    double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
    double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);

    // On cherche l'intersection la plus proche de l'origine,
    // donc celle avec le facteur d'échelle le plus petit

    double t = Math.min(t1, t2);
    
    // On remplace donc t par la solution trouvée dans l'expression des points du rayon
    // pour trouver l'intersection la plus proche

    Point intersection = new Point(ray.origin.x + rayVector.x * t, ray.origin.y + rayVector.y * t, ray.origin.z + rayVector.z * t);
    return Optional.of(intersection);
  }
}
