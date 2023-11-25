package Shapes;


public class Circle extends Shape {
    double radius;

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius(){
        return this.radius;
    }

    public double getArea() {
        return this.radius * this.radius * Math.PI;
    }
}
