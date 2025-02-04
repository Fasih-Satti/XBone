package com.example.xbone.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import com.example.xbone.R;

import java.util.ArrayList;
import java.util.List;

public class Chatbot extends Fragment {

    private LinearLayout messageContainer;
    private EditText userInput;
    private ImageButton sendButton;
    private ScrollView scrollView;

    private List<String> messages = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatbot, container, false);

        messageContainer = view.findViewById(R.id.message_container);
        userInput = view.findViewById(R.id.user_input);
        sendButton = view.findViewById(R.id.send_button);
        scrollView = view.findViewById(R.id.scrollView);

        sendButton.setOnClickListener(v -> {
            sendMessage();
            hideKeyboard();
        });

        userInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                scrollToBottom();
                showKeyboard();
            }
        });

        if (savedInstanceState != null) {
            messages = savedInstanceState.getStringArrayList("messages");
            if (messages != null) {
                displayMessages();
            }
        }

        return view;
    }

    private void sendMessage() {
        String message = userInput.getText().toString();
        if (!message.isEmpty()) {
            addMessage("You: " + message, true);
            userInput.setText("");

            String botResponse = getBotResponse(message);
            addMessageWithLogo("Bot: " + botResponse);
        }
    }

    private void addMessage(String message, boolean isUser) {
        TextView textView = new TextView(getActivity());
        textView.setText(message);
        textView.setPadding(16, 8, 16, 8);

        if (isUser) {
            // User message styling
            textView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.purple_200));
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        } else {
            // Bot message styling
            textView.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        messageContainer.addView(textView);
        messages.add(message);
        scrollToBottom();
    }

    private void addMessageWithLogo(String message) {
        LinearLayout messageLayout = new LinearLayout(getActivity());
        messageLayout.setOrientation(LinearLayout.HORIZONTAL);
        messageLayout.setPadding(16, 16, 16, 16);

        ImageView botLogo = new ImageView(getActivity());
        botLogo.setImageResource(R.drawable.bot);
        botLogo.setLayoutParams(new LinearLayout.LayoutParams(50, 50));

        TextView messageText = new TextView(getActivity());
        messageText.setText(message);
        messageText.setPadding(16, 0, 0, 0);
        messageText.setTextSize(16);
        messageText.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

        messageLayout.addView(botLogo);
        messageLayout.addView(messageText);
        messageContainer.addView(messageLayout);
        messages.add(message);
        scrollToBottom();
    }

    private String getBotResponse(String message) {
        return "You said: " + message;
    }

    private void scrollToBottom() {
        scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
    }

    private void displayMessages() {
        for (String message : messages) {
            if (message.startsWith("You:")) {
                addMessage(message, true);
            } else {
                addMessageWithLogo(message);
            }
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(userInput.getWindowToken(), 0);
        }
    }

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(userInput, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("messages", new ArrayList<>(messages));
    }
}
