package com.xboot.jpa.demo.asm;

import com.sun.istack.NotNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.lang.reflect.Method;

/**
 * 注释
 *
 * @author xboot
 **/
@Slf4j
public class ClassWriter2Test extends ClassLoader implements Opcodes {

    @SneakyThrows
    public static void main(String[] args) {
        ClassWriter cwt = new ClassWriter(0);
        // class
        cwt.visit(V1_6, ACC_PUBLIC, "Person",  null, "java/lang/Object", null);

        // generate String field name
        FieldVisitor nameFieldVisitor = cwt.visitField(ACC_PRIVATE, "name", "Ljava/lang/String;", null, "defaultValue");
        nameFieldVisitor.visitEnd();

        // constructor
        // 生成默认的构造方法
        MethodVisitor mw = cwt.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mw.visitMaxs(1, 1);
        // 生成构造方法的字节码指令
        mw.visitVarInsn(ALOAD, 0);
        mw.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mw.visitInsn(RETURN);
        mw.visitEnd();



        // 生成get方法
        mw = cwt.visitMethod(ACC_PUBLIC , "getName", "()Ljava/lang/String;", null, null);
        mw.visitMaxs(1,1);
        mw.visitVarInsn(ALOAD, 0);
        mw.visitFieldInsn(GETFIELD, "Person", "name", "Ljava/lang/String;");
//        mw.visitLineNumber(9 , new Label());
        // generate setter method
        mw = cwt.visitMethod(ACC_PUBLIC , "setName", "(Ljava/lang/String;)V", null, null);
        mw.visitMaxs(2,2);
        mw.visitVarInsn(ALOAD, 0);
        mw.visitVarInsn(ALOAD, 1);
        mw.visitFieldInsn(PUTFIELD, "Person", "name", "Ljava/lang/String;");
        mw.visitInsn(RETURN);
//        mw.visitLineNumber(10, new Label());

        // 字节码生成完成
        mw.visitEnd();

        byte[] code = cwt.toByteArray();

        // 将二进制流写入到文件中
        FileOutputStream fileOutputStream = new FileOutputStream("Person.class");
        fileOutputStream.write(code);
        fileOutputStream.close();


        ClassWriter2Test test = new ClassWriter2Test();
        Class<?> clazz = test.defineClass("Person", code, 0, code.length);
        Object newInstance = clazz.getConstructor().newInstance();
        clazz.getField("name").set(newInstance, "Hello world!");

//        String annoValue = clazz.getField("name").getAnnotations()[0].annotationType().getName();
//        System.out.println("anno value = " + annoValue);

        Method setName = clazz.getMethod("setName", String.class);
        setName.invoke(newInstance, "Hello world!--setName");

        String name = (String)clazz.getField("name").get(newInstance);
        System.out.println("field name value = " + name);
    }
}
