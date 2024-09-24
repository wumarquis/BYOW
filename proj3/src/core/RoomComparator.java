package core;

import java.util.Comparator;

public class RoomComparator implements Comparator<Room> {
    private Room currRoom;
    public RoomComparator(Room room) {
        currRoom = room;
    }
    @Override
    public int compare(Room o1, Room o2) {
        double distR1 = distance(currRoom, o1);
        double distR2 = distance(currRoom, o2);

        return Double.compare(distR1, distR2);
    }

    private double distance(Room r1, Room r2) {
        int x1 = r1.getxCoord();
        int y1 = r1.getyCoord();
        int x2 = r2.getxCoord();
        int y2 = r2.getyCoord();

        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
