import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

import eu.michalbuda.offerGenerator.db.FireBirdDAO;
import eu.michalbuda.offerGenerator.model.Offer;
import eu.michalbuda.offerGenerator.model.OfferTextTemplate;
import eu.michalbuda.offerGenerator.model.ProductAttribute;

public class RunApp {

	private final static String secondSectionTitle = "ZASTOSOWANIE"; 
	private final String thirdSectionTitle = "";
	
	public static void main(String[] args) {
		
		FireBirdDAO dao = new FireBirdDAO();
		
		for(Offer offer : dao.getOffersFromGroup(34)) {
			offer = dao.getProductText(offer);
			offer = dao.getOldProductText(offer);
			
			if(offer.getText().isEmpty()) {

				OfferTextTemplate template = new OfferTextTemplate();
				template.setSecondSectionTitle(secondSectionTitle);
				template.setTitle(offer.getTitle());
				
				String n = br2nl(offer.getOldHtmlText()).toUpperCase();
				
			    Scanner sc = new Scanner(n);
			    
			    while (sc.hasNextLine()) {
			        String newLine = sc.nextLine().trim();
			        if(newLine.length()>2) {
			        	System.out.println(newLine);
			        	
			        	if(newLine.contains(": ")) {
			        		//attrib
			        		template.addAttrib(textToAttrib(newLine));
			        		
			        	} else if(newLine.contains("ROCZNIKI")) {
			        		//vehicle
			        		template.addVehicleString(newLine);
			        		
			        	} else {
			        		//additional text
			        		template.addAdditionalText(newLine);
			        	}
			        	
			        }
			        
			    }
			    offer.setText(template.toString());
			    
			    //save new descripton to db
			    dao.insertProductText(offer);
			    
			} else {
				System.out.println("Offer with new text  -  offer id: " + offer.getOfferId() + "  product Code: " + offer.getProductCode());
			}
			
		}
	}
	
	public static ProductAttribute textToAttrib(String string) {
		ProductAttribute attrib = new ProductAttribute();
		String[] arr = string.split(":");
		attrib.setAttribName(arr[0]);
		attrib.setAttribValue(arr[1]);
		return attrib;
	}

	
	public static String cleanHtml(String oldText) {
		Document doc = Jsoup.parse(oldText);
		doc = new Cleaner(Whitelist.simpleText()).clean(doc);
		doc.outputSettings().escapeMode(EscapeMode.xhtml);
		return doc.body().html();
	}
	
	public static String br2nl(String html) {
	    if(html==null)
	        return html;
	    Document document = Jsoup.parse(html);
	    document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
	    document.select("br").append("\\n");
	    document.select("p").prepend("\\n\\n");
	    String s = document.html().replaceAll("\\\\n", "\n");
	    return Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
	}

}
