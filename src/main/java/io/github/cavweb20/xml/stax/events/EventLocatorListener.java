package io.github.cavweb20.xml.stax.events;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventLocatorListener
{
    // Setting up the logging properties
    private static Logger LOG = LoggerFactory.getLogger(EventLocatorListener.class);

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        InputStream is;
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader parser;

        if (LOG.isDebugEnabled())
            LOG.debug("##### Start #####");

        try
        {
            if (args.length < 1)
            {
                if (LOG.isInfoEnabled())
                    LOG.info("Usage: java EventLocatorListener URL");
                if (LOG.isDebugEnabled())
                    LOG.debug("##### End #####");
                return;
            }

            factory.setProperty(XMLInputFactory.IS_VALIDATING, Boolean.FALSE);
            factory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
            is = (new URL(args[0])).openStream();
            parser = factory.createXMLStreamReader(is);
            while(true)
            {
                int event = parser.getEventType();
                switch(event)
                {
                case XMLStreamConstants.START_DOCUMENT:
                    if (LOG.isInfoEnabled())
                    {
                        LOG.info("[" + parser.getLocation().getLineNumber() +
                                 ":" + parser.getLocation().getColumnNumber() +
                                 "] Start Document");
                    }
                    break;
                case XMLStreamConstants.END_DOCUMENT:
                    if (LOG.isInfoEnabled())
                    {
                        LOG.info("[" + parser.getLocation().getLineNumber() +
                                 ":" + parser.getLocation().getColumnNumber() +
                                 "] End Document");
                    }
                    break;
                case XMLStreamConstants.START_ELEMENT:
                    if (LOG.isInfoEnabled())
                    {
                        LOG.info("[" + parser.getLocation().getLineNumber() +
                                 ":" + parser.getLocation().getColumnNumber() +
                                 "] Start Element");
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if (LOG.isInfoEnabled())
                    {
                        LOG.info("[" + parser.getLocation().getLineNumber() +
                                 ":" + parser.getLocation().getColumnNumber() +
                                 "] End Element");
                    }
                    break;
                case XMLStreamConstants.DTD:
                    if (LOG.isInfoEnabled())
                    {
                        LOG.info("[" + parser.getLocation().getLineNumber() +
                                 ":" + parser.getLocation().getColumnNumber() +
                                 "] DTD");
                    }
                    break;
                default:
                    break;
                }
                if(parser.hasNext()) parser.next();
                else break;
            }
            parser.close();
        }
        catch (XMLStreamException e)
        {
            LOG.error(args[0] + " is not well-formed.");
            LOG.error(e.getLocalizedMessage());
        }
        catch (MalformedURLException e)
        {
            LOG.error(args[0] + " is not a valid URL.");
            LOG.error(e.getLocalizedMessage());
        }
        catch (IOException e)
        {
            LOG.error(args[0] + " cannot be open.");
            LOG.error(e.getLocalizedMessage());
        }

        if (LOG.isDebugEnabled())
            LOG.debug("##### End #####");
    }

}
