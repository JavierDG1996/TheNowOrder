package infilms.asee.giiis.unex.es.thenoworder.ui.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import infilms.asee.giiis.unex.es.thenoworder.R;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        final TextView main_text_about = root.findViewById(R.id.text_about);
        final TextView author_name1 = root.findViewById(R.id.text_Author1_name);
        final TextView author_gmail1 = root.findViewById(R.id.text_Author1_gmail);
        final TextView author_name2 = root.findViewById(R.id.text_Author2_name);
        final TextView author_gmail2 = root.findViewById(R.id.text_Author2_gmail);
        sendViewModel.getAbout().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                main_text_about.setText(s);
            }
        });
        sendViewModel.getAuthor_name_1().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                author_name1.setText(s);
            }
        });
        sendViewModel.getAuthor_gmail_1().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                author_gmail1.setText(s);
            }
        });
        sendViewModel.getAuthor_name_2().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                author_name2.setText(s);
            }
        });
        sendViewModel.getAuthor_gmail_2().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                author_gmail2.setText(s);
            }
        });
        return root;
    }
}