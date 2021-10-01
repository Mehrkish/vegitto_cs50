package ir.vegitto.registry.signUp;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import ir.vegitto.R;
import ir.vegitto.main.MainActivity;
import ir.vegitto.model.SignUpInputs;

public class SignUp extends Fragment {
    private View view;
    private EditText username;
    private EditText user_input;
    private EditText password;
    private EditText confirm_password;
    private ImageButton back;
    private Button signUp;
    private Button navToLogIn;
    private SignUpViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_registry_sign_up, container, false);

            viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
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
        username.setFilters(new InputFilter[]{filter});
        user_input.setFilters(new InputFilter[]{filter});

        signUp.setOnClickListener(v -> {
            boolean isBlank = blankChecker(username, user_input, password, confirm_password);
            if (isBlank)
                return;
            viewModel.sendSignUpInfo(getContext(), new SignUpInputs(username.getText().toString(), user_input.getText().toString(), password.getText().toString(), confirm_password.getText().toString()));
            viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
                if (status.isStatus())
                    startMainActivity();
                else {
                    String jsonString = status.getMessage();
                    try {
                        // Convert to JsonObject
                        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
                        // Check if it contains mobile_number
                        if (jsonObject.has("mobile_number")) {
                            // Check if it's a JsonArray
                            if (jsonObject.get("mobile_number").getClass().equals(JsonArray.class)) {
                                JsonArray jsonArray = (JsonArray) jsonObject.get("mobile_number");
                                // Get the first warning
                                String message = jsonArray.get(0).getAsString();
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }
                        // Check if it contains email
                        else if (jsonObject.has("email")) {
                            // Check if it's a JsonArray
                            if (jsonObject.get("email").getClass().equals(JsonArray.class)) {
                                JsonArray jsonArray = (JsonArray) jsonObject.get("email");
                                // Get the first warning
                                String message = jsonArray.get(0).getAsString();
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }
                        // Check if it contains password
                        else if (jsonObject.has("password")) {
                            // Check if it's a JsonArray
                            if (jsonObject.get("password").getClass().equals(JsonArray.class)) {
                                JsonArray jsonArray = (JsonArray) jsonObject.get("password");
                                // Get the first warning
                                String message = jsonArray.get(0).getAsString();
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            } else if (jsonObject.get("password").getClass().equals(JsonPrimitive.class)) {
                                String message = jsonObject.get("password").getAsString();
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception ignored) {
                        Toast.makeText(getContext(), jsonString.substring(1, jsonString.length() - 2), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        navToLogIn.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_signUp_to_logIn));

        back.setOnClickListener(v -> Navigation.findNavController(view).popBackStack());
    }

    private Boolean blankChecker(EditText username, EditText userInput, EditText password, EditText confirmPassword) {
        if (username.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "نام‌‌کاربری  مورد علاقه‌تان را واردبفرمایید", android.widget.Toast.LENGTH_SHORT).show();
            return true;
        } else if (userInput.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "ایمیل یا شماره‌موبایل خود را واردبفرمایید", android.widget.Toast.LENGTH_SHORT).show();
            return true;
        } else if (password.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "رمزعبورتان را انتخاب‌‌‌بفرمایید", android.widget.Toast.LENGTH_SHORT).show();
            return true;
        } else if (confirmPassword.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "مجدد رمز‌عبورتان را واردبفرمایید", android.widget.Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void initialize() {
        back = view.findViewById(R.id.back_registry);
        navToLogIn = view.findViewById(R.id.already_logged_in_btn);
        username = view.findViewById(R.id.sign_up_username_edt);
        user_input = view.findViewById(R.id.user_input_sign_up);
        password = view.findViewById(R.id.sign_up_pass_edt);
        confirm_password = view.findViewById(R.id.sign_up_confirm_pass_edt);
        signUp = view.findViewById(R.id.sign_up_btn);
    }

    private void startMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        assert getActivity() != null;
        getActivity().finish();
    }

}
