package com.pycom.app.ui.terminal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.pycom.app.R;
import com.pycom.app.viewmodel.TerminalViewModel;

public class TerminalFragment extends Fragment {

    private TerminalViewModel viewModel;
    private TextView terminalOutput;
    private EditText commandInput;
    private Button sendButton;
    private ScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terminal, container, false);
        
        viewModel = new ViewModelProvider(this).get(TerminalViewModel.class);
        
        terminalOutput = view.findViewById(R.id.terminal_output);
        commandInput = view.findViewById(R.id.command_input);
        sendButton = view.findViewById(R.id.btn_send);
        scrollView = view.findViewById(R.id.scroll_view);
        
        setupObservers();
        setupListeners();
        
        return view;
    }

    private void setupObservers() {
        viewModel.getTerminalText().observe(getViewLifecycleOwner(), text -> {
            terminalOutput.setText(text);
            scrollToBottom();
        });
    }

    private void setupListeners() {
        sendButton.setOnClickListener(v -> {
            String command = commandInput.getText().toString();
            if (!command.trim().isEmpty()) {
                viewModel.executeCommand(command);
                commandInput.setText("");
            }
        });
        
        commandInput.setOnEditorActionListener((v, actionId, event) -> {
            sendButton.performClick();
            return true;
        });
    }

    private void scrollToBottom() {
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }
}
