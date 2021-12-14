package demo;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class AtomicCASIntPan {

    int count = 0;

    public AtomicCASIntPan(int count) {
        this.count = count;
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
                    (AtomicCASIntPan.class.getDeclaredField("count"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    public final int getAndAdd(int delta) {
        return unsafe.getAndAddInt(this, valueOffset, delta);
    }


    @Override
    public String toString() {
        return "AtomicCASIntPan{" +
                "count=" + count +
                '}';
    }
}
