package sample;

import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class TetrisModel {
    private ArrayList <ArrayList<Integer>> occupiedCordinates=new ArrayList<>(999);

    private int score;

    private static String difficultyString;

    private static String languageString = "english";

    private int gridWidth = 10;
    private int gridHeight = 20;

    public int getGridWidth(){
        return gridWidth;
    }
    public int getGridHeight(){
        return gridHeight;
    }



    public String getLanguageString(){
        return languageString;
    }
    public void setLanguageString(String languageString){
        this.languageString = languageString;
    }

    public TetrisModel(){
        for (int i = 0; i < 20 ; i++) {
            occupiedCordinates.add(new ArrayList<>());
        }


    }
    public String getDifficultyString(){
        return difficultyString;
    }

    public int getDifficultyInt(){

        if (difficultyString == "easy"){
            return 500;
        }
        if (difficultyString == "normal"){
            return 250;
        }
        if (difficultyString == "hard"){
            return 100;
        }

        return 400;
    }
    public void setDifficultyString(String difficultyString){
        this.difficultyString = difficultyString;
    }
    public void initiateOccupiedCordinates(){
        for (int i = 0; i < 20 ; i++) {
            occupiedCordinates.add(new ArrayList<>());
        }

    }



    /**
     *
     * @param cordinatesArrayList
     */
        public void addOccupiedCordinates(ArrayList<Integer> cordinatesArrayList){

            for (int i = 0; i < cordinatesArrayList.size() ; i++) {

                occupiedCordinates.get(cordinatesArrayList.get(i+1)).add(cordinatesArrayList.get(i));
                i++;
            }



    }
    public void addSingleCordinateOccupiedCordinates(int x, int y){
            occupiedCordinates.get(y).add(x);

    }

    public void removeOccupiedCordinatesRow(int row){

            occupiedCordinates.get(row).clear();
    }
    public void removeOccupiedCordinates(int x, int y){

            occupiedCordinates.get(y).remove(Integer.valueOf(x));

    }
    public void setOccupiedCordinates(ArrayList <ArrayList<Integer>> occupiedCordinates){
        this.occupiedCordinates = occupiedCordinates;

    }

    public ArrayList <ArrayList<Integer>> getOccupiedCordinates(){
            return occupiedCordinates;

    }
    public void setScore(int score){
        this.score=score;

    }
    public int getScore(){
        return score;
    }

    // kod för att skriva i text fil tagen från https://www.baeldung.com/java-write-to-file
    public void saveHighscore(String highScoreString)
            throws IOException {
        ArrayList<String> currentHighScoreList = getHighscore();

        BufferedWriter writer = new BufferedWriter(new FileWriter("highscore.txt"));



        Integer [] sortHighScore = {Integer.parseInt(currentHighScoreList.get(0)),Integer.parseInt(currentHighScoreList.get(1)),Integer.parseInt(currentHighScoreList.get(2)),Integer.parseInt(highScoreString)};
        for (int i = 0; i < sortHighScore.length; i++) {

        };
       Arrays.sort(sortHighScore, Collections.reverseOrder());

        for (int i = 0 ; i < sortHighScore.length-1;  i++) {

                writer.write(Integer.toString(sortHighScore[i]));


            writer.newLine();


        }
        writer.close();

    }

    public ArrayList<String> getHighscore(){

            ArrayList <String> highScoreArray = new ArrayList<>();

            // tagen från https://stackoverflow.com/questions/5868369/how-can-i-read-a-large-text-file-line-by-line-using-java

        try (BufferedReader br = new BufferedReader(new FileReader("highscore.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
                highScoreArray.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return highScoreArray;
    }
}

