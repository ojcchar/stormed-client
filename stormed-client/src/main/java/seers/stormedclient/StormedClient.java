package seers.stormedclient;

import static scala.collection.JavaConversions.asJavaCollection;

import java.util.Collection;

import scala.collection.Seq;
import ch.usi.inf.reveal.parsing.model.HASTNode;
import ch.usi.inf.reveal.parsing.model.TextFragmentNode;
import ch.usi.inf.reveal.parsing.stormed.service.ErrorResponse;
import ch.usi.inf.reveal.parsing.stormed.service.Response;
import ch.usi.inf.reveal.parsing.stormed.service.StormedService;
import ch.usi.inf.reveal.parsing.stormed.service.SuccessResponse;

public class StormedClient {

	private static final String SERVICE_KEY = "C3B1C9EFFE8BD169984FF9A19B95EFD01072C574D575C27F50E1FE780F11E75F";
	
	public static Collection<HASTNode> parseText(String codeToParse) {

		final Response response = StormedService
				.parse(codeToParse, SERVICE_KEY);

		switch (response.status()) {
		case "OK":
			final SuccessResponse success = (SuccessResponse) response;
			int quotaRemaining = success.quotaRemaining();
			if (quotaRemaining == 0) {
				System.err.println("Quota Remaining: " + quotaRemaining);
			}
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
