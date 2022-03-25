package org.bin.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

/**
 * @author 1x481n
 */
public class CollectionDemo {

    public static void main(String[] args) {
        arrayList();
        System.out.println("---------------");

    }

    /**
     * 动态扩容数组，非线程安全
     */
    public static void arrayList()
    {
        // 动态扩容数组，线程安全,效率低 不推荐使用。
        //Vector<Boolean> vector =new Vector<>();
        //vector.add(true);
        //vector.add(false);
        //System.out.println(vector);
        //System.out.println("---------------");

        ArrayList<String> arrayList = new ArrayList<>();
        // 追加到末尾，不存在扩容时，时间复杂度 O(1)。
        arrayList.add("a1");
        arrayList.add("b1");
        arrayList.add("c1");
        arrayList.add("c1");
        System.out.println(arrayList);
        // 在指定索引位置，插入元素，时间复杂度 O(n)
        // 指定末尾插入
        arrayList.add(arrayList.size(),"d1");
        System.out.println(arrayList);
        // 指定中间插入
        arrayList.add(1,"a2");
        System.out.println(arrayList);
        //直接覆盖
        arrayList.set(2,"b2");
        System.out.println(arrayList);
        //error，索引越界
        //arrayList.set(4,"dd");
        // 移除指定索引的元素 O(n)
        arrayList.remove(1);
        System.out.println(arrayList);
        // 移除与指定元素相同的第一个元素 O(n)
        arrayList.add(3,"c2");
        System.out.println(arrayList);
        arrayList.remove("c1");
        System.out.println(arrayList);

        // 时间复杂度O(1)
        System.out.println(arrayList.get(0));
    }

    /**
     * 双向链表
     */
    public static void linkedList(){
        //LinkedList<Integer> linkedList = new LinkedList<>();
        //linkedList.add(1);
        //linkedList.add(2);
    }

}
