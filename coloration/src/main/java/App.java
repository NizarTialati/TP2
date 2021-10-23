import java.util.ArrayList;

import org.apache.commons.cli.ParseException;

import com.uds.coloration.Graph;
import com.uds.coloration.Main_File;
import com.uds.coloration.Main_Region_France;
import com.uds.coloration.Node;

import coloration_ppc.ColorationPPC_Region_France;

public class App {

	public static void main(String[] args) throws ParseException {
		long start = System.currentTimeMillis();
		ColorationPPC_Region_France.main(null);
		
		long end = System.currentTimeMillis();

		System.out.println("Temps d'exécution du solver choco : " + (end - start) + "ms");

		// Notre solution

		start = System.currentTimeMillis();
		Main_Region_France.main(null);
		end = System.currentTimeMillis();

		System.out.println("Temps d'exécution de l'algorithme D-Sature : " + (end - start) + "ms");

	}

}
