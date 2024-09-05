package com.xboot.jpa.demo.asm;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;

/**
 * 注释
 *
 * @author xboot
 **/
@Slf4j
public class ClassWriterTest extends ClassLoader implements Opcodes {

    @SneakyThrows
    public static void main(String[] args) {
        ClassWriter cwt = new ClassWriter(0);
        cwt.visit(V1_1, ACC_PUBLIC, "Example",  null, "java/lang/Object", null);
        cwt.visitField(ACC_PRIVATE + ACC_STATIC + ACC_FINAL, "name", "Ljava/lang/String;", null, "XBOOT");
//        cwt.visit
        // 生成默认的构造方法
        MethodVisitor mw = cwt.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        // 生成构造方法的字节码指令
        mw.visitVarInsn(ALOAD, 0);
        mw.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mw.visitInsn(RETURN);
        mw.visitMaxs(1, 1);
        mw.visitEnd();

        // 生成main方法
        mw = cwt.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);

        mw.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mw.visitLdcInsn("Hello world!");
        mw.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mw.visitInsn(RETURN);
        mw.visitMaxs(2, 2);

        // 字节码生成完成
        mw.visitEnd();

        byte[] code = cwt.toByteArray();

        // 将二进制流写入到文件中
        FileOutputStream fileOutputStream = new FileOutputStream("Example.class");
        fileOutputStream.write(code);
        fileOutputStream.close();


        ClassWriterTest test = new ClassWriterTest();
        Class<?> clazz = test.defineClass("Example", code, 0, code.length);
        clazz.getMethod("main", String[].class).invoke(null, (Object) null);
    }
}
