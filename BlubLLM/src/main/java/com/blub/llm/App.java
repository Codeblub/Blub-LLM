package com.blub.llm;

import dev.langchain4j.model.ollama.OllamaChatModel;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("--- 🐳 Blub-LLM Initialized ---");
        System.out.print("Enter your GitHub Repo URL to study: ");
        String repoUrl = scanner.nextLine();

        // 1. Setup the AI Model (Points to your local Ollama)
        OllamaChatModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434") // This is the default Ollama port on Windows
                .modelName("llama3")               // Make sure you 'ollama pull llama3' later!
                .build();

        System.out.println("AI Brain Linked. Type 'exit' to quit.");

        while (true) {
            System.out.print("\nYOU: ");
            String userPrompt = scanner.nextLine();

            if (userPrompt.equalsIgnoreCase("exit")) break;

            // 2. Chat with the AI
            System.out.println("AI is thinking...");
            String response = model.generate(userPrompt);
            System.out.println("AI: " + response);

            // 3. Simple "Learning" - Save and Push to Git
            try {
                saveInsightToGit(response);
            } catch (Exception e) {
                System.out.println("[Git Error] Could not save insight: " + e.getMessage());
            }
        }
    }

    private static void saveInsightToGit(String insight) throws Exception {
        // This is a placeholder for the logic that will write a file 
        // and use JGit to push it back to your repo.
        String fileName = "learning_" + System.currentTimeMillis() + ".txt";
        Files.write(Paths.get(fileName), insight.getBytes());
        System.out.println(">> Insight recorded in " + fileName);
    }
}