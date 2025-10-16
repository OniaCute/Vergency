package cc.vergency.utils.others;

public class Pair<T> {
    private T a;
    private T b;

    public Pair(T a, T b) {
        this.a = a;
        this.b = b;
    }

    public void setA(T a) {
        this.a = a;
    }

    public void setB(T b) {
        this.b = b;
    }

    public T getA() {
        return a;
    }

    public T getB() {
        return b;
    }

    @SuppressWarnings("unchecked")
    public T[] getAsArray(Class<T> clazz) {
        T[] array = (T[]) java.lang.reflect.Array.newInstance(clazz, 2);
        array[0] = a;
        array[1] = b;
        return array;
    }
}
