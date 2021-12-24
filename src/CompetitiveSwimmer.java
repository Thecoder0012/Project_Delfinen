import java.util.ArrayList;

public class CompetitiveSwimmer extends Member{

    private String discipline;
    private String team;
    private ArrayList<TrainingResult> trainingResults;

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    private String trainer;

    public CompetitiveSwimmer(String name, String dateOfBirth, String address, boolean status, String discipline, String team, ArrayList<TrainingResult> trainingResults, String trainer) {
        super(name, dateOfBirth,address, status);
        this.discipline = discipline;
        this.team = team;
        this.setType("Competitive");
        this.trainingResults = trainingResults;
        this.trainer = trainer;
    }

    public CompetitiveSwimmer() {

    }

    public ArrayList<TrainingResult> getTrainingResults() {
        return trainingResults;
    }

    public void setTrainingResults(ArrayList<TrainingResult> trainingResults) {
        this.trainingResults = trainingResults;
    }
    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }


}
