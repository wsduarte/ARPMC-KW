package pruebas;

import java.util.Scanner;

import Recomendacion.Comparador.Criterio;
import Recomendacion.MateriaFiltro;

public class Main {



	public static void main(String[] args) {
		Cargar c = new Cargar();
		Scanner sc = new Scanner(System.in);
		
		do {
			menu();
			int op  = sc.nextInt();
			if(op == 0) break;
			else if(op==1) {
				System.out.println("Ingrese la cantidad de creditos del primer semestre: ");
				int creditos = sc.nextInt();
				criterios();
				c.recomendar(creditos, darListaCritrios(sc.next().split("-")));
				imprimerResultados(c);
			}
			else if(op==2) {
				
			}				
		} while(true);
				
					
		sc.close();
		System.out.println("Programa terminado ...");

	}
	
	public static void menu() {
		System.out.println("Ingrese la opción:");
		System.out.println("(0) Terminar");
		System.out.println("(1) Filtro de primer semestre.");
		System.out.println("(2) Filtro de un semestre especifico.");
		System.out.println("(3) Proyección");
	}
	
	public static void criterios() {
		System.out.println("Ingrese los criterios de filtrado. Se separa por -");
		System.out.println("(1) Promedio");
		System.out.println("(2) Creditos");
		System.out.println("(3) Dificultad estimada");
	}
	
	public static void imprimerResultados(Cargar c) {
		for(MateriaFiltro m : c.recomendacion) System.out.println(m.codigo+"\t"+m.nombre);
		System.out.println("----------------------------------");
	}
	
	public static Criterio[] darListaCritrios(String[] ss) {
		Criterio[] ret = new Criterio[ss.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = get(ss[i]);
		}
		return ret;
	}
	
	public static Criterio get(String s) {
		switch (s) {
		case "1": return Criterio.PROMEDIO;
		case "2": return Criterio.CREDITOS;
		case "3": return Criterio.DIFICULTAD;
		default: return null;
		}
	}

}
