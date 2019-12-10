package infilms.asee.giiis.unex.es.thenoworder.ui.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import infilms.asee.giiis.unex.es.thenoworder.R;

public class SendFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SendViewModel sendViewModel = ViewModelProviders.of(this).get(SendViewModel.class);
        View root;
        root = inflater.inflate(R.layout.fragment_send, container, false);
        final TextView main_text_about = root.findViewById(R.id.text_about);
        final TextView author_name1 = root.findViewById(R.id.text_Author1_name);
        final TextView author_gmail1 = root.findViewById(R.id.text_Author1_gmail);
        final TextView author_name2 = root.findViewById(R.id.text_Author2_name);
        final TextView author_gmail2 = root.findViewById(R.id.text_Author2_gmail);
        sendViewModel.getAbout().observe(this, main_text_about::setText);
        sendViewModel.getAuthor_name_1().observe(this, author_name1::setText);
        sendViewModel.getAuthor_gmail_1().observe(this, author_gmail1::setText);
        sendViewModel.getAuthor_name_2().observe(this, author_name2::setText);
        sendViewModel.getAuthor_gmail_2().observe(this, author_gmail2::setText);
        return root;
    }
}