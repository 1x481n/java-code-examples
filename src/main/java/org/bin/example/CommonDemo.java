package org.bin.example;

import org.bin.example.utils.CommonUtils;

/**
 * @author 1x481n
 */
public class CommonDemo {
    public static void main(String[] args) {
        System.out.printf("字符串：%s，数字%d\n", "hello", 55);
        System.out.println("a".getClass().getSimpleName());
        System.out.println((CommonUtils.getBaseType('a')));
        System.out.println((CommonUtils.getBaseType(8)));

        System.out.println(0x0f010001);

        //////////////////////////////////////////////////////

        System.out.println(((Integer)1).intValue());
    }
}
