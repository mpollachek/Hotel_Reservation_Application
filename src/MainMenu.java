import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import api.HotelResource;
import model.*;

public class MainMenu {

    private static HotelResource hotelResource = HotelResource.getSingleton();

    private static String pattern = "MM/dd/yyyy";
    private static SimpleDateFormat simpleDateFormat =  new SimpleDateFormat(pattern);

    public static void printMainMenu() {
        System.out.println("\nWelcome to the Hotel!");
        System.out.println("Please Select a number 1 through 6 from the following options");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("6. Create test data: customer");
    }

    public static void mainMenu() {

        Scanner scanner = new Scanner(System.in);

        printMainMenu();

        try {
            int selection = Integer.parseInt(scanner.nextLine());

            switch (selection) {
                case 1:
                    findAndReserveRoom();
                    break;
                case 2:
                    seeMyReservations();
                    break;
                case 3:
                    createAccount();
                    break;
                case 4:
                    AdminMenu.adminMenu();
                    break;
                case 5:
                    System.exit(0);
                    break;
                case 6: //TESTING
                    hotelResource.createACustomer("mike", "p", "mp@gmail.com");
                    hotelResource.createACustomer("test", "test", "test@gmail.com");
                    mainMenu();
                default:
                    System.out.println("Invalid selection - Please choose an " +
                            "integer between 1 and 6");
                    mainMenu();

            }
        } catch (Exception ex){
            ex.getLocalizedMessage();
            mainMenu();
        }
    }

    private static void findAndReserveRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your email address? i.e. name@domain.com");
        String email = scanner.nextLine();
        //need to check if customer has account or not and send to create account if not
        if (!hotelResource.getAllCustomersEmails().contains(email)) {
            System.out.println("Please create an account before reserving a room");
            createAccount();
        } else {
            System.out.println("What is your check in date? MM/DD/YYYY");
            Date checkInDate = getInputDate(scanner);
            System.out.println("What is your check out date? MM/DD/YYYY");
            Date checkOutDate = getInputDate(scanner);
            Collection<IRoom> findRooms = hotelResource.findRoom(checkInDate, checkOutDate);
            if (findRooms.isEmpty()) {
                Date recCheckIn = hotelResource.recommendedDate(checkInDate);
                Date recCheckOut = hotelResource.recommendedDate(checkOutDate);
                Collection<IRoom> findRecommendedRooms = hotelResource.findRoom(recCheckIn, recCheckOut);
                if (findRecommendedRooms.isEmpty()) {
                    System.out.println("There are no available rooms for those dates" +
                            "\nPlease select new dates for your stay");
                    mainMenu();
                } else {
                    System.out.println("There are no rooms available for those dates" +
                            "\nHere are alternative Rooms you may choose from: " +
                            "\nCheck In: " + recCheckIn +
                            "\nCheck Out: " + recCheckOut);
                    System.out.println(findRecommendedRooms);
                    bookRoom(email, recCheckIn, recCheckOut, findRecommendedRooms);
                }
            } else {
                System.out.println(findRooms);
                bookRoom(email, checkInDate, checkOutDate, findRooms);
            }
        }
    }


    private static void bookRoom(String email, Date checkInDate, Date checkOutDate, Collection<IRoom> availableRooms) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to book a room, Y or N?");
        Collection <String> rooms = new ArrayList<>();
        for (IRoom room : availableRooms) {
            rooms.add(room.getRoomNumber());        }

        String choice = scanner.nextLine();
            if (choice.toLowerCase().equals("n")) {
                mainMenu();
            } else if (choice.toLowerCase().equals("y")) {

                System.out.println("Enter the room number you wish to book");
                String roomNumber = scanner.nextLine();
                if (rooms.contains(roomNumber)) {
                    Reservation reservation = hotelResource.bookARoom
                            (email, hotelResource.getRoom(roomNumber), checkInDate, checkOutDate);
                    System.out.println("Your Reservation is as follows: \n" + reservation);
                    mainMenu();
                } else {
                    System.out.println("That is not a valid choice, pick from the following rooms:" +
                            "\n" + rooms);
                    bookRoom(email, checkInDate, checkOutDate, availableRooms);
                }
            } else {
                System.out.println("That is not a valid choice " +
                        "If you would like to book a room enter Y and if not enter N");
                mainMenu();
            }
            }



        private static Date getInputDate (Scanner scanner){
        //source: https://mkyong.com/java/java-date-and-calendar-examples/
            String pattern = "MM/dd/yyyy";
            SimpleDateFormat simpleDateFormat =  new SimpleDateFormat(pattern);
            try {
                Date date = simpleDateFormat.parse(scanner.nextLine());
                return date;

            } catch(ParseException ex) {
                System.out.println("Wrong format - Date should be MM/DD/YYYY");
                findAndReserveRoom();
            }
            return null;
        }

        private static void seeMyReservations () {
            Scanner scanner = new Scanner(System.in);
            System.out.println("What is your email address?  i.e. name@domain.com");
            String email = scanner.nextLine();
            System.out.println(hotelResource.getCustomerReservations(email));
            mainMenu();
        }

        private static void createAccount() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("What is your email address?  i.e. name@domain.com");
            String email = scanner.nextLine();
            if (hotelResource.getAllCustomersEmails().contains(email)) {
                System.out.println("There is already an account registered with that email address");
                mainMenu();
            } else {
                System.out.println("What is your first name?");
                String firstName = scanner.nextLine();
                System.out.println("What is your last name?");
                String lastName = scanner.nextLine();
                try {
                    hotelResource.createACustomer(firstName, lastName, email);
                    Customer justRegistered = hotelResource.getCustomer(email);
                    System.out.println("You have just registered the following account information:");
                    System.out.println("email: " + justRegistered.getEmail());
                    System.out.println("first name: " + justRegistered.getFirstName());
                    System.out.println("last name: " + justRegistered.getLastName());
                    mainMenu();
                } catch (Exception ex) {
                    System.out.println(ex.getLocalizedMessage());
                    mainMenu();
                }
            }

        }

    }