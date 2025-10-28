package com.pycom.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pycom.app.data.repository.ProjectRepository;

import java.io.File;
import java.util.List;

public class FilesViewModel extends AndroidViewModel {

    private final MutableLiveData<List<File>> files = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final ProjectRepository repository;

    public FilesViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ProjectRepository(application.getApplicationContext());
    }

    public LiveData<List<File>> getFiles() {
        return files;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadFiles() {
        try {
            List<File> fileList = repository.listProjectFiles();
            files.postValue(fileList);
        } catch (Exception e) {
            errorMessage.postValue("Error loading files: " + e.getMessage());
        }
    }

    public void deleteFile(File file) {
        try {
            repository.deleteFile(file);
            loadFiles();
        } catch (Exception e) {
            errorMessage.postValue("Error deleting file: " + e.getMessage());
        }
    }

    public void createNewFile(String filename) {
        try {
            repository.createFile(filename);
            loadFiles();
        } catch (Exception e) {
            errorMessage.postValue("Error creating file: " + e.getMessage());
        }
    }
}
