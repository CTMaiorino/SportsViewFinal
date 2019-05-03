package edu.quinnipiac.ser210.sportsview;


import java.util.HashMap;

public class BackEndData {




    //User Input
    private static String UserInput;

    // HashMaps to store both teamData (team_id, name) & link to a png of their flag
    private  static HashMap<String, String> teamHashMap = new HashMap<String, String>();
    private static HashMap<String, String> teamLogo = new HashMap<String, String>();


    // String Arrays to store specific stats (Home stats array[0], away stat array[1])
    private static String[] goalsFor = new String[2];
    private static String[] goalsAgainst = new String[2];
    private  static String[] teamLineUp = new String[15];

    private static String textZero,textOne, textTwo, textThree, textFour, textFive, textSix, textSeven, totalWins, totalLoss, totalDraw;

    // One Constructor used on SplashActivity to initialize HashMaps
    public BackEndData(HashMap<String, String> teamData,HashMap<String, String> teamFlag){
        teamHashMap = teamData;
        teamLogo = teamFlag;


    }

    // One Constructor used on MainActivity to initialize Arrays
    public BackEndData(String[] goalsUp, String[] goalsDown){
        goalsFor = goalsUp;
        goalsAgainst = goalsDown;
    }


    // Setters
    static void setTeamHashMap( HashMap<String, String> HashMap){
        teamHashMap = HashMap;

    }

    static void setTeamLogo( HashMap<String, String> HashMap){
        teamLogo = HashMap;
    }

    static void setUserInput(String input){  UserInput = input; }

    static void setTeamLinUp(String[] array){ teamLineUp = array; }

    static void setTotalWins(String string){totalWins = string;}

    static void setTotalLoss(String string){totalLoss = string;}

    static void setTotalDraw(String string){totalDraw = string;}

    static void setText(String string, int num){

        switch (num){
            case 0:
                textZero = string;
                break;
            case 1:
                textOne = string;
                break;
            case 2:
                textTwo = string;
                break;
            case 3:
                textThree = string;
                break;
            case 4:
                textFour = string;
                break;
            case 5:
                textFive = string;
                break;
            case 6:
                textSix = string;
                break;
            case 7:
                textSeven = string;
                break;
        }
    }

    static void setTeamStats(String[] goalsUp, String[] goalsDown){
        goalsFor = goalsUp;
        goalsAgainst = goalsDown;
    }



    // Getters
    static HashMap<String, String> getTeamHashMap(){
        return teamHashMap;
    }

    static HashMap<String, String> getTeamLogo(){
        return teamLogo;
    }

    static String[] getGoalsFor(){
        return goalsFor;
    }

    static String[] getGoalsAgainst(){
        return goalsAgainst;
    }

    static String getUserInput(){  return UserInput;}

    static String[] getTeamLineUp(){ return teamLineUp;}

    static String getTotalWins(){return totalWins;}

    static String getTotalLoss(){return totalLoss;}

    static String getTotalDraw(){return totalDraw;}

    static String getText(int num){

        String text = null;
        switch (num){
            case 0:
                text = textZero;
                break;
            case 1:
                text = textOne;
                break;
            case 2:
                text = textTwo;
                break;
            case 3:
                text = textThree;
                break;
            case 4:
                text = textFour;
                break;
            case 5:
                text = textFive;
                break;
            case 6:
                text = textSix;
                break;
            case 7:
                text = textSeven;
                break;
        }
        return text;
    }





}
