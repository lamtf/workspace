package org.lamtf.db.query;

import java.util.List;

public interface IRead<T> {

	T findOne(long Id);

	List<T> find(long pn, long ps);
}
