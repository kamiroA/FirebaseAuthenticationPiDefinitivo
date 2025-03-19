package dam.pmdm.firebaseauthenticationpi.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import dam.pmdm.firebaseauthenticationpi.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Infla el layout para este fragmento
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Aquí no necesitas un ViewModel, así que puedes omitir esa parte

        return root; // Devuelve el layout inflado
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Limpia el binding
    }
}