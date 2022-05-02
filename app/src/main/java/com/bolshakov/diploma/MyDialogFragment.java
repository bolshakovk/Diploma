package com.bolshakov.diploma;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {
    private String m_TextCost = "";
    private String m_TextParam = "";
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String buttonAddString = "Add";
        String buttonCancelString = "Cancel";
        LinearLayout linearLayout = new LinearLayout(getActivity());


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Adding hardware data..");  // заголовок

        final EditText inputCost = new EditText(getActivity());
        final EditText inputParam = new EditText(getActivity());
        inputCost.setHint("Cost");
        inputParam.setHint("Params");
        linearLayout.addView(inputCost);

        linearLayout.addView(inputParam);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        inputCost.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputParam.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setView(linearLayout);


        builder.setPositiveButton(buttonAddString, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                m_TextCost = inputCost.getText().toString();
                m_TextParam = inputParam.getText().toString();
                Toast.makeText(getActivity(), m_TextCost +"\t" + m_TextParam,
                        Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton(buttonCancelString, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.setCancelable(true);

        return builder.create();
    }
}
