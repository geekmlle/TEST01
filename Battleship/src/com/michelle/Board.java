package com.michelle;

/**
 * Created by blondieymollo on 2/24/16.
 */

import java.util.ArrayList;

public class Board {

    private int boardSize = 0;
    private String[][] board;
    private String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "Q" };
    private String[] ships = {"B", "C", "C", "F","F","F","F", "M","M","M","M"};
    private int[] shipSizes = { 5,4,4,3,3,3,3,2,2,2,2 };
    private ArrayList<String[]> shipCoordinates = null;


    public boolean Null(int i, int j){
        return (board[i][j]==null);
    }

    public boolean coordinatesOutOfBounds(int i,  int j){
        return (i >= boardSize ||  j >= boardSize || i == 0 || j == 0);
    }

    public boolean shipAlreadyExistsThere(int i, int j, int kind, String orientation){
        try{
            for (int k =0; k<shipSizes[kind]; k++){
                if (orientation.equals("h")){
                    if (board[i][j] != null){
                        return true;
                    }
                    j++;
                } else {
                    if (board[i][j] != null){
                        return true;
                    }
                    i++;
                }
            }
        } catch (ArrayIndexOutOfBoundsException a) {
            return false;
        }
        return false;
    }

    public boolean shipOutOfBounds(int i, int j, int kind, String orientation){

        if (orientation.equalsIgnoreCase("h")){
            if (j+shipSizes[kind]>boardSize){
                return true;
            }
        } else{
            if(i+shipSizes[kind]>boardSize){
                return true;
            }
        }
        return false;
    }

    public int getBoardSize(){
        return boardSize;
    }

    public Board(int boardSize){
        this.boardSize = boardSize+1;
        board = new String[this.boardSize][this.boardSize];
        shipCoordinates = new ArrayList<>();
        prepBoard();
    }

    public void prepBoard () {
        for (int i = 0; i<boardSize; i++){
            for (int j = 0; j<boardSize; j++){
                //When J = 0, it fills the first column full of numbers
                if (j == 0){
                    if(i<10){
                        board[i][j]=Integer.toString(j)+Integer.toString(i);
                    }else {
                        board[i][j]=Integer.toString(i);
                    }
                }
                //When I = 0, fill the first row full of letters
                if (i == 0){
                    if (j != 0){
                        board[i][j] = letters[j-1];
                    }
                }

            }
        }
    }

    public void printBoard(){
        for (int i = 0; i<boardSize; i++){
            for (int j = 0; j<boardSize; j++){
                    if (board[i][j]==null){
                        System.out.print("- ");
                    }
                    else {
                        System.out.print(board[i][j] + " ");
                    }
            }
            System.out.print("\n");
        }
    }

    public boolean addShipToBoard (int kind, int i /*row*/, int j /*column*/, String orientation ){

        int shipSize = shipSizes[kind];
        String[] coordinates = new String[shipSize];

        for (int k =0; k<shipSize; k++){
            if (orientation.equals("h")){
                //If it's horizontal then i will be the same
                board[i][j] = ships[kind];
                coordinates[k] = i+"/"+j;
                j++;
            } else {
                //if it's vertical then j will be the same
                board[i][j] = ships[kind];
                coordinates[k] = i+"/"+j;
                i++;
            }
        }
        shipCoordinates.add(coordinates);

        return true;
    }

    public String[] returnCoordinatesOfShip(int i, int j){

        for (String [] ship: shipCoordinates){
            for (String coord: ship){
                if (coord.equals(i+"/"+j)){
                    shipCoordinates.remove(ship);
                    return ship;
                }
            }
        }

        return null;
    }

    public boolean shipsLeft(){
        return ( shipCoordinates.size()>0 );
    }

    public void insertSunkenShip( String[] ship){

        for(String coord: ship){
            String[] temp = coord.split("/");
            board[Integer.parseInt(temp[0])][Integer.parseInt(temp[1])]="*";
        }

    }

    public void insertTurn(int i, int j, String kind){
        board[i][j]=kind;
    }

    public int attack(int i, int j){
        if (board[i][j]!=null){
            return 1;
        } else {
            return -1;
        }
    }


    public int getIndex(String letter){
        for (int i = 0; i < letters.length; i++) {
            if(letters[i].equalsIgnoreCase(letter)){
                return i+1;
            }
        }
        return -1;//not found
    }

}
