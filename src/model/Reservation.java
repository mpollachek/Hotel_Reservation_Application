package model;

import java.util.Date;
import java.util.Calendar;

public class Reservation {


    public Customer customer;

    public IRoom room;

    public Date checkInDate;

    public Date checkOutDate;

    public Reservation(Customer customer, IRoom room,
                       Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;

    }

    public IRoom getRoom() { return this.room; }

    public Customer getCustomer() { return this.customer; }

    public String getCustomerEmail() { return this.customer.getEmail(); }

    public Date getCheckInDate() { return this.checkInDate; }

    public Date getCheckOutDate() { return this.checkOutDate; }

    @Override
    public String toString() {
        return "\nCustomer: " + this.customer.toString()
                + "\nRoom: " + this.room.toString()
                + "\nCheckIn Date: " + this.checkInDate
                + "\nCheckOut Date: " + this.checkOutDate;
    }
}
