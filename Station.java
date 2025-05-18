import java.util.*;

class Station {
    String name;
    int platformCount;
    List<Train> occupyingTrains = new ArrayList<>();
    PriorityQueue<Train> waitingQueue = new PriorityQueue<>((a, b) -> b.priority - a.priority);

    public Station(String name, int platformCount) {
        this.name = name;
        this.platformCount = platformCount;
    }

    public boolean hasFreePlatform() {
        return occupyingTrains.size() < platformCount;
    }

    public void arriveTrain(Train train) {
        if (hasFreePlatform()) {
            occupyingTrains.add(train);
        } else {
            waitingQueue.offer(train);
        }
    }

    public void releaseTrain(Train train) {
        occupyingTrains.remove(train);
        if (!waitingQueue.isEmpty()) {
            Train next = waitingQueue.poll();
            arriveTrain(next);
        }
    }
}