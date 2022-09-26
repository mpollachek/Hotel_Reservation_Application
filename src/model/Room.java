package model;

import java.util.Objects;

public class Room implements IRoom {

    public String roomNumber;

    public Double price;

    public RoomType enumeration;

    public Room(String roomNumber, Double price, RoomType enumeration) {
        super();
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public Double getRoomPrice() {
        return price;
    }

    public RoomType getRoomType() {
        return enumeration;
    }

    public boolean isFree() {
        return false;
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber + ", Price: $" + price + " Room Type: " + enumeration;
    }

    @Override
    public boolean equals(Object obj) {

        if(this == obj)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        Room room = (Room) obj;

        return (room.roomNumber.equals(this.roomNumber) && room.price == this.price &&
                room.enumeration == this.enumeration);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.roomNumber);
    }

}
