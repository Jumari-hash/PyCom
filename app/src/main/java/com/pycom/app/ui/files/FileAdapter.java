package com.pycom.app.ui.files;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pycom.app.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {

    private List<File> files = new ArrayList<>();
    private OnFileClickListener clickListener;
    private OnFileLongClickListener longClickListener;

    public interface OnFileClickListener {
        void onFileClick(File file);
    }

    public interface OnFileLongClickListener {
        boolean onFileLongClick(File file);
    }

    public FileAdapter(OnFileClickListener clickListener, OnFileLongClickListener longClickListener) {
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    public void setFiles(List<File> files) {
        this.files = files;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_file, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        File file = files.get(position);
        holder.bind(file);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    class FileViewHolder extends RecyclerView.ViewHolder {
        private ImageView iconView;
        private TextView nameView;
        private TextView sizeView;

        public FileViewHolder(@NonNull View itemView) {
            super(itemView);
            iconView = itemView.findViewById(R.id.file_icon);
            nameView = itemView.findViewById(R.id.file_name);
            sizeView = itemView.findViewById(R.id.file_size);
        }

        public void bind(File file) {
            nameView.setText(file.getName());
            
            long sizeInBytes = file.length();
            String sizeText = formatFileSize(sizeInBytes);
            sizeView.setText(sizeText);
            
            if (file.getName().endsWith(".py")) {
                iconView.setImageResource(R.drawable.ic_python_file);
            } else if (file.isDirectory()) {
                iconView.setImageResource(R.drawable.ic_folder);
            } else {
                iconView.setImageResource(R.drawable.ic_file);
            }
            
            itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onFileClick(file);
                }
            });
            
            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    return longClickListener.onFileLongClick(file);
                }
                return false;
            });
        }

        private String formatFileSize(long bytes) {
            if (bytes < 1024) return bytes + " B";
            int exp = (int) (Math.log(bytes) / Math.log(1024));
            String pre = "KMGTPE".charAt(exp - 1) + "";
            return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
        }
    }
}
