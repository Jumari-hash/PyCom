package com.pycom.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pycom.app.executor.PythonExecutorStub;

public class EditorViewModel extends ViewModel {

    private final MutableLiveData<String> executionResult = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final PythonExecutorStub pythonExecutor;

    public EditorViewModel() {
        this.pythonExecutor = new PythonExecutorStub();
    }

    public LiveData<String> getExecutionResult() {
        return executionResult;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void executeCode(String code) {
        try {
            String result = pythonExecutor.execute(code);
            executionResult.postValue(result);
        } catch (Exception e) {
            errorMessage.postValue(e.getMessage());
        }
    }

    public void saveFile(String filename, String content) {
        // TODO: Implement file saving through ProjectRepository
    }

    public void openFile(String filename) {
        // TODO: Implement file loading through ProjectRepository
    }
}
