package com.pedro.game2048.service;

import com.pedro.game2048.model.Score;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreService {

    private static final String FILE_NAME = "scores.txt";

    private final Path filePath;

    public ScoreService() {
        filePath = Paths.get(FILE_NAME);

        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveScore(Score score) {
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(filePath.toFile(), true))) {

            writer.write(score.getPlayerName() + ";" + score.getScore());
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Score> loadScores() {
        List<Score> scores = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(filePath.toFile()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String name = parts[0];
                    int value = Integer.parseInt(parts[1]);
                    scores.add(new Score(name, value));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return scores;
    }
}
