package demo;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class AtomicCASObjPan<T> {

    T obj;

    public AtomicCASObjPan() {

    }

    public AtomicCASObjPan(T obj) {
        this.obj = obj;
    }

    //在使用该getUnsafe方法是，会判断classLoader的类型，如果不是systemClassLoader则会抛出SecurityException(“Unsafe”)异常，所以用户编写的程序使用不了unsafe实例。
//    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static Unsafe unsafe = null;


    static long valueOffset;

    static {
        try {
            //
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            //字段不是静态字段的话,要传入反射类的对象.如果传null是会报
            unsafe = (Unsafe) theUnsafe.get(null);
            valueOffset = unsafe.objectFieldOffset
                    (AtomicCASObjPan.class.getDeclaredField("obj"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    public final boolean compareAndSwap(T cmp, T val) {
        return unsafe.compareAndSwapObject(this, valueOffset, cmp, val);
    }

    public final T getObj() {
        return (T) unsafe.getObject(this, valueOffset);
    }

    @Override
    public String toString() {
        return "AtomicCASObjPan{" +
                "obj=" + obj +
                '}';
    }
}
