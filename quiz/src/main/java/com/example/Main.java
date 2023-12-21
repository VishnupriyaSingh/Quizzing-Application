package com.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
            throws InstructorNotFoundException, 
            PlayerNotFoundException,
            QuizNotFoundException
            {
                Scanner sc = new Scanner(System.in);
                QuizConsole.startQuizConsole(sc);
            }
}