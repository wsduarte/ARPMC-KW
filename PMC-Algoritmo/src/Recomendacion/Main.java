package Recomendacion;

import java.util.List;
import java.util.Scanner;

import Recomendacion.Comparador.Criterio;

public class Main {

	public static void main(final String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		Filtro f = new Filtro();
//		for(;;) {
//			System.out.println("Ingrese la opción:");
//			System.out.println("(1) Filtro primer semestre");
//			System.out.println("(2) Proyección carera");
//			System.out.println("(3) Finalizar");
//			int op1 = sc.nextInt();
//			System.out.println("Ingrese los criterios de filtrado. Se separa por -");
//			System.out.println("(1) Promedio");
//			System.out.println("(2) Creditos");
//			System.out.println("(3) Dificultad estimada");
//			String op2 = sc.next();
//			String[] ss = op2.split("-");
//			Criterio[] fil = new Criterio[ss.length];
//			for (int i = 0; i < fil.length; i++) {
//				fil[i] = get(ss[i]);
//			}
//			f.setCriterios(fil);
//			switch (op1) {
//			case 1:
//				System.out.println("Cantidad de credito del semestre.");
//				List<MateriaFiltro> resultados = f.recomendar(sc.nextInt());
//				for (MateriaFiltro materiaFiltro : resultados) {
//					System.out.println(materiaFiltro);
//				}
//				break;
//			case 2: 
//				System.out.println("Ingrese la cantidad de creditos del semestre");
//				int a = sc.nextInt();
//				System.out.println("Ingrese la cantidad de creditos de la carrera");
//				int b = sc.nextInt();
//				List<Pensum> res = f.proyección(b, a);
//				for (Pensum p : res) {
//					System.out.println(p.getNumero());
//					for(MateriaFiltro m : p.getMaterias()) {
//						System.out.println(m);
//					}
//					System.out.println();
//				}
//				break;
//			case 3: return;
//			default:
//				System.out.println("op1 no valida");
//				break;
//			}
//			System.out.println("\n");
//		}
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
