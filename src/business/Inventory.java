package business;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Inventory {

    // Create an inventory hashmap to store products
    public HashMap contents = new HashMap();

    public Inventory() {
        try {
            // Create new instance of DocumentBuilderFactory
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

            // Get the XML file by URL. Use URL so that it works online as well.
            URL url = this.getClass().getResource("items.xml");
            // Open stream of the XML file.
            InputStream stream = url.openStream();
            // Parse the XML file.
            Document doc = docBuilder.parse(stream);
            // Normalize XML file.
            doc.getDocumentElement().normalize();

            // Get the elements inside the "Item" tag.
            NodeList node = doc.getElementsByTagName("item");

            // Loop through all the elements inside the XML file with the tag as "Item"
            for (int temp = 0; temp < node.getLength(); temp++) {
                Node nNode = node.item(temp);
                Element eElement = (Element) nNode;

                switch (eElement.getAttribute("type")) {
                    case "book": {
                        String id = eElement.getElementsByTagName("id").item(0).getTextContent();
                        String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                        String medium = eElement.getElementsByTagName("medium").item(0).getTextContent();
                        String author = eElement.getElementsByTagName("author").item(0).getTextContent();
                        String genre = eElement.getElementsByTagName("genre").item(0).getTextContent();
                        String pages = eElement.getElementsByTagName("pages").item(0).getTextContent();
                        String qty = eElement.getElementsByTagName("quantity").item(0).getTextContent();
                        String imageURL = eElement.getElementsByTagName("imageURL").item(0).getTextContent();
                        Book book = new Book(id, name, medium, author, genre, pages, qty, imageURL);
                        // Add to hashmap.
                        contents.put(id, book);
                        break;
                    }
                    case "video": {
                        String id = eElement.getElementsByTagName("id").item(0).getTextContent();
                        String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                        String medium = eElement.getElementsByTagName("medium").item(0).getTextContent();
                        String genre = eElement.getElementsByTagName("genre").item(0).getTextContent();
                        String rating = eElement.getElementsByTagName("rating").item(0).getTextContent();
                        String actors = eElement.getElementsByTagName("actors").item(0).getTextContent();
                        String director = eElement.getElementsByTagName("director").item(0).getTextContent();
                        String runningTime = eElement.getElementsByTagName("runningTime").item(0).getTextContent();
                        String format = eElement.getElementsByTagName("format").item(0).getTextContent();
                        String year = eElement.getElementsByTagName("year").item(0).getTextContent();
                        String qty = eElement.getElementsByTagName("quantity").item(0).getTextContent();
                        String imageURL = eElement.getElementsByTagName("imageURL").item(0).getTextContent();
                        Video video = new Video(id, name, genre, medium, rating, actors, director, runningTime, format, year, qty, imageURL);
                        // Add to hashmap
                        contents.put(id, video);
                        break;
                    }
                    case "music": {
                        String id = eElement.getElementsByTagName("id").item(0).getTextContent();
                        String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                        String medium = eElement.getElementsByTagName("medium").item(0).getTextContent();
                        String artist = eElement.getElementsByTagName("artist").item(0).getTextContent();
                        String genre = eElement.getElementsByTagName("genre").item(0).getTextContent();
                        String format = eElement.getElementsByTagName("format").item(0).getTextContent();
                        String qty = eElement.getElementsByTagName("quantity").item(0).getTextContent();
                        String imageURL = eElement.getElementsByTagName("imageURL").item(0).getTextContent();
                        Music music = new Music(id, name, medium, artist, genre, format, qty, imageURL);
                        // Add to hashmap
                        contents.put(id, music);
                        break;
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
