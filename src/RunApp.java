import eu.michalbuda.offerGenerator.db.FireBirdDAO;
import eu.michalbuda.offerGenerator.model.Offer;

public class RunApp {

	public static void main(String[] args) {
		FireBirdDAO dao = new FireBirdDAO();
		for(Offer offer : dao.getOffersFromGroup(34)) {
			offer = dao.getProductText(offer);
			if(offer.getText().isEmpty()) {
				//System.out.println("Offer without new text  -  offer id: " + offer.getOfferId() + "  product Code: " + offer.getProductCode());
			} else {
				System.out.println("Offer with new text  -  offer id: " + offer.getOfferId() + "  product Code: " + offer.getProductCode());
			}
			
		}
	}

}
