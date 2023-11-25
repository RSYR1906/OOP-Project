public class PlaneSeat{
    int seatId;
    boolean assigned;
    int customerId;

    public PlaneSeat(int seat_id){
        this.seatId = seat_id;
    }
    public int getSeatId() {
        return seatId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public boolean isAssigned() {
        return assigned;
    }
    public void assign(int cust_Id){
        this.assigned = true;
        this.customerId = cust_Id;
    }
    public void unassign(){
        this.assigned = false;
    }
}

