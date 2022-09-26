package service;

import model.Customer;
import model.Reservation;
import model.IRoom;
import model.Room;

import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {

    private static ReservationService Singleton = new ReservationService();

    private ReservationService() {}

    public static ReservationService getSingleton() {
        return Singleton;
    }

    private Map<String, Collection> reservations = new HashMap<>();

    private Map<String, IRoom> rooms = new HashMap<>();

    public void addRoom(String roomNumber, IRoom room) {
        rooms.put(roomNumber, room);
    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        return reservations.get(customer.getEmail());
    }

    public Collection<Reservation> getRoomReservation(IRoom room) {

        Collection <Reservation> allReservations = getAllReservations();
        Collection <Reservation> roomReservations = new ArrayList<>();
        for (Reservation res : allReservations) {
            if (res.room.getRoomNumber().equals(room.getRoomNumber())) {
                roomReservations.add(res);
            }
        }
       return roomReservations;
    }

    public void printAllReservation() {
        Collection<Reservation> reservations = getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("There are no reservations yet");
        } else {
            for(Reservation reservation : reservations) {
                System.out.println(reservation + "\n");
            }
        }
    }

    Collection <Reservation> getAllReservations() {
        Collection <Reservation> allReservations = new LinkedList<>();

        for(Collection<Reservation> reservations : reservations.values()) {
            allReservations.addAll(reservations);
        }
        return allReservations;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate,
                                    Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);

        LinkedList<Reservation> currentReservations = new LinkedList<>();

        if (getCustomersReservation(customer) != null) {
            for (Reservation res : getCustomersReservation(customer)) {
                currentReservations.add(res);
            }
        }
        currentReservations.add(reservation);
        reservations.put(customer.getEmail(), currentReservations);
        return reservation;
    }


    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public Set<String> getAllRoomNumbers() { return rooms.keySet(); }

    public Collection <IRoom> findARoom(Date checkIn, Date checkOut) {

        Collection<IRoom> availableRooms = findOpenRooms(checkIn, checkOut);

        if (availableRooms == null || availableRooms.isEmpty()) {
            return Collections.emptyList();
        } else {
            return availableRooms;
        }
    }

    public Collection<IRoom> findOpenRooms(Date checkInDate, Date checkOutDate) {
        //Source for removing with stream: https://www.baeldung.com/java-collection-remove-elements
        //source for comparing dates: https://mkyong.com/java/how-to-compare-dates-in-java/

        Collection<IRoom> allRooms = getAllRooms();
        Collection<IRoom> bookedRooms = new ArrayList<>();

        for (IRoom room : getAllRooms()) {
            for (Reservation res : getRoomReservation(room)) {
                String booked = isBooked(res.getRoom().getRoomNumber(), checkInDate,
                        checkOutDate, res.getCheckInDate(), res.getCheckOutDate());
                bookedRooms.add(getARoom(booked));
            }
        }
        return allRooms.stream().filter(rm -> !bookedRooms.contains(rm)).toList();
    }

    public String isBooked(String roomNumber, Date checkInDate,
                    Date checkOutDate, Date resCheckInDate, Date resCheckOutDate) {
        if (checkOutDate.after(resCheckInDate) && checkInDate.before(resCheckOutDate)) {
            return roomNumber;
        } else { return null; }
    }

    public Date getRecommendedDate(Date date) {

        int RecommendedDays = 7;

        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.add(Calendar.DATE, RecommendedDays);
        return day.getTime();
    }

    public Collection <IRoom> getRecommendedRooms(Date checkInDate, Date checkOutDate) {

        Date checkIn = getRecommendedDate(checkInDate);
        Date checkOut = getRecommendedDate(checkOutDate);

        return findOpenRooms(checkIn, checkOut);
    }
}