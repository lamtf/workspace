package org.lamtf.db;

import android.content.ContentProvider;

public interface IContentDataSource<T,U> extends IDataSource<T,U> {
	void setContentProvider(ContentProvider provider);
}
