package service;


import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class Driver {
    public static void main(String[] args) {

        CustomerService customerService = CustomerService.getSingleton();
        ReservationService reservationService = ReservationService.getSingleton();


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2022);
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        calendar.set(Calendar.DAY_OF_MONTH, 18);
        Date checkIn = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, 22);
        Date checkOut = calendar.getTime();

        Customer customer = new Customer("Mike", "P", "mp@gmail.com");
        Room room = new Room("101", 100.0, RoomType.SINGLE);

        System.out.println("Customer Service Testing");
        System.out.println("Room: " + room);
        System.out.println("Customer: " + customer);

        customerService.addCustomer(customer.getFirstName(), customer.getLastName(), customer.getEmail());
        Customer customerByEmail = customerService.getCustomer(customer.getEmail());
        Collection<Customer> allCustomers = customerService.getAllCustomers();

        System.out.println("customer by email: " + customerByEmail);
        System.out.println("All Customers: " + allCustomers);

        System.out.println("\nCustomer Service Testing");

        reservationService.addRoom(room.getRoomNumber(), room);

        System.out.println("Added Room: " + reservationService.getARoom("101"));
        System.out.println("All Rooms: " + reservationService.getAllRooms());

        Collection<IRoom> open = reservationService.findOpenRooms(checkIn, checkOut);
        System.out.println("Open: " + open);

        reservationService.reserveARoom(customer, room, checkIn, checkOut);
        System.out.println("Reservation added: " + reservationService.getCustomersReservation(customer));

        Collection<IRoom> openAfterReserve = reservationService.findOpenRooms(checkIn, checkOut);
        System.out.println("Open After Reservation: " + openAfterReserve);

        System.out.println("find rooms:");
        reservationService.findARoom(checkIn, checkOut);

        System.out.println("print all reservations:");
        reservationService.printAllReservation();

    }
}
