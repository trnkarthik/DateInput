package com.dateinput.dateinput;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText dateInput;

    String lastText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateInput = (EditText) findViewById(R.id.date_input);

        dateInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {
                if (newText.length() > 0 && !newText.toString().equals(lastText)) {
                    newText = newText.toString().replace(":", "");
                    String newHH = formatHH(newText.toString());

                    if (newHH != null && newHH.length() > 0 && newHH.contains(":")) {
                        int indexMM = newHH.indexOf(":");
                        String mm = newHH.substring(indexMM + 1);
                        mm = formatMM(mm);
                        newHH = newHH.substring(0, indexMM + 1) + mm;
                    }
                    lastText = newHH;

                    dateInput.setText(newHH, TextView.BufferType.EDITABLE);
                    if (newHH.length() > 0) {
                        dateInput.setSelection(newHH.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private String formatHH(String newText) {
        if (newText.length() == 0) {
            return null;
        } else if (newText.length() == 1) {
            return newText;
        } else if (newText.length() == 2 && newText.charAt(0) > '1') {
            return newText.charAt(0) + ":" + newText.subSequence(1, 2);
        } else if (newText.length() == 2 && newText.charAt(0) == '1' && newText.charAt(1) <= '2') {
            return newText + ":";
        } else if (newText.length() == 2 && newText.charAt(0) == '1' && newText.charAt(1) > '2') {
            return newText.charAt(0) + ":" + newText.subSequence(1, 2);
        } else if (newText.length() > 2) {
            return formatHH(newText.substring(0, 2)) + newText.substring(2);
        }
        return newText;
    }

    private String formatMM(String mm) {
        if (mm.length() > 0) {
            int min = Integer.parseInt(mm);
            if (min >= 0 && min <= 60) {
                return min + "";
            }
        }
        return "";
    }
}
