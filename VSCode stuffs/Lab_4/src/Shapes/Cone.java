package Shapes;

public class Cone extends ThreeDeeShape {
    private double radius;
    private double height;

    public void setDimensions(double radius, double height) {
        this.radius = radius;
        this.height = height;
        setTopArea(0);
        setBottomArea(Math.PI * radius * radius);
    }

    public double getVolume() {
        double volume = (1.0 / 3.0) * Math.PI * radius * radius * height;
        return volume;
    }

    public double getArea() {
        double slantHeight = Math.sqrt(radius * radius + height * height);
        double lateralSurfaceArea = Math.PI * radius * slantHeight / 2;
        double baseArea = getBottomArea();
    
        double surfaceArea = lateralSurfaceArea + baseArea;
        return surfaceArea;
    }
}

