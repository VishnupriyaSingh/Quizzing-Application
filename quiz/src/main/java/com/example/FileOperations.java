package com.example;
import java.nio.file.Path;
import java.util.HashSet;
import java.io.IOException;
import java.nio.file.Files;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;

public class FileOperations {
    // method to create file
    public static void createFile(String filepath) {
        if (!exists(filepath)) {
            Path path = Path.of(filepath);
            try {
                Files.createFile(path);
                System.out.println("Created file: " + filepath);
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        } else {
            System.out.println("File already exists!");
            System.out.println("\n");
        }
    }

    // method to write instructor data
    public static void writeInstructor(Instructor instructor, String filepath) {
        if (exists(filepath)) {
            HashSet<Integer> uniqueIds = getAllUniqueIDs(filepath);
            if (uniqueIds != null && !uniqueIds.contains(instructor.instructorID)) {
                try (PrintWriter writer = new PrintWriter(new FileWriter(filepath, true))) {
                    if (countEntries(filepath) == 0) {
                        writer.println("Instructor ID,Instructor Name");
                    }
                    writer.println(instructor.toString());
                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        }
    }

    // method to write player data
    public static void writePlayer(Player player, String filepath) {
        if (exists(filepath)) {
            HashSet<Integer> uniqueIDs = getAllUniqueIDs(filepath);
            if (uniqueIDs != null && !uniqueIDs.contains(player.playerID)) {
                try (PrintWriter writer = new PrintWriter(new FileWriter(filepath, true))) {
                    if (countEntries(filepath) == 0) {
                        writer.println("Player ID,Player Name");
                    }
                    writer.println(player.toString());
                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        }
    }

    // method to get all unique IDs from the file
    public static HashSet<Integer> getAllUniqueIDs(String filepath) {
        if (exists(filepath)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
                String header = reader.readLine();
                HashSet<Integer> uniqueIDs = new HashSet<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] lineData = line.split(",");
                    int id = Integer.parseInt(lineData[0]);
                    uniqueIDs.add(id);
                }
                return uniqueIDs;
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
                return null;
            }
        }
        return null;
    }

    // method to count entries in file
    public static int countEntries(String filepath) {
        if (exists(filepath)) {
            int lines = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
                while (reader.readLine() != null) {
                    lines++;
                }
                return lines;
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
                return -1;
            }
        } else {
            System.out.println("File does not exist!");
            return -1;
        }
    }

    // method to check if file exists or not
    public static boolean exists(String filepath) {
        Path path = Path.of(filepath);
        return Files.exists(path);
    }
}
