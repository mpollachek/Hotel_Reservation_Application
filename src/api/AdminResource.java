package api;

import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AdminResource {

    private static AdminResource Singleton = new AdminResource();

    private CustomerService customerService = CustomerService.getSingleton();

    private ReservationService reservationService = ReservationService.getSingleton();

    private AdminResource() {}

    public static AdminResource getSingleton() { return Singleton; }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

//    public void addRoom(List<IRoom> rooms) {
//        rooms.forEach(room -> {
//            reservationService.addRoom(room);
//        });
//    }

    public void addRoom(String roomNumber, Double roomPrice, RoomType roomType) {
        Room room = new Room(roomNumber, roomPrice, roomType);
        reservationService.addRoom(roomNumber, room);
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservation();
    }

    public Set<String> getAllRoomNumbers() {
        return reservationService.getAllRoomNumbers();
    }

}
