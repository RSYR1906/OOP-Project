package org.sc2002.entity;

public class Suggestion implements Entity{

    private String suggestionID;
    private String campID;
    private String studentID;

    public Suggestion(String suggestionID, String campID, String studentID){
        this.suggestionID = suggestionID;
        this.campID = campID;
        this.studentID = studentID;
    }

    public void setSuggestionID(String suggestionID){
        this.suggestionID = suggestionID;
    }

    public void setStudentID(String studentID){
        this.studentID = studentID;
    }

    public void setCampID(String campID){
        this.campID = campID;
    }

    @Override
    public String getID(){
        return suggestionID;
    }

    public String getStudentID(){
        return studentID;
    }

    public String getCampID(){
        return campID;
    }
}
