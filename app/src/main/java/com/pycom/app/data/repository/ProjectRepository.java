package com.pycom.app.data.repository;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectRepository {

    private static final String PYCOM_DIR = "PyCom";
    private static final String PROJECTS_DIR = "projects";
    private static final String PACKAGES_DIR = "site-packages";
    
    private final Context context;
    private final File projectsDirectory;
    private final File packagesDirectory;

    public ProjectRepository(Context context) {
        this.context = context;
        
        // Use app-specific external storage (works on API 33+ without special permissions)
        // If external storage is unavailable, fall back to internal storage
        File appStorageDir = context.getExternalFilesDir(null);
        if (appStorageDir == null) {
            // External storage not mounted - use internal storage as fallback
            appStorageDir = context.getFilesDir();
        }
        
        File pycomRoot = new File(appStorageDir, PYCOM_DIR);
        this.projectsDirectory = new File(pycomRoot, PROJECTS_DIR);
        this.packagesDirectory = new File(pycomRoot, PACKAGES_DIR);
        
        initializeDirectories();
        copySampleProjectsOnFirstLaunch();
    }

    private void initializeDirectories() {
        if (!projectsDirectory.exists()) {
            projectsDirectory.mkdirs();
        }
        if (!packagesDirectory.exists()) {
            packagesDirectory.mkdirs();
        }
    }

    /**
     * Copy sample .py files from assets to projects directory on first launch
     */
    private void copySampleProjectsOnFirstLaunch() {
        try {
            AssetManager assetManager = context.getAssets();
            String[] sampleFiles = assetManager.list("sample_projects");
            
            if (sampleFiles != null && sampleFiles.length > 0) {
                for (String filename : sampleFiles) {
                    File targetFile = new File(projectsDirectory, filename);
                    
                    // Only copy if file doesn't exist
                    if (!targetFile.exists()) {
                        copyAssetToFile("sample_projects/" + filename, targetFile);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyAssetToFile(String assetPath, File targetFile) throws IOException {
        InputStream in = context.getAssets().open(assetPath);
        OutputStream out = new FileOutputStream(targetFile);
        
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        
        in.close();
        out.close();
    }

    /**
     * List all .py files in the projects directory
     */
    public List<File> listProjectFiles() {
        List<File> pyFiles = new ArrayList<>();
        
        if (projectsDirectory.exists() && projectsDirectory.isDirectory()) {
            File[] files = projectsDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".py")) {
                        pyFiles.add(file);
                    }
                }
            }
        }
        
        return pyFiles;
    }

    /**
     * Create a new Python file
     */
    public File createFile(String filename) throws IOException {
        if (!filename.endsWith(".py")) {
            filename += ".py";
        }
        
        File newFile = new File(projectsDirectory, filename);
        if (newFile.createNewFile()) {
            return newFile;
        } else {
            throw new IOException("File already exists: " + filename);
        }
    }

    /**
     * Delete a file
     */
    public boolean deleteFile(File file) {
        return file.exists() && file.delete();
    }

    /**
     * Read file content
     */
    public String readFile(File file) throws IOException {
        // TODO: Implement file reading
        return "";
    }

    /**
     * Write content to file
     */
    public void writeFile(File file, String content) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content.getBytes());
        fos.close();
    }

    public File getProjectsDirectory() {
        return projectsDirectory;
    }

    public File getPackagesDirectory() {
        return packagesDirectory;
    }
}
