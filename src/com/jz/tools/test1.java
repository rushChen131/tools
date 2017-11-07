package com.jz.tools;

import java.lang.reflect.Field;

//public class ReflectClass3 {

    public class test1{

    /**
     * @param args
     */
    public static void main(String[] args) {
        Person p = new Person(1, "ctl", true, 'c', 2.0f, 2.0, 1L, (short) 1,
                (byte) 1);

        p.setId(0);
        p.setName("张三");
        p.setIsMen(true);
        p.setCh('c');
        p.setFloat_(2.0f);
        p.setDouble_(3.0);
        p.setLong_(2l);
        p.setShort_((short) 1);
        p.setByte_((byte) 2);
        reflect(p);
    }

    public static void reflect(Object obj) {
        if (obj == null)
            return;
        Field[] fields = obj.getClass().getDeclaredFields();
        String[] types1={"int","java.lang.String","boolean","char","float","double","long","short","byte"};
        String[] types2={"Integer","java.lang.String","java.lang.Boolean","java.lang.Character","java.lang.Float","java.lang.Double","java.lang.Long","java.lang.Short","java.lang.Byte"};
        for (int j = 0; j < fields.length; j++) {
            fields[j].setAccessible(true);
            // 字段名
            System.out.print(fields[j].getName() + ":");
            // 字段值
            for(int i=0;i<types1.length;i++){
                if(fields[j].getType().getName()
                        .equalsIgnoreCase(types1[i])|| fields[j].getType().getName().equalsIgnoreCase(types2[i])){
                    try {
                        System.out.print(fields[j].get(obj)+"     ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

 class Person {
    public int id;
    public String name;
    public boolean isMen;
    public Character ch;
    public Float float_;
    public Double double_;
    public Long long_;
    public Short short_;
    public Byte byte_;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean getIsMen() {
        return isMen;
    }
    public void setIsMen(boolean isMen) {
        this.isMen = isMen;
    }
    public Character getCh() {
        return ch;
    }
    public void setCh(Character ch) {
        this.ch = ch;
    }
    public Float getFloat_() {
        return float_;
    }
    public void setFloat_(Float float_) {
        this.float_ = float_;
    }
    public Double getDouble_() {
        return double_;
    }
    public void setDouble_(Double double_) {
        this.double_ = double_;
    }
    public Long getLong_() {
        return long_;
    }
    public void setLong_(Long long_) {
        this.long_ = long_;
    }
    public Short getShort_() {
        return short_;
    }
    public void setShort_(Short short_) {
        this.short_ = short_;
    }
    public Byte getByte_() {
        return byte_;
    }
    public void setByte_(Byte byte_) {
        this.byte_ = byte_;
    }
    public Person(int id, String name, Boolean isMen, Character ch, Float float_,
                  Double double_, Long long_, Short short_, Byte byte_) {
        super();
        this.id = id;
        this.name = name;
        this.isMen = isMen;
        this.ch = ch;
        this.float_ = float_;
        this.double_ = double_;
        this.long_ = long_;
        this.short_ = short_;
        this.byte_ = byte_;
    }

    public Person() {
        super();
    }
}
