package com.example.todoapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.textviewTitle.setText(currentNote.getTitle());
        holder.textviewDescription.setText(currentNote.getDescription());
        holder.textviewPriority.setText(String.valueOf(currentNote.getPriority()));
        holder.textviewDate.setText(String.valueOf(currentNote.getDate()));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }
//View Holder
    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textviewTitle;
        private TextView textviewDescription;
        private TextView textviewPriority;
        private TextView textviewDate;


        public NoteHolder(View itemView) {
            super(itemView);
            textviewTitle = itemView.findViewById(R.id.text_view_title);
            textviewDescription = itemView.findViewById(R.id.text_view_description);
            textviewPriority = itemView.findViewById(R.id.text_view_priority);
            textviewDate=itemView.findViewById(R.id.text_view_date);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(notes.get(position));
                    }
                }
            });
        }


    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }
}
