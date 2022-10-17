package AbstrLifo;

import AbstrDoubleList.AbstrDoubleList;

import java.util.Iterator;

public class AbstrLifo<T> implements IAbstrLifo<T> {
    AbstrDoubleList<T>abstrDoubleList = new AbstrDoubleList<T>();

    @Override
    public void zrus() {
    abstrDoubleList.zrus();
    }

    @Override
    public boolean jePrazdny() {
        return abstrDoubleList.jePrazdny();
    }

    @Override
    public void vloz(T data) {
    abstrDoubleList.vlozPosledni(data);
    }

    @Override
    public T odeber() {
        return abstrDoubleList.odeberPrvni();
    }

    @Override
    public Iterator iterator() {
        return abstrDoubleList.iterator();
    }
}
