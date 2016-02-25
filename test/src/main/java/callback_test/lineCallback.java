package callback_test;

public interface lineCallback<T> {
	T doSomethingWithLine(String line, T value);
}
