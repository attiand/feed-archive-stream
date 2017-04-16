package com.github.attiand.archive;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.Element;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.rometools.rome.feed.CopyFrom;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndLink;
import com.rometools.rome.feed.synd.SyndPerson;

public class Entry {

	private final SyndEntry deligate;

	public Entry(SyndEntry deligate) {
		this.deligate = deligate;
	}

	public String getContentsAsString() {
		return getContents().stream().map(c -> c.getValue()).collect(Collectors.joining());
	}

	@Override
	public String toString() {
		return getContentsAsString();
	}

	private static Document getDom(DocumentBuilder builder, String xml) {
		try {
			return builder.parse(new InputSource(new StringReader(xml)));
		} catch (SAXException | IOException e) {
			throw new XmlParseException("Can't create DOM", e);
		}
	}

	public Stream<Document> dom() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			return getContents().stream().map(e -> getDom(builder, e.getValue()));
		} catch (ParserConfigurationException e) {
			throw new XmlParseException("Can't configure DOM factory", e);
		}
	}

	// Delegates

	public String getUri() {
		return deligate.getUri();
	}

	public void copyFrom(CopyFrom obj) {
		deligate.copyFrom(obj);
	}

	public void setUri(String uri) {
		deligate.setUri(uri);
	}

	public String getTitle() {
		return deligate.getTitle();
	}

	public void setTitle(String title) {
		deligate.setTitle(title);
	}

	public SyndContent getTitleEx() {
		return deligate.getTitleEx();
	}

	public void setTitleEx(SyndContent title) {
		deligate.setTitleEx(title);
	}

	public String getLink() {
		return deligate.getLink();
	}

	public void setLink(String link) {
		deligate.setLink(link);
	}

	public List<SyndLink> getLinks() {
		return deligate.getLinks();
	}

	public void setLinks(List<SyndLink> links) {
		deligate.setLinks(links);
	}

	public SyndContent getDescription() {
		return deligate.getDescription();
	}

	public void setDescription(SyndContent description) {
		deligate.setDescription(description);
	}

	public List<SyndContent> getContents() {
		return deligate.getContents();
	}

	public void setContents(List<SyndContent> contents) {
		deligate.setContents(contents);
	}

	public List<SyndEnclosure> getEnclosures() {
		return deligate.getEnclosures();
	}

	public void setEnclosures(List<SyndEnclosure> enclosures) {
		deligate.setEnclosures(enclosures);
	}

	public Date getPublishedDate() {
		return deligate.getPublishedDate();
	}

	public void setPublishedDate(Date publishedDate) {
		deligate.setPublishedDate(publishedDate);
	}

	public Date getUpdatedDate() {
		return deligate.getUpdatedDate();
	}

	public void setUpdatedDate(Date updatedDate) {
		deligate.setUpdatedDate(updatedDate);
	}

	public List<SyndPerson> getAuthors() {
		return deligate.getAuthors();
	}

	public void setAuthors(List<SyndPerson> authors) {
		deligate.setAuthors(authors);
	}

	public String getAuthor() {
		return deligate.getAuthor();
	}

	public void setAuthor(String author) {
		deligate.setAuthor(author);
	}

	public List<SyndPerson> getContributors() {
		return deligate.getContributors();
	}

	public void setContributors(List<SyndPerson> contributors) {
		deligate.setContributors(contributors);
	}

	public List<SyndCategory> getCategories() {
		return deligate.getCategories();
	}

	public void setCategories(List<SyndCategory> categories) {
		deligate.setCategories(categories);
	}

	public SyndFeed getSource() {
		return deligate.getSource();
	}

	public void setSource(SyndFeed source) {
		deligate.setSource(source);
	}

	public Object getWireEntry() {
		return deligate.getWireEntry();
	}

	public Module getModule(String uri) {
		return deligate.getModule(uri);
	}

	public List<Module> getModules() {
		return deligate.getModules();
	}

	public void setModules(List<Module> modules) {
		deligate.setModules(modules);
	}

	public List<Element> getForeignMarkup() {
		return deligate.getForeignMarkup();
	}

	public void setForeignMarkup(List<Element> foreignMarkup) {
		deligate.setForeignMarkup(foreignMarkup);
	}

	public String getComments() {
		return deligate.getComments();
	}

	public void setComments(String comments) {
		deligate.setComments(comments);
	}

	public Object clone() throws CloneNotSupportedException {
		return deligate.clone();
	}

	public SyndLink findRelatedLink(String relation) {
		return deligate.findRelatedLink(relation);
	}
}
