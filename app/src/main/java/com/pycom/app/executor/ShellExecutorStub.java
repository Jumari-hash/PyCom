package com.pycom.app.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellExecutorStub {

    // Tracks the current working directory for cd command
    private static File currentDir = new File("/");

    /**
     * Executes a shell command in Android using /system/bin/sh
     * Supports ls, cd, cp, mv, mkdir, cat, and python program.py via Chaquopy bridge.
     */
    public static String executeCommand(String command) {
        command = command.trim();
        if (command.isEmpty()) return "";

        String[] parts = command.split("\\s+");
        String cmd = parts[0];

        // Handle built-in cd command manually (since it doesn't persist between processes)
        if (cmd.equals("cd")) {
            if (parts.length > 1) {
                File newDir = new File(currentDir, parts[1]);
                if (newDir.exists() && newDir.isDirectory()) {
                    currentDir = newDir;
                    return "Changed directory to: " + currentDir.getAbsolutePath();
                } else {
                    return "cd: no such directory: " + parts[1];
                }
            } else {
                currentDir = new File(System.getProperty("user.home", "/"));
                return "Changed directory to home: " + currentDir.getAbsolutePath();
            }
        }

        // Handle Python file execution via PythonExecutorStub
        if (cmd.equals("python") && parts.length > 1) {
            String scriptName = parts[1];
            File scriptFile = new File(currentDir, scriptName);
            if (!scriptFile.exists()) {
                return "python: file not found: " + scriptFile.getAbsolutePath();
            }

            try {
                // Read file contents
                StringBuilder scriptContent = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new java.io.FileReader(scriptFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        scriptContent.append(line).append("\n");
                    }
                }
                // Execute Python code using Chaquopy
                return PythonExecutorStub.executeCode(scriptContent.toString());
            } catch (Exception e) {
                return "Python execution error: " + e.getMessage();
            }
        }

        // Handle regular shell commands (ls, cp, mv, mkdir, cat, etc.)
        try {
            Process process = new ProcessBuilder("/system/bin/sh", "-c", command)
                    .directory(currentDir)
                    .redirectErrorStream(true)
                    .start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            reader.close();
            process.waitFor();
            return output.toString().trim();

        } catch (IOException e) {
            return "IO error: " + e.getMessage();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Interrupted: " + e.getMessage();
        }
    }

    public static String getCurrentDir() {
        return currentDir.getAbsolutePath();
    }
}