package com.example.viaporter.managers;

public class DialogManager {
    private static final String TAG = "DialogManager";

    /*                                      *
     * Use of Bill Pugh Singleton structure *
     *                                      */
    // Private constructor //
    private DialogManager() {
        createAlertDialogs();
    }
    // Static inner class are not loaded until they are referenced
    private static class dialogManagerholder {
        private static DialogManager manager = new DialogManager();
    }
    // Global excess point
    public static DialogManager getSharedInstance() {
        return dialogManagerholder.manager;
    }
    /* ************************************ */

    private void createAlertDialogs() {

    }
}
