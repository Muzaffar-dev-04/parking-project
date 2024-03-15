package parking.back.modul;

import java.io.Serializable;

public class Car implements Serializable {
    private Park parking;
    private int floor;
    private int row;
    private int col;
    private String type;

    public Car(String type,Park parking) {
        this.type = type;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getType() {
        return type;
    }
    public void setType(String type){
        this.type = type;
    }

    @Override
    public String toString() {
        return "Car{" +
                "parking=" + parking +
                ", floor=" + floor +
                ", row=" + row +
                ", col=" + col +
                ", type='" + type + '\'' +
                '}';
    }
}
