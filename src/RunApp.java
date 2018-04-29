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

	private final static String secondSectionTitle = "ZASTOSOWANIE:"; 
	private final String thirdSectionTitle = "";
	private final static FireBirdDAO dao = new FireBirdDAO();
	
	private final static int groupId = 34;
	
	private final static String[] blockedString = {"VAUXHALL"};
	
	public static void main(String[] args) {
		
		startConvertWithRegex();
	}
	
	public static boolean vehicleLine(String line) {
		String[] regex = {"\"(\\\\d\\\\d\\\\d\\\\d\\\\s(.*){0,300})", "vehicleLineTag"};
		for (int i = 0; i < regex.length; i++) {
			if(line.length() >= regex[i].length()) {
				//System.out.println();
				//System.out.print("searching for vehicle string in : " + line);
				if(line.matches(regex[i]) || line.contains(regex[i])){
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean banned(String line) {
		for (int i = 0; i < blockedString.length; i++) {
			if(line.contains(blockedString[i])) {
				return true;
			}
		}
		return false;
	}
	
	public static void startConvertWithRegex() {
		
		for(Offer offer : dao.getOffersFromGroupWithoutNewText(groupId)) {
			offer = dao.getProductText(offer);
			offer = dao.getOldProductText(offer);
			
			if(offer.getText().isEmpty()) {

											
				OfferTextTemplate template = new OfferTextTemplate();
				template.setSecondSectionTitle(secondSectionTitle);
				template.setTitle(offer.getTitle().replaceAll("&(?!amp;)", "&amp;"));
				template.setProductCode(offer.getProductCode());
				
				String n = br2nl(offer.getOldHtmlText());
			    Scanner sc = new Scanner(n);
			    
			    while (sc.hasNextLine()) {
			        String newLine = sc.nextLine().trim();
			        newLine = newLine.replaceAll("&(?!amp;)", "&amp;");
			        
			        if(newLine.length()>2) {
			        	//System.out.println(newLine);
			        	
			        	if(newLine.contains(": ")) {
			        		//attrib
			        		template.addAttrib(textToAttrib(newLine));
			        		
			        	} else if(vehicleLine(newLine)) {
			        		//vehicle
			        		newLine = newLine.replaceAll("vehicleLineTag", "");
			        		newLine = newLine.replaceAll("&amp;gt;", ">");
			        		
			        		if(newLine.length()>2) {
			        			if(!banned(newLine)) {
			        				System.out.println(newLine);
			        				template.addVehicleString(newLine);
			        			}

			        		}

			        		
			        	} else {
			        		//additional text
			        		template.addAdditionalText(newLine);
			        	}
			        	
			        }
			        
			    }
			    offer.setText(template.toString());
			    
			    //save new descripton to db
			    //if there is no roczniki mark (vehicles) in description - we need use other search template - do not save
			    if(!template.getVehicleStrings().isEmpty()) {
			    	dao.setProductText(offer);
			    	System.out.println(offer.getOfferId() + "  kod: " + offer.getProductCode() + "  done.");
			    } else {
			    	System.out.println("kod: " + offer.getProductCode() + " not done.");
			    }
			    	
			    
			} else {
				System.out.println("Offer with new text  -  offer id: " + offer.getOfferId() + "  product Code: " + offer.getProductCode());
			}
			
		}
	}
	
	
	public static void startConvertFirstPhase() {
		
		for(Offer offer : dao.getOffersFromGroup(groupId)) {
			offer = dao.getProductText(offer);
			offer = dao.getOldProductText(offer);
			
			if(offer.getText().isEmpty()) {

											
				OfferTextTemplate template = new OfferTextTemplate();
				template.setSecondSectionTitle(secondSectionTitle);
				template.setTitle(offer.getTitle().replaceAll("&(?!amp;)", "&amp;"));
				template.setProductCode(offer.getProductCode());
				
				String n = br2nl(offer.getOldHtmlText());
				
			    Scanner sc = new Scanner(n);
			    
			    while (sc.hasNextLine()) {
			        String newLine = sc.nextLine().trim();
			        newLine = newLine.replaceAll("&(?!amp;)", "&amp;");
			        
			        if(newLine.length()>2) {
			        	//System.out.println(newLine);
			        	
			        	if(newLine.contains(":  ")) {
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
			    //if there is no roczniki mark (vehicles) in description - we need use other search template - do not save
			    if(!template.getVehicleStrings().isEmpty()) {
			    	dao.setProductText(offer);
			    	System.out.println(offer.getOfferId() + "  kod: " + offer.getProductCode() + "  done.");
			    } else {
			    	System.out.println("kod: " + offer.getProductCode() + " not done.");
			    }
			    	
			    
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
	    
	    html = html.toUpperCase().replaceAll("<TR>", System.getProperty("line.separator"));
	    html = html.replaceAll("<TABLE CLASS=\"ZASTOSOWANIE\">", "vehicleLineTag");
	    html = html.replaceAll("</TD>", "  ");
	    
	    Document document = Jsoup.parse(html);
	    document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
	    document.select("br").append("\\n");
	    document.select("p").prepend("\\n\\n");
	    String s = document.html().replaceAll("\\\\n", "\n");
	    return Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
	}

}
