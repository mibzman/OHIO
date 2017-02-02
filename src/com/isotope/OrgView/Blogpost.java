package com.isotope.OrgView;

/**
 * Created by Sam on 11/14/2015.
 */
public class Blogpost {
    private String author;
    private String title;
    public Blogpost() {
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }
    public String getAuthor() {
        return author;
    }
    public String getTitle() {
        return title;
    }
}
