import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws Exception {
        Scanner mainMenu = new Scanner(System.in);

        System.out.println("What would you like to do?" +
                "\n[0] Submit a ticket" +
                "\n[1] View previous tickets");

        int choice = mainMenu.nextInt();

        switch(choice) {
            case 0:
                submitTicket();
                break;
            case 1:
                viewPrevTickets();
                break;
            default:
                System.out.println("Not a valid option");
        }

        mainMenu.close();
    }

    public static void submitTicket() throws Exception {
        Scanner ticketMenu = new Scanner(System.in);

        Ticket ticket = new Ticket();

        System.out.print("Amount: ");
        ticket.setAmount(ticketMenu.nextDouble());
        System.out.println();

        System.out.print("Description: ");
        ticket.setDescription(ticketMenu.next());
        System.out.println();

        Status status = Status.PENDING;

        System.out.println("Ticket sent!" +
                "\n Amount: " + ticket.getAmount() +
                "\n Description: " + ticket.getDescription() +
                "\n Status: " + status);

        ticketMenu.close();
    }

    public static void viewPrevTickets() {

    }
}
