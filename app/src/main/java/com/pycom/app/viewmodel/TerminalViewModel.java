package com.pycom.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pycom.app.executor.ShellExecutorStub;

public class TerminalViewModel extends ViewModel {

    private final MutableLiveData<String> terminalText = new MutableLiveData<>("");
    private final ShellExecutorStub shellExecutor;
    private StringBuilder outputBuffer = new StringBuilder();

    public TerminalViewModel() {
        this.shellExecutor = new ShellExecutorStub();
        appendToTerminal("PyCom Terminal v1.0\n");
        appendToTerminal("Type 'help' for available commands\n\n");
    }

    public LiveData<String> getTerminalText() {
        return terminalText;
    }

    public void executeCommand(String command) {
        appendToTerminal("$ " + command + "\n");
        
        try {
            String output = shellExecutor.executeCommand(command);
            appendToTerminal(output + "\n");
        } catch (Exception e) {
            appendToTerminal("Error: " + e.getMessage() + "\n");
        }
        
        appendToTerminal("\n");
    }

    private void appendToTerminal(String text) {
        outputBuffer.append(text);
        terminalText.postValue(outputBuffer.toString());
    }

    public void clearTerminal() {
        outputBuffer = new StringBuilder();
        terminalText.postValue("");
    }
}
