package sample.Controller;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import sample.Model.*;
import sample.View.ShapeDrawer;
import sample.View.View;

import java.util.ArrayList;
import java.util.LinkedList;

public class Controller {

    /* View */
    private View view;

    /* Model */
    private ArrayList<ShapeInter> shapesInToolBar;
    private ArrayList<ShapeInter> shapesInCanvas;
    private ArrayList<Events> events;
    private ArrayList<ShapeInter> shapeGroups;
    private ShapeInter shapeGroupTmp;

    /* Pattern Command */
    private int currentPosInCommands;
    private LinkedList<Command> commands;

    public Controller(View view) {
        this.view = view;
        shapesInCanvas = new ArrayList<>();
        shapesInToolBar = new ArrayList<>();
        shapeGroups = new ArrayList<>();
        events = new ArrayList<>();
        commands = new LinkedList<>();
        shapeGroupTmp = new ShapeGroup();
        currentPosInCommands = 0;
    }

    public void initializeView() {
        view.addMenuBar();
        view.addCanvas();
        view.addShapeMenu();


        shapesInToolBar.add(new RectangleNoJavaFX(50, 25, new Point(0,0), new RGB(1, 0, 0)));
        shapesInToolBar.add(new RectangleNoJavaFX(50, 25, new Point(0,0), new RGB(0, 1, 0)));
        shapesInToolBar.add(new Polygon(5, 25, new Point(0,20), new RGB(0, 0, 1)));
        shapesInToolBar.add(new Polygon(7, 35, new Point(0,20), new RGB(1, 1, 0)));

        for(ShapeInter shape : shapesInToolBar) {
           ShapeDrawer drawer = shape.createShapeDrawer(this);
           drawer.drawShapeInToolBar();
        }

        view.addTrash();
    }

    public void updateViewColor() {
        for(ShapeInter shapeModel : shapesInCanvas) {
            for(Shape shapeView : view.getShapesInCanvas()) {
                if(shapeModel.getPos().getX() == view.getShapeXPositionInToolBar(shapeView) && shapeModel.getPos().getY() == view.getShapeYPositionInToolBar(shapeView)) {
                    double red = shapeModel.getRGB().getR();
                    double blue = shapeModel.getRGB().getB();
                    double green = shapeModel.getRGB().getG();
                    shapeView.setFill(Color.color(red,green,blue));
                }
            }
        }
    }

    public void updateViewTranslate(double dragX, double dragY){
        for(int i = 0; i < shapesInCanvas.size(); i++){
            ShapeInter shapeModel = shapesInCanvas.get(i);
            Shape shapeView = view.getShapesInCanvas().get(i);
            if(shapeModel.getPos().getX() != view.getShapeXPositionInToolBar(shapeView) || shapeModel.getPos().getY() != view.getShapeYPositionInToolBar(shapeView)){
                shapeView.setTranslateX(shapeView.getTranslateX() + dragX);
                shapeView.setTranslateY(shapeView.getTranslateY() + dragY);
            }
        }
    }

    /**
     * Remove a shape in view and controller
     */
    public void removeShape(ShapeInter shapeModel, Shape shapeView){
        if (!getView().getShapesInCanvas().remove(shapeView)) {
            System.out.println("Shape in view.getShapesCanvas not find");
        }
        if (!getShapesInCanvas().remove(shapeModel)) {
            System.out.println("model in getShapesInCanvas not find");
        }
        getView().getRoot().getChildren().remove(shapeView);
    }

    /**
     * Update view after removing shape(s)
     */
    public void updateViewRemove(ShapeInter shape){
        boolean noGroup = true;
        // Range shape group in order to find the corresponding shape in view
        for(ShapeInter shapeGroup : getShapeGroups()) {
            if(shape.equals(shapeGroup)){
                for(int nChild =0; nChild < shapeGroup.getChildren().size(); nChild++){
                    for(int i =0; i < view.getShapesInCanvas().size(); i++) {
                        ShapeInter shapeModel = shapeGroup.getChild(nChild);
                        Shape shapeView = view.getShapesInCanvas().get(i);
                        if (shapeModel.getPos().getX() == view.getShapeXPositionInToolBar(shapeView) && shapeModel.getPos().getY() == view.getShapeYPositionInToolBar(shapeView)) {
                            removeShape(shapeModel, shapeView);
                            noGroup = false;
                        }
                    }
                }
            }
        }
        // if the shape doesn't belong to a group
        if(noGroup){
            for(int i =0; i < view.getShapesInCanvas().size(); i++) {
                Shape shapeView = view.getShapesInCanvas().get(i);
                if (shape.getPos().getX() == view.getShapeXPositionInToolBar(shapeView) && shape.getPos().getY() == view.getShapeYPositionInToolBar(shapeView)) {
                    removeShape(shape, shapeView);
                }
            }
        }
    }


    public void updateEvents(){
        for(Events event : getEvents()) {
            event.launchEvent();
        }
    }


    public void initEvents() {

        events.add(new RightClick(this));
        events.add(new DragAndDropEvent(this));
        events.add(new CreateShapeEvent(this));
        events.add(new SelectionShapeEvent(this));
        events.add(new GroupShapeEvent(this));
        events.add(new RedoEvent(this));
        events.add(new UndoEvent(this));

        updateEvents();
    }

    public ShapeInter getShapeGroupTmp(){
        return shapeGroupTmp;
    }


    public ArrayList<Events> getEvents() {
        return events;
    }

    public Scene getScene() {
        return view.getScene();
    }

    public View getView() {
        return this.view;
    }

    public ArrayList<ShapeInter> getShapesInToolBar() {
        return shapesInToolBar;
    }

    public ArrayList<ShapeInter> getShapesInCanvas() {
        return shapesInCanvas;
    }

    public ArrayList<ShapeInter> getShapeGroups() {
        return shapeGroups;
    }

    public LinkedList<Command> getCommands() {
        return commands;
    }

    public void addLastCommand(Command command){
        commands.addLast(command);
    }

    public int getNbCommands(){
        return commands.size()-1;
    }

    public int getCurrentPosInCommands() {
        return currentPosInCommands;
    }

    public void setCurrentPosInCommands(int currentPosInCommands) {
        this.currentPosInCommands = currentPosInCommands;
    }

    public void setShapeGroupTmp(ShapeInter shapeGroupTmp) {
        this.shapeGroupTmp = shapeGroupTmp;
    }
}
