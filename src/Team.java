import java.util.ArrayList;

public class Team {

    private ArrayList<CompetitiveSwimmer> swimmers = new ArrayList<>();
    private String teamName;
    private String trainer;

    public Team() {

    }

    public Team(ArrayList<CompetitiveSwimmer> swimmers, String teamName, String trainer) {
        this.swimmers = swimmers;
        this.teamName = teamName;
        this.trainer = trainer;
    }


    public ArrayList<CompetitiveSwimmer> getTeam() {
        return swimmers;
    }

    public void setTeam(ArrayList<CompetitiveSwimmer> swimmers) {
        this.swimmers = swimmers;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }
}
