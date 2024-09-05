package com.xboot.jpa.demo.asm;

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
public class ClassWriter3Test extends ClassLoader implements Opcodes {

    @SneakyThrows
    public static void main(String[] args) {
        ClassWriter cwt = new ClassWriter(0);
        // class
        cwt.visit(V18, ACC_PUBLIC, "com/xboot/jpa/demo/asm/sourcecode/Math2",  null, "java/lang/Object", null);

        // generate String field name
        FieldVisitor nameFieldVisitor = cwt.visitField(ACC_PRIVATE, "num1", "Ljava/lang/Integer;", null, null);
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

        // generate add method eg: public int add(int a, int b) { return a + b + num1; }
        mw = cwt.visitMethod(ACC_PUBLIC, "add", "(II)I", null, null);
        mw.visitCode();
        Label l0 = new Label();
        mw.visitLineNumber(10, l0);
        mw.visitVarInsn(ILOAD, 1);
        mw.visitVarInsn(ILOAD, 2);
        mw.visitInsn(IADD);
        mw.visitVarInsn(ALOAD, 0);
        mw.visitFieldInsn(GETFIELD, "com/xboot/jpa/demo/asm/sourcecode/Math2", "num1", "Ljava/lang/Integer;");
        mw.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
        mw.visitInsn(IADD);
        mw.visitInsn(IRETURN);

        Label l1 = new Label();
        mw.visitLabel(l1);
        mw.visitLocalVariable("this", "Lcom/xboot/jpa/demo/asm/sourcecode/Math2;", null, l0, l1, 0);
        mw.visitLocalVariable("a", "I", null, l0, l1, 1);
        mw.visitLocalVariable("b", "I", null, l0, l1, 2);
        mw.visitMaxs(2, 3);

        // setNum1 method
        mw = cwt.visitMethod(ACC_PUBLIC, "setNum1", "(Ljava/lang/Integer;)V", null, null);
        mw.visitCode();
        l0 = new Label();
        mw.visitLineNumber(14, l0);
        mw.visitVarInsn(ALOAD, 0);
        mw.visitVarInsn(ALOAD, 1);
        mw.visitFieldInsn(PUTFIELD, "com/xboot/jpa/demo/asm/sourcecode/Math2", "num1", "Ljava/lang/Integer;");
        l1 = new Label();
        mw.visitLineNumber(15, l1);
        mw.visitInsn(RETURN);

        Label l2 = new Label();
        mw.visitLocalVariable("this", "Lcom/xboot/jpa/demo/asm/sourcecode/Math2;", null, l0, l2, 0);
        mw.visitLocalVariable("num1", "Ljava/lang/Integer;", null, l0, l2, 1);
        mw.visitMaxs(2, 2);

        // 字节码生成完成
        mw.visitEnd();

        byte[] code = cwt.toByteArray();

        // 将二进制流写入到文件中
        FileOutputStream fileOutputStream = new FileOutputStream("Math2.class");
        fileOutputStream.write(code);
        fileOutputStream.close();
//
//
//        ClassWriter3Test test = new ClassWriter3Test();
//        Class<?> clazz = test.defineClass("Person", code, 0, code.length);
//        Object newInstance = clazz.getConstructor().newInstance();
//        clazz.getField("name").set(newInstance, "Hello world!");
//
////        String annoValue = clazz.getField("name").getAnnotations()[0].annotationType().getName();
////        System.out.println("anno value = " + annoValue);
//
//        Method setName = clazz.getMethod("setName", String.class);
//        setName.invoke(newInstance, "Hello world!--setName");
//
//        String name = (String)clazz.getField("name").get(newInstance);
//        System.out.println("field name value = " + name);
    }
}
