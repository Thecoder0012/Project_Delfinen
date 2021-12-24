import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Motionist extends Member{

        boolean training;
        boolean classTraining;

        public Motionist(String name, String dateOfBirth, String address, boolean status, boolean training, boolean classTraining){
            super(name, dateOfBirth, address, status);
            this.training = training;
            this.classTraining = classTraining;
            this.setType("Motionist");



        }

    public Motionist() {

    }


    public boolean getTraining(){
            return training;
        }

        public void setTraining(boolean training){
            this.training = training;
        }
        public boolean getClassTraining(){
            return classTraining;
        }
        public void setClassTraining(boolean training){
            this.classTraining = training;
        }

}
