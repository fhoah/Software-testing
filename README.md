Advanced Password Strength Analyzer & Security Suite

快速说明（Windows, cmd.exe）

1) 编译并跳过测试（快速）：

```
mvn -f "c:\Users\fat\IdeaProjects\final java\pom.xml" -DskipTests package
```

2) 运行主程序（直接从 classpath）：

```
java -cp "c:\Users\fat\IdeaProjects\final java\target\classes" com.example.security.App
```

或运行打包后的 jar：

```
java -cp "c:\Users\fat\IdeaProjects\final java\target\security-suite-1.0-SNAPSHOT.jar" com.example.security.App
```

3) 运行所有测试并生成 JaCoCo/PMD 报告：

```
mvn -f "c:\Users\fat\IdeaProjects\final java\pom.xml" verify
```

4) 报告位置（执行完成后）：
- JaCoCo HTML 报告: target/site/jacoco/index.html
- PMD 报告: target/site/pmd.html

备注
- 使用 Java 17 编译与运行（pom.xml 已设置 source/target/release 为 17）。
- 如果路径包含空白，请在 Windows cmd 中确保使用双引号包裹路径。
- 若遇到 JaCoCo 与 JDK 版本的不兼容问题，可临时跳过 JaCoCo: `-Djacoco.skip=true`。

