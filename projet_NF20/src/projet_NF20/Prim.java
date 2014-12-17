package projet_NF20;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Prim {
	
	ArrayList<ArrayList<ArrayList<Integer>>> list3D = new ArrayList<ArrayList<ArrayList<Integer>>>();
	ArrayList<Integer> sommetVisite;
	ArrayList<ArrayList<ArrayList<Integer>>> resultat = new ArrayList<ArrayList<ArrayList<Integer>>>();
	
	public Prim(Graphe g)
	{
		this.list3D = g.getList3D();
		this.sommetVisite = new ArrayList<Integer>();
	}
	
	public void supCycle(int sommet)
	{
		for (int i = 0; i < this.list3D.get(sommet).size()-1 ; i++) { //On parcours la liste des sommets adjacents à un sommet (sommet)
			int sommetAdj = this.list3D.get(sommet).get(i).get(0).intValue(); //On récupère le num du sommet adj
			for (int j = 0; j < sommetVisite.size(); j++) {//On compare avec la liste des sommets déjà visités
				if(sommetAdj == this.sommetVisite.get(j).intValue()) //Si un sommet adj fait déjà partit de la liste des sommets visités
				{
					try {
						this.list3D.get(sommet).remove(i);//Je le supprime
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}		
		}
	}
	
	public ArrayList<Integer> getSommetMinPoids(int sommet)
	{
		this.supCycle(sommet);
//		int res=-1;
//		int minimum=0;
//		for (int i = 0; i < this.list3D.get(sommet).size(); i++) {
//			int poids = this.list3D.get(sommet).get(i).get(1);
//			if(i==0){
//				minimum = poids;
//				res = i;
//			}else if(poids < minimum){
//				minimum = poids;
//				res = i;
//			}
//		}
//		
//		return minimum;
		//System.out.println("Sommet: " + sommet + " Sommet adj: "+ this.list3D.get(sommet).get(0).get(0)+ " Poids: " +this.list3D.get(sommet).get(0).get(1));
		return this.list3D.get(sommet).get(0);
	}
	
	public ArrayList<Integer> getCompareToutLesMin()
	{
		ArrayList<ArrayList<Integer>> listeComparaison = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> minimum = new ArrayList<Integer>();

		for (int i = 0; i < this.sommetVisite.size(); i++) {
			listeComparaison.add(getSommetMinPoids(i));			
		}
		for (int i = 0; i < listeComparaison.size(); i++) {
			
			if(i==0){
				minimum = listeComparaison.get(i);	
			}
			else if(listeComparaison.get(i).get(1).intValue() < minimum.get(1))
			{
				minimum.set( 0,listeComparaison.get(i).get(0).intValue());
				minimum.set( 1,listeComparaison.get(i).get(1).intValue());
			}
		}
		
		return minimum;
		
	}
	
	public void affichage(){
		String res = "";
		for (int i = 0; i < sommetVisite.size(); i++) {
			res+= this.sommetVisite.get(i) + "; ";
		}
		System.out.println("Résultat : \n" + res);
	}
	
	public void triCroissant()
	{
		boolean trie;
	  
		long debut = System.currentTimeMillis();
		for (int i = 0; i < this.list3D.size() ; i++) {
			int taille = this.list3D.get(i).size();
			do
			{
				trie = false;
			    for(int j=0 ; j < taille-1 ; j++)
			    {
			        if(this.list3D.get(i).get(j).get(1) > this.list3D.get(i).get(j+1).get(1))
			        {
			          	int tempoSommet = this.list3D.get(i).get(j).get(0).intValue();
			            int tempoPoids = this.list3D.get(i).get(j).get(1).intValue();
			            this.list3D.get(i).get(j).set(0, this.list3D.get(i).get(j+1).get(0).intValue());
			            this.list3D.get(i).get(j).set(1, this.list3D.get(i).get(j+1).get(1).intValue());
			            this.list3D.get(i).get(j+1).set(0, tempoSommet);
			            this.list3D.get(i).get(j+1).set(1, tempoPoids);
			            trie = true;
			        }			       
			    }
			    taille--;
			}while(trie);
		}
		System.out.println("\n \n Temps d'éxécution tri Croissant: " + ((System.currentTimeMillis() - debut) / 1000) + "\n");
	}
	
	public void Prim()
	{
		long debut = System.currentTimeMillis();
		triCroissant();
		int poidsMin;
		int sommetAdj;
		for (int i = 0; i < this.list3D.size()-1 ; i++) {
			//System.out.println("i : "+ i);
			ArrayList<Integer> resultat;
			if(i==0){
				this.sommetVisite.add(i);
				resultat=getSommetMinPoids(i);
				//System.out.println("Sommet ajouté: " + i );
			}else{
				resultat = this.getCompareToutLesMin();
			}			
			this.sommetVisite.add(resultat.get(0));
			//System.out.println("Sommet ajouté:" + resultat.get(0) );
			//System.out.println("Nombre de sommet visités: " + this.sommetVisite.size());
		}
		System.out.println();
		System.out.println("\n \n Temps d'éxécution Prim: " + ((System.currentTimeMillis() - debut) / 1000) + " \n");
	}
	
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
		System.out.println("\n \n Temps d'éxécution affichage liste3D: " + ((System.currentTimeMillis() - debut) / 1000) + "\n");

	}

	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		// choix du fichier
		//System.out.println("Veuillez choisir le fichier à lire");
		//String file = sc.nextLine();

		//Graphe g = new Graphe("graphe_k5.dat");
		Graphe g = new Graphe("inst_v1000.dat");
		Prim p = new Prim(g);
		//p.affichageList3D();
		long debut = System.currentTimeMillis();
		//p.triCroissant();
		//p.affichageList3D();
		p.Prim();
		p.affichage();
		//p.affichageList3D();
		System.out.println("\n \n Temps d'éxécution global: ");
		System.out.println((System.currentTimeMillis() - debut) / 1000);
	}
	
}
