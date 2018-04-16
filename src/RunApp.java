import eu.michalbuda.offerGenerator.db.FireBirdDAO;

public class RunApp {

	public static void main(String[] args) {
		FireBirdDAO dao = new FireBirdDAO();
		dao.printCodes();
		
	}

}
