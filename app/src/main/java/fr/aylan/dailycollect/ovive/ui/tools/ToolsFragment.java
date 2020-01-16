package fr.aylan.dailycollect.ovive.ui.tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.auth.SignInActivity;

public class ToolsFragment extends Fragment {

    FirebaseAuth auth;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

       auth = FirebaseAuth.getInstance();

        signOut();

        View root = inflater.inflate(R.layout.ovive_fragment_tools, container, false);
        return root;
    }

    public void signOut(){
        auth.signOut();
        Intent intent = new Intent(getContext(), SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getParentFragment().getActivity().finish();
        startActivity(intent);
    }
}