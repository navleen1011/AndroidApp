package com.example.miwokapp;

public class word {
    private String defaultWord;
    private String miwokWord;

    private static final int NO_IMAGE = -1;
    private int image = NO_IMAGE;

    public word(String dw,String mw){
        defaultWord = dw;
        miwokWord = mw;

    }

    public word(String dw, String mw,int id){
        defaultWord = dw;
        miwokWord = mw;
        image = id;

    }
    public String getdefaultWord(){
        return defaultWord;
    }
    public String getMiwokWord(){
        return miwokWord;
    }
    public int getImage(){return image;}

    public boolean hasImage(){
       /* if(image != no_image)
            return true;
        else return false;
        */
       return image != NO_IMAGE;
    }
}
