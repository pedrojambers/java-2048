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

    public void saveScore(Score newScore) {
        List<Score> scores = loadScores();

        boolean updated = false;

        for (int i = 0; i < scores.size(); i++) {
            Score existing = scores.get(i);

            if (existing.getPlayerName().equals(newScore.getPlayerName())) {
                if (newScore.getScore() > existing.getScore()) {
                    scores.set(i, newScore);
                }
                updated = true;
                break;
            }
        }

        if (!updated) {
            scores.add(newScore);
        }

        saveAll(scores);
    }

    private void saveAll(List<Score> scores) {
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(filePath.toFile()))) {

            for (Score s : scores) {
                writer.write(s.getPlayerName() + ";" + s.getScore());
                writer.newLine();
            }
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
