package com.mattiasselin.linewriter;

import java.io.IOException;
import java.io.Writer;

public class WriterLineWriter extends AbstractLineWriter implements ILineWriter {
    private Writer writer;
    private boolean autoFlush;

	public WriterLineWriter(Writer writer, boolean autoFlush) {
		this(0, writer, autoFlush);
	}

	public WriterLineWriter(int indent, Writer writer, boolean autoFlush) {
		super(indent);
		this.writer = writer;
		this.autoFlush = autoFlush;
	}

	@Override
	protected WriterLineWriter newIndented(int indent) {
		return new WriterLineWriter(indent, writer, autoFlush);
	}

	@Override
	protected void doPrintIndent(int indent) {
        for(int i = 0; i < indent; ++i) {
            doPrint(getIndention());
        }
	}

	protected String getIndention() {
		return "    ";
	}

	protected String getLinebreak() {
		return "\n";
	}

	@Override
	protected void doPrint(String text) {
		try {
			writer.write(text);
			if(autoFlush) {
			    writer.flush();
            }
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void doPrintln() {
		doPrint(getLinebreak());
	}

	public void flush() throws IOException {
		writer.flush();
	}
}
