package io.github.cavweb20.xml.dom.echo;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.xml.resolver.tools.CatalogResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author cavweb20
 * @since  2004-04-01
 */
public class TextExtractor
{

    // Setting up the logging properties
    private static final Logger LOG = LoggerFactory.getLogger(TextExtractor.class);

    public static void followNode(Node node)
    {
        int type = node.getNodeType();

        if(type == Node.TEXT_NODE)
            System.out.println(node.getNodeValue());
        if(node.hasChildNodes())
        {
            NodeList children = node.getChildNodes();
            for(int i = 0; i < children.getLength(); i++)
                followNode(children.item(i));
        }
    }

    public static void main(String[] args)
    {
        if (LOG.isDebugEnabled())
            LOG.debug("##### Start #####");

        if (args.length < 1)
        {
            LOG.error("Usage: java TextExtractor URL");
            System.exit(-1);
        }

        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder parser = factory.newDocumentBuilder();
            parser.setEntityResolver(new CatalogResolver());
            Document doc = parser.parse(args[0]);
            followNode(doc);
        }
        catch (FactoryConfigurationError e)
        {
            LOG.error("Error: Cannot create factory.\n"
                    + e.getLocalizedMessage());
            System.exit(-1);
        }
        catch (ParserConfigurationException e)
        {
            LOG.error("Error: Parser configuration problem.\n"
                    + e.getLocalizedMessage());
            System.exit(-1);
        }
        catch (SAXException e)
        {
            LOG.error("Error: SAX exception." + e.getLocalizedMessage());
            System.exit(-1);
        }
        catch (IOException e)
        {
            LOG.error("Error: I/O exception." + e.getLocalizedMessage());
            System.exit(-1);
        }
        if (LOG.isDebugEnabled())
            LOG.debug("##### End #####");
    }

}