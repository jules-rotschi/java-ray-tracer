public class Sensor {
  ImageDefinition definition;
  double width;
  double height;
  int sensivity;

  Sensor(ImageDefinition definition, double width, int sensivity) {
    this.definition = definition;
    this.width = width;
    this.sensivity = sensivity;
    height = width / definition.getAspectRatio();
  }

  double getAspectRatio() {
    return width / height;
  }

  double getPixelDimension() throws Exception {
    double horizontalDimension = Math.round((width / definition.widthPixel) * 100000) / 100000.0;
    double verticalDimension = Math.round((height / definition.heightPixel) * 100000) / 100000.0;
    if (horizontalDimension != verticalDimension)
      throw new Exception("Pixels are not squares");
    return horizontalDimension;
  }
}
