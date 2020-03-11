package com.mattiasselin.linewriter;

import java.util.ArrayList;
import java.util.List;

public class LineWriter extends AbstractLineWriter implements ILineWriter, ILineSource {
	private List<String> lines = new ArrayList<>();
	protected StringBuilder currentLine = new StringBuilder();
	
	public LineWriter() {
		this(0);
	}

	public LineWriter(int indent) {
		super(indent);
	}
	
	@Override
	protected LineWriter newIndented(int indent) {
		return new LineWriter(indent);
	}
	
	@Override
	protected void doPrintIndent(int indent) {
		currentLine.setLength(0);
		for(int i = 0; i < indent; ++i) {
			currentLine.append(getIndention());
		}
	}
	
	@Override
	protected void doPrint(String text) {
		currentLine.append(text);
	}
	
	@Override
	protected void doPrintln() {
		lines.add(currentLine.toString());
		currentLine.setLength(0);
	}


	protected String getIndention() {
		return "    ";
	}

	@Override
	public void writeTo(ILineWriter lineWriter) {
		for(String line : lines) {
            lineWriter.println(line);
		}
		if(!isEmptyLine()) {
            lineWriter.print(currentLine.toString());
		}
	}
}
