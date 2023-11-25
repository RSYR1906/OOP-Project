import java.util.Scanner;
public class P4 {
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Height: ");
        int h = sc.nextInt();
        
        int x = 1;
        if(h < 1)
        {
            System.out.println("error");
        }
        else
        {
            for(int i = 0; i<h; i++)
            {
                int count = i;
                for(int j = 0; j<x; j++)
                {   
                    
                    if(count % 2 == 0)
                    {
                        System.out.print("AA");
                    }
                    else
                    {
                        System.out.print("BB");
                    }
                    count++;
                    
                }
                
                System.out.println("");
                x++;
            }
        }
        sc.close();
    }
}
