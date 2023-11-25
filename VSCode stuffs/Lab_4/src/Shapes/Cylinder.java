package Shapes;

public class Cylinder extends ThreeDeeShape {
    private double radius;
    private double height;

    public void setDimensions(double radius, double height) {
        this.radius = radius;
        this.height = height;
        setTopArea(Math.PI * radius * radius);
        setBottomArea(getTopArea());
    }

    public double getVolume() {
        double volume = getTopArea() * height;
        return volume;
    }

    public double getArea() {
        double lateralSurfaceArea = 2 * Math.PI * radius * height;
        double baseArea = getTopArea(); // One circular base
    
        double surfaceArea = lateralSurfaceArea + baseArea;
    
        return surfaceArea;
    }
}
