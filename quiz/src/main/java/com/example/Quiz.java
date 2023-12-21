package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Quiz {
    int quizID;
    ArrayList<Question> questions;
    private boolean randomized;

    public Quiz(int quizID, Scanner sc) {
        this.quizID = quizID;
        this.questions = new ArrayList<>();
        this.createQuiz(sc);  //creating quiz
        this.randomized = false;
    }

    private void createQuiz(Scanner sc) {
        System.out.print("Enter total no. of questions for quiz: ");
        int num_of_questions = sc.nextInt();
        sc.nextLine();
        System.out.print("Rank the question difficulty (1:Easy/ 2:Medium/ 3:Hard) ");
        int difficulty = sc.nextInt();
        sc.nextLine();

        for (int ques_num = 1; ques_num <= num_of_questions; ques_num++) {
            System.out.print("\nEnter question " + ques_num + ": ");
            String question = sc.nextLine();

            System.out.print("*Option 1: ");
            String op_1 = sc.nextLine();

            System.out.print("*Option 2: ");
            String op_2 = sc.nextLine();

            System.out.print("*Option 3: ");
            String op_3 = sc.nextLine();

            System.out.print("Enter expected correct answer option: ");
            int correct_option = sc.nextInt();
            sc.nextLine();

            Question q = new Question(question, op_1, op_2, op_3, correct_option);
            this.questions.add(q);
            System.out.println("Question added successfully!\n");
        }
        System.out.println("Quiz created successfully!\n");
    }
    //randomize the questions sequence by Instructor
    public void randomizedQues() {
        Collections.shuffle(this.questions);
        randomized = true;
    }

    // public void startQuiz() {
    //     Scanner scanner = new Scanner(System.in); // Assuming Scanner is used for input
    //     for (Question question : questions) {
    //         this.timer.start();
    //         // Display the question
    //         System.out.println(question); // Or however you display the question
    
    //         while (!timer.isTimeUp()) {
    //             if (scanner.hasNextLine()) { // Check if there is an input from the user
    //                 String userInput = scanner.nextLine();
    //                 // Process the user input here
    //                 break; // Break the loop if the user has answered
    //             }
    //         }
    
    //         if (timer.isTimeUp()) {
    //             System.out.println("Time's up for this question!");
    //             // Handle the time-up scenario
    //         }
    //     }
    // }
    


    public double playQuiz(Scanner sc) {
        int[] choices = new int[this.questions.size()];
        
        for (int i = 0; i < this.questions.size(); i++) {
            Question question=questions.get(i);
            question.getTimer().start();
            //displayQuestion(question);
            System.out.println(question);
            System.out.print("Enter your answer (1/2/3): ");

            while (!question.getTimer().isTimeUp()) {
                // Check for user input or timer expiration
                if(sc.hasNext()){
                if (sc.hasNextInt()) {
                    choices[i] = sc.nextInt();
                    sc.nextLine();
                    break; // Break the loop once an input is received
                } else{
                    sc.next();
                }
                }

                try {
                    Thread.sleep(200); // Short delay to avoid excessive CPU usage
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            //     if (question.getTimer().isTimeUp()) {
            //         System.out.println("Time's up!");
            //         break;
            //         // Move to the next question or end the quiz
            //     }
            // }
        if (question.getTimer().isTimeUp() && choices[i] == 0) {
            System.out.println("Time's up!");
        }

        // Reset the timer for the next question
        question.getTimer().reset();
        System.out.println();
        }
        return scoreQuiz(choices);
    }

    //scoring the participant's quiz
    public double scoreQuiz(int[] choices) {
        double correct = 0;
        double questions = (double) choices.length;
        for (int i = 0; i < choices.length; i++) {
            if (choices[i] != 0 && this.questions.get(i).checkAnswer(choices[i])) {
                correct += 1.0;
            }
        }
        if((correct / questions) * 100 == 0){
            System.out.println("Poor Performance");
        }
        else if((correct / questions) * 100 < 50){
            System.out.println("Need improvement");
        }
        else if((correct / questions) * 100 == 0){
            System.out.println("Good performnce!");
        }
        return (correct / questions) * 100;
    }

    public void addNewQuestion(Scanner sc) {
        System.out.print("\n\tEnter question: ");
        String question = sc.nextLine();

        System.out.print("\t\tEnter option 1: ");
        String op_1 = sc.nextLine();

        System.out.print("\t\tEnter option 2: ");
        String op_2 = sc.nextLine();

        System.out.print("\t\tEnter option 3: ");
        String op_3 = sc.nextLine();

        System.out.print("Enter correct answer (enter 1/2/3): ");
        int correct_option = sc.nextInt();
        sc.nextLine();

        Question q = new Question(question, op_1, op_2, op_3, correct_option);
        this.questions.add(q);
        System.out.println("\tQuestion added successfully!\n");
    }

    public void editQuestion(int ques_num, Scanner sc) {
        deleteQuestion(ques_num);
        addNewQuestionAtIndex(ques_num - 1, sc);
    }

    public void addNewQuestionAtIndex(int index, Scanner sc) {
        System.out.print("\n\tEnter question: ");
        String question = sc.nextLine();

        System.out.print("\t\tEnter option 1: ");
        String op_1 = sc.nextLine();

        System.out.print("\t\tEnter option 2: ");
        String op_2 = sc.nextLine();

        System.out.print("\t\tEnter option 3: ");
        String op_3 = sc.nextLine();

        System.out.print("Enter your answer (1/2/3): ");
        int correct_option = sc.nextInt();
        sc.nextLine();

        Question q = new Question(question, op_1, op_2, op_3, correct_option);
        this.questions.add(index, q);
        System.out.println("\tQuestion added successfully!\n");
    }

    public void deleteQuestion(int ques_num) {
        this.questions.remove(ques_num - 1);
    }

    @Override
    public String toString() {
        if (this.questions.isEmpty()) {
            return "Quiz is empty.";
        }
        String quiz = "";
        int ques_num = 1;
        for (Question question : this.questions) {
            quiz += "\n" + ques_num + ")\n" + question.toString();
            ques_num++;
        }
        return quiz;
    }

    public Question[] getQuestions() {
        return null;
    }
}
