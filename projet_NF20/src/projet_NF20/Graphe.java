package projet_NF20;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Graphe {

	private int[][] gr;
	private boolean estOriente;
	private int nbNodes;
	private ArrayList<ArrayList<Integer>> list2D = new ArrayList<ArrayList<Integer>>();
	private ArrayList<ArrayList<ArrayList<Integer>>> list3D = new ArrayList<ArrayList<ArrayList<Integer>>>();

	public Graphe(String file) {
		String chaine = lireFichier(file);
	}
	
	public int getNbNodes()
	{
		return this.nbNodes;
	}

	public void afficheMatrice() {
		long debut = System.currentTimeMillis();
		System.out.println("Affichage de la matrice \br");
		for (int i = 0; i < gr.length; i++) {
			for (int j = 0; j < gr[i].length; j++) {
				System.out.println(i + " " + j + " " + gr[i][j]);
			}
		}
		System.out.println("\n \n Temps d'éxécution: ");
		System.out.println((System.currentTimeMillis() - debut) / 1000);
	}

	public void affichageList2D() {
		long debut = System.currentTimeMillis();
		System.out.println("Affichage de l'ArrayList " + this.nbNodes);
		for (int i = 0; i < this.list2D.size(); i++) {
			String affichage = "S " + i + " A : ";
			for (int j = 0; j < this.list2D.get(i).size(); j++) {
				affichage += list2D.get(i).get(j) + " ";
			}
			System.out.println(affichage);
		}
		System.out.println("\n \n Temps d'éxécution: ");
		System.out.println((System.currentTimeMillis() - debut) / 1000);

	}

	/*
	 * Pour avoir un sommet 0 = this.list3D.get(0)
	 * Pour avoir les sommets adjacents au sommet et poid 0 = 
	 * for(int i=0 ; i< this.list3D.get(0).size(); i++)
	 * {
	 *		 this.list3D.get(0).get(i).get(O); //numéro du sommet adjacent
	 *		 this.list3D.get(0).get(i).get(1); //poid du sommet 
	 * }
	 * 
	 */
	
	public void affichageList3D() {
		long debut = System.currentTimeMillis();
		System.out.println("Affichage de l'ArrayList ");
		for (int i = 0; i < this.list3D.size(); i++) {
			String affichage = "S " + i + " A : ";
			for (int j = 0; j < this.list3D.get(i).size(); j++) {
				for (int j2 = 0; j2 < this.list3D.get(i).get(j).size(); j2++) {
					affichage += this.list3D.get(i).get(j).get(j2) + " ";
				}

			}
			System.out.println(affichage);
		}
		System.out.println("\n \n Temps d'éxécution: ");
		System.out.println((System.currentTimeMillis() - debut) / 1000);

	}

	public int[][] getMatriceGraphe() {
		return this.gr;
	}

	public ArrayList<ArrayList<Integer>> getList2D() {
		return this.list2D;
	}

	public ArrayList<ArrayList<ArrayList<Integer>>> getList3D() {
		return this.list3D;
	}
	
	public String lireFichier(String file) {
		String chaine = "";
		try {

			InputStream ips = new FileInputStream(file);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			while ((ligne = br.readLine()) != null) {
				lireLigne(ligne);
				if (ligne.startsWith("LIST_OF")) {
					// On va écrire les lignes suivantes dans une chaine de
					// caractère jusqu'a tomber sur "END"
					while ((ligne = br.readLine()) != null
							&& (!ligne.equals("END"))) {
						this.ajoutLigne(ligne);
					}
				}
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return chaine;
	}

	// Récupération du nombre de noeuds et d'arrêtes et vérifie si le graphe est
	// orienté ou non
	public void lireLigne(String ligne) {
		if (ligne.startsWith("NB_NODES")) { // repere la ligne commencant par
											// NB_NODES
			int nb = ligne.lastIndexOf('\t'); // nb devient le num de la place
												// du caractère de tabulation
			System.out.println(ligne.substring(nb + 1) + " nombre de sommets"); // nb+1
																				// pour
																				// prendre
																				// le
																				// caractère
																				// après
																				// la
																				// tabulation
			int nbs = Integer.parseInt(ligne.substring(nb + 1));
			this.nbNodes = nbs;
			this.initialisationArrayList2D();
			this.initialisationArrayList3D();
			this.gr = new int[nbs][nbs];
		} else if (ligne.startsWith("NB_EDGES")) {
			int nb = ligne.lastIndexOf('\t');
			System.out.println(ligne.substring(nb + 1) + " nombre d'arretes");
		} else if (ligne.startsWith("UNDIRECTED")) {
			// graphe.setOriente(false);
			System.out.println("Le graphe n'est pas orienté");
			estOriente = false;
		} else if (ligne.startsWith("DIRECTED")) {

			System.out.println("Le graphe est orienté");
			estOriente = true;
		}
	}

	public void initialisationArrayList2D() {
		for (int i = 0; i < this.nbNodes; i++) {
			this.list2D.add(new ArrayList<Integer>());
		}
	}

	public void initialisationArrayList3D() {
		for (int i = 0; i < this.nbNodes; i++) {
			this.list3D.add(new ArrayList<ArrayList<Integer>>());
		}
	}

	public void ajoutLigne(String chaine) {

		String noeud1 = "";
		String noeud2 = "";
		String poids = "";
		int cpt = 0;
		noeud1 = "";
		noeud2 = "";
		poids = "";
		while (chaine.charAt(cpt) != '\t') {
			noeud1 += chaine.charAt(cpt);
			cpt++;
		}
		cpt++;
		while (chaine.charAt(cpt) != '\t') {
			noeud2 += chaine.charAt(cpt);
			cpt++;
		}
		cpt++;
		while (chaine.charAt(cpt) != '\n') {
			poids += chaine.charAt(cpt);
			if (cpt < chaine.length() - 1)
				cpt++;
			else
				break;
		}
		// On met les Strings dans des Integer
		int noeud1Int = Integer.parseInt(noeud1);
		// System.out.println(noeud1Int);
		int noeud2Int = Integer.parseInt(noeud2);
		// System.out.println(noeud2Int);
		int poidsInt = Integer.parseInt(poids);
		// System.out.println(poidsInt);

		this.list2D.get(noeud1Int).add(noeud2Int);

		this.list3D.get(noeud1Int).add(new ArrayList<Integer>());
		// System.out.println(this.list3D.get(noeud1Int).size()-1);
		this.list3D.get(noeud1Int).get(this.list3D.get(noeud1Int).size() - 1)
				.add(noeud2Int);
		this.list3D.get(noeud1Int).get(this.list3D.get(noeud1Int).size() - 1)
				.add(poidsInt);

		this.gr[noeud1Int][noeud2Int] = poidsInt;
		if (this.estOriente == false) {
			this.gr[noeud2Int][noeud1Int] = poidsInt;
			this.list2D.get(noeud2Int).add(noeud1Int);

			this.list3D.get(noeud2Int).add(new ArrayList<Integer>());
			this.list3D.get(noeud2Int)
					.get(this.list3D.get(noeud2Int).size() - 1).add(noeud1Int);
			this.list3D.get(noeud2Int)
					.get(this.list3D.get(noeud2Int).size() - 1).add(poidsInt);

		}

	}
}