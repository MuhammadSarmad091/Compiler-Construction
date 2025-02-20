package compiler;

import java.util.Arrays;

public class stateTransitions {
    public static void main(String[] args) {
        String[][] table = {
            {"Current State", "Input Symbols", "Next State"},
            {"START_0", "0-9", "ININT_2"},
            {"START_0", "a-z", "INIDEN_10"},
            {"START_0", "\\t, \\n", "START_0"},
            {"START_0", "+, -", "PLUSMINUS_1"},
            {"START_0", "*/%(){}=:!<>\"';\\0/", "DONE_21"},
            {"PLUSMINUS_1", "0-9", "ININT_2"},
            {"PLUSMINUS_1", "other", "DONE_21"},
            {"ININT_2", "0-9", "ININT_2"},
            {"ININT_2", ".", "INFLOAT_3"},
            {"ININT_2", "other", "DONE_21"},
            {"INFLOAT_3", "0-9", "INFLOAT_4"},
            {"INFLOAT_3", "other", "DONE_21"},
            {"INFLOAT_4", "0-9", "INFLOAT_4"},
            {"INFLOAT_4", "e", "INFLOAT_5"},
            {"INFLOAT_4", "other", "DONE_21"},
            {"INFLOAT_5", "0-9", "INFLOAT_7"},
            {"INFLOAT_5", "+, -", "INFLOAT_6"},
            {"INFLOAT_5", "other", "DONE_21"},
            {"INFLOAT_6", "0-9", "INFLOAT_7"},
            {"INFLOAT_6", "other", "DONE_21"},
            {"INFLOAT_7", "0-9", "INFLOAT_7"},
            {"INFLOAT_7", ".", "INFLOAT_8"},
            {"INFLOAT_7", "other", "DONE_21"},
            {"INFLOAT_8", "0-9", "INFLOAT_9"},
            {"INFLOAT_8", "other", "DONE_21"},
            {"INFLOAT_9", "0-9", "INFLOAT_9"},
            {"INFLOAT_9", "other", "DONE_21"},
            {"INIDEN_10", "a-z", "INIDEN_10"},
            {"INIDEN_10", "other", "DONE_21"},
            {"INDIVIDE_11", "/", "INSINCOMM_12"},
            {"INDIVIDE_11", "*", "INMULTCOMM_13"},
            {"INDIVIDE_11", "other", "DONE_21"},
            {"INSINCOMM_12", "\\n", "START_0"},
            {"INSINCOMM_12", "other", "INSINCOMM_12"},
            {"INMULTCOMM_13", "*", "INMULTCOMM_14"},
            {"INMULTCOMM_13", "other", "INMULTCOMM_13"},
            {"INMULTCOMM_14", "/", "START_0"},
            {"INMULTCOMM_14", "*", "INMULTCOMM_14"},
            {"INMULTCOMM_14", "other", "INMULTCOMM_13"},
            {"INLESS_15", "=", "DONE_21"},
            {"INLESS_15", "other", "DONE_21"},
            {"INGREATER_16", "=", "DONE_21"},
            {"INGREATER_16", "other", "DONE_21"},
            {"INCHAR_17", "a-zA-Z", "INCHAR_18"},
            {"INCHAR_17", "other", "DONE_21"},
            {"INCHAR_18", "'", "DONE_21"},
            {"INCHAR_18", "other", "DONE_21"},
            {"INSTR_19", "\"", "DONE_21"},
            {"INSTR_19", "other", "INSTR_19"}
        };

        printTable(table);
    }

    private static void printTable(String[][] table) {
        int[] colWidths = new int[table[0].length];
        Arrays.fill(colWidths, 0);
        for (String[] row : table) {
            for (int i = 0; i < row.length; i++) {
                colWidths[i] = Math.max(colWidths[i], row[i].length());
            }
        }

        for (String[] row : table) {
            for (int i = 0; i < row.length; i++) {
                System.out.printf("%-" + (colWidths[i] + 2) + "s", row[i]);
            }
            System.out.println();
        }
    }
}