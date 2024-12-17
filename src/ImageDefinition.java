public class ImageDefinition {
  int widthPixel;
  int heightPixel;
  
  ImageDefinition(int width, int height) {
    this.widthPixel = width;
    this.heightPixel = height;
  }

  double getAspectRatio() {
    return Double.valueOf(widthPixel) / heightPixel;
  }
}
