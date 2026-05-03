package tn.amin.keyboard_gpt.external.dialog.box;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import tn.amin.keyboard_gpt.external.ConfigContainer;
import tn.amin.keyboard_gpt.external.dialog.DialogBoxManager;
import tn.amin.keyboard_gpt.ui.UiInteractor;

public class HelpDialogBox extends DialogBox {

    public HelpDialogBox(DialogBoxManager dialogManager, Activity parent,
                         Bundle inputBundle, ConfigContainer configContainer) {
        super(dialogManager, parent, inputBundle, configContainer);
    }

    @Override
    protected Dialog build() {
        String helpText = getInputBundle().getString(UiInteractor.EXTRA_HELP_TEXT, "");

        return new AlertDialog.Builder(getContext())
                .setTitle("📖 KeyboardGPT Commands")
                .setMessage(helpText)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    getParent().finish();
                })
                .create();
    }
}
