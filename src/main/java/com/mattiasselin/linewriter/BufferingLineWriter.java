package com.mattiasselin.linewriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BufferingLineWriter implements ILineWriter, ILineSource {
	private final IIdGenerator idGenerator;
	private final List<ICommand> bufferedCommands;
	private final String id;
	
	public BufferingLineWriter() {
		this(new IIdGenerator() {
			private int nextId = 0;
			@Override
			public String newId() {
				return String.valueOf(nextId++);
			}
		}, new ArrayList<ICommand>());
	}
	
	private BufferingLineWriter(IIdGenerator idGenerator, List<ICommand> bufferedCommands) {
		this.idGenerator = idGenerator;
		this.bufferedCommands = bufferedCommands;
		this.id = idGenerator.newId();
	}

	@Override
	public void writeTo(ILineWriter lineWriter) {
		ExecutionContext context = new ExecutionContext();
		context.registerImplementation(id, lineWriter);
		for(ICommand bufferedCommand: bufferedCommands) {
			bufferedCommand.execute(context);
		}
	}

	@Override
	public void indent() {
		bufferedCommands.add(new SimpleCommand() {
			@Override
			public void writeTo(ILineWriter lineWriter) {
				lineWriter.indent();
			}
		});
	}

	@Override
	public void unindent() {
		bufferedCommands.add(new SimpleCommand() {
			@Override
			public void writeTo(ILineWriter lineWriter) {
				lineWriter.unindent();	
			}
		});
	}

	@Override
	public ILineWriter indented() {
		BufferingLineWriter indentedWriter = newBufferingLineWriter(idGenerator, bufferedCommands);
		final String indentedId = indentedWriter.id;
		bufferedCommands.add(new ICommand() {
			@Override
			public void execute(IExecutionContext executionContext) {
				ILineWriter lineWriter = executionContext.getImplementation(id);
				executionContext.registerImplementation(indentedId, lineWriter.indented());
			}
		});
		return indentedWriter;
	}
	
	protected BufferingLineWriter newBufferingLineWriter(IIdGenerator idGenerator, List<ICommand> bufferedCommands) {
		return new BufferingLineWriter(idGenerator, bufferedCommands);
	}

	@Override
	public ILineWriter print(final String text) {
		bufferedCommands.add(new SimpleCommand() {
			@Override
			public void writeTo(ILineWriter lineWriter) {
				lineWriter.print(text);	
			}
		});
		return this;
	}

	@Override
	public void println() {
		bufferedCommands.add(new SimpleCommand() {
			@Override
			public void writeTo(ILineWriter lineWriter) {
				lineWriter.println();	
			}
		});
	}

	@Override
	public void println(final String text) {
		bufferedCommands.add(new SimpleCommand() {
			@Override
			public void writeTo(ILineWriter lineWriter) {
				lineWriter.println(text);	
			}
		});
	}
	
	private interface IExecutionContext {
		void registerImplementation(String id, ILineWriter lineWriter);
		ILineWriter getImplementation(String id);
	}
	
	private interface ICommand {
		void execute(IExecutionContext context);
	}
	
	private abstract class SimpleCommand implements ICommand, ILineSource {
		@Override
		public void execute(IExecutionContext context) {
			this.writeTo(context.getImplementation(id));
		}
	}
	
	private static class ExecutionContext implements IExecutionContext {
		private final Map<String, ILineWriter> implementations = new HashMap<>();

		@Override
		public void registerImplementation(String id, ILineWriter lineWriter) {
			if(lineWriter == null) {
				throw new IllegalArgumentException("indented() returned null");
			}
			implementations.put(id, lineWriter);
		}

		@Override
		public ILineWriter getImplementation(String id) {
			ILineWriter lineWriter = implementations.get(id);
			if(lineWriter == null) {
				throw new IllegalArgumentException("No implementation registered for id "+id);
			}
			return lineWriter;
		}
	}
	
	private interface IIdGenerator {
		String newId();
	} 
}
