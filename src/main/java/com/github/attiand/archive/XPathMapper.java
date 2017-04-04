package com.github.attiand.archive;

import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import net.sf.saxon.lib.NamespaceConstant;

public class XPathMapper {

	public static Function<Document, ? extends Stream<Element>> xpath(String expression) {
		return document -> xpath(document, expression);
	}

	private static Stream<Element> xpath(Document document, String expression) {
		try {
			System.setProperty("javax.xml.xpath.XPathFactory:" + NamespaceConstant.OBJECT_MODEL_SAXON,
					"net.sf.saxon.xpath.XPathFactoryImpl");
			XPathFactory xPathFactory = XPathFactory.newInstance(NamespaceConstant.OBJECT_MODEL_SAXON);
			XPath xPath = xPathFactory.newXPath();
			XPathExpression xPathExpression = xPath.compile(expression);
			// Object nodes = xPathExpression.evaluate(document,
			// XPathConstants.NODESET);
			// return ((List<Element>) nodes).stream();
			NodeList res = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
			return IntStream.range(0, res.getLength()).mapToObj(i -> (Element) res.item(i));
		} catch (XPathFactoryConfigurationException | XPathExpressionException e) {
			throw new XmlParseException("Can't evaluate Xpath", e);
		}
	}
}
