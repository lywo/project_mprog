package com.example.lydia.savethenight;

/**
 * Created by Lydia on 3-6-2016.
 */
public class Question {
    private String question;
    private Integer id;
    private Boolean favourite;

    // constructor
    public Question (String question, Integer id, Boolean favourite){
        super();
        this.question = question;
        this.id = id;
        this.favourite = favourite;
    }

    // methods
    public String getQuestion(){return question;}
    public void setQuestion(String question){this.question = question;}

    public Integer getId(){return id;}
    public void setId(Integer id){this.id = id;}

    public Boolean getFavouriteStatus(){return favourite;}
    public void setFavouriteStatus(Boolean favouriteStatus){this.favourite = favouriteStatus;}
}
