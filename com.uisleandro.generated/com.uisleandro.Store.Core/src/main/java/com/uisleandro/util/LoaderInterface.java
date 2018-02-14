package com.uisleandro.util;

import android.database.Cursor;

public interface LoaderInterface{
	void onLoadFinished(int id, Cursor cursor);
}