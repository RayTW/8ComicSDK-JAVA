package test;

public class Reference<T> {
	private T mObject;

	public Reference() {
	}

	public void set(T obj) {
		mObject = obj;
	}

	public T get() {
		return mObject;
	}
}
