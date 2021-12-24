import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Member{

    private String name;
    private String dateOfBirth;
    private String address;
    private boolean status;
    private double price;
    private String type;

    public Member(){

    }

    public Member(String name, String dateOfBirth, String address, boolean status){

        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.status = status;

        //Calc price
        double basePrice = 1000;
        //double VAT = 1.25;
        double seniorDiscount = 0.75;
        double seniorAddon = 600;
        double passiveMemberPrice = 500;

        //If the member has passive membership
        if (status == false) {
            price = passiveMemberPrice;
        } else {
            //else membership is active
            price = basePrice;

            //formatting birthday string to calculate age
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String date = dateOfBirth;

            //convert String to LocalDate
            LocalDate birthday = LocalDate.parse(date, formatter);
            LocalDate today = LocalDate.now();
            Period p = Period.between(birthday, today);

            //if member is 18+
            if (p.getYears() >= 18) {
                price += seniorAddon;
            }
            //if member is 60+
            if (p.getYears() >= 60) {
                price *= seniorDiscount;
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", price=" + price +
                ", type='" + type + '\'' +
                '}';
    }
}
