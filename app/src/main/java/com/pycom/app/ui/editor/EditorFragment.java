package com.pycom.app.ui.editor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pycom.app.R;
import com.pycom.app.viewmodel.EditorViewModel;

public class EditorFragment extends Fragment {

    private EditorViewModel viewModel;
    private EditText codeEditor;
    private FloatingActionButton runButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editor, container, false);
        
        viewModel = new ViewModelProvider(this).get(EditorViewModel.class);
        
        codeEditor = view.findViewById(R.id.code_editor);
        runButton = view.findViewById(R.id.fab_run);
        
        setupObservers();
        setupListeners();
        loadSampleCode();
        
        return view;
    }

    private void setupObservers() {
        viewModel.getExecutionResult().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                Toast.makeText(getContext(), "Output: " + result, Toast.LENGTH_LONG).show();
            }
        });
        
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupListeners() {
        runButton.setOnClickListener(v -> {
            String code = codeEditor.getText().toString();
            if (code.trim().isEmpty()) {
                Toast.makeText(getContext(), "Please enter some Python code", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.executeCode(code);
            }
        });
    }

    private void loadSampleCode() {
        String sampleCode = "# Welcome to PyCom!\n" +
                "# Write your Python code here and tap the Run button\n\n" +
                "print('Hello from PyCom!')\n\n" +
                "# Example: Calculate factorial\n" +
                "def factorial(n):\n" +
                "    if n <= 1:\n" +
                "        return 1\n" +
                "    return n * factorial(n - 1)\n\n" +
                "result = factorial(5)\n" +
                "print(f'Factorial of 5 is {result}')\n";
        
        codeEditor.setText(sampleCode);
    }
}
