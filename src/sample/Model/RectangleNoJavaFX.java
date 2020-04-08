package sample.Model;

import sample.Controller.Controller;
import sample.View.RectangleDrawerJavaFX;
import sample.View.ShapeDrawer;

public class RectangleNoJavaFX extends ShapeNoJavaFX {
	private double width;
    private double height;
    private double arrondi;
	private Point rotationCenter;

	public RectangleNoJavaFX(double width, double height, Point pos, RGB rgb) {
		super(pos, rgb);
		this.width = width;
		this.height = height;
		rotationCenter = new Point();
		this.arrondi = 0.0;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getArrondi() {
		return arrondi;
	}

	public void setArrondi(int arrondi) {
		this.arrondi = arrondi;
	}


	@Override
	public Point getRotationCenter() {
		double CenterX = getPos().getX() + width/2;
		double CenterY = getPos().getY() + height/2;
		rotationCenter.setX(CenterX);
		rotationCenter.setY(CenterY);
		return rotationCenter;
	}

	@Override
	public void setRotationCenter(Point center) {
		this.rotationCenter.setX(center.getX());
		this.rotationCenter.setY(center.getY());
	}

	@Override
	public ShapeDrawer createShapeDrawer(Controller controller) {
		RGB rgb = this.getRGB();
		double red = rgb.getR();
		double green = rgb.getG();
		double blue = rgb.getB();

		Double[] rgbTab = new Double[3];
		rgbTab[0] = red; rgbTab[1] = green; rgbTab[2] = blue;

		return new RectangleDrawerJavaFX(this.getPos().getX(), this.getPos().getY(), this.getWidth(), this.getHeight(),
				rgbTab,  controller.getView(), this.getArrondi());
	}

}


