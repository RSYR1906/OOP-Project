package Shapes;
import java.util.Scanner;

public class Shape3DApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter total number of shapes: ");
        int shapeNum = sc.nextInt();
        ThreeDeeShape[] shapes = new ThreeDeeShape[shapeNum];

        for (int i = 0; i < shapeNum; i++) {
            System.out.print("Choose a shape:\n1.Sphere\n2.Cone\n3.Cylinder\n:");
            int shapeChoice = sc.nextInt();

            switch (shapeChoice) {
                case 1:
                    Sphere sphere = new Sphere();
                    System.out.print("Enter the radius of the sphere: ");
                    double sphereRadius = sc.nextDouble();
                    sphere.setRadius(sphereRadius);
                    shapes[i] = sphere;
                    break;

                case 2:
                    Cone cone = new Cone();
                    System.out.print("Enter the radius of the base of the cone: ");
                    double coneRadius = sc.nextDouble();
                    System.out.print("Enter the height of the cone: ");
                    double coneHeight = sc.nextDouble();
                    cone.setDimensions(coneRadius, coneHeight);
                    shapes[i] = cone;
                    break;

                case 3:
                    Cylinder cylinder = new Cylinder();
                    System.out.print("Enter the radius of the base of the cylinder: ");
                    double cylinderRadius = sc.nextDouble();
                    System.out.print("Enter the height of the cylinder: ");
                    double cylinderHeight = sc.nextDouble();
                    cylinder.setDimensions(cylinderRadius, cylinderHeight);
                    shapes[i] = cylinder;
                    break;

                default:
                    System.out.println("Invalid choice, try again...");
                    i--;
                    break;
            }
        }

        double totalArea = 0;

        for (int i = 0; i < shapeNum; i++) {
            totalArea += shapes[i].getArea();
            if (i > 0) {
                double area = Math.min(shapes[i - 1].getTopArea(), shapes[i].getBottomArea());
                totalArea -= 2 * area;
            }
        }

        System.out.format("Total area of shapes is %.2f\n", totalArea);
        sc.close();
    }
}
