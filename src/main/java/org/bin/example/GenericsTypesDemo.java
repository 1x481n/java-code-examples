package org.bin.example;

import java.util.ArrayList;
import java.util.List;

import org.bin.example.utils.CommonUtils;

/**
 * 泛型示例
 *
 * @author 1x481n
 */
public class GenericsTypesDemo {

    /**
     * PRINTER 打印者常量
     */
    private final static Printer PRINTER = new Printer();

    public static void main(String[] args) {

        System.out.println("----------------------------------- GenericsTypesDemo -----------------------------------");
        //printByConcreteElement();
        //demo2();
        demo3();
        //demo4();
        //demo5();

        //Print<Object> print = new PrintObj();
        //PrintObj<Object> print1 = new PrintObj<>();
        //print1.printObj();

    }

    /**
     * 以指定的具体类型元素实例化泛型类
     */
    private static void printByConcreteElement() {
        System.out.println("------------------ printByConcreteElement begin ------------------");

        ArrayList<Man> personList = new ArrayList<>();
        personList.add(new Boy("小明"));
        personList.add(new Man("张三"));
        Man man = personList.get(0);
        Person person = personList.get(0);
        Boy boy = (Boy) personList.get(0);
        Man man1 = personList.get(1);

        //不能编译通过：对象虽然是Boy的实例。但是以Man类接收的引用只能指向Man自身的方法。
        /* System.out.println(man.isBoy()); */
        System.out.println(man);
        System.out.println(person);
        System.out.println(boy);
        System.out.println(boy.isBoy());
        System.out.println(man1);

        System.out.println("------------------ printByConcreteElement end ------------------");
    }

    private static void demo2() {
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");

        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);

        PRINTER.printArrayList(list1, "3");
        PRINTER.printArrayList(list2, 3);
    }

    private static void demo3() {
        //下界是Man,通配符只能匹配到Man和其【基｜父】类，Man已知，父类未知，但不管使用Man还是其父类的引用，都可传入已知的子类对象Man和Boy。
        ArrayList<? super Man> personList = new ArrayList<Person>();
        // null 可添加
        personList.add(null);
        personList.add(null);
        // Boy是Man的子类，可添加
        personList.add(new Boy("John"));
        // Man自身类可以添加
        personList.add(new Man("张三"));

        // Person非Man的子类，不能添加
        // personList.add(new org.example.Person("人"));

        //只能用Object接收
        Object obj = personList.get(2);
        Boy boy = (Boy) personList.get(2);
        System.out.println(boy.getName());

        System.out.println(personList);
    }

    private static void demo4() {
        //上界是Man，可能是Man或其子类。无法确定在添加时使用任何一个类作为引用，包含所有子类的对象。但在获取的时候可以使用Man类接收
        ArrayList<Man> list0 = new ArrayList<>();
        list0.add(new Man("111"));
        list0.add(new Boy("1212"));
        //ArrayList<? extends org.example.Man> personList2 = new ArrayList<>();
        ArrayList<? extends Man> personList2 = list0;
        //通配符?可能是比Boy还要小的子类，所以无法添加Boy对象
        //personList2.add(new org.example.Boy("boy"));
        personList2.add(null);
        //personList2.add(new org.example.Man("333"));
        // 无论多小的子类对象，都可以使用上界类Man作为引用来获取
        Man item = personList2.get(0);
        Man item1 = personList2.get(1);
        Man item2 = personList2.get(2);
        System.out.println(personList2);
        System.out.println(item);
        System.out.println(item1);
        System.out.println(item2);
        //也可以
        Object item3 = personList2.get(0);
    }

    private static void demo5() {
        ArrayList<Person> personList2 = new ArrayList<>();
        Person person = new Person("人");
        Man man = new Man("男人");
        Woman woman = new Woman("女人");
        Boy boy = new Boy("男孩");

        personList2.add(person);
        personList2.add(man);
        personList2.add(woman);
        PRINTER.printPersonList(personList2);
        PRINTER.printPersonList(personList2, boy);

        List<Person> list = new ArrayList<>();
        List<Person>[] arr = null;
        //PRINTER.printPersonList2(list, new org.example.Man("Jim"));
        //
        //for (org.example.Person element : personList) {
        //    System.out.println(element.getName());
        //}
        //
        //PRINTER.printPersonList2();
    }

}


