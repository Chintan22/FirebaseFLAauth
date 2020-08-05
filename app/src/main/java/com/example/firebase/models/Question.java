package com.example.firebase.models;

/**
 * Created by Arvin on 2/21/2018.
 */

public class Question {

    public String questions[] = {
            "What was the first phone released that ran the Android OS?",
            "What is the name of the program that converts Java byte code into Dalvik byte code?",
            "Which company developed android?",
            "Which Media Format Is Not Supported By Android",
            "In Which Directory XML Layout Files Are Stored",
            "How Many Levels Of Securities Are In Android?",
            "What Are The Functionalities In AsyncTask In Android?"
    };

    public String choices[][] = {
            {"Google gPhone", " T-Mobile G1", " Motorola Droid", " HTC Hero"},
            {"Android Interpretive Compiler (AIC)", "Dalvik Converter", "Dex compiler", "Mobile Interpretive Compiler (MIC)"},
            {"Apple ", "Google", "Android Inc", "Nokia"},
            {"MP4","AVI","MIDI","MPEG"},
            {"/assets","/src","/res/values","/res/layout"},
            {"Android Level Security","App And Kernel Level Security","Java Level Security","None Of The Above"},
            {"OnPreExecution()","OnPostExecution()","DoInBackground()","OnProgressUpdate()"},
    };

    public String correctAnswer[] = {
        "Google gPhone",
        "Dalvik Converter",
        "Google",
            "AVI",
            "/res/layout",
            "App And Kernel Level Security",
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
