import java.util.Scanner;
public class P2 {
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("What is your salary?");
        int sal = sc.nextInt();

        System.out.println("What is your merit points?");
        int merit = sc.nextInt();

        char Grade;
        if (merit < 10 || sal < 600)
        {
            Grade = 'C';
        }
        else if (merit < 20 || sal < 700)
        {
            Grade = 'B';
        }
        else
        {
            Grade = 'A';
        }

        sc.close();

        System.out.println("Your Grade is " + Grade);
    }
}
