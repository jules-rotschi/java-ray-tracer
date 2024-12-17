public class Lens {
  double focalDistance;
  
  final private double[] STANDARD_APERTURE_VALUES = { 1, 1.4, 2, 2.8, 4, 5.6, 8, 11, 16, 22, 32 };
  
  private double minFocalDistance;
  private double maxFocalDistance;
  private double zoomSpeed = 1;
  private int apertureValueIndex;

  // No zoom
  Lens(double focalDistance, double aperture) throws Exception {
    this.minFocalDistance = focalDistance;
    this.maxFocalDistance = focalDistance;
    this.focalDistance = focalDistance;
    setApertureValue(aperture);
  }

  // Zoom
  Lens(double minFocalDistance, double maxFocalDistance, double focalDistance, double aperture) throws Exception {
    this.minFocalDistance = minFocalDistance;
    this.maxFocalDistance = maxFocalDistance;
    this.focalDistance = focalDistance;
    setApertureValue(aperture);
  }

  void zoomOut() {
    double newFocalDistance = focalDistance - zoomSpeed / 25;
    double roundedFocalDistance = Math.round(newFocalDistance * 1000) / 1000.0;
    focalDistance = Math.max(roundedFocalDistance, minFocalDistance);
  }

  void zoomIn() {
    double newFocalDistance = focalDistance + zoomSpeed / 25;
    double roundedFocalDistance = Math.round(newFocalDistance * 1000) / 1000.0;
    focalDistance = Math.min(roundedFocalDistance, maxFocalDistance);
  }

  void increaseAperture() {
    if (apertureValueIndex != 0) apertureValueIndex--;
  }

  void decreaseAperture() {
    if (apertureValueIndex != STANDARD_APERTURE_VALUES.length - 1) apertureValueIndex++;
  }

  double getAperture() {
    return STANDARD_APERTURE_VALUES[apertureValueIndex];
  }

  private void setApertureValue(double apertureValue) throws Exception {
    apertureValueIndex = -1;
    for (int i = 0; i < STANDARD_APERTURE_VALUES.length; i++) {
      if (STANDARD_APERTURE_VALUES[i] == apertureValue) {
        apertureValueIndex = i;
      }
    }
    if (apertureValueIndex < 0) {
      throw new Exception("Invalid aperture value");
    }
  }
}
