package sample.Model;

import java.io.Serializable;

public class RGB implements Serializable {
	private double r;
	private double g;
	private double b;
	private double a;

	public RGB(double r, double g, double b) {
	    this.r = r;
	    this.g = g;
	    this.b = b;
	    this.a = 1;
	}

	public RGB(double r, double g, double b, double a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
		public double getR() {
	        return r;
	    }

	    public void setR(int r) {
	        this.r = r;
	    }

	    public double getG() {
	        return g;
	    }

	    public void setG(int g) {
	        this.g = g;
	    }

	    public double getB() {
	        return b;
	    }

	    public void setB(int b) {
	        this.b = b;
	    }

	    public double getA(){
	   		return this.a;
		}

	    public void setA(int a){
	   		this.a = a;
		}

}
