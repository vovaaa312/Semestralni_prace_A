package AbstrLifo;

import AbstrDoubleList.AbstrDoubleList;

public class AbstrLifo<T> implements IAbstLifo<T>{
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
}
