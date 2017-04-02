package com.github.attiand.archive;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.jdom2.Element;

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

	// deligates

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
