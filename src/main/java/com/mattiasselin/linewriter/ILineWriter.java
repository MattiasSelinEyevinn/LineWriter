package com.mattiasselin.linewriter;

public interface ILineWriter {
	void indent();
	void unindent();
	ILineWriter indented();
    ILineWriter print(String text);
	void println();
    void println(String text);
}
