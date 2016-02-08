package uk.ac.cam.ba325.Tab.Translation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;

/**
 * Created by root on 06/02/16.
 */
public class Scraper {

    public static final String WEBSITE = "http://blindleaf.freeservers.com/Tabs/Drum/311";
    Document doc = null;

    private void connect(String URL) throws IOException{
        doc = Jsoup.connect(URL).userAgent("Chrome").get();

    }



    public static void main(String[] args) throws IOException {
        Scraper scraper = new Scraper();
        scraper.connect(WEBSITE);
        Scraper txtFile = new Scraper();
        txtFile.connect(WEBSITE + "/allmixedup.txt");
        Elements links = scraper.doc.select("a[href]");
        for (Element link : links){
            System.out.println("\nlink :" + link.attr("href"));
            System.out.println("text :" + link.text());
        }

        System.out.println("title is: " + scraper.doc.title());

    }

}
