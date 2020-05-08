package sample;

import javafx.scene.shape.Rectangle;

import java.io.*;
import java.util.ArrayList;

public class TetrisModel {
    private String [] piecesAsStringArray = {"hej","ghfhgf"};
    private ArrayList <ArrayList<Integer>> occupiedCordinates=new ArrayList<>(999);

    private int score;

    public TetrisModel(){
        for (int i = 0; i < 20 ; i++) {
            occupiedCordinates.add(new ArrayList<>());
        }

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
    public void removeOccupiedCordinatesRow(int row){

            occupiedCordinates.get(row).clear();
    }
    public void removeOccupiedCordinates(){

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

    // kod tagen från https://www.baeldung.com/java-write-to-file
    public void saveHighscore()
            throws IOException {
        String str = "1337";
        BufferedWriter writer = new BufferedWriter(new FileWriter("highscore.txt"));
        writer.write(str);
        writer.newLine();
        writer.write(str);
        writer.close();
    }
    public String getHighscore(){



            return null;
    }
}

