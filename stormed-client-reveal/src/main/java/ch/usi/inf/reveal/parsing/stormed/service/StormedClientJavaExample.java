package ch.usi.inf.reveal.parsing.stormed.service;

import static scala.collection.JavaConversions.asJavaCollection;

import java.util.Collection;

import scala.collection.Seq;
import ch.usi.inf.reveal.parsing.model.HASTNode;

public class StormedClientJavaExample {

	public static void main(final String args[]) {
		final String codeToParse = 
				"getLInfNorm() uses wrong formula in both ArrayRealVector and OpenMapRealVector (in different ways)\n"+
				"the L_infinity norm of a finite dimensional vector is just the max of the absolute value of its " +
						"entries.\n" +
						"\n" +
						"The current implementation in ArrayRealVector has a typo:\n" +
						"\n" +
						"{code}\n" +
						"    public double getLInfNorm() {\n" +
						"        double max = 0;\n" +
						"        for (double a : data) {\n" +
						"            max += Math.max(max, Math.abs(a));\n" +
						"        }\n" +
						"        return max;\n" +
						"    }\n" +
						"{code}\n" +
						"\n" +
						"the += should just be an =.\n" +
						"\n" +
						"There is sadly a unit test assuring us that this is the correct behavior (effectively a " +
						"regression-only test, not a test for correctness).\n" +
						"\n" +
						"Worse, the implementation in OpenMapRealVector is not even positive semi-definite:\n" +
						"\n" +
						"{code}   \n" +
						"    public double getLInfNorm() {\n" +
						"        double max = 0;\n" +
						"        Iterator iter = entries.iterator();\n" +
						"        while (iter.hasNext()) {\n" +
						"            iter.advance();\n" +
						"            max += iter.value();\n" +
						"        }\n" +
						"        return max;\n" +
						"    }\n" +
						"{code}\n" +
						"\n" +
						"I would suggest that this method be moved up to the AbstractRealVector superclass and " +
						"implemented using the sparseIterator():\n" +
						"\n" +
						"{code}\n" +
						"  public double getLInfNorm() {\n" +
						"    double norm = 0;\n" +
						"    Iterator&lt;Entry&gt; it = sparseIterator();\n" +
						"    Entry e;\n" +
						"    while(it.hasNext() &amp;&amp; (e = it.next()) != null) {\n" +
						"      norm = Math.max(norm, Math.abs(e.getValue()));\n" +
						"    }\n" +
						"    return norm;\n" +
						"  }\n" +
						"{code}\n" +
						"\n" +
						"Unit tests with negative valued vectors would be helpful to check for this kind of thing in " +
						"the future.";


		final Response response = StormedService.parse(codeToParse);
		
		/*Whenever accessing fields of Scala case classes, 
		 *getters are always in the form variableName.fieldName();
		 */
		switch(response.status()) {
		case "OK":
			final ParsingResponse success = (ParsingResponse) response;
			System.out.println("Status: " + success.status());
			System.out.println("Quota Remaining: " + success.quotaRemaining());
			System.out.println("Parsing Result: ");
			printHASTNodes(success.result());
			break;
		case "ERROR":
			final ErrorResponse error = (ErrorResponse) response;
			System.out.println(error.status() +": " + error.message());
			break;
		}

	}
	
	/* All HASTNode carrying a Scala collection can be easily converted to
	 * a Java collection by using Scala' SDK conversion for java.
	 * For matter of convenience, just statically import asJavaCollection 
	 * as above and use it to convert Seq to Collection as below.
	 */
	private static void printHASTNodes(final Seq<HASTNode> result) {
		final Collection<HASTNode> collection = asJavaCollection(result);
		for(final HASTNode node : collection){
			System.out.println(node.getClass().getSimpleName());
		}
	}
}
