package com.sortinghat.ashokkumarshrestha.sortinghat;

/**
 * Created by ashok on 12/12/17.
 */

public class Level7Block {
    private int ansId;
    private int blockId;
    private boolean flag;
    private String letter;

    public void Level10Block(){
        this.flag = false;
        this.blockId = 0;
    }

    public int getAnsId(){
        return this.ansId;
    }

    public void setAnsId(int ansId){
        this.ansId = ansId;
    }

    public String getLetter(){
        return this.letter;
    }

    public void setLetter(String letter){
        this.letter = letter;
    }

    public int getBlockId(){
        return this.blockId;
    }

    public void setBlockId(int blockId){
        this.blockId = blockId;
    }

    public boolean getFlag(){
        return this.flag;
    }

    public void setFlag(boolean flag){
        this.flag = flag;
    }
}
