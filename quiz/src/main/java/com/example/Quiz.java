package com.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Quiz {
    int quizID;
    ArrayList<Question> questions;

    public Quiz(int quizID, Scanner sc) {
        this.quizID = quizID;
        this.questions = new ArrayList<>();
        this.createQuiz(sc);  //creating quiz
    }

    private void createQuiz(Scanner sc) {
        System.out.print("Enter number of questions for quiz: ");
        int num_of_questions = sc.nextInt();
        sc.nextLine();

        for (int ques_num = 1; ques_num <= num_of_questions; ques_num++) {
            System.out.print("\nEnter question " + ques_num + ": ");
            String question = sc.nextLine();

            System.out.print("\tEnter option 1: ");
            String op_1 = sc.nextLine();

            System.out.print("\tEnter option 2: ");
            String op_2 = sc.nextLine();

            System.out.print("\tEnter option 3: ");
            String op_3 = sc.nextLine();

            System.out.print("Enter your answer (1/2/3): ");
            int correct_option = sc.nextInt();
            sc.nextLine();

            Question q = new Question(question, op_1, op_2, op_3, correct_option);
            this.questions.add(q);
            System.out.println("Question added successfully!\n");
        }
        System.out.println("Quiz created successfully!\n");
    }

    public double playQuiz(Scanner sc) {
        int[] choices = new int[this.questions.size()];
        for (int i = 0; i < this.questions.size(); i++) {
            System.out.println(questions.get(i));
            System.out.print("Enter your answer (1/2/3): ");
            choices[i] = sc.nextInt();
            sc.nextLine();
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
}
