import java.util.Arrays;

public class Plane{
    PlaneSeat[] seat = new PlaneSeat[12];
    int numEmptySeat;
    public Plane(){
        this.seat = new PlaneSeat[12];
        for (int i = 0; i < 12; i++) {
            this.seat[i] = new PlaneSeat(i + 1);
        }
        this.numEmptySeat = 12;
    }
    public PlaneSeat[] sortSeats(){
        PlaneSeat[] copySeats = new PlaneSeat[12];
        for(int i = 0; i<12; i++){
            copySeats[i] = seat[i];
        }
        for(int i = 0; i<12; i++){
            int key = copySeats[i].customerId;
            int j = i-1;

            while(j>=0 && copySeats[j].customerId > key){
                copySeats[j+1] = copySeats[j];
                j--;
            }
            copySeats[j+1].customerId = key;
        }
        return copySeats;
    }
    public void showNumEmptySeats(){
        System.out.println(numEmptySeat + " seats empty");
    }
    public void showEmptySeats(){
        for(int i = 0; i<12; i++){
            if(seat[i].assigned == false){
                System.out.println(seat[i].seatId);
            }
        }
    }
    public void showAssignedSeats(boolean bySeatId){
        PlaneSeat[] sortedSeats = Arrays.copyOf(seat, seat.length);
        
        if (bySeatId) {
            Arrays.sort(sortedSeats, (a, b) -> Integer.compare(a.getSeatId(), b.getSeatId()));
        } else {
            Arrays.sort(sortedSeats, (a, b) -> Integer.compare(a.getCustomerId(), b.getCustomerId()));
        }
        
        System.out.println("List of customers with assigned seats:");
        for (PlaneSeat seat : sortedSeats) {
            if (seat.isAssigned()) {
                System.out.println("Seat " + seat.getSeatId() + ": Customer " + seat.getCustomerId());
            }
        }
    }
    public void assignSeat(int seatId, int cust_id){
        if (seatId >= 1 && seatId <= 12 && seat[seatId - 1].isAssigned() == false) {
            seat[seatId - 1].assign(cust_id);
            numEmptySeat--;

            System.out.println("Seat " + seatId + " has been assigned to customer " + cust_id);
        } else {
            System.out.println("Seat " + seatId + " is already occupied or invalid.");
        }
    }
    public void unAssignSeat(int seatId){
        for(int i = 0; i<12; i++){
            if(seat[i].seatId == seatId){
                seat[i].unassign();
                numEmptySeat++;
                break;
            }
        }
    }
}

