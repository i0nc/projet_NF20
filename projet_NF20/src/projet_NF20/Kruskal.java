package projet_NF20;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Kruskal {
	
	private int sommet1; //sommet1
	private int sommetAdjacent; //sommetAdjacent
	private int tmpPoids; //poids de l'arête
	private int indexSommetAdjacent;
	
	private ArrayList<ArrayList<Integer>> temp; //ici stock temporairement juste les arêtes
	private ArrayList<Integer> sommetParcouru; //liste des sommets parcourus pour l'algorithme
	
	private ArrayList<ArrayList<ArrayList<Integer>>> gr; //liste où l'on va stocker la liste3d
	private ArrayList<Integer> test; //
	int poidsFinal;
	private long[] grapheComplexite;
	
	public Kruskal(Graphe g){
		sommet1 = 0;
		sommetAdjacent = 0;
		tmpPoids = -1;
		indexSommetAdjacent = -1;
		temp = new ArrayList<ArrayList<Integer>>();
		sommetParcouru = new ArrayList<Integer>();
		this.gr = g.getList3D();
		test = new ArrayList<Integer>();
		poidsFinal = 0;
		grapheComplexite =  new long[this.gr.size()];
	}
	
	public void kruskal(){
		
		triCroissant();
		
		//graphe 1
		long debut = System.currentTimeMillis();
		long dureeDeTraitement = 0;
		
		for(int l = 0; l<this.gr.size()-1; l++){
			
			/*//graphe 2
			long debut = System.currentTimeMillis();
			long dureeDeTraitement = 0;*/
			
			//trouve la plus petite arête
			trouverPetiteArete();
			
			poidsFinal += tmpPoids;
			
			//enlève la plus petite arête trouvée pour ne pas la reprendre
			supprimerSommet(); 
			ajoutSommet();
			regarderTemp();
			dureeDeTraitement = (System.currentTimeMillis()-debut);
			this.grapheComplexite[l] = dureeDeTraitement;
		}
		System.out.println("Coût : "+poidsFinal);
	}
	
	private void trouverPetiteArete(){
		this.tmpPoids=-1;
		this.sommet1=-1;
		this.sommetAdjacent=-1;
		this.indexSommetAdjacent=-1;
		for (int i = 0; i < gr.size();i++){
			for (int j = 0; j < gr.get(i).size();j++){
				if(tmpPoids==-1 || tmpPoids>gr.get(i).get(j).get(1)){
					//stock le poid
					tmpPoids = gr.get(i).get(j).get(1);
					//stock le sommet1
					sommet1 = i;
					//stock le sommetAdjacent adjacent
					sommetAdjacent = gr.get(i).get(j).get(0);	
					indexSommetAdjacent = j;
				}
			}
		}
	}
	
	//à regarder ici problème ! d'ajout à la liste sommet
	private void regarderTemp(){
		if(!temp.isEmpty()){
			//je parcours temp pour avoir la ou les arraylist contenant les sommets
			for (int i = 0; i < temp.size(); i++){
				//je parcours les sommets de 0 à 1 du coup (taille de 2)
				for (int j = 0; j < temp.get(i).size(); j++){
					//ensuite ici je parcours tous les sommets pour regarder si les sommets de temp sont dedans ou non
					for(int k = 0; k < sommetParcouru.size(); k++){
						//si un sommet est dedans
						if(temp.get(i).get(j)==sommetParcouru.get(k)){
							sommetParcouru.add(temp.get(i).get(0));
							sommetParcouru.add(temp.get(i).get(1));
							temp.get(i).clear();
							break;
						}
					}
				}
			}
		} 
	}
	
	private void ajoutSommet(){
		boolean bool = false;
		if (sommetParcouru.isEmpty()){
			sommetParcouru.add(sommet1);
			sommetParcouru.add(sommetAdjacent);
		}else{
			//parcour de la liste pour voir si sommet adjacent y est
			for(int i = 0; i < sommetParcouru.size(); i++){
				if(sommetAdjacent==sommetParcouru.get(i))
				{
					bool = true;
					sommetParcouru.add(sommet1);
					break;
				}
				else if(sommet1==sommetParcouru.get(i)){
					bool=true;
					sommetParcouru.add(sommetAdjacent);
					break;
				}
			}
			if(!bool){
				temp.add(temp.size(), test);
				//les ajouter dans la liste temp
				temp.get(temp.size()-1).add(sommet1);
				temp.get(temp.size()-1).add(sommetAdjacent);	
			}		
		}
	}
	
	public void triCroissant(){
		boolean trie;
	  
		long debut = System.currentTimeMillis();
		for (int i = 0; i < this.gr.size() ; i++) {
			int taille = this.gr.get(i).size();
			do
			{
				trie = false;
			    for(int j=0 ; j < taille-1 ; j++)
			    {
			        if(this.gr.get(i).get(j).get(1) > this.gr.get(i).get(j+1).get(1))
			        {
			          	int tempoSommet = this.gr.get(i).get(j).get(0).intValue();
			            int tempoPoids = this.gr.get(i).get(j).get(1).intValue();
			            this.gr.get(i).get(j).set(0, this.gr.get(i).get(j+1).get(0).intValue());
			            this.gr.get(i).get(j).set(1, this.gr.get(i).get(j+1).get(1).intValue());
			            this.gr.get(i).get(j+1).set(0, tempoSommet);
			            this.gr.get(i).get(j+1).set(1, tempoPoids);
			            trie = true;
			        }			       
			    }
			    taille--;
			}while(trie);
		}
		System.out.println("\n \n Temps d'éxécution tri Croissant: " + ((System.currentTimeMillis() - debut) / 1000) + " sec \n");
	}
	
	public void supprimerSommet(){
		this.gr.get(sommet1).remove(indexSommetAdjacent);
		for (int j = 0; j < this.gr.get(indexSommetAdjacent).size() ; j++) {
			if(this.gr.get(sommetAdjacent).get(j).get(0) == sommet1){
				this.gr.get(sommetAdjacent).remove(j);
				break;
			}
		}
	}
	
	public void recupererGraphe(){

		File f = new File ("kruskalComplexite1000.txt");
		try
		{
		    FileWriter fw = new FileWriter (f);
		 
		    for (int i = 0; i < this.grapheComplexite.length; i++) {
		    		fw.write(i + "\n");	
		    }		    
		    fw.write("Sommet \n");		    
		    for (int i = 0; i < this.grapheComplexite.length; i++) {
	    			long valeur = this.grapheComplexite[i];
	    			fw.write(valeur + "\n"); 		
	    }
		    System.out.println("Graphe récupéré");
		    fw.close();
		}
		catch (IOException exception)
		{	
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	}
	
	
	public static void main(String[] args){
		/*Scanner sc = new Scanner(System.in);
		
		//choix du fichier
		System.out.println("Veuillez choisir le fichier à lire");
		String file = sc.nextLine();
		
		Graphe g = new Graphe(file);*/
		
		Graphe g = new Graphe("inst_v1000.dat");
		
		//int [][] test;
		long debut = System.currentTimeMillis();
		Kruskal k = new Kruskal(g);
		System.out.println("Résultats Kruskal : ");
		k.kruskal();
		System.out.println("\n \n Temps d'éxécution: ");
		System.out.println((System.currentTimeMillis() - debut) /*/ 1000*/+" ms ");
		k.recupererGraphe();
		
	}
}

