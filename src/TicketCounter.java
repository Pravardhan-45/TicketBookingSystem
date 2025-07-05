public class TicketCounter {
    private int availableSeats;
    private int capacity;

    public TicketCounter(int seats) {
        this.availableSeats = seats;
        this.capacity = seats;
    }

    public synchronized boolean bookTicket(String name, int requestedSeats) {
        System.out.println(name + " is trying to book " + requestedSeats + " seats.");

        if (requestedSeats <= availableSeats) {
            System.out.println("✅ Booking successful for " + name);
            availableSeats -= requestedSeats;
            System.out.println("🎫 Seats left: " + availableSeats + "/" + capacity);
            return true;
        } else {
            System.out.println("❌ Booking failed for " + name + " (Requested: " + requestedSeats +
                    ", Available: " + availableSeats + "/" + capacity + ")");
            return false;
        }
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public int getCapacity() {
        return capacity;
    }
}
