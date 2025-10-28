package com.pycom.app.ui.files;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pycom.app.R;
import com.pycom.app.viewmodel.FilesViewModel;

public class FileManagerFragment extends Fragment {

    private FilesViewModel viewModel;
    private RecyclerView recyclerView;
    private FileAdapter fileAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private FloatingActionButton fabNewFile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_files, container, false);
        
        viewModel = new ViewModelProvider(this).get(FilesViewModel.class);
        
        recyclerView = view.findViewById(R.id.recycler_files);
        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        fabNewFile = view.findViewById(R.id.fab_new_file);
        
        setupRecyclerView();
        setupObservers();
        setupListeners();
        
        viewModel.loadFiles();
        
        return view;
    }

    private void setupRecyclerView() {
        fileAdapter = new FileAdapter(file -> {
            Toast.makeText(getContext(), "Opening: " + file.getName(), Toast.LENGTH_SHORT).show();
        }, file -> {
            viewModel.deleteFile(file);
            return true;
        });
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(fileAdapter);
    }

    private void setupObservers() {
        viewModel.getFiles().observe(getViewLifecycleOwner(), files -> {
            fileAdapter.setFiles(files);
            swipeRefresh.setRefreshing(false);
        });
        
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void setupListeners() {
        swipeRefresh.setOnRefreshListener(() -> viewModel.loadFiles());
        
        fabNewFile.setOnClickListener(v -> {
            Toast.makeText(getContext(), "New file feature - to be implemented", Toast.LENGTH_SHORT).show();
        });
    }
}
