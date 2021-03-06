package io.github.cavweb20.xml.sax.validator;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import io.github.cavweb20.xml.sax.error.CustomErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.xml.resolver.tools.CatalogResolver;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Typical example of SAX validator with a DTD read from a catalog.
 * Test without network connection.
 * 
 * Usage:
 * - java SAXCatalogValidator http://imergo.com/ http://wired.com/
 * 
 * @author cavweb20
 * @since  2004
 */
public class SAXCatalogValidator extends DefaultHandler
{

    // Setting up the logging properties
    private static final Logger LOG = LoggerFactory.getLogger(SAXCatalogValidator.class);

    /**
     * Main executable program
     * @param args
     */
    public static void main(String[] args)
    {
        if (LOG.isDebugEnabled()) LOG.debug("##### Start #####");

        if (args.length < 1)
        {
            if (LOG.isInfoEnabled())
                LOG.info("Usage: java SAXCatalogValidator URL1 [URL2 URL3 ...]");
            return;
        }

        for (String arg : args)
        {
            try
            {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                factory.setNamespaceAware(true);
                factory.setValidating(true);
                XMLReader parser = factory.newSAXParser().getXMLReader();
                parser.setEntityResolver(new CatalogResolver());
                parser.setErrorHandler(new CustomErrorHandler());
                parser.parse(arg);
                if (LOG.isInfoEnabled())
                {
                    LOG.info(arg + " is valid.");
                }
            }
            catch (SAXNotRecognizedException | SAXNotSupportedException | ParserConfigurationException | IOException e)
            {
                LOG.error("Error: " + e.getLocalizedMessage());
            }
            catch (SAXException e)
            {
                LOG.error("Error: " + e.getLocalizedMessage());
                LOG.error(arg + " is invalid.");
            }
        }

        if (LOG.isDebugEnabled()) LOG.debug("##### End #####");
    }

}