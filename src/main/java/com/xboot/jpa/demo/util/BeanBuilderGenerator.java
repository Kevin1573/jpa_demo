package com.xboot.jpa.demo.util;

import java.util.ArrayList;
import java.util.List;

public class BeanBuilderGenerator {

    public static void main(String[] args) {
        String className = "RestDto";
        List<String> fields = new ArrayList<>();
        fields.add("String code");
        fields.add("String msg");
        fields.add("String data");
        fields.add("String apiUri");
        fields.add("ApiHeaders apiHeaders");
        fields.add("String apiParams");
        fields.add("String apiResult");
        fields.add("Long timeStamp");  // 添加新的字段

        generateBuilder(className, fields);
    }

    public static void generateBuilder(String className, List<String> fields) {
        StringBuilder builderClass = new StringBuilder();

        // 生成类声明
        builderClass.append("import java.io.Serializable;\n\n");
        builderClass.append("public class ").append(className).append(" implements Serializable {\n\n");

        // 生成私有字段
        for (String field : fields) {
            String fieldName = field.substring(field.indexOf(" ") + 1);
            builderClass.append("    private ").append(field).append(";\n");
        }
        builderClass.append("\n");

        // 生成无参构造函数
        builderClass.append("    public ").append(className).append("() {}\n\n");

        // 生成 Builder 类
        builderClass.append("    public static class ").append(className).append("Builder implements Serializable {\n");
        for (String field : fields) {
            String fieldName = field.substring(field.indexOf(" ") + 1);
            builderClass.append("        private ").append(field).append(";\n");
        }
        builderClass.append("\n");

        // 生成 Builder 构造函数
        builderClass.append("        public ").append(className).append("Builder() {}\n\n");

        // 生成 setter 方法
        for (String field : fields) {
            String fieldName = field.substring(field.indexOf(" ") + 1);
            String capitalizedFieldName = capitalize(fieldName);
            builderClass.append("        public ").append(className).append("Builder ").append(capitalizedFieldName.toLowerCase()).append("(final ").append(field).append(" ").append(") {\n");
            builderClass.append("            this.").append(fieldName).append(" = ").append(fieldName).append(";\n");
            builderClass.append("            return this;\n");
            builderClass.append("        }\n\n");
        }

        // 生成 build 方法
        builderClass.append("        public ").append(className).append(" build() {\n");
        builderClass.append("            return new ").append(className).append("(this);\n");
        builderClass.append("        }\n\n");

        // 生成静态工厂方法
        builderClass.append("        public static ").append(className).append("Builder builder() {\n");
        builderClass.append("            return new ").append(className).append("Builder();\n");
        builderClass.append("        }\n");

        builderClass.append("    }\n\n");

        // 生成私有构造函数
        builderClass.append("    private ").append(className).append("(").append(className).append("Builder builder) {\n");
        for (String field : fields) {
            String fieldName = field.substring(field.indexOf(" ") + 1);
            builderClass.append("        this.").append(fieldName).append(" = builder.").append(fieldName).append(";\n");
        }
        builderClass.append("    }\n\n");

        // 生成 getter 方法
        for (String field : fields) {
            String fieldName = field.substring(field.indexOf(" ") + 1);
            String capitalizedFieldName = capitalize(fieldName);
            builderClass.append("    public ").append(field.substring(0, field.indexOf(" "))).append(" get").append(capitalizedFieldName).append("() {\n");
            builderClass.append("        return ").append(fieldName).append(";\n");
            builderClass.append("    }\n\n");
        }

        // 生成 setter 方法
        for (String field : fields) {
            String fieldName = field.substring(field.indexOf(" ") + 1);
            String capitalizedFieldName = capitalize(fieldName);
            builderClass.append("    public void set").append(capitalizedFieldName).append("(").append(field).append(" ").append(") {\n");
            builderClass.append("        this.").append(fieldName).append(" = ").append(fieldName).append(";\n");
            builderClass.append("    }\n\n");
        }

        // 结束类声明
        builderClass.append("}\n");

        // 输出生成的代码
        System.out.println(builderClass.toString());
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
