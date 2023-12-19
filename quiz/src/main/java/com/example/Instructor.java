package com.example;
import java.util.HashMap;
import java.util.Scanner;

public class Instructor {
    int instructorID;
    String name;
    HashMap<Integer, Quiz> quizzes;

    public Instructor(int instructorID, String name) {
        this.instructorID = instructorID;
        this.name = name;
        quizzes = new HashMap<>();
    }

    public void addQuiz(int quizID, Quiz quiz) {
        this.quizzes.put(quizID, quiz);
    }

    public void manageQuiz(int quizID, Scanner sc) {
        if (this.quizzes.containsKey(quizID)) 
        {
            Quiz quiz = quizzes.get(quizID);
            System.out.println("\t*Welcome to QUIZ PORTAL for Instructor*");
            System.out.println("\t[1] Add new question");
            System.out.println("\t[2] Edit Question.");
            System.out.println("\t[3] Delete Question.");
            System.out.print("\tEnter your choice (enter 0 to exit): ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 0:
                    return;

                case 1:
                    quiz.addNewQuestion(sc);
                    break;

                case 2:
                    System.out.println("Current Quiz:");
                    System.out.println(quiz);
                    System.out.print("\nQuestion to be edited: ");
                    int edit_ques = sc.nextInt();
                    sc.nextLine();
                    quiz.editQuestion(edit_ques, sc);
                    break;

                case 3:
                    System.out.println("\nCurrent Quiz:");
                    System.out.println(quiz);
                    System.out.print("\nQuestion to be deleted: ");
                    int del_ques = sc.nextInt();
                    sc.nextLine();
                    quiz.deleteQuestion(del_ques);
                    System.out.println("Question deleted successfully!\n");
                    break;

                default:
                    break;
            }
        } 
        else {
            System.out.println("Error! Quiz does not exist!");
        }
    }

    @Override
    public String toString() {
        return this.instructorID + "," + this.name;
    }
}
