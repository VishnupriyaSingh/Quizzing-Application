package com.example;

import java.util.HashMap;
import java.util.Scanner;

public class QuizConsole {
    public static final String INSTRUCTOR_CSV_PATH = "C:\\Users\\KIIT\\Desktop\\Workshop-4\\Quizzing-Application\\quiz\\CSVFiles\\Instructors.csv";
    public static final String PLAYER_CSV_PATH = "C:\\Users\\KIIT\\Desktop\\Workshop-4\\Quizzing-Application\\quiz\\CSVFiles\\Players.csv";
    public static HashMap<Integer, Quiz> quizzes = new HashMap<>();
    public static HashMap<Integer, Player> players = new HashMap<>();
    public static HashMap<Integer, Instructor> instructors = new HashMap<>();

    public static void startQuizConsole(Scanner sc) {
        FileOperations.createFile(INSTRUCTOR_CSV_PATH);
        FileOperations.createFile(PLAYER_CSV_PATH);
        while (true) {
            System.out.println("Choose your option from below:");
            System.out.println("[1] Add new quiz.");
            System.out.println("[2] Play a quiz.");
            System.out.println("[3] Create new player.");
            System.out.println("[4] Create new instructor.");
            System.out.println("[5] View player score.");
            System.out.println("[6] Manage quiz.");
            System.out.print("Enter your choice (enter 0 to exit): ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 0:
                    sc.close();
                    return;

                case 1:
                    try {
                        addQuiz(sc);
                        break;
                    } catch (Exception exception) {
                        System.out.println("Error in adding quiz: " + exception.getMessage());
                    }
                    break;

                case 2:
                    try {
                        playQuiz(sc);
                        break;
                    } catch (Exception exception) {
                        System.out.println("Error in playing quiz: " + exception.getMessage());
                    }
                    break;

                case 3:
                    createPlayer(sc);
                    break;

                case 4:
                    createInstructor(sc);
                    break;

                case 5:
                    try {
                        viewPlayerScore(sc);
                        break;
                    } catch (Exception exception) {
                        System.out.println("Error in viewing score: " + exception.getMessage());
                    }
                    break;

                case 6:
                    try {
                        manageQuiz(sc);
                        break;
                    } catch (Exception exception) {
                        System.out.println("Error in managing quiz: " + exception.getMessage());
                    }
                    break;

                default:
                    break;
            }
        }
    }

    // add new quiz by instructor
    public static void addQuiz(Scanner sc) throws InstructorNotFoundException {
        System.out.print("Enter your instructor ID: ");
        int inst_id = sc.nextInt();
        sc.nextLine();
        if (instructors.containsKey(inst_id)) {
            System.out.print("Enter quiz ID: ");
            int quiz_id_new = sc.nextInt();
            if (!quizzes.containsKey(quiz_id_new)) {
                Quiz new_quiz = new Quiz(quiz_id_new, sc);
                quizzes.put(quiz_id_new, new_quiz);
                instructors.get(inst_id).addQuiz(quiz_id_new, new_quiz);
            } else {
                System.out.println("Quiz already exists!\n");
            }
        } else {
            throw new InstructorNotFoundException("Instructor not found!\n");
        }
    }

    // take quiz by player
    public static void playQuiz(Scanner sc) throws QuizNotFoundException, PlayerNotFoundException {
        System.out.print("Enter player ID: ");
        int player_id = sc.nextInt();
        sc.nextLine();
        if (players.containsKey(player_id)) {
            System.out.print("Enter quiz ID: ");
            int play_quiz_id = sc.nextInt();
            sc.nextLine();
            if (quizzes.containsKey(play_quiz_id)) {
                Quiz player_quiz = quizzes.get(play_quiz_id);
                double score = player_quiz.playQuiz(sc);
                players.get(player_id).updateScore(play_quiz_id, score);
                System.out.println("\nQuiz Completed successfully. Your score is " + score + "%\n");
            } else {
                throw new QuizNotFoundException("Quiz not found!\n");
            }
        } else {
            throw new PlayerNotFoundException("Player not found!\n");
        }
    }

    // create new player
    public static void createPlayer(Scanner sc) {
        System.out.print("Enter player ID: ");
        int new_player_id = sc.nextInt();
        sc.nextLine();
        if (!players.containsKey(new_player_id)) {
            System.out.print("Enter player name: ");
            String new_player_name = sc.nextLine();
            Player new_player = new Player(new_player_id, new_player_name);
            players.put(new_player_id, new_player);
            FileOperations.writePlayer(new_player, PLAYER_CSV_PATH);
            System.out.println("Player added successfully!\n");
        } else {
            System.out.println("Player already exists!\n");
        }
    }

    // create new instructor
    public static void createInstructor(Scanner sc) {
        System.out.print("Enter instructor ID: ");
        int new_inst_id = sc.nextInt();
        sc.nextLine();
        if (!instructors.containsKey(new_inst_id)) {
            System.out.print("Enter instructor name: ");
            String new_inst_name = sc.nextLine();
            Instructor new_instructor = new Instructor(new_inst_id, new_inst_name);
            instructors.put(new_inst_id, new_instructor);
            FileOperations.writeInstructor(new_instructor, INSTRUCTOR_CSV_PATH);
            System.out.println("Instructor added successfully.\n");
        } else {
            System.out.println("Instructor already exists!\n");
        }
    }

    // view player's score from past quizzes
    public static void viewPlayerScore(Scanner sc) throws QuizNotFoundException, PlayerNotFoundException {
        System.out.print("Enter player ID: ");
        int search_player_id = sc.nextInt();
        sc.nextLine();
        if (players.containsKey(search_player_id)) {
            System.out.print("Enter quiz ID: ");
            int search_quiz_id = sc.nextInt();
            sc.nextLine();
            if (quizzes.containsKey(search_quiz_id)) {
                double player_score = players.get(search_player_id).getScore(search_quiz_id);
                if (player_score != Double.MIN_VALUE) {
                    System.out.println("Player " + search_player_id + " scored " + player_score
                            + " in quiz " + search_quiz_id + ".\n");
                }
            } else {
                throw new QuizNotFoundException("Quiz not found!\n");
            }
        } else {
            throw new PlayerNotFoundException("Player not found!\n");
        }
    }

    // manage quiz to add, edit, delete questions by instructor
    public static void manageQuiz(Scanner sc) throws QuizNotFoundException, InstructorNotFoundException {
        System.out.print("Enter instructor ID: ");
        int inst_id_manage = sc.nextInt();
        sc.nextLine();
        if (instructors.containsKey(inst_id_manage)) {
            System.out.print("Enter quiz ID: ");
            int quiz_id_manage = sc.nextInt();
            sc.nextLine();
            if (quizzes.containsKey(quiz_id_manage)) {
                instructors.get(inst_id_manage).manageQuiz(quiz_id_manage, sc);
            } else {
                throw new QuizNotFoundException("Quiz not found!\n");
            }
        } else {
            throw new InstructorNotFoundException("Instructor not found!\n");
        }
    }
}
