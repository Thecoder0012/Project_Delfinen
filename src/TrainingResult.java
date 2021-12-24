public class TrainingResult {

    public String getCompetition() {
        return competition;
    }

    public int getPlace() {
        return place;
    }

    public double getTime() {
        return time;
    }

    private String competition;
    private int place;
    private double time;

    public TrainingResult(String competition, int place, double time){
        this.competition = competition;
        this.place = place;
        this.time = time;
    }
}
