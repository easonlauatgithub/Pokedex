package edu.harvard.cs50.pokedex;

public class Pokemon {
    private String name;
    private String url;
    Pokemon(String name, String url){
        this.name = name;
        this.url = url;
    }
    public String getName(){
        return name;
    }
    public String getUrl(){
        return url;
    }

    private String[] types;
    public void setTypes(String[] types){
        this.types = types;
    }
    public String[] getTypes(){
        return types;
    }
    //for static data
    /*
    private int number;
    Pokemon(String name, int number){
        this.name = name;
        this.number = number;
    }
    public String getName(){
        return name;
    }
    public int getNumber(){
        return number;
    }
    */
}
