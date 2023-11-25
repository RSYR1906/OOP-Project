import java.util.Scanner;

public class PlaneApp {
    public static void main(String[] args){

        Plane plane = new Plane();

        // plane.assignSeat(10, 10001);
        // plane.assignSeat(12, 10002);
        // plane.assignSeat(8, 10003);

        int choice;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Perform the following methods:");
            System.out.println("1: show num of empty seats");
            System.out.println("2: show list of empty seats");
            System.out.println("3: show list of customers tgt w seat num in order of seat num");
            System.out.println("4: show list of customers tgt w seat num in order of customerid");
            System.out.println("5: assign a customer to a seat");
            System.out.println("6: remove a seat assignment");
            System.out.println("7: quit");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                plane.showNumEmptySeats();
                break;
                case 2:
                plane.showEmptySeats();
                break;
                case 3:
                plane.showAssignedSeats(true);
                break;
                case 4:
                plane.showAssignedSeats(false);
                break;
                case 5:
                System.out.print("Enter seat ID: ");
                int seatId = sc.nextInt();
                System.out.print("Enter customer ID: ");
                int customerId = sc.nextInt();
                plane.assignSeat(seatId, customerId);
                break;
                case 6:
                System.out.print("Enter seat ID to unassign: ");
                int unassignSeatId = sc.nextInt();
                plane.unAssignSeat(unassignSeatId);
                break;
                case 7:
                sc.close();
                System.exit(0);
                break;
            }
        } while (choice < 7);
        
    }
    
}
