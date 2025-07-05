public class BookingThread extends Thread {
    private TicketCounter counter;
    private String name;
    private int seatsToBook;

    public BookingThread(TicketCounter counter, String name, int seatsToBook) {
        this.counter = counter;
        this.name = name;
        this.seatsToBook = seatsToBook;
    }

    @Override
    public void run() {
        counter.bookTicket(name, seatsToBook);
    }
}
