package com.mattiasselin.linewriter;

public class SystemOutLineWriter extends AbstractLineWriter implements ILineWriter {
	
	public SystemOutLineWriter() {
		super(0);
	}

	public SystemOutLineWriter(int indent) {
		super(indent);
	}

	@Override
	protected SystemOutLineWriter newIndented(int indent) {
		return new SystemOutLineWriter(indent);
	}

	@Override
	protected void doPrintIndent(int indent) {
		for(int i = 0; i < indent; ++i) {
			System.out.print("  ");
		}
	}

	@Override
	protected void doPrint(String text) {
		System.out.print(text);
	}

	@Override
	protected void doPrintln() {
		System.out.println();
	}
}
