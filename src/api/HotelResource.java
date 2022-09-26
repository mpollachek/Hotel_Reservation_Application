package api;

import model.Customer;
import service.CustomerService;
import model.Reservation;
import model.IRoom;
import service.ReservationService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class HotelResource {

    private static HotelResource Singleton = new HotelResource();

    private CustomerService customerService = CustomerService.getSingleton();

    private static ReservationService reservationService = ReservationService.getSingleton();

    private HotelResource() {}

    public static HotelResource getSingleton() {
        return Singleton;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String firstName, String lastName, String email) {
        customerService.addCustomer(firstName, lastName, email);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return reservationService.reserveARoom(getCustomer(customerEmail), room, checkInDate,checkOutDate);
    }

    public Collection<Reservation> getCustomerReservations(String customerEmail) {
       Collection<Reservation> customerReservations;
       customerReservations = reservationService.getCustomersReservation(getCustomer(customerEmail));

        return customerReservations;
    }

    public static Collection<IRoom> findRoom(Date checkIn, Date checkOut) {
        return reservationService.findARoom(checkIn, checkOut);
    }

//    public Collection <IRoom> findARoom(Date checkIn, Date checkOut) {
//
//        Collection<IRoom> availableRooms = reservationService.findOpenRooms(checkIn, checkOut);
//
//                    if (availableRooms == null || availableRooms.isEmpty()) {
//                        availableRooms = reservationService.getRecommendedRooms(checkIn, checkOut);
//
//                        if (!availableRooms.isEmpty()) {
//                            System.out.println(availableRooms);
//                            return availableRooms;
//                        } else {
//                            System.out.println("There are no available rooms for those dates" +
//                                    "\nPlease select new dates for your stay");
//                            return Collections.emptyList();
//                        }
//                    } else {
//                        System.out.println(availableRooms);
//                        return availableRooms;
//                    }
//                }






//    public Collection getDatesBetween(Date checkIn, Date checkOut) {
//
//        Collection<Date> dates = new LinkedList<>();
//        Date count = checkIn;
//
//        do {
//            dates.add(count);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(count);
//            calendar.add(Calendar.DATE, 1);
//            count = calendar.getTime();
//
//        }  while (count.before(checkOut));
//
//        return dates;
//    }

    //get reserved dates for room

//    public Collection<IRoom> findRecommendedRooms(Date checkIn, Date checkOut) {
//        return reservationService.recommendedRooms(checkIn, checkOut);
//    }
//
//    public Date addSearchDays(Date date) {
//        return reservationService.addSearchDays(date);
//    }

    public Set<String> getAllCustomersEmails() {
        return customerService.getAllCustomersEmails();
    }

    public Set<String> getAllRoomNumbers() {
        return reservationService.getAllRoomNumbers();
    }

    public Date recommendedDate(Date date) {
        return reservationService.getRecommendedDate(date);
    }
}
