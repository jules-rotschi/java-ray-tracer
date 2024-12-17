public class SceneManager {
  Scene scene;
  CameraControl cameraControl;

  SceneManager(Scene scene, CameraControl cameraControl) {
    this.scene = scene;
    this.cameraControl = cameraControl;
  }

  void updateScene() {
    Camera camera = scene.camera;

    if (cameraControl.cameraMovingLeft) {
      camera.moveLeft();
    }
    if (cameraControl.cameraMovingRight) {
      camera.moveRight();
    }
    if (cameraControl.cameraMovingDown) {
      camera.moveDown();
    }
    if (cameraControl.cameraMovingUp) {
      camera.moveUp();
    }
    if (cameraControl.cameraMovingBackward) {
      camera.moveBackward();
    }
    if (cameraControl.cameraMovingForward) {
      camera.moveForward();
    }

    if (cameraControl.cameraPanningLeft) {
      camera.panLeft();
    }
    if (cameraControl.cameraPanningRight) {
      camera.panRight();
    }
    if (cameraControl.cameraTiltingUp) {
      camera.tiltUp();
    }
    if (cameraControl.cameraTiltingDown) {
      camera.tiltDown();
    }

    if (cameraControl.cameraZoomingOut) {
      camera.zoomOut();
    }
    if (cameraControl.cameraZoomingIn) {
      camera.zoomIn();
    }
  }
}
