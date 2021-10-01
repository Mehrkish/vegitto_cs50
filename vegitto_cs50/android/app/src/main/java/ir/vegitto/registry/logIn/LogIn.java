package ir.vegitto.registry.logIn;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;

import java.sql.SQLOutput;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import ir.vegitto.R;
import ir.vegitto.main.MainActivity;
import ir.vegitto.model.LogInInputs;
import ir.vegitto.tool.AccessTokenHolder;

public class LogIn extends Fragment {
    private View view;
    private EditText user_input;
    private EditText password;
    private Button logIn;
    private LogInViewModel viewModel;
    private Button navToSignUp;
    private Button navToRecovery;
    private ImageButton back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_registry_log_in, container, false);
            viewModel = new ViewModelProvider(this).get(LogInViewModel.class);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize();

        // Whitespace is not allowed
        InputFilter filter = (source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                if (source.charAt(i) == ' ') {
                    Toast.makeText(getContext(), "ورود اسپیس مجاز نمی‌باشد.", Toast.LENGTH_SHORT).show();
                    return "";
                }
            }
            return null;
        };
        user_input.setFilters(new InputFilter[]{filter});

        logIn.setOnClickListener(v -> {
            // Check if all the fields are filled
            boolean isBlank = blankChecker(user_input, password);
            if (isBlank)
                return;

            //send info to view model
            viewModel.sendLogInInfo(getContext(), new LogInInputs(user_input.getText().toString(), password.getText().toString()));
            //get status
            viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
                String jsonString = status.getMessage();
                if (status.isStatus())
                    startMainActivity();
                else
                    Toast.makeText(getContext(), jsonString.substring(1, jsonString.length() - 2), Toast.LENGTH_SHORT).show();
            });
        });

        navToRecovery.setOnClickListener(v -> {
            // nav to pass recovery fragment
        });

        navToSignUp.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_logIn_to_signUp));

        back.setOnClickListener(v -> Navigation.findNavController(view).popBackStack());
    }

    private void initialize() {
        back = view.findViewById(R.id.back_registry);
        navToRecovery = view.findViewById(R.id.go_to_recovery);
        navToSignUp = view.findViewById(R.id.go_to_signUp);
        logIn = view.findViewById(R.id.log_in_logIn_bt);
        user_input = view.findViewById(R.id.log_in_user_input);
        password = view.findViewById(R.id.log_in_password);
    }

    private Boolean blankChecker(EditText userInput, EditText password) {
        if (userInput.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "ایمیل یا شماره‌‌همراه ثبت‌نامی خود را واردبفرمایید", android.widget.Toast.LENGTH_SHORT).show();
            return true;
        } else if (password.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "رمز‌عبورتان را واردنکردید", android.widget.Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void startMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        assert getActivity() != null;
        getActivity().finish();
    }
}
