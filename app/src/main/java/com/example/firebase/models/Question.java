package com.example.firebase.models;

/**
 * Created by Arvin on 2/21/2018.
 */

public class Question {

    public String questions[] = {
            "What was the first phone released that ran the Android OS?",
            "What is the name of the program that converts Java byte code into Dalvik byte code?",
            "Which company developed android?"
    };

    public String choices[][] = {
            {"Google gPhone", " T-Mobile G1", " Motorola Droid", " HTC Hero"},
            {"Android Interpretive Compiler (AIC)", "Dalvik Converter", "Dex compiler", "Mobile Interpretive Compiler (MIC)"},
            {"Apple ", "Google", "Android Inc", "Nokia"}
    };

    public String correctAnswer[] = {
        "Google gPhone",
        "Dalvik Converter",
        "Google"
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
