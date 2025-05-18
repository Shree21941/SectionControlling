class Train {
    String trainId;
    Station currentStation;
    Station destination;
    int priority;

    public Train(String trainId, Station from, Station to, int priority) {
        this.trainId = trainId;
        this.currentStation = from;
        this.destination = to;
        this.priority = priority;
    }

    public void moveTo(Station next) {
        if (currentStation != null) {
            currentStation.releaseTrain(this);
        }
        currentStation = next;
        next.arriveTrain(this);
    }
}