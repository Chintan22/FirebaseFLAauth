package com.example.firebase.models;

/**
 * Created by Arvin on 2/21/2018.
 */

public class Question {

    public String questions[] = {
            "Where are you from?",
            "Where are you going?",
            "What are you studying?",
            "What does your father do?",
            "Which school are you?",

    };

    public String choices[][] = {
            {"D'où êtes-vous", "quel âge avez-vous", "ton âge", "que fais-tu"},
            {"où allez-vous", "quel endroit", "je t'aime", "J'aime la nourriture"},
            {"quel âge avez-vous", "où allez-vous", "que fais-tu", "je t'aime"},
            {"quel endroit","que fais-tu","je t'aime","ton père fait"},
            {"je t'aime","quel endroit","quelle école es-tu","ton âge"},

    };

    public String correctAnswer[] = {
            "D'où êtes-vous",
            "où allez-vous",
            "que fais-tu",
            "ton père fait",
            "quelle école es-tu",
            "OnPostExecution()"
    };

    public String getQuestion(int a){
        String question = questions[a];
        return question;
    }

    public String getchoice1(int a){
        String choice = choices[a][0];
        return choice;
    }

    public String getchoice2(int a){
        String choice = choices[a][1];
        return choice;
    }

    public String getchoice3(int a){
        String choice = choices[a][2];
        return choice;
    }

    public String getchoice4(int a){
        String choice = choices[a][3];
        return choice;
    }

    public String getCorrectAnswer(int a){
        String answer = correctAnswer[a];
        return answer;
    }
}
