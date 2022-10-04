package AbstrLifo;

public interface IAbstLifo<T> {
    void zrus();
    boolean jePrazdny();

    void vloz(T data);
    T odeber();
}