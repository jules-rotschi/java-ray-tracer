public class Camera {
  final private double MOVE_SPEED = 1;
  final private double ROTATION_SPEED = Math.PI / 12;
  
  Sensor sensor;
  Lens lens;
  int frameRate;
  int shutterAngle;
  Point position;
  double panAngle;
  double tiltAngle;
  
  private Vector3 upDirection;
  private Vector3 forwardDirection;
  private Vector3 rightDirection;

  Camera(Sensor sensor, Lens lens, int frameRate, int shutterAngle, Point position) {
    this.sensor = sensor;
    this.lens = lens;
    this.frameRate = frameRate;
    this.shutterAngle = shutterAngle;
    this.position = position;

    panAngle = 0;
    tiltAngle = 0;
    upDirection = new Vector3(0, 1, 0);
    forwardDirection = new Vector3(0, 0, -1);
    rightDirection = new Vector3(1, 0, 0);
  }

  Point getPixelPosition(int linePosition, int line) throws Exception {
    double lensBeginX = position.x - 1 * (sensor.width / 2);
    double pixelBeginX = lensBeginX + linePosition * sensor.getPixelDimension();
    double x = pixelBeginX + sensor.getPixelDimension() / 2;

    double lensBeginY = position.y + sensor.height / 2;
    double pixelBeginY = lensBeginY - line * sensor.getPixelDimension();
    double y = pixelBeginY - sensor.getPixelDimension() / 2;

    return new Point(x, y, position.z - lens.focalDistance).rotate(panAngle, tiltAngle, position);
  }

  double getExposure() {
    return (sensor.sensivity * getShutterSpeed(frameRate)) / (270 * lens.getAperture() * lens.getAperture());
  }

  void moveLeft() {
    position.x -= rightDirection.x * MOVE_SPEED;
    position.y -= rightDirection.y * MOVE_SPEED;
    position.z -= rightDirection.z * MOVE_SPEED;
  }

  void moveRight() {
    position.x += rightDirection.x * MOVE_SPEED;
    position.y += rightDirection.y * MOVE_SPEED;
    position.z += rightDirection.z * MOVE_SPEED;
  }
  
  void moveDown() {
    position.x -= upDirection.x * MOVE_SPEED;
    position.y -= upDirection.y * MOVE_SPEED;
    position.z -= upDirection.z * MOVE_SPEED;
  }
  
  void moveUp() {
    position.x += upDirection.x * MOVE_SPEED;
    position.y += upDirection.y * MOVE_SPEED;
    position.z += upDirection.z * MOVE_SPEED;
  }

  void moveBackward() {
    position.x -= forwardDirection.x * MOVE_SPEED;
    position.y -= forwardDirection.y * MOVE_SPEED;
    position.z -= forwardDirection.z * MOVE_SPEED;
  }

  void moveForward() {
    position.x += forwardDirection.x * MOVE_SPEED;
    position.y += forwardDirection.y * MOVE_SPEED;
    position.z += forwardDirection.z * MOVE_SPEED;
  }

  void panLeft() {
    panAngle = (panAngle + ROTATION_SPEED) % (2 * Math.PI);
    upDirection = upDirection.rotate(ROTATION_SPEED, 0);
    forwardDirection = forwardDirection.rotate(ROTATION_SPEED, 0);
    rightDirection = rightDirection.rotate(ROTATION_SPEED, 0);
  }

  void panRight() {
    panAngle = (panAngle - ROTATION_SPEED) % (2 * Math.PI);
    upDirection = upDirection.rotate(-ROTATION_SPEED, 0);
    forwardDirection = forwardDirection.rotate(-ROTATION_SPEED, 0);
    rightDirection = rightDirection.rotate(-ROTATION_SPEED, 0);
  }

  void tiltUp() {
    tiltAngle = (tiltAngle - ROTATION_SPEED) % (2 * Math.PI);
    upDirection = upDirection.rotate(0, -ROTATION_SPEED);
    forwardDirection = forwardDirection.rotate(0, -ROTATION_SPEED);
    rightDirection = rightDirection.rotate(0, -ROTATION_SPEED);
  }

  void tiltDown() {
    tiltAngle = (tiltAngle + ROTATION_SPEED) % (2 * Math.PI);
    upDirection = upDirection.rotate(0, ROTATION_SPEED);
    forwardDirection = forwardDirection.rotate(0, ROTATION_SPEED);
    rightDirection = rightDirection.rotate(0, ROTATION_SPEED);
  }

  void zoomOut() {
    lens.zoomOut();
  }

  void zoomIn() {
    lens.zoomIn();
  }

  void increaseAperture() {
    lens.increaseAperture();
  }

  void decreaseAperture() {
    lens.decreaseAperture();
  }

  private double getShutterSpeed(double frameRate) {
    return (shutterAngle / 360.0) / frameRate;
  }
}
