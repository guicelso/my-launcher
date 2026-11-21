package com.dofun.carsetting.f;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/* JADX INFO: compiled from: ReflectUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class l {
    public static void a(Object obj, String str, Object obj2) {
        Field fieldA = a(obj, str);
        if (fieldA == null) {
            throw new IllegalArgumentException("Could not find field [" + str + "] on target [" + obj + "]");
        }
        try {
            fieldA.set(obj, obj2);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static Field a(Object obj, String str) {
        if (obj == null) {
            throw new IllegalArgumentException("object can't be null");
        }
        if (str == null || str.length() <= 0) {
            throw new IllegalArgumentException("fieldName can't be blank");
        }
        for (Class<?> superclass = obj.getClass(); superclass != Object.class; superclass = superclass.getSuperclass()) {
            try {
                Field declaredField = superclass.getDeclaredField(str);
                a(declaredField);
                return declaredField;
            } catch (NoSuchFieldException e) {
                g.a().a(e.getMessage());
            }
        }
        return null;
    }

    private static void a(Field field) {
        if ((Modifier.isPublic(field.getModifiers()) && Modifier.isPublic(field.getDeclaringClass().getModifiers()) && !Modifier.isFinal(field.getModifiers())) || field.isAccessible()) {
            return;
        }
        field.setAccessible(true);
    }
}
