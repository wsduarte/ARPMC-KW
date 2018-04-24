package pruebas;

import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import Estructuras.LinearProbingHashST;
import Recomendacion.Comparador.Criterio;
import Recomendacion.Filtro;
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
	private List<MateriaFiltro> listaN1;
	private List<String> codigosVistos;
	private List<MateriaFiltro> posibles;
	
	//-------------------------------------------------
	// Constructor
	//-------------------------------------------------
	public Cargar() {
		try {
			listaN1 = new ArrayList<>();
			listaMaterias = cargar(RUTA);
			codigosVistos = new ArrayList<>();
			posibles = new ArrayList<>();
		} catch (Exception e) { e.printStackTrace(); }
	}

	//-------------------------------------------------
	// Métodos
	//-------------------------------------------------
	
	
	

	
	// Métodos para sucesores
	private void cargarSucesores() {
		MateriaFiltro materiaaInicial = listaMaterias.get(inicio);
		for(String codigo : materiaaInicial.getSucesores()) {
			MateriaFiltro m = listaMaterias.get(codigo);
			if(!posibles.contains(m)) posibles.add(m);
		}
			
	}
	
	private void cargarSucesores(String codigoMateria) {
		MateriaFiltro materia = listaMaterias.get(codigoMateria);
		for(String codigo : materia.getSucesores()) {
			MateriaFiltro m = listaMaterias.get(codigo);
			if(!posibles.contains(m)) posibles.add(listaMaterias.get(codigo));
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
		for (MateriaFiltro m : new GsonBuilder().setPrettyPrinting().create().fromJson(array, MateriaFiltro[].class)) {
			ret.put(m.getCodigo(), m);
			if(m.getNivel() == 1) listaN1.add(m);
		}
		return ret;
	}

	
	// Main
	public static void main(String[] args) throws Exception {
		System.out.println("-----------------------------");
		Cargar c = new Cargar();
		c.cargarSucesores();
		c.cargarSucesores("MATE1203");
		c.cargarSucesores("ISIS1204");
		c.cargarSucesores("LENG1501");
		c.cargarSucesores("DERE1300");
		c.cargarSucesores("CBU1");
		c.cargarSucesores("CLE1");
		c.cargarSucesores("MBIO-QUIM");
		
		MateriaFiltro[] ms = new MateriaFiltro[c.posibles.size()];
		int i = 0;
		for(MateriaFiltro m :  c.posibles) {
			ms[i++] = m;
			System.out.println(m.getCodigo());
		}
		
		Arrays.sort(ms, new Comparator<MateriaFiltro>() {

			@Override
			public int compare(MateriaFiltro o1, MateriaFiltro o2) {
				return o1.getCodigo().compareTo(o2.getCodigo());
			}
		});
		System.out.println("-------------------------");
		for (int j = 0; j < ms.length; j++) {
			System.out.println(ms[j].getCodigo());
		}
		
	}

}
