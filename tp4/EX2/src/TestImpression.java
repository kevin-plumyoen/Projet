
public class TestImpression {
	/** Test de l'impression avec deux documments */
	public static void main(String[] args){
		Thread t1 = new Thread(new Impression("Rapport de Stage",5));
		Thread t2 = new Thread(new Impression(7));
		
		t1.start();
		t2.start();
	}
}
