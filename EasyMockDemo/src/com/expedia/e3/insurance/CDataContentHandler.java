package com.expedia.e3.insurance;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.apache.xerces.dom.DOMMessageFormatter;
import org.apache.xml.serialize.ElementState;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;

@SuppressWarnings("deprecation")
public class CDataContentHandler extends XMLSerializer {
    // see http://www.w3.org/TR/xml/#syntax
    private final Pattern XML_CHARS = Pattern.compile("[<>&]");

    public CDataContentHandler(OutputFormat format) {
        super(format);
    }

    public void setPrintNamespacePrefixes(boolean printNamespaces) {
        fNamespacePrefixes = printNamespaces;
        _prefixes = null;
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        boolean useCData = XML_CHARS.matcher(new String(ch, start, length))
                .find();
        if (useCData)
            super.startCDATA();
        super.characters(ch, start, length);
        if (useCData)
            super.endCDATA();
    }

    @SuppressWarnings("PMD.NPathComplexity")
    public void startElement(String namespaceURI, String localName,
                             String rawName, Attributes attrs) throws SAXException {
        boolean preserveSpace;
        ElementState state;
        String name;

        try {
            if (_printer == null) {
                String msg = DOMMessageFormatter.formatMessage(
                        DOMMessageFormatter.SERIALIZER_DOMAIN,
                        "NoWriterSupplied", null);
                throw new IllegalStateException(msg);
            }

            state = getElementState();
            if (isDocumentState()) {
                // If this is the root element handle it differently.
                // If the first root element in the document, serialize
                // the document's DOCTYPE. Space preserving defaults
                // to that of the output format.
                if (!_started)
                    startDocument((localName == null || localName.length() == 0) ? rawName
                            : localName);
            } else {
                // For any other element, if first in parent, then
                // close parent's opening tag and use the parnet's
                // space preserving.
                if (state.empty)
                    _printer.printText('>');
                // Must leave CData section first
                if (state.inCData) {
                    _printer.printText("]]>");
                    state.inCData = false;
                }
                // Indent this element on a new line if the first
                // content of the parent element or immediately
                // following an element or a comment
                if (_indenting
                        && !state.preserveSpace
                        && (state.empty || state.afterElement || state.afterComment))
                    _printer.breakLine();
            }
            preserveSpace = state.preserveSpace;

            _printer.printText('<');
            _printer.printText(localName);
            _printer.indent();

            // Now it's time to enter a new element state
            // with the tag name and space preserving.
            // We still do not change the curent element state.
            state = enterElementState(namespaceURI, localName, rawName,
                    preserveSpace);
            name = (localName == null || localName.length() == 0) ? rawName
                    : namespaceURI + "^" + localName;
            state.doCData = _format.isCDataElement(name);
            state.unescaped = _format.isNonEscapingElement(name);
        } catch (IOException except) {
            throw new SAXException(except);
        }
    }

    public void endElementIO(String namespaceURI, String localName,
                             String rawName) throws IOException {
        ElementState state;
        // Works much like content() with additions for closing
        // an element. Note the different checks for the closed
        // element's state and the parent element's state.
        _printer.unindent();
        state = getElementState();
        if (state.empty) {
            _printer.printText("/>");
        } else {
            // Must leave CData section first
            if (state.inCData)
                _printer.printText("]]>");
            // This element is not empty and that last content was
            // another element, so print a line break before that
            // last element and this element's closing tag.
            if (_indenting && !state.preserveSpace
                    && (state.afterElement || state.afterComment))
                _printer.breakLine();
            _printer.printText("</");
            _printer.printText(state.localName);
            _printer.printText('>');
        }
        // Leave the element state and update that of the parent
        // (if we're not root) to not empty and after element.
        state = leaveElementState();
        state.afterElement = true;
        state.afterComment = false;
        state.empty = false;
        if (isDocumentState())
            _printer.flush();
    }

}