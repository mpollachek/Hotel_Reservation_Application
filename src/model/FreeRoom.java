package model;

public class FreeRoom extends Room {

    public FreeRoom(String roomNumber, Double price, RoomType enumeration) {
        super(roomNumber, price, enumeration);
        this.price = 0.0;
    }

    @Override
    public String toString() {
        return super.toString() + "\nFree Room: " + super.toString();
    }
}