class Printer {

    public <T extends ArrayList<E>, E> void printArrayList(T arrayList, E input) {
        System.out.println("input：" + input + "，type：" + input.getClass().toString());
        //System.out.println();
        int index = 0;
        for (E element : arrayList) {
            System.out.printf("arrayList[%d]=%s", index, element);
            System.out.println();
            System.out.println("String equals：" + "2".equals(element));
            System.out.println("Object equals：" + element.equals("2"));

            // 先进行类型判断，确保转换安全
            if (element instanceof String) {
                System.out.println("String contentEquals：" + ((String) element).contentEquals("2"));
            }

            if (element instanceof Integer) {
                System.out.println("Integer compareTo：" + ((Integer) element).equals(2));

                //自动拆箱
                System.out.println((Integer) element == 2);
            }
            index++;
            //System.out.println();
        }
        //System.out.print(CommonUtils.NEW_LINE + CommonUtils.NEW_LINE + CommonUtils.NEW_LINE);
        System.out.print(CommonUtils.lines(3));
    }

    public <T extends List<E>, E> void printPersonList(T list) {
        for (E element : list) {
            System.out.println(element);
        }
    }

    public <T extends List<? super E>, E extends Man> void printPersonList(T list, E e) {
        list.add(e);
        System.out.println(list);
        System.out.println(list.get(3));
        //for (org.example.Man element : list) {
        //    System.out.println(element.getName());
        //}
    }

    public static <T> void printPersonList2(Print<? extends Boy> print) {

    }

}


class Person {
    protected String name;

    public Person() {
        System.out.println("111");
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Man extends Person {
    public Man(String name) {
        super(name);
    }
}

class Woman extends Person {

    public Woman(String name) {
        super(name);
    }

    @Override
    public String getName() {
        return name;
    }
}

class Boy extends Man {
    public Boy(String name) {
        super(name);
    }

    public boolean isBoy() {
        return true;
    }
}


class A<T> {
    public A() {
    }
}


/**
 * 自定义泛型类
 *
 * @param <T>
 */
class Print<T> extends A<T> {
    //public Print() {
    //}

    /**
     * 指定有参的构造方法，想要被子类自动继承，必须指定一个无参的构造方法
     */
    public Print(T t) {
        System.out.println(t);
    }

    public Print(String string) {
        System.out.println(string);
    }

    public Print(Integer integer) {
        System.out.println(integer);
    }

    public Print(String string, Integer integer) {
        System.out.println(integer);
    }

    public Print(Integer integer, String string) {
        System.out.println(integer);
    }

    // 形参不同，但的类型的顺序相同的方法签名，不符合重载规则
    //public Print(Integer integer1, String string1) {
    //    System.out.println(integer1);
    //}

    // 泛型类型擦除为object，不可重载
    //public Print(Object object) {
    //    System.out.println(object);
    //}

    // 泛型会类型擦除，不可重载
    //public <E> Print(E e){
    //    System.out.println(e);
    //}

    /**
     * 自定义泛型方法,同时使用泛型类上下文的泛型占位符
     *
     * @param t    泛型形参
     * @param <T1> 泛型占位符
     * @return 以调用传入的具体类型作为引用返回的参数，外部变量需以传入的具体类型接收
     */
    public <T1> T1 print(T t, T1 t1) {
        System.out.println(t);
        return t1;
    }
}

class PrintObj<T> extends Print<Object> {

    /**
     * 构造方法：如果父类没有无参构造方法，必须在显示在自己的构造方法中以super(i...)方式显示创建一个构造方法
     */
    public PrintObj() {
        // 只能继承一个父类的构造方法放在首行
        super(1);
        //super("1");
        //super(1, "1");
        //super("1", 1);
    }

    /**
     * 普通方法
     */
    public void printObj() {
        System.out.println("666");
    }
}


class PrintWoman extends Print<Woman> {
    /**
     * 父类不存在无参构造方法，至少需要实现父类的一个有参构造方法
     *
     * @param woman woman对象
     */
    public PrintWoman(Woman woman) {
        super(woman);
    }
}

//class PrintMan extends Print<Man> {
//
//}

class PrintBoy<Boy> extends PrintObj {

    //父类已显示指定了无参构造方法，可以作为默认构造方法，被子类顺利继承，无需再显示指定。

}








