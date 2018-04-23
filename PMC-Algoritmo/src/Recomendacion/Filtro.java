package Recomendacion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Recomendacion.Comparador.Criterio;

public class Filtro {
	public static final String RUTA = "./data/IngSistemas.json";
	public static String inicio = "INICIO";
	private LinearProbingHashST<String, MateriaFiltro> listaMaterias;
	private List<MateriaFiltro> recado;
	private List<MateriaFiltro> codigosVistos;
	private Criterio[] criterios;
	private List<MateriaFiltro> p;
	private Pensum pen;

	public Filtro() throws Exception {
		listaMaterias = cargar(RUTA);
		criterios = Criterio.values();
		recado = new ArrayList<>();
		codigosVistos = new ArrayList<>();
		p = new ArrayList<>();
	}

	public void setCriterios(Criterio... criterios) {
		this.criterios = criterios;
	}


	public List<Pensum> proyección(Integer maxCreditod, Integer creditosMax) {
		List<Pensum> ret = new ArrayList<>();
		int pos = 0;
		ret.add(new Pensum("Semestre 1", recomendar(creditosMax)));
		maxCreditod-=creditosMax;
		while(maxCreditod - creditosMax >= 0) {
			ret.add(new Pensum("Semestre "+(pos+2), recomendarList(creditosMax)));
			pos++;
			maxCreditod-=creditosMax;
			for(MateriaFiltro m : ret.get(pos).getMaterias()) {
				recado.remove(m);
			}

		}
		return ret;
	}

	public List<MateriaFiltro> recomendar(Integer maxCreditos) {
		MateriaFiltro[] recom = sucesoresArray(listaMaterias.get(inicio).getSucesores());
		return darSeleccionadas(recom, maxCreditos);
	}

	public List<MateriaFiltro> recomendarList(Integer maxCreditos) {
		//recado.clear();

		p = pen.getMaterias();
		for (MateriaFiltro s : p)
			for(MateriaFiltro m : sucesoresList(s.getSucesores()))
				if(!codigosVistos.contains((m)) && !recado.contains(m))
					recado.add(m);
		MateriaFiltro[] recom2 = recado.toArray(new MateriaFiltro[] {});
		return darSeleccionadas(recom2, maxCreditos);
	}

	private List<MateriaFiltro> darSeleccionadas(MateriaFiltro[] array, Integer maxCreditos) {
		List<MateriaFiltro> ret = new ArrayList<>();
		Arrays.sort(array, Comparador.filtrar(criterios));
		for (int i = 0; i < array.length; i++) {
			if (maxCreditos - array[i].getCreditos() >= 0) {
				ret.add(array[i]);
				maxCreditos -= array[i].getCreditos();
				codigosVistos.add(array[i]);
				
			} 
		}
		return ret;
	}

	private List<MateriaFiltro> sucesoresList(List<String> codigos) {
		List<MateriaFiltro> ret = new ArrayList<>();
		for (String s : codigos) {
//			if(!recado.contains(listaMaterias.get(s)))
				ret.add(listaMaterias.get(s));
		}
		return ret;
	}

	private MateriaFiltro[] sucesoresArray(List<String> codigos) {
		MateriaFiltro[] ret = new MateriaFiltro[codigos.size()];
		int i = 0;
		for (String s : codigos)
//			if(!recado.contains(listaMaterias.get(s)))
			ret[i++] = listaMaterias.get(s);//recado.add();
		return ret;
	}

	//Cargar informacion

	private LinearProbingHashST<String, MateriaFiltro> cargar(String ruta) throws Exception {
		pen = new Pensum();
		List<MateriaFiltro> mf = new ArrayList<>();
		LinearProbingHashST<String, MateriaFiltro> ret = new LinearProbingHashST<>();
		Random r = new Random();
		BufferedReader br = new BufferedReader(new FileReader("./data/MateriasSucesor.json"));
		br.readLine();
		for (String linea = br.readLine(); linea != null; linea = br.readLine()) {
			linea = linea.substring(1, linea.length() - 2);
			String[] data = linea.split("/");
			String token = data[1].substring(0, data[1].length() - 1);
			data = data[0].split(",");
			MateriaFiltro m = new MateriaFiltro();
			m.setCodigo(re(data[0]));
			m.setPromedioEstimado(r.nextInt(501) / 100.0);
			m.setCreditos(Integer.parseInt(data[2]));
			m.setDificultadEstimada(m.getPromedioEstimado() / ((double) m.getCreditos()));
			m.setObligatoria(!data[1].equals("t"));
			m.setSucesores(sucesores(token));
			mf.add(m);
			ret.put(m.getCodigo(), m);
		}
		pen.setMaterias(mf);
		br.close();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonEjemplo = gson.toJson(mf);
        System.out.println(jsonEjemplo);
        String ruta1 = "./data/IngSistemas.json";
        PrintWriter pw = new PrintWriter(new FileWriter(ruta1));
        pw.print(jsonEjemplo);
        pw.close();
		return ret;
	}

	private List<String> sucesores(String token) {
		List<String> ret = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(token, ",");
		while (st.hasMoreElements())
			ret.add(re(st.nextToken()));
		return ret;
	}

	private String re(String r) {
		return r.substring(1, r.length() - 1);
	}

}
