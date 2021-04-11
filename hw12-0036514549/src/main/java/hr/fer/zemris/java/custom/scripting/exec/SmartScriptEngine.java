package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents an engine that is capable of interpreting scripts written
 * in {@code SmartScript}. It can execute document whose parsed tree it obtains
 * from {@link SmartScriptParser}.
 * 
 * @author Matija Frandolić
 */
public class SmartScriptEngine {
	
	/**
	 * Document node of the parsed script.
	 */
	private DocumentNode documentNode;
	
	/**
	 * Request context to which the output is written.
	 */
	private RequestContext requestContext;
	
	/**
	 * Multistack which is used for implementation.
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/**
	 * Visitor that is used to visit all the nodes of the parsed document.
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		/**
		 * Map of actions for each function.
		 */
		Map<String, Consumer<Deque<ValueWrapper>>> functionActions = new HashMap<>();
		
		{
			functionActions.put("sin", stack -> {
				double x = ((Number) stack.pop().getValue()).doubleValue();
				stack.push(new ValueWrapper(Math.sin(x * Math.PI / 180)));
			});
			
			functionActions.put("decfmt", stack -> {
				String formatString = (String) stack.pop().getValue();
				Object x = stack.pop().getValue();
				DecimalFormat formatter = new DecimalFormat(formatString);
				stack.push(new ValueWrapper(formatter.format(x)));
			});
			
			functionActions.put("dup", stack -> {
				Object x = stack.peek().getValue();
				stack.push(new ValueWrapper(x));
			});
			
			functionActions.put("swap", stack -> {
				Object a = stack.pop().getValue();
				Object b = stack.pop().getValue();
				stack.push(new ValueWrapper(a));
				stack.push(new ValueWrapper(b));
			});
			
			functionActions.put("setMimeType", stack -> {
				requestContext.setMimeType((String) stack.pop().getValue());
			});
			
			functionActions.put("paramGet", stack -> {
				Object defaultValue = stack.pop().getValue();
				String name = (String) stack.pop().getValue();
				String value = requestContext.getParameter(name);
				stack.push(new ValueWrapper(value == null ? defaultValue : value));
			});
			
			functionActions.put("pparamGet", stack -> {
				Object defaultValue = stack.pop().getValue();
				String name = (String) stack.pop().getValue();
				String value = requestContext.getPersistentParameter(name);
				stack.push(new ValueWrapper(value == null ? defaultValue : value));
			});
			
			functionActions.put("pparamSet", stack -> {
				String name = (String) stack.pop().getValue();
				Object value = stack.pop().getValue();
				requestContext.setPersistentParameter(name, value.toString());
			});
			
			functionActions.put("pparamDel", stack -> {
				String name = (String) stack.pop().getValue();
				requestContext.removePersistentParameter(name);
			});
			
			functionActions.put("tparamGet", stack -> {
				Object defaultValue = stack.pop().getValue();
				String name = (String) stack.pop().getValue();
				String value = requestContext.getTemporaryParameter(name);
				stack.push(new ValueWrapper(value == null ? defaultValue : value));
			});
			
			functionActions.put("tparamSet", stack -> {
				String name = (String) stack.pop().getValue();
				Object value = stack.pop().getValue();
				requestContext.setTemporaryParameter(name, value.toString());
			});
			
			functionActions.put("tparamDel", stack -> {
				String name = (String) stack.pop().getValue();
				requestContext.removeTemporaryParameter(name);
			});
		}
		
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String key = node.getVariable().getName();
			
			ValueWrapper currentValue = new ValueWrapper(node.getStartExpression().asText());
			ValueWrapper endValue = new ValueWrapper(node.getEndExpression().asText());
			ValueWrapper incrementValue = new ValueWrapper(1);
			
			if (node.getStepExpression() != null) {
				incrementValue.setValue(node.getStepExpression().asText());
			}
			
			multistack.push(key, currentValue);
			
			while (currentValue.numCompare(endValue.getValue()) <= 0) {
				for (int i = 0, last = node.numberOfChildren(); i < last; i++) {
					node.getChild(i).accept(this);
				}
				currentValue.add(incrementValue.getValue());
			}
			
			multistack.pop(key);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Deque<ValueWrapper> stack = new ArrayDeque<>();
			
			for (Element e : node.getElements()) {
				if (e instanceof ElementConstantDouble) {
					double d = ((ElementConstantDouble) e).getValue();
					stack.push(new ValueWrapper(d));
					continue;
				} 
				if (e instanceof ElementConstantInteger) {
					int i = ((ElementConstantInteger) e).getValue();
					stack.push(new ValueWrapper(i));
					continue;
				} 
				if (e instanceof ElementString) {
					String s = ((ElementString) e).getValue();
					stack.push(new ValueWrapper(s));
					continue;
				} 
				if (e instanceof ElementVariable) {
					String variableName = ((ElementVariable) e).getName();
					ValueWrapper variableValue = multistack.peek(variableName);
					stack.push(variableValue);
					continue;
				} 
				if (e instanceof ElementFunction) {
					String functionName = ((ElementFunction) e).getName();
					functionActions.get(functionName).accept(stack);
					continue;
				} 
				if (e instanceof ElementOperator) {
					ValueWrapper operand2 = stack.pop();
					ValueWrapper operand1 = stack.pop();
					ValueWrapper result = new ValueWrapper(operand1.getValue());
					
					String operator = ((ElementOperator) e).getSymbol();
					switch (operator) {
					case "+":
						result.add(operand2.getValue());
						break;
					case "-":
						result.subtract(operand2.getValue());
						break;
					case "*":
						result.multiply(operand2.getValue());
						break;
					case "/":
						result.divide(operand2.getValue());
						break;
					}
					
					stack.push(result);
					continue;
				}
			}
			
			while (!stack.isEmpty()) {
				try {
					requestContext.write(stack.pollLast().getValue().toString());
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0, last = node.numberOfChildren(); i < last; i++) {
				node.getChild(i).accept(this);
			}
		}
		
	};
	
	/**
	 * Constructs new {@code SmartScriptEngine} from the given document node
	 * of the parsed document and request context to which the output is written.
	 * 
	 * @param documentNode   document node of the parsed document
	 * @param requestContext request context to which the output is written
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}
	
	/**
	 * Executes the script. Tries to interpret the document node by node and throws
	 * {@link RuntimeException} if syntax is invalid or if script format is invalid.
	 * 
	 * @throws RuntimeException if syntax is invalid or if script format is invalid
	 */
	public void execute() {
		try {
			documentNode.accept(visitor);			
		} catch (UncheckedIOException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(
				"Script execution error: invalid syntax or script format."
			);
		}
	}
	
}
