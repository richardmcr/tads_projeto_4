package uni9.projetopraticoemsistemas.myhealth.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import uni9.projetopraticoemsistemas.myhealth.R;

public class HomeFragment extends Fragment {

    Button btnLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnLogin = view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v -> Navigation.findNavController(v).navigate(HomeFragmentDirections.actionHomeFragmentToLembretesFragment()));

        return view;
    }
}
