package com.samuelbernard147.consumerapp;

import android.database.Cursor;

interface LoadNotesCallback {
    void postExecute(Cursor notes);
}

