import java.util.Scanner;
public class P3 {
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int starting, ending, increment;
        System.out.println("Starting number: ");
        starting = sc.nextInt();
        System.out.println("Ending number: ");
        ending = sc.nextInt();
        System.out.println("Increment: ");
        increment = sc.nextInt();

        System.out.println("US$     S$");
        System.out.println("----------");
        for(int i = starting; i <= ending; i+= increment)
        {
            System.out.println(i + "       " + 1.82*i);
        }
        
        int j = starting;
        while(j <= ending)
        {
            System.out.println(j + "       " + 1.82*j);
            j+=increment;
        }

        j = starting;
        do
        {
            System.out.println(j + "       " + 1.82*j);
            j+= increment;
        }
        while(j <= ending);

        sc.close();
    }
}
