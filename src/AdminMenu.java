import api.AdminResource;
import model.*;

import java.util.*;

public class AdminMenu {

    private static AdminResource adminResource = AdminResource.getSingleton();

    public static void adminMenu() {
        System.out.println("Welcome to the Admin Menu");
        System.out.println("Please Select a number 1 through 6 from the following options");
        System.out.println("1. See All Customers");
        System.out.println("2. See All Rooms");
        System.out.println("3. See All Reservations");
        System.out.println("4. Add Room");
        System.out.println("5. Back to Main Menu");
        System.out.println("6. Create test data: add room");
        Scanner scanner = new Scanner(System.in);

        try {
            int selection = Integer.parseInt(scanner.nextLine());

            switch (selection) {
                case 1:
                    seeAllCustomers();
                    break;
                case 2:
                    seeAllRooms();
                    break;
                case 3:
                    seeAllReservations();
                    break;
                case 4:
                    addRoom();
                    break;
                case 5:
                    MainMenu.mainMenu();
                    break;
                case 6: //TESTING
                    Room room = new Room("101", 100.0, RoomType.valueOfLabel("1"));
                    adminResource.addRoom(room.getRoomNumber(), room.price, room.getRoomType());
                    Room room2 = new Room("2", 100.0, RoomType.valueOfLabel("1"));
                    adminResource.addRoom(room2.getRoomNumber(), room.price, room.getRoomType());
                    adminMenu();
                default:
                    System.out.println("Invalid selection - Please choose an " +
                            "integer between 1 and 6");
                    adminMenu();

            }
        } catch (Exception ex) {
            ex.getLocalizedMessage();
            adminMenu();
        }
    }

    public static void seeAllCustomers() {
        System.out.println(adminResource.getAllCustomers());
        adminMenu();
    }

    public static void seeAllRooms() {
        System.out.println(adminResource.getAllRooms());
        adminMenu();
    }

    public static void seeAllReservations() {
        adminResource.displayAllReservations();
        adminMenu();
    }

    public static void addRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is the Room Number?");
        String roomNumber = scanner.nextLine();
        if (!adminResource.getAllRoomNumbers().contains(roomNumber)) {


            System.out.println("Room Type: Enter 1 for SINGLE and 2 for DOUBLE");
            RoomType roomType = RoomTypeInput(scanner);
            System.out.println("What is the Room Price?");
            Double roomPrice = scanner.nextDouble();
            adminResource.addRoom(roomNumber, roomPrice, roomType);
            adminMenu();
        } else {
            System.out.println("That Room number is already in the system");
            adminMenu();
        }
    }

    public static RoomType RoomTypeInput(Scanner scanner) {
        //source for labels and valueOfLabel:  https://www.baeldung.com/java-enum-values
        try {
            return RoomType.valueOfLabel(scanner.nextLine());
        } catch (IllegalArgumentException ex){
            System.out.println("Please enter 1 if the Room Type is a Single and 2 if it is a Double.");
            return RoomTypeInput(scanner);
        }
    }

}
