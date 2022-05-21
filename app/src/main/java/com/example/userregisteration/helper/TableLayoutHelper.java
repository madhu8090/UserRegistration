package com.example.userregisteration.helper;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;


import com.example.userregisteration.R;

import java.util.Map;

public class TableLayoutHelper {

    public static void buildTable(Context context, TableLayout tableLayout, Map<String, String> rowValues) {

        tableLayout.removeAllViews();

        int rowCount = rowValues.keySet().size();

        for (String key : rowValues.keySet()) {
            tableLayout.addView(buildRow(context, key, rowValues.get(key), --rowCount == 0));
        }
    }

    private static LinearLayout buildRow(Context context, String name, String value, boolean hideUnderline) {
        // if there is no value
        if (value == null) {
            value = "";
        }

        LinearLayout row = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.row_table, null);
        ((TextView) row.findViewById(R.id.col_name)).setText(name);
        ((TextView) row.findViewById(R.id.col_value)).setText(value);

        if (hideUnderline) {
            row.findViewById(R.id.line1).setVisibility(View.GONE);
        }

        return row;
    }
}
