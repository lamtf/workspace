package com.uisleandro.util;

import android.database.Cursor;
import android.support.v4.content.Loader;

public interface LoaderInterface {

   void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor);

}

