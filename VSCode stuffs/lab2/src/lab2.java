import java.util.*;
public class lab2 {
    public static void main(String[] args)
    {
        int choice;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Perform the following methods:");
            System.out.println("1: multiplication test");
            System.out.println("2: quotient using division by subtraction");
            System.out.println("3: remainder using division by subtraction");
            System.out.println("4: count the number of digits");
            System.out.println("5: position of a digit");
            System.out.println("6: extract all odd digits");
            System.out.println("7: quit");
            choice = sc.nextInt();
            switch (choice) {
                case 1: /* add mulTest() call */
                mulTest();
                break;
                case 2: /* add divide() call */
                System.out.println("input a number");
                int M = sc.nextInt();
                System.out.println("divide it by...");
                int N = sc.nextInt();
                System.out.println(divide(M,N));
                break;
                case 3: /* add modulus() call */
                System.out.println("input a number");
                M = sc.nextInt();
                System.out.println("mod it by...");
                N = sc.nextInt();
                System.out.println(modulus(M,N));
                break;
                case 4: /* add countDigits() call */
                System.out.println("Input number to count digits");
                N = sc.nextInt();
                System.out.println(countDigits(N) + " number of digits");
                break;
                case 5: /* add position() call */
                System.out.println("Input number");
                M = sc.nextInt();
                System.out.println("Which digit do you want to find?");
                int Digit = sc.nextInt();
                System.out.println("Postition " + position(M, Digit));
                break;
                case 6: /* add extractOddDigits() call */
                System.out.println("Insert digit to print only the odd digits from");
                N = sc.nextInt();
                System.out.println("New Number: " + extractOddDigits(N));
                break;
                case 7: System.out.println("Program terminating ….");
            }
        } while (choice < 7);
        sc.close();
    }  
 /* add method code here */
    public static void mulTest(){
        Scanner sc = new Scanner(System.in);
        int correct = 0;
        for(int i = 0; i<5; i++){
            int x = (int) (Math.random() * 10);
            int y = (int) (Math.random() * 10);
            System.out.println("What is "+ x + " x " + y + "?");
            int ans = sc.nextInt();
            if (ans == x*y){
                correct++;
            }
        }
        System.out.println("You got " + correct + " questions correct.");
        sc.close();
    }

    public static int divide(int m, int n){
        int count = 0;
        while(m >= n){
            m = m - n;
            count++;
        }
        return count;
    }

    public static int modulus(int m, int n){
        int result;
        int multiple = divide(m,n);
        result = m - n*multiple;
        return result;
    }

    public static int countDigits(int n){
        int count = 0;
        if(n<0){
            System.out.println("error input");
            return -1;
        }
        while (n > 0){
            n = divide(n, 10);
            if(n==0){
                break;
            }
            count++;
        }
        return count+1;
    }

    public static int position(int m, int digit){
        int numofdigits = countDigits(m);
        for(int i = 0; i<numofdigits; i++){
            int var;
            var = modulus(m, 10);
            m = divide(m, 10);
            if(var == digit){
                return i+1;
            }
        }
        return -1;
    }

    public static long extractOddDigits(long n){
        int count = 1;
        long tempn = n;
        long newnew = 0;
        int neverodd = 0;
        while(tempn >= 10){
            tempn = tempn/10;
            count = count + 1;
        }
        for(int i = 0; i<count; i++){
            long num = n % 10;
            if(num % 2==1){
                newnew = newnew + num;
                newnew = newnew * 10;
                neverodd = 1;
            }
            n = n/10;
        }
        newnew = newnew/10;

        count = 1;
        long tempnew = newnew;
        while(tempnew >= 10){
            tempnew = tempnew/10;
            count = count + 1;
        }
        long newer = 0;
        for(int i = 0; i<count; i++){
            long num = newnew % 10;
            if(num % 2==1){
                newer = newer + num;
                newer = newer * 10;
            }
            newnew = newnew/10;
        }

        if(neverodd == 0){
            return -1;
        }

        return newer/10;
    }

}