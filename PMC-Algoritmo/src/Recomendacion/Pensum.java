package Recomendacion;

import java.util.List;

public class Pensum {
	
	private String numero;
	private List<MateriaFiltro> materias;
	
	
	
	public Pensum(String numero, List<MateriaFiltro> materias) {
		super();
		this.numero = numero;
		this.materias = materias;
	}
	
	public Pensum()
	{
		super();
		this.numero = numero;
		this.materias = materias;
	}
	
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public List<MateriaFiltro> getMaterias() {
		return materias;
	}
	public void setMaterias(List<MateriaFiltro> materias) {
		this.materias = materias;
	}
	

}
