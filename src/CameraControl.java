import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CameraControl implements KeyListener {
  Camera camera;

  boolean cameraMovingLeft;
  boolean cameraMovingRight;
  boolean cameraMovingDown;
  boolean cameraMovingUp;
  boolean cameraMovingBackward;
  boolean cameraMovingForward;
  boolean cameraZoomingOut;
  boolean cameraZoomingIn;
  boolean cameraPanningLeft;
  boolean cameraPanningRight;
  boolean cameraTiltingUp;
  boolean cameraTiltingDown;

  final int MOVE_LEFT_KEY = KeyEvent.VK_LEFT;
  final int MOVE_RIGHT_KEY = KeyEvent.VK_RIGHT;
  final int MOVE_DOWN_KEY = KeyEvent.VK_S;
  final int MOVE_UP_KEY = KeyEvent.VK_Z;
  final int MOVE_BACKWARD_KEY = KeyEvent.VK_DOWN;
  final int MOVE_FORWARD_KEY = KeyEvent.VK_UP;
  final int ZOOM_OUT_KEY = KeyEvent.VK_O;
  final int ZOOM_IN_KEY = KeyEvent.VK_I;
  final int LEFT_PAN_KEY = KeyEvent.VK_NUMPAD4;
  final int RIGHT_PAN_KEY = KeyEvent.VK_NUMPAD6;
  final int UP_TILT_KEY = KeyEvent.VK_NUMPAD8;
  final int DOWN_TILT_KEY = KeyEvent.VK_NUMPAD2;
  final int CLOSE_IRIS_KEY = KeyEvent.VK_R;
  final int OPEN_IRIS_KEY = KeyEvent.VK_E;

  CameraControl(Camera camera, RenderPanel renderPanel) {
    this.camera = camera;
    cameraMovingLeft = false;
    cameraMovingRight = false;
    cameraMovingDown = false;
    cameraMovingUp = false;
    cameraMovingBackward = false;
    cameraMovingForward = false;
    cameraZoomingOut = false;
    cameraZoomingIn = false;
    cameraPanningLeft = false;
    cameraPanningRight = false;
    cameraTiltingUp = false;
    cameraTiltingDown = false;

    renderPanel.addKeyListener(this);
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case MOVE_LEFT_KEY :
        cameraMovingLeft = true;
        break;
      case MOVE_RIGHT_KEY :
        cameraMovingRight = true;
        break;
      case MOVE_DOWN_KEY :
        cameraMovingDown = true;
        break;
      case MOVE_UP_KEY :
        cameraMovingUp = true;
        break;
      case MOVE_BACKWARD_KEY :
        cameraMovingBackward = true;
        break;
      case MOVE_FORWARD_KEY :
        cameraMovingForward = true;
        break;
      case ZOOM_OUT_KEY :
        cameraZoomingOut = true;
        break;
      case ZOOM_IN_KEY :
        cameraZoomingIn = true;
        break;
      case LEFT_PAN_KEY :
        cameraPanningLeft = true;
        break;
      case RIGHT_PAN_KEY :
        cameraPanningRight = true;
        break;
      case UP_TILT_KEY :
        cameraTiltingUp = true;
        break;
      case DOWN_TILT_KEY :
        cameraTiltingDown = true;
        break;
      case CLOSE_IRIS_KEY :
        camera.decreaseAperture();
        break;
      case OPEN_IRIS_KEY :
        camera.increaseAperture();
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case MOVE_LEFT_KEY :
        cameraMovingLeft = false;
        break;
      case MOVE_RIGHT_KEY :
        cameraMovingRight = false;
        break;
      case MOVE_DOWN_KEY :
        cameraMovingDown = false;
        break;
      case MOVE_UP_KEY :
        cameraMovingUp = false;
        break;
      case MOVE_BACKWARD_KEY :
        cameraMovingBackward = false;
        break;
      case MOVE_FORWARD_KEY :
        cameraMovingForward = false;
        break;
      case LEFT_PAN_KEY :
        cameraPanningLeft = false;
        break;
      case RIGHT_PAN_KEY :
        cameraPanningRight = false;
        break;
      case UP_TILT_KEY :
        cameraTiltingUp = false;
        break;
      case DOWN_TILT_KEY :
        cameraTiltingDown = false;
        break;
      case ZOOM_OUT_KEY :
        cameraZoomingOut = false;
        break;
      case ZOOM_IN_KEY :
        cameraZoomingIn = false;
        break;
    }
  }
}
