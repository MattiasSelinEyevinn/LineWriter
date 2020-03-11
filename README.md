# LineWriter

A minimalistic library for writing properly indented code using code. 

## Gradle dependency
In `build.gradle`
```gradle
...

repositories {
	maven { url "https://jitpack.io" }
	...
}

...
dependencies {
	...
	implementation 'com.github.MattiasSelinEyevinn:LineWriter:1.0'
}
...
```

### Example `build.gradle` for java project
```gradle
apply plugin: 'java'

repositories {
	maven { url "https://jitpack.io" }
	mavenCentral()
}



dependencies {
	implementation 'com.github.MattiasSelinEyevinn:LineWriter:1.0'
}
```

## Usage example
```java
package linewriterexample;

import com.mattiasselin.linewriter.ILineSource;
import com.mattiasselin.linewriter.ILineWriter;
import com.mattiasselin.linewriter.LineWriter;
import com.mattiasselin.linewriter.SystemOutLineWriter;

public class UsageExample {

	public static void main(String[] args) {
		LineWriter lineWriter = new LineWriter();
		writeCoolClass(lineWriter);
		printToSystemOut(lineWriter);
	}

	private static void writeCoolClass(ILineWriter lineWriter) {
		lineWriter.println("package coolpackage;");
		lineWriter.println();
		lineWriter.println("public class CoolClass {");
			lineWriter.indent();
			lineWriter.println("private int coolInt;");
			lineWriter.println();
			lineWriter.println("public CoolClass(int coolInt) {");
				lineWriter.indent();
				lineWriter.println("this.coolInt = coolInt;");
				lineWriter.unindent();
			lineWriter.println("}");
			lineWriter.unindent();
		lineWriter.println("}");
	}

	private static void printToSystemOut(ILineSource lineSource) {
		lineSource.writeTo(new SystemOutLineWriter());
	}
}

```

produces output:
```
package coolpackage;

public class CoolClass {
    private int coolInt;

    public CoolClass(int coolInt) {
        this.coolInt = coolInt;
    }
}

```