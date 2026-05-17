package tn.amin.keyboard_gpt.external.dialog.box;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import tn.amin.keyboard_gpt.R;
import tn.amin.keyboard_gpt.external.ConfigContainer;
import tn.amin.keyboard_gpt.external.dialog.DialogBoxManager;
import tn.amin.keyboard_gpt.external.dialog.DialogType;
import tn.amin.keyboard_gpt.llm.LanguageModel;
import tn.amin.keyboard_gpt.llm.LanguageModelField;

public class ConfigureModelDialogBox extends DialogBox {

    private static final String[] CODEX_MODELS = {
        "gpt-5", "gpt-5.1", "gpt-5.2",
        "chatgpt-4o-latest", "o1-preview", "o3-mini",
        "anthropic/claude-sonnet-4",
        "google/gemini-2.5-pro-preview-05-06",
        "x-ai/grok-4",
        "deepseek-ai/deepseek-v3.2", "deepseek-ai/deepseek-R1-0528",
        "meta/llama-4-maverick-17b-128e-instruct",
        "meta/llama-4-scout-17b-16e-instruct",
        "meta-llama-3.3-70b-instruct",
        "qwen/qwen3-235b-a22b", "qwen/qwq-32b",
        "moonshotai/kimi-k2-instruct-0905", "moonshotai/kimi-k2-thinking",
        "mistralai/mistral-large-3-675b-instruct-2512",
        "mistralai/magistral-small-2506",
        "mercury-coder", "nvidia/nemotron-3-nano-30b-a3b",
        "minimaxai/minimax-m2"
    };

    private static final String[] POLLINATIONS_MODELS = {
    "openai",
    "openai-large",
    "openai-reasoning",
    "llama",
    "llamalight",
    "gemini",
    "gemini-thinking",
    "deepseek",
    "deepseek-r1",
    "mistral",
    "qwen-coder"
};

    public ConfigureModelDialogBox(DialogBoxManager dialogManager, Activity parent,
                                   Bundle inputBundle, ConfigContainer configContainer) {
        super(dialogManager, parent, inputBundle, configContainer);
    }

    @Override
    protected Dialog build() {
        safeguardModelData();

        Bundle modelConfig = getConfig().languageModelsConfig.getBundle(getConfig().selectedModel.name());
        if (modelConfig == null) {
            throw new RuntimeException("No model " + getConfig().selectedModel.name());
        }

        LinearLayout layout = (LinearLayout)
                getParent().getLayoutInflater().inflate(R.layout.dialog_configue_model, null);

        LinearLayout advancedLayout = new LinearLayout(getContext());
        advancedLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout advancedExpand = (LinearLayout)
                getParent().getLayoutInflater().inflate(R.layout.dialog_configure_model_advanced, null);

        Bundle tempModelConfig = new Bundle();

        boolean isCodexAPI = getConfig().selectedModel == LanguageModel.CodexAPI;
        boolean isPollinations = getConfig().selectedModel == LanguageModel.Pollinations;

        for (LanguageModelField field : LanguageModelField.values()) {

            // Free models ke liye API Key hide karo
            if ((isCodexAPI || isPollinations) && field == LanguageModelField.ApiKey) {
                continue;
            }

            // CodexAPI ke liye SubModel dropdown
            if (isCodexAPI && field == LanguageModelField.SubModel) {
                TextView label = new TextView(getContext());
                label.setText("Sub Model");
                label.setPadding(16, 24, 16, 8);
                layout.addView(label);

                Spinner spinner = new Spinner(getContext());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getContext(),
                        android.R.layout.simple_spinner_item,
                        CODEX_MODELS
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                String currentModel = modelConfig.getString(field.name);
                if (currentModel == null) currentModel = "gpt-5";
                for (int i = 0; i < CODEX_MODELS.length; i++) {
                    if (CODEX_MODELS[i].equals(currentModel)) {
                        spinner.setSelection(i);
                        break;
                    }
                }

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        tempModelConfig.putString(field.name, CODEX_MODELS[position]);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });

                spinner.setPadding(16, 8, 16, 16);
                layout.addView(spinner);
                continue;
            }

            // Pollinations ke liye SubModel dropdown
            if (isPollinations && field == LanguageModelField.SubModel) {
                TextView label = new TextView(getContext());
                label.setText("Sub Model");
                label.setPadding(16, 24, 16, 8);
                layout.addView(label);

                Spinner spinner = new Spinner(getContext());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getContext(),
                        android.R.layout.simple_spinner_item,
                        POLLINATIONS_MODELS
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                String currentModel = modelConfig.getString(field.name);
                if (currentModel == null) currentModel = "openai";
                for (int i = 0; i < POLLINATIONS_MODELS.length; i++) {
                    if (POLLINATIONS_MODELS[i].equals(currentModel)) {
                        spinner.setSelection(i);
                        break;
                    }
                }

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        tempModelConfig.putString(field.name, POLLINATIONS_MODELS[position]);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });

                spinner.setPadding(16, 8, 16, 16);
                layout.addView(spinner);
                continue;
            }

            RelativeLayout fieldLayout = (RelativeLayout)
                    getParent().getLayoutInflater().inflate(R.layout.dialog_configure_model_field, layout, false);
            if (!field.advanced) {
                layout.addView(fieldLayout);
            } else {
                advancedLayout.addView(fieldLayout);
            }

            TextView fieldTitle = fieldLayout.findViewById(R.id.field_tile);
            EditText fieldEdit = fieldLayout.findViewById(R.id.field_edit);
            fieldEdit.setInputType(field.inputType);

            fieldTitle.setText(field.title);
            String fieldValue = modelConfig.getString(field.name);
            fieldEdit.setText(fieldValue != null ? fieldValue : getConfig().selectedModel.getDefault(field));
            fieldEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {}
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    tempModelConfig.putString(field.name, s.toString());
                }
            });
        }

        layout.addView(advancedExpand);
        layout.addView(advancedLayout);

        advancedLayout.setVisibility(View.GONE);
        ImageView advancedArrow = advancedExpand.findViewById(R.id.icon_advanced);
        advancedExpand.setOnClickListener(new View.OnClickListener() {
            private boolean expanded = false;
            @Override
            public void onClick(View v) {
                expanded = !expanded;
                if (expanded) {
                    advancedArrow.animate().rotation(90);
                    advancedLayout.setVisibility(View.VISIBLE);
                } else {
                    advancedArrow.animate().rotation(0);
                    advancedLayout.setVisibility(View.GONE);
                }
            }
        });

        return new AlertDialog.Builder(getContext())
                .setTitle(getConfig().selectedModel.label + " configuration")
                .setView(layout)
                .setPositiveButton("Ok", (dialog, which) -> {
                    modelConfig.putAll(tempModelConfig);
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    switchToDialog(DialogType.ChoseModel);
                })
                .create();
    }
}
