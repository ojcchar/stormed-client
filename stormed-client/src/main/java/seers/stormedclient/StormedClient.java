package seers.stormedclient;

import static scala.collection.JavaConversions.asJavaCollection;

import java.util.Collection;

import ch.usi.inf.reveal.parsing.stormed.service.*;

import ch.usi.inf.reveal.parsing.model.HASTNode;
import ch.usi.inf.reveal.parsing.model.TextFragmentNode;

public class StormedClient {

	private static final String SERVICE_KEY = "4F8D61679CC7928DC11130CEC5F46ECA6C51E3CCAA0C3EAD1EB20699E09ACFBE";

	public static Collection<HASTNode> parseText(String codeToParse) {

		final Response response = StormedService.parse(codeToParse, SERVICE_KEY);

		switch (response.status()) {
		case "OK":
			final ParsingResponse success = (ParsingResponse) response;
			int quotaRemaining = success.quotaRemaining();
			System.out.println("Quota Remaining: " + quotaRemaining);
			return asJavaCollection(success.result());
		case "ERROR":
			final ErrorResponse error = (ErrorResponse) response;
			throw new RuntimeException(error.message());

		}

		return null;
	}

	public static String isolateText(String codeToParse) {

		StringBuffer buffer = new StringBuffer();
		final Collection<HASTNode> collection = parseText(codeToParse);
		for (final HASTNode node : collection) {
			if (node instanceof TextFragmentNode) {
				TextFragmentNode txtF = (TextFragmentNode) node;
				buffer.append(txtF.text());
			}
		}
		return buffer.toString();
	}

}
