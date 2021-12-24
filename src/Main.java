import java.io.*;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        ArrayList<Member> memberList = new ArrayList<>();
        ArrayList<Team> teamList = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        populateMemberArrayList(memberList, teamList);
        System.out.println("Welcome to our program!\n");
        boolean check = true;


        while (check) {
            System.out.println("Choose 1 to create member\nChoose 2 to edit member\nChoose 3 to view all members\nChoose 4 to manage teams\nChoose 5 to view expected yearly income\nChoose 6 to view member competition stats\nChoose 7 to leave the program");
            switch (Integer.parseInt(input.nextLine())) {
                case 1:
                    //CREATE MEMBER
                    Member newMember = (createMember(input));
                    //printMemberToFile(newMember);
                    memberList.add(newMember);
                    updateMemberFile(memberList);
                    break;
                case 2:
                    //EDIT MEMBER SUBMENU
                    System.out.println("Do you want to:\n1: Edit existing member\n2: Delete existing member\n3: Add new score to records");
                    int editSelect = Integer.parseInt(input.nextLine());
                    if (editSelect == 1) { //UPDATE MEMBER
                        viewMember(memberList, "Motionist Competitive");
                        System.out.println("Type in member index number to edit: ");
                        int indexMember = input.nextInt();
                        editMember(memberList, createMember(input), indexMember);
                        input.nextLine();
                        System.out.println("Your member is edited succesfully\n");
                    } else if (editSelect == 2) { //DELETE MEMBER
                        viewMember(memberList, "Motionist Competitive");
                        System.out.println("Type in member index number to delete: ");
                        memberList.remove(Integer.parseInt(input.nextLine()));
                        updateMemberFile(memberList);
                        System.out.println("Removed!");
                    } else if (editSelect == 3) { //ADD TRAINING RESULT
                        viewMember(memberList, "Competitive");
                        System.out.println("Type in member index number to add to: ");
                        int indexMember = Integer.parseInt(input.nextLine());
                        System.out.println("Type in competition name: ");
                        String competition = input.nextLine();
                        System.out.println("Type in placement in the competition: ");
                        int place = Integer.parseInt(input.nextLine());
                        System.out.println("Type in their time: ");
                        double time = Double.parseDouble(input.nextLine());
                        addTrainingResultToCompSwimmer(memberList, competition, place, time, indexMember);
                        updateMemberFile(memberList);
                        System.out.println("competition succesfully added to member\n");
                    }
                    break;
                case 3:
                    //READ MEMBER
                    viewMember(memberList, "Motionist Competitive");
                    break;
                case 4:
                    //MANAGE TEAMS
                    System.out.println("1: Create team\n2: Edit team\n3: View teams\n4: Back");
                    int teamSelect = Integer.parseInt(input.nextLine());
                    if (teamSelect == 1) { //CREATE TEAM
                        teamList.add(createTeam(input, memberList));
                        System.out.println("Team added!");
                    } else if(teamSelect == 2) { //EDIT TEAMS
                        viewTeams(teamList);
                        System.out.println("Enter team index number to edit: ");
                        int teamEditSelect = Integer.parseInt(input.nextLine());
                        System.out.println("Do you want to:\n1: Add new member to team\n2: Edit whole team\n3: Cancel");
                        int editType = Integer.parseInt(input.nextLine());
                        if(editType==1){
                            viewMember(memberList, "Competitive");
                            System.out.println("Enter member index number to add: ");
                            int membSelect = Integer.parseInt(input.nextLine());
                            teamList.get(teamEditSelect).getTeam().add((CompetitiveSwimmer) memberList.get(membSelect));
                            ((CompetitiveSwimmer) memberList.get(membSelect)).setTeam(teamList.get(teamEditSelect).getTeamName());
                            ((CompetitiveSwimmer) memberList.get(membSelect)).setTrainer(teamList.get(teamEditSelect).getTrainer());
                            System.out.println("Added!");
                        }else if(editType==2){
                            teamList.set(teamEditSelect, createTeam(input, memberList));
                        }
                        updateMemberFile(memberList);
                    } else if (teamSelect == 3) { //VIEW TEAMS
                        viewTeams(teamList);
                    }
                    break;
                case 5:
                    //YEARLY INCOME
                    System.out.println("\nTOTAL EXPECTED YEARLY INCOME: " + yearlyIncome(memberList) + " KR.\n\n");
                    break;
                case 6:
                    System.out.println(viewCompetitionStats(memberList));
                    break;
                case 7:
                    //END PROGRAM
                    check = false;
                default:
                    if (check != false)
                        System.out.println("Invalid input. Try again with a valid number\n ");
            }
        }
    }

    public static String viewCompetitionStats(ArrayList<Member> memberList){
        String returnString = "*** MEMBER COMPETITION STATISTICS ***\n\n";
        for (int i = 0; i <= memberList.size() - 1; i++) {
            if (memberList.get(i).getType().equals("Competitive")) {
                CompetitiveSwimmer tempMemb = (CompetitiveSwimmer) memberList.get(i);
                    String competitionStatistic = "";
                    if(tempMemb.getTrainingResults()!=null) {
                        for (int j = 0; j <= tempMemb.getTrainingResults().size() - 1; j++) {
                            competitionStatistic += ("\nCompetition name: " + tempMemb.getTrainingResults().get(j).getCompetition() + "\nFinal place in competition: " + tempMemb.getTrainingResults().get(j).getPlace() + "\nTime: " + tempMemb.getTrainingResults().get(j).getTime());
                        }
                        returnString += (("INDEX " + i + "\nNAME: " + tempMemb.getName() + "\nADDRESS: " + tempMemb.getAddress() + "\nDATE OF BIRTH: " + tempMemb.getDateOfBirth() + "\nTEAM: " + tempMemb.getTeam() + "\nDISCIPLINE: " + tempMemb.getDiscipline() + "\nACTIVE: " + tempMemb.getStatus()) + "\nCOMPETITION SCORES: " + competitionStatistic + "\n\n");
                    }
            }
        }
        return "\n*******************************" + returnString;
    }

    public static double yearlyIncome(ArrayList<Member> memberList) {
        double sum = 0;
        for (int i = 0; i <= memberList.size() - 1; i++) {
            sum += memberList.get(i).getPrice();
        }
        return sum;
    }

    public static Member createMember(Scanner scan) throws IOException {
        System.out.println("Enter name of the member");
        String name = scan.nextLine();
        System.out.println("Enter date of birth (dd/mm/yyyy)");
        String date = scan.nextLine();
        System.out.println("Enter address");
        String address = scan.nextLine();
        System.out.println("Will the customer have an active membership? Y/n: ");
        boolean active = scan.nextLine().equalsIgnoreCase("y");
        System.out.println("Enter 1 to create a motionist\nEnter 2 to create a competitive member\nEnter 3 to go back");
        int answer = scan.nextInt();
        scan.nextLine();
        Member newMember = new Member();
        if (answer == 1) {
            System.out.println("Regular training? Y/n");
            boolean ans = scan.nextLine().equalsIgnoreCase("y");
            System.out.println("Class training?");
            boolean ans1 = scan.nextLine().equalsIgnoreCase("y");
            newMember = new Motionist(name, date, address, active, ans, ans1);
        } else if (answer == 2) {
            System.out.println("Enter discipline\n1: Butterfly\n2: Crawl\n3: Back crawl\n4: Breaststroke");
            int discinput = Integer.parseInt(scan.nextLine());
            String discipline;
            if (discinput == 1) {
                discipline = "Butterfly";
            } else if (discinput == 2) {
                discipline = "Crawl";
            } else if (discinput == 3) {
                discipline = "Back crawl";
            } else {
                discipline = "Breaststroke";
            }
            newMember = new CompetitiveSwimmer(name, date, address, active, discipline, "None", new ArrayList<TrainingResult>(), "None");
        }

        return newMember;

    }

    public static void addTrainingResultToCompSwimmer(ArrayList<Member> memberList, String competition, int place, double time, int indexMember) {
        CompetitiveSwimmer tempMemb = (CompetitiveSwimmer) memberList.get(indexMember);
        if (tempMemb.getTrainingResults()!=null) {
            tempMemb.getTrainingResults().add(new TrainingResult(competition, place, time));
        }else{
            ArrayList<TrainingResult> tempResult = new ArrayList<TrainingResult>();
            tempResult.add(new TrainingResult(competition,place,time));
            tempMemb.setTrainingResults(tempResult);
        }
        memberList.set(indexMember, tempMemb);
    }

    public static void editMember(ArrayList<Member> memberList, Member member, int index) throws IOException {
        if (member.getType().equals("Motionist")) {
            Motionist outputmember = (Motionist) member;
            memberList.set(index, outputmember);
        } else {
            CompetitiveSwimmer outputmember = (CompetitiveSwimmer) member;
            memberList.set(index, outputmember);
        }
        updateMemberFile(memberList);
    }

    public static void viewMember(ArrayList<Member> memberList, String type) throws IOException {
        for (int i = 0; i <= memberList.size() - 1; i++) {
            if (memberList.get(i).getType().equals("Motionist")) {
                Motionist tempMemb = (Motionist) memberList.get(i);
                if (type.contains("Motionist")) {
                    System.out.println(("INDEX " + i + "\nTYPE: " + tempMemb.getType() + "\nNAME: " + tempMemb.getName() + "\nADDRESS: " + tempMemb.getAddress() + "\nDATE OF BIRTH: " + tempMemb.getDateOfBirth() + "\nTRAINING: " + tempMemb.getTraining() + "\nCLASS TRAINING: " + tempMemb.getClassTraining() + "\nACTIVE: " + tempMemb.getStatus()) + "\nPRICE: " + tempMemb.getPrice() + "\n\n");
                }
            } else {
                CompetitiveSwimmer tempMemb = (CompetitiveSwimmer) memberList.get(i);
                if (type.contains("Competitive")) {
                    System.out.println(("INDEX " + i + "\nTYPE: " + tempMemb.getType() + "\nNAME: " + tempMemb.getName() + "\nADDRESS: " + tempMemb.getAddress() + "\nDATE OF BIRTH: " + tempMemb.getDateOfBirth() + "\nTEAM: " + tempMemb.getTeam() + "\nDISCIPLINE: " + tempMemb.getDiscipline() + "\nACTIVE: " + tempMemb.getStatus()) + "\nPRICE: " + tempMemb.getPrice() + "\n\n");
                }
            }
        }
    }

    public static void sortMember(ArrayList<Member> memberList) {
        //let user pick between which categories to sort from
        //if user choice is true, print out the corrosponding forloop
    }

    public static void updateMemberFile(ArrayList<Member> memberList) throws FileNotFoundException {
        //MOTIONIST: Part 0 = Type, Part 1 = Navn, Part 2 = Adresse, Part 3 = Fødselsdag, Part 4 = Træning, Part 5 = Class training, Part 6 = Status
        //COMPETITIVE: Part 0 = Type, Part 1 = Navn, Part 2 = Adresse, Part 3 = Fødselsdag, Part 4 = Team, Part 5 = Disciplin, Part 6 = Status, Part 7 = Træningsresultater, Part 8 = Træner
        String output = "";
        for (int i = 0; i <= memberList.size() - 1; i++) {
            if (memberList.get(i).getType().equals("Motionist")) {
                Motionist tempMemb = (Motionist) memberList.get(i);
                output += (tempMemb.getType() + " // " + tempMemb.getName() + " // " + tempMemb.getAddress() + " // " + tempMemb.getDateOfBirth() + " // " + tempMemb.getTraining() + " // " + tempMemb.getClassTraining() + " // " + tempMemb.getStatus()) + "\n";
            } else {
                CompetitiveSwimmer tempMemb = (CompetitiveSwimmer) memberList.get(i);
                output += (tempMemb.getType() + " // " + tempMemb.getName() + " // " + tempMemb.getAddress() + " // " + tempMemb.getDateOfBirth() + " // " + tempMemb.getTeam() + " // " + tempMemb.getDiscipline() + " // " + tempMemb.getStatus() + " // " );
                for (int j = 0; j <= tempMemb.getTrainingResults().size() - 1; j++) {
                    output += (tempMemb.getTrainingResults().get(j).getCompetition()) + " £ ";
                    output += (tempMemb.getTrainingResults().get(j).getPlace()) + " £ ";
                    output += (tempMemb.getTrainingResults().get(j).getTime());
                    //if there are more competitions to add, separate with &&
                    if (j != tempMemb.getTrainingResults().size() - 1) {
                        output += " && ";
                    }
                }
                output += " // " + tempMemb.getTrainer() + "\n";
            }
        }
        PrintStream fileWriter = new PrintStream("files/members.txt");
        fileWriter.print(output);
    }

    public static void populateMemberArrayList(ArrayList<Member> memberList, ArrayList<Team> teamList) throws IOException {
        memberList.clear();
        BufferedReader br = new BufferedReader(new FileReader("files/members.txt"));
        String s;
        int count = 0;
        while ((s = br.readLine()) != null) {
            String[] parts = s.split(" // ");
            if (parts[0].equals("Motionist")) {
                Motionist outputMember = new Motionist(parts[1], parts[3], parts[2], (parts[6].equals("true")), (parts[4]).equals("true"), parts[5].equals("true"));
                memberList.add(outputMember);
            } else {
                ArrayList<TrainingResult> trainingResultsOutput = new ArrayList<TrainingResult>();
                try {
                    String[] resultArrayParts = parts[7].split(" && ");
                    for (int i = 0; i <= resultArrayParts.length - 1; i++) {
                        String[] resultParameterParts = resultArrayParts[i].split(" £ ");
                        trainingResultsOutput.add(new TrainingResult(resultParameterParts[0], Integer.parseInt(resultParameterParts[1]), Double.parseDouble(resultParameterParts[2])));
                    }
                }
                catch(Exception e) {
                   trainingResultsOutput = null;
                }
                CompetitiveSwimmer outputMember = new CompetitiveSwimmer(parts[1], parts[3], parts[2], (parts[6].equals("true")), parts[5], parts[4], trainingResultsOutput, parts[8]);
                if (!parts[4].equals("None")) {
                    boolean teamExists = false;
                    for (int i = 0; i <= teamList.size() - 1; i++) {
                        if (teamList.get(i).getTeamName().equalsIgnoreCase(parts[4])) {
                            teamExists = true;
                            teamList.get(i).getTeam().add(outputMember);
                        }
                    }
                    if (!teamExists) {
                        ArrayList<CompetitiveSwimmer> tempList = new ArrayList<CompetitiveSwimmer>();
                        tempList.add(outputMember);
                        Team newTeam = new Team(tempList, outputMember.getTeam(), outputMember.getTrainer());
                        teamList.add(newTeam);
                    }
                }
                memberList.add(outputMember);
            }
            count++;
        }
    }

    public static Team createTeam(Scanner scan, ArrayList<Member> memberList) throws IOException {
        boolean addMoreMembers = true;
        ArrayList<CompetitiveSwimmer> swimmers = new ArrayList<>();
        System.out.println("What is the team name? ");
        String teamName = scan.nextLine();
        System.out.println("What is the trainer's name?");
        String trainerName = scan.nextLine();
        while (addMoreMembers) {
            // As long as you want to add new members
            viewMember(memberList, "Competitive");
            System.out.println("Which members should be a part of this team?");
            System.out.println("Type in a index to choose a member");
            int memberIndex = Integer.parseInt(scan.nextLine());
            ((CompetitiveSwimmer) memberList.get(memberIndex)).setTeam(teamName); //Changes team variable in specified member
            ((CompetitiveSwimmer) memberList.get(memberIndex)).setTrainer(trainerName); //Changes team variable in specified member
            swimmers.add((CompetitiveSwimmer) memberList.get(memberIndex)); //Adds to temporary arraylist
            System.out.println("Competitive swimmer is added to the team!");
            System.out.println("Do you want to add another member? y/n");
            if (scan.nextLine().equalsIgnoreCase("n")) {
                addMoreMembers = false;
            }
        }
        // Here we create the team
        Team team = new Team(swimmers, teamName, trainerName);
        return team;
    }

    public static void viewTeams(ArrayList<Team> teamList){
        for(int i=0;i<= teamList.size()-1;i++){
            System.out.println("\nTEAM: " + teamList.get(i).getTeamName() + "\nTRAINER: " + teamList.get(i).getTrainer() +"\nINDEX: " + i + "\n*** MEMBERS ***");
            for(int j=0;j<=teamList.get(i).getTeam().size()-1;j++){
                System.out.println("NAME: " + teamList.get(i).getTeam().get(j).getName() + " - "  + teamList.get(i).getTeam().get(j).getDiscipline());
            }
            System.out.println("***************\n");
        }
    }

    public static void viewTopFive(ArrayList<Member> memberList) {
        //most likely built the same as sort method
        //let user pick which discipline they want to see top 5 of
        //print out corrosponding forloop
    }

}
