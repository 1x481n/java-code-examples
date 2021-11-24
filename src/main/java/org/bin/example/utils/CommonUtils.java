package org.bin.example.utils;

/**
 * @author 1x481n
 */
public class CommonUtils {

    /**
     * 换行符
     */
    public final static String NEW_LINE = System.getProperty("line.separator");


    /**
     * 判断基本类型
     *
     * @param o
     * @return
     */
    public static String getBaseType(Object o) {
        String type = o.getClass().getSimpleName();
        if ("Integer".equals(type)) {
            return String.valueOf(Integer.TYPE);
        } else if ("Long".equals(type)) {
            return String.valueOf(Long.TYPE);
        } else if ("Float".equals(type)) {
            return String.valueOf(Float.TYPE);
        } else if ("Double".equals(type)) {
            return String.valueOf(Double.TYPE);
        } else if ("Short".equals(type)) {
            return String.valueOf(Short.TYPE);
        } else if ("Byte".equals(type)) {
            return String.valueOf(Byte.TYPE);
        } else if ("Character".equals(type)) {
            return String.valueOf(Character.TYPE);
        } else if ("Boolean".equals(type)) {
            return String.valueOf(Boolean.TYPE);
        }
        return "非基本数据类型！";
    }
}
