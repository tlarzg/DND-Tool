package com.rprescott.dndtool.client.ui.documentfilters;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Filter to be applied to documents to only allow numerics and null.
 */
public class NumericDocumentFilter extends DocumentFilter {

    private String regex;

    /**
     * Creates a document filter that by default, only allows digits (0-9) and null.
     */
    public NumericDocumentFilter() {
        this.regex = "[^0-9]*";
    }

    @Override
    public void insertString(FilterBypass fb, int off, String str, AttributeSet attr) throws BadLocationException {
        // remove non-digits
        if (str != null) {
            fb.insertString(off, str.replaceAll(this.regex, ""), attr);
        }
        else {
            fb.insertString(off, null, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int off, int len, String str, AttributeSet attr) throws BadLocationException {
        // remove non-digits
        if (str != null) {
            fb.replace(off, len, str.replaceAll(this.regex, ""), attr);
        }
        else {
            fb.replace(off, len, null, attr);
        }
    }

}
