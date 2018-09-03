## Xcelite 1.0.4 compatibility testing lib
Not much to see here, the only reason this exists is to have a gold 
standard for compatiblity tests of new version against the last released
version 1.0.4

### How To Use?
Create a new repository entry in your pom.xml:

```xml
<!-- Only used for pulling in the Xcelite compat lib -->
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Add xcelite compat as a dependency:
```xml
<dependency>
    <groupId>com.github.iSnow</groupId>
    <artifactId>Xcelite-compatability-1.0.4</artifactId>
    <version>1.0.4</version>
    <scope>test</scope>
</dependency>
```
