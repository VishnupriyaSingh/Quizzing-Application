package com.example;

public class Timer {
    private long startTime;
    private final int timeLimitInSeconds;
    private boolean timeUp;

    public Timer(int timeLimitInSeconds) {
        this.timeLimitInSeconds = timeLimitInSeconds;
        this.timeUp = false;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
        new Thread(() -> {
            try {
                Thread.sleep(timeLimitInSeconds * 1000);
                timeUp = true;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public boolean isTimeUp() {
        return timeUp || (System.currentTimeMillis() - startTime) >= timeLimitInSeconds * 1000;
    }

    public void reset() {
        timeUp = false;
    }
}






// package com.example;

// public class Timer {
//     private long startTime;
//     private int durationInSeconds;

//     public Timer(int durationInSeconds) {
//         this.durationInSeconds = durationInSeconds;
//     }

//     public void start() {
//         this.startTime = System.currentTimeMillis();
//     }

//     public int getRemainingTime() {
//         long currentTime = System.currentTimeMillis();
//         long elapsedTime = (currentTime - this.startTime) / 1000;
//         System.out.println("Elapsed time: "+ elapsedTime);
//         return (int) (this.durationInSeconds - elapsedTime);
//     }

//     public boolean isTimeUp() {
//         return getRemainingTime() <= 0;
//     }
// }





// import java.util.Timer;
// import java.util.TimerTask;

// public class QuizTimer {
//     private static final int QUIZ_DURATION = 60; // Quiz duration in seconds
//     private static int timeLeft = QUIZ_DURATION;

//     public static void main(String[] args) {
//         startQuizTimer();

//         // Simulate a quiz with 3 questions
//         for (int i = 1; i <= 3; i++) {
//             System.out.println("Question " + i + ": ");
//             // Simulate a question here
//             waitForAnswer(); // Wait for the participant's answer
//         }

//         // End the quiz when all questions are answered
//         endQuiz();
//     }

//     private static void startQuizTimer() {
//         Timer timer = new Timer();
//         timer.scheduleAtFixedRate(new TimerTask() {
//             @Override
//             public void run() {
//                 timeLeft--;
//                 if (timeLeft <= 0) {
//                     endQuiz();
//                     timer.cancel(); // Stop the timer
//                 }
//                 System.out.print("\rTime Left: " + timeLeft + " seconds"); // Update time on the same line
//             }
//         }, 0, 1000); // Update every 1 second
//     }

//     private static void waitForAnswer() {
//         // Simulate waiting for the participant's answer
//         try {
//             Thread.sleep(5000); // Wait for 5 seconds (replace with actual question time)
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }
//     }

//     private static void endQuiz() {
//         System.out.println("\nQuiz ended. Submitting the quiz.");
//         // Logic to submit the quiz automatically
//     }
// }