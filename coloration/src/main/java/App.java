import java.io.IOException;

import org.apache.commons.cli.ParseException;

import com.uds.coloration.Main_File;

import coloration_ppc.ColorationPPC_File;

public class App {

	public static void main(String[] args) throws ParseException {
		long start = System.currentTimeMillis();
		try {
			ColorationPPC_File.main(null);
		} catch (ParseException | IOException e) {
			System.out.println("Erreur ");
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();

		System.out.println("Temps d'exécution du solver choco : " + (end - start) + "ms");

		// Notre solution

		start = System.currentTimeMillis();
		Main_File.main(null);
		end = System.currentTimeMillis();

		System.out.println("Temps d'exécution de l'algorithme D-Sature : " + (end - start) + "ms");

	}

}
