package com.mattiasselin.linewriter;

public abstract class AbstractLineWriter implements ILineWriter {
	private boolean emptyLine = true;
	private int minIndent;
	private int indent;
	
	protected AbstractLineWriter(int indent) {
		this.minIndent = indent;
		this.indent = indent;
	}

	@Override
	public void indent() {
		indent++;
	}

	@Override
	public void unindent() {
		indent--;
		if(indent < minIndent) {
			indent = minIndent;
		}
	}

	@Override
	public ILineWriter indented() {
		return newIndented(indent+1);
	}
	
	protected abstract ILineWriter newIndented(int indent);

	@Override
	public ILineWriter print(String text) {
		if(emptyLine) {
			doPrintIndent(indent);
			emptyLine = false;
		}
		doPrint(text);
		return this;
	}

	protected abstract void doPrintIndent(int indent);
	
	protected abstract void doPrint(String text);

	protected abstract void doPrintln();

	@Override
	public void println() {
		doPrintln();
		emptyLine = true;
	}


	@Override
	public void println(String text) {
		this.print(text);
		this.println();
	}
	
	protected boolean isEmptyLine() {
		return emptyLine;
	}
}
