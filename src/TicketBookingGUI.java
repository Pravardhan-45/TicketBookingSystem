import javax.swing.*;
import java.awt.*;

public class TicketBookingGUI extends JFrame {
    private TicketCounter counter;
    private JTextField nameField;
    private JTextField seatsField;
    private JLabel remainingLabel;

    public TicketBookingGUI() {
        counter = new TicketCounter(10);

        setTitle("Ticket Booking System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 220);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setResizable(false);

        // Name Panel
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Name:"));
        nameField = new JTextField(20);
        namePanel.add(nameField);
        add(namePanel);

        // Seats Panel
        JPanel seatsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        seatsPanel.add(new JLabel("No. of Seats:"));
        seatsField = new JTextField(10);
        seatsPanel.add(seatsField);
        add(seatsPanel);

        // Book Button
        JButton bookBtn = new JButton("Book Ticket");
        bookBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(bookBtn);

        // Remaining Label
        remainingLabel = new JLabel("Available Seats: " + counter.getAvailableSeats() + "/" + counter.getCapacity(), JLabel.CENTER);
        remainingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        remainingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(10));
        add(remainingLabel);

        // Action
        bookBtn.addActionListener(e -> bookTicket());

        setVisible(true);
    }

    private void bookTicket() {
        String name = nameField.getText().trim();
        String seatsText = seatsField.getText().trim();

        if (name.isEmpty() || seatsText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int requestedSeats;
        try {
            requestedSeats = Integer.parseInt(seatsText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for seats.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (requestedSeats <= 0) {
            JOptionPane.showMessageDialog(this, "Seat count must be at least 1.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int before = counter.getAvailableSeats();

        BookingThread thread = new BookingThread(counter, name, requestedSeats);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int after = counter.getAvailableSeats();
        remainingLabel.setText("Available Seats: " + after + "/" + counter.getCapacity());

        if (after == before) {
            JOptionPane.showMessageDialog(this,
                    "❌ Booking failed!\nRequested: " + requestedSeats + "\nAvailable: " + before,
                    "Booking Failed", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "✅ Booking successful!\n" + name + " booked " + requestedSeats + " seat(s).",
                    "Booking Success", JOptionPane.INFORMATION_MESSAGE);
        }

        nameField.setText("");
        seatsField.setText("");

        if (counter.getAvailableSeats() == 0) {
            JOptionPane.showMessageDialog(this, "All tickets are booked!\nApp will now close.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicketBookingGUI());
    }
}
