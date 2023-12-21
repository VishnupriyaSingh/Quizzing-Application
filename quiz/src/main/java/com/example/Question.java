package com.example;
import com.example.Timer;

public class Question {
    String question;
    String[] options;
    private int correct_option;
    private Timer timer;

    public Question(String question, String option1, String option2, String option3, int correct_option) {
        this.question = question;
        this.options = new String[3];
        options[0] = option1;
        options[1] = option2;
        options[2] = option3;
        this.correct_option = correct_option;
        this.timer = new Timer(10); // Timer initialized with 10 seconds per question
        
    }

    
    // Getter for Timer
    public Timer getTimer() {
        return this.timer;
    }

    public boolean checkAnswer(int choice) {
        return choice == this.correct_option;
    }

    @Override
    public String toString() {
        return this.question
                + "\n\t1) " + this.options[0]
                + "\n\t2) " + this.options[1]
                + "\n\t3) " + this.options[2];
    }
}