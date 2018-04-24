package pruebas;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import Estructuras.LinearProbingHashST;
import Recomendacion.Comparador;
import Recomendacion.Comparador.Criterio;
import Recomendacion.MateriaFiltro;

public class Cargar {

	//-------------------------------------------------
	// Constantes
	//-------------------------------------------------
	public static final String RUTA = "./data/IngSistemas.json";
	private final String inicio = "INICIO";

	//-------------------------------------------------
	// Atributos
	//-------------------------------------------------
	private LinearProbingHashST<String, MateriaFiltro> listaMaterias;
	private LinearProbingHashST<String, MateriaFiltro> listaN1;
	private LinearProbingHashST<String, String> codigosVistos;
	private List<MateriaFiltro> posibles;
	private List<MateriaFiltro> recomendacion;

	//-------------------------------------------------
	// Constructor
	//-------------------------------------------------
	public Cargar() {
		try {
			listaN1 = new LinearProbingHashST<>();
			listaMaterias = cargar(RUTA);
			codigosVistos = new LinearProbingHashST<>();
			posibles = new ArrayList<>();
			recomendacion = new ArrayList<>();
		} catch (Exception e) { e.printStackTrace(); }
	}

	//-------------------------------------------------
	// Métodos
	//-------------------------------------------------


	// Recomendación
	public void recomendar(int creditos, Criterio...criterios) {
		recomendacion = new ArrayList<>();
		cargarSucesores();
		posibles.sort(Comparador.filtrar(criterios));
		selección(creditos);
	}
	
	public void recomendar(int creditos, List<MateriaFiltro> materiasVistas, Criterio...criterios) {
		recomendacion = new ArrayList<>();
		cargarSucesores(materiasVistas);
		posibles.sort(Comparador.filtrar(criterios));
		selección(creditos);
	}
	
	
	//Selección 
	private void selección(int creditos) {
		for(int i = 0; i < posibles.size(); i++) {
			if(creditos-posibles.get(i).getCreditos() >= 0 && evaluarPredicados(posibles.get(i))) {
				MateriaFiltro m = posibles.remove(i);
				recomendacion.add(m);
				codigosVistos.put(m.getCodigo(), m.getCodigo());
				if(m.getNivel() == 1) listaN1.delete(m.getCodigo());
				creditos -= m.getCreditos();
			}
		}
	}


	// Predicados
	private boolean evaluarPredicados(MateriaFiltro materia) {
		return pVerPrerequisitos(materia) && pReglaNivel(materia);
	}

	private boolean pVerPrerequisitos(MateriaFiltro materia) {
		boolean ret =  true;
		for(int i = 0;  i < materia.getPrerequisito().size() && ret; i++) {
			ret = codigosVistos.get(materia.getPrerequisito().get(i)) != null;
		}
		return ret;
	}

	private boolean pReglaNivel(MateriaFiltro materia) {
		if(materia.getNivel() == 3) {
			return listaN1.isEmpty();
		} else {
			return true;
		}
	}


	// Métodos para sucesores
	private void cargarSucesores() {
		MateriaFiltro materiaInicial = listaMaterias.get(inicio);
		for(String codigo : materiaInicial.getSucesores()) {
			MateriaFiltro m = listaMaterias.get(codigo);
			if(!posibles.contains(m.getCodigo()) && !codigosVistos.contains(m.getCodigo())) posibles.add(m);
		}

	}

	private void cargarSucesores(String codigoMateria) {
		MateriaFiltro materia = listaMaterias.get(codigoMateria);
		for(String codigo : materia.getSucesores()) {
			MateriaFiltro m = listaMaterias.get(codigo);
			if(!posibles.contains(m.getCodigo()) && !codigosVistos.contains(m.getCodigo())) posibles.add(m);
		}
	}

		private void cargarSucesores(List<MateriaFiltro> materias) {
			for (MateriaFiltro materiaFiltro : materias) {
				cargarSucesores(materiaFiltro.codigo);
			}
		}

	// Método de cargue
	private LinearProbingHashST<String, MateriaFiltro> cargar(String ruta) throws Exception {
		LinearProbingHashST<String, MateriaFiltro> ret = new LinearProbingHashST<>();
		JsonParser parser = new JsonParser();
		FileReader fr = new FileReader(ruta);
		JsonElement datos = parser.parse(fr);
		JsonArray array = datos.getAsJsonArray();
		MateriaFiltro[] mf = new GsonBuilder().setPrettyPrinting().create().fromJson(array, MateriaFiltro[].class);
		for (int i = 0; i < mf.length; i++) {
			ret.put(mf[i].getCodigo(), mf[i]);
			if(mf[i].getNivel() == 1) listaN1.put(mf[i].getCodigo(), mf[i]);
		}
		return ret;
	}

	// Main
	public static void main(String[] args) throws Exception {
		Cargar c = new Cargar();
		
		c.recomendar(20, Criterio.DIFICULTAD);
		int creditos = 0;
		for(MateriaFiltro m : c.recomendacion) {
			creditos += m.getCreditos();
			System.out.println(m.getNombre()+"\t\t"+m.getCreditos());
		}
		System.out.println("\nCreditos: "+creditos);
		
		c.recomendar(20, c.recomendacion, Criterio.DIFICULTAD);
		creditos = 0;
		for(MateriaFiltro m : c.recomendacion) {
			creditos += m.getCreditos();
			System.out.println(m.getCodigo()+"\t"+ m.getNombre()+"\t\t"+m.getCreditos());
		}
		
		System.out.println("\nCreditos: "+creditos);
		
		c.recomendar(20, c.recomendacion, Criterio.DIFICULTAD);
		creditos = 0;
		for(MateriaFiltro m : c.recomendacion) {
			creditos += m.getCreditos();
			System.out.println(m.getCodigo()+"\t"+ m.getNombre()+"\t\t"+m.getCreditos());
		}
		
		System.out.println("\nCreditos: "+creditos);
		
		c.recomendar(20, c.recomendacion, Criterio.DIFICULTAD);
		creditos = 0;
		for(MateriaFiltro m : c.recomendacion) {
			creditos += m.getCreditos();
			System.out.println(m.getCodigo()+"\t"+ m.getNombre()+"\t\t"+m.getCreditos());
		}
		
		System.out.println("\nCreditos: "+creditos);
		
		c.recomendar(20, c.recomendacion, Criterio.DIFICULTAD);
		creditos = 0;
		for(MateriaFiltro m : c.recomendacion) {
			creditos += m.getCreditos();
			System.out.println(m.getCodigo()+"\t"+ m.getNombre()+"\t\t"+m.getCreditos());
		}
		
		System.out.println("\nCreditos: "+creditos);
	}

}
