package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Leaderboard {

    private List<Player> players;

    public Leaderboard() {
        this.players = new ArrayList<>();
    }

    public void updateLeaderboard(Map<Integer, Player> players, int quizID) {
        // Extract scores for the given quiz
        List<QuizScore> quizScores = new ArrayList<>();
        for (Map.Entry<Integer, Player> entry : players.entrySet()) {
            int playerID = entry.getKey();
            Player player = entry.getValue();
            Integer score = (int) player.getScore(quizID);

            if (score != Integer.MIN_VALUE) {
                quizScores.add(new QuizScore(playerID, player.getName(), score));
            }
        }

        // sort the scores in descending order
        Collections.sort(quizScores, Collections.reverseOrder());

        players.clear();
        for (int i = 0; i < Math.min(quizScores.size(), 10); i++) {
            QuizScore quizScore = quizScores.get(i);
            players.put(quizScore.getPlayerID(), new Player(quizScore.getPlayerID(), quizScore.getPlayerName()));
        }
    }

    public void displayLeaderboard() {
        System.out.println("\n--- Leaderboard ---");
        for (Player player : players) {
            System.out.println(player.toString());
        }
        System.out.println();
    }

    private static class QuizScore implements Comparable<QuizScore> {
        private int playerID;
        private String playerName;
        private int score;

        public QuizScore(int playerID, String playerName, int score) {
            this.playerID = playerID;
            this.playerName = playerName;
            this.score = score;
        }

        public int getPlayerID() {
            return playerID;
        }

        public String getPlayerName() {
            return playerName;
        }

        @Override
        public int compareTo(QuizScore other) {
            return Integer.compare(this.score, other.score);
        }
    }
}
