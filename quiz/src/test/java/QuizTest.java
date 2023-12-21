import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import com.example.FileOperations;
import com.example.InstructorNotFoundException;
import com.example.PlayerNotFoundException;
import com.example.Quiz;
import com.example.QuizConsole;
import com.example.QuizNotFoundException;

public class QuizTest {

    // method to verify quiz creation
    @Test
    public void testQuizAdd() {
        // creating new instructor with quiz
        String input = "4\n100\nTestInstructor1\n1\n100\n1\n1\nDemo Question\nA\nB\nC\n1\n0\n";
        ByteArrayInputStream testInputs = new ByteArrayInputStream(input.getBytes());
        Scanner sc = new Scanner(testInputs);
        int quizzes_before = QuizConsole.quizzes.size();
        try {
            QuizConsole.startQuizConsole(sc);
        } catch (Exception exception) {
            fail("Unexpected exception: " + exception.getMessage());
        }
        int quizzes_after = QuizConsole.quizzes.size();
        assertEquals(quizzes_before + 1, quizzes_after);
    }

    // method to verify player creation
    @Test
    public void testPlayerAdd() {
        String input = "3\n200\nTestPlayer1\n0\n"; 
        ByteArrayInputStream testInputs = new ByteArrayInputStream(input.getBytes());
        Scanner sc = new Scanner(testInputs);
        int players_before = QuizConsole.players.size();
        int players_before_in_csv = FileOperations.countEntries(QuizConsole.PLAYER_CSV_PATH);
        try {
            QuizConsole.startQuizConsole(sc);
        } catch (Exception exception) {
            fail("Unexpected Exception: " + exception.getMessage());
        }
        int players_after = QuizConsole.players.size();
        int players_after_in_csv = FileOperations.countEntries(QuizConsole.INSTRUCTOR_CSV_PATH);
        assertEquals(players_before + 1, players_after);
        if (!FileOperations.getAllUniqueIDs(QuizConsole.PLAYER_CSV_PATH).contains(200)) {
            assertEquals(players_before_in_csv + 1, players_after_in_csv);
        }
    }

    // method to verify instructor creation
    @Test
    public void testInstructorAdd() {
        String input = "4\n103\nTestInstructor4\n0\n"; // creating instructor with id = 103
        ByteArrayInputStream testInputs = new ByteArrayInputStream(input.getBytes());
        Scanner sc = new Scanner(testInputs);
        int instructors_before = QuizConsole.instructors.size();
        int instructors_before_in_csv = FileOperations.countEntries(QuizConsole.INSTRUCTOR_CSV_PATH);
        try {
            QuizConsole.startQuizConsole(sc);
        } catch (Exception exception) {
            fail("Unexpected exception: " + exception.getMessage());
        }
        int instructors_after = QuizConsole.instructors.size();
        int instructors_after_in_csv = FileOperations.countEntries(QuizConsole.INSTRUCTOR_CSV_PATH);
        assertEquals(instructors_before + 1, instructors_after);
        if (!FileOperations.getAllUniqueIDs(QuizConsole.INSTRUCTOR_CSV_PATH).contains(103)) {
            assertEquals(instructors_before_in_csv + 1, instructors_after_in_csv);
        }
    }

    // method to test if player can take quiz and check score
    @Test
    public void testQuizPlay() {
        // creating new instructor then quiz then adding 2 demo questions (ans 1 and 2)
        String createQuiz = "4\n101\nTestInstructor2\n1\n101\n1\n2\nDemo Question 1\nA\nB\nC\n1\nDemo Question 2\nA\nB\nC\n2\n0\n";
        String playQuiz = "1\n1\n"; // playing quiz with giving answers 1 and 1

        // creating a quiz
        ByteArrayInputStream testCreateQuiz = new ByteArrayInputStream(createQuiz.getBytes());
        Scanner sc_createQuiz = new Scanner(testCreateQuiz);
        try {
            QuizConsole.startQuizConsole(sc_createQuiz);
        } catch (Exception exception) {
            fail("Unexpected exception: " + exception.getMessage());
        }

        // playing the quiz
        ByteArrayInputStream testPlayQuiz = new ByteArrayInputStream(playQuiz.getBytes());
        Scanner sc_playQuiz = new Scanner(testPlayQuiz);
        Quiz test_quiz = QuizConsole.quizzes.get(1);
        double score = test_quiz.playQuiz(sc_playQuiz);
        assertEquals(50.0, score);
    }

    // method to test multiple quiz addition to console
    @Test
    public void testMultipleQuizAdd() {
        // creating three quizzes with ids 1, 2 and 3 after creating instructor
        String[] createQuizzes = {
                "4\n102\nTestInstructor3\n1\n102\n1\n2\nDemo Question 1\nA\nB\nC\n1\nDemo Question 2\nA\nB\nC\n2\n0\n",
                "1\n102\n2\n2\nDemo Question 1\nA\nB\nC\n1\nDemo Question 2\nA\nB\nC\n2\n0\n",
                "1\n102\n3\n2\nDemo Question 1\nA\nB\nC\n1\nDemo Question 2\nA\nB\nC\n2\n0\n" };

        int quizzes_before = QuizConsole.quizzes.size();
        for (String createQuiz : createQuizzes) {
            ByteArrayInputStream testCreateQuiz = new ByteArrayInputStream(createQuiz.getBytes());
            Scanner sc = new Scanner(testCreateQuiz);
            try {
                QuizConsole.startQuizConsole(sc);
            } catch (Exception exception) {
                fail("Unexpected exception: " + exception.getMessage());
            }
            assertEquals(quizzes_before + 1, QuizConsole.quizzes.size());
            quizzes_before++;
        }
    }

    // method for sad test case for incorrect instructor
    @Test
    public void testIncorrectInstructor() {
        String input = "1\n0\n"; // trying to add new quiz without creating instructor
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Scanner sc = new Scanner(testInput);
        try {
            QuizConsole.addQuiz(sc);
            fail("InstructorNotFoundException expected");
        } catch (Exception exception) {
            assertEquals(InstructorNotFoundException.class, exception.getClass());
        }
    }

    // method for sad test case for incorrect player
    @Test
    public void testIncorrectPlayer() {
        String input = "1\n0\n"; // trying to play quiz without creating player
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Scanner sc = new Scanner(testInput);
        try {
            QuizConsole.playQuiz(sc);
            fail("PlayerNotFoundException expected");
        } catch (Exception exception) {
            assertEquals(PlayerNotFoundException.class, exception.getClass());
        }
    }

    // method for sad test case for incorrect quiz
    @Test
    public void testIncorrectQuiz() {
        String addPlayer = "3\n201\nTestPlayer2\n0\n"; // add new player
        String playQuiz = "201\n1\n0\n";
        ByteArrayInputStream testAddPlayer = new ByteArrayInputStream(addPlayer.getBytes());
        Scanner sc_addPlayer = new Scanner(testAddPlayer);
        ByteArrayInputStream testPlayQuiz = new ByteArrayInputStream(playQuiz.getBytes());
        Scanner sc_playQuiz = new Scanner(testPlayQuiz);
        try {
            QuizConsole.startQuizConsole(sc_addPlayer);
            QuizConsole.playQuiz(sc_playQuiz);
            fail("QuizNotFoundException expected");
        } catch (Exception exception) {
            assertEquals(QuizNotFoundException.class, exception.getClass());
        }
    }
}
