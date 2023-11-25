import java.util.Scanner;
public class MyFirstJavaProgram {

    public static void main(String[] args)
    {
        System.out.println("Hello! This is my first program.");
        System.out.println("Bye Bye!");

        System.out.println("");
        System.out.println("Movie Fan? A/B/C/D?");
        String x;
        Scanner sc = new Scanner(System.in);
        x = sc.nextLine();

        switch(x){
            case "a", "A" : System.out.println("Action Movie Fan");
            break;

            case "c", "C" : System.out.println("Comedy Movie Fan");
            break;

            case "d", "D" : System.out.println("Drama Movie Fan");
            break;

            default : System.out.println("Error");
        }
        sc.close();
    }
    
}
