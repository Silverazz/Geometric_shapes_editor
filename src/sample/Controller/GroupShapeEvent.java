package sample.Controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import sample.Model.*;
import sample.View.ShapeDrawer;

public class GroupShapeEvent implements Events{
    protected Controller controller;
    private Point mousePosStart;

    public GroupShapeEvent(Controller controller) {
        this.controller = controller;
    }

    EventHandler<MouseEvent> createSelectionRectangleOnClick = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            //if()mouseEvent.getSource() == NULL ou != Shape, pour être sûr qu'on ne fait pas un rectangle alors que y a un shape
            mousePosStart = new Point(mouseEvent.getSceneX(), mouseEvent.getSceneY());
            ShapeInter selectionRectangle = new RectangleNoJavaFX(0, 0, mousePosStart, new RGB(0, 0, 0));
            ShapeDrawer drawer = selectionRectangle.createShapeDrawer(controller);
            drawer.drawShape();

            Shape shape = (Shape) mouseEvent.getSource();
            shape.setStrokeDashOffset(5);
        }
    };

    EventHandler<MouseEvent> moveSelectionRectangleOnPressingMouse = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            Shape shape = (Shape) mouseEvent.getSource();
            shape.toBack();
        }
    };

    EventHandler<MouseEvent> selectionRectangleDraggedInCanvas = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            Rectangle shape = (Rectangle) mouseEvent.getSource();

            double dragX = mouseEvent.getSceneX() - mousePosStart.getX();
            double dragY = mouseEvent.getSceneY() - mousePosStart.getY();
            //calculate new position
            double newWidth = mousePosStart.getX() + dragX;
            double newHeight = mousePosStart.getY() + dragY;
            if (newWidth >= 0 && newHeight >= 0) {
                shape.setWidth(newWidth);
                shape.setHeight(newHeight);
            }
        }
    };

    EventHandler<MouseEvent> releasedMouseAndCreateGroupShape = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            Point MousePosEnd = new Point(mouseEvent.getSceneX(),mouseEvent.getSceneY());
            Shape shape = (Shape) mouseEvent.getSource();
            ShapeGroup shapeGroup = new ShapeGroup();
            controller.getView().getShapesInCanvas().remove(shape);
            for(ShapeInter s : controller.getShapesInCanvas()){
                Point rotationCenter = s.getRotationCenter();
                if(rotationCenter.getX() >= mousePosStart.getX() && rotationCenter.getX() <= MousePosEnd.getX()
                    && rotationCenter.getY() >= mousePosStart.getY() && rotationCenter.getY() <= MousePosEnd.getY()){
                    shapeGroup.add(s);
                }
            }
            /*
            if (shapeGroup.getChildren().isEmpty()==false){
                controller.getShapeGroups().add(shapeGroup);
            }*/
        }
    };

    @Override
    public void launchEvent() {
        controller.getView().launch_createSelectionRectangleOnClick(createSelectionRectangleOnClick);
        controller.getView().launch_rectangleSelectionReleased(releasedMouseAndCreateGroupShape);
        controller.getView().launch_selectionRectangleDraggedInCanvas(selectionRectangleDraggedInCanvas);
        controller.getView().launch_moveSelectionRectangleOnPressingMouse(moveSelectionRectangleOnPressingMouse);
    }
}
