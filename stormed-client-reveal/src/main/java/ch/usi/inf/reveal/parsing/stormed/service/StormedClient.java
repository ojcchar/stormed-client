package ch.usi.inf.reveal.parsing.stormed.service;

import static scala.collection.JavaConversions.asJavaCollection;

import java.util.Collection;

import ch.usi.inf.reveal.parsing.model.java.BlockDeclarationNode;
import ch.usi.inf.reveal.parsing.model.java.JavaASTNode;
import ch.usi.inf.reveal.parsing.stormed.service.*;

import ch.usi.inf.reveal.parsing.model.HASTNode;
import ch.usi.inf.reveal.parsing.model.TextFragmentNode;

public class StormedClient {

	public static Collection<HASTNode> parseText(String textToParse) {

		final Response response = StormedService.parse(textToParse);

		switch(response.status()) {
			case "OK":
				final ParsingResponse success = (ParsingResponse) response;
				System.out.println("Stormed remaining quota: " + success.quotaRemaining());
				return asJavaCollection(success.result());
			case "ERROR":
				final ErrorResponse error = (ErrorResponse) response;
				throw new RuntimeException(error.message());
		}

		return null;
	}

	public static String tagText(String textToTag) {

		final Response response = StormedService.tag(textToTag, false);

		switch(response.status()) {
			case "OK":
				final TaggingResponse success = (TaggingResponse) response;
				System.out.println("Stormed remaining quota: " + success.quotaRemaining());
				return success.result();
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
