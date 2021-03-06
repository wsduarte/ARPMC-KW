/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recomendacion;

import java.util.List;

/**
 *
 * @author ws.duarte
 */
public class MateriaFiltro {
    
    public String codigo; //llave
    public String nombre;
    public List<String> prerequisito;
    public List<String> corequisitos;
    //filtros <Con los que comparo>
    public Double promedioEstimado;
    public Double dificultadEstimada;
    public Boolean obligatoria;
    public Integer creditos;
    public Integer nivel;
    //materias que desbloquea
    
    private List<String> sucesores;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<String> getSucesores() {
        return sucesores;
    }

    public void setSucesores(List<String> sucesores) {
        this.sucesores = sucesores;
    }

    public Double getPromedioEstimado() {
        return promedioEstimado;
    }

    public void setPromedioEstimado(Double promedioEstimado) {
        this.promedioEstimado = promedioEstimado;
    }

    public Double getDificultadEstimada() {
        return dificultadEstimada;
    }

    public void setDificultadEstimada(Double dificultadEstimada) {
        this.dificultadEstimada = dificultadEstimada;
    }

    public Boolean getObligatoria() {
        return obligatoria;
    }

    public void setObligatoria(Boolean obligatoria) {
        this.obligatoria = obligatoria;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }
    
    public Integer getNivel() {
		return nivel;
	}
    
    public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}
    
    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<String> getPrerequisito() {
		return prerequisito;
	}

	public void setPrerequisito(List<String> prerequisito) {
		this.prerequisito = prerequisito;
	}

	public List<String> getCorequisitos() {
		return corequisitos;
	}

	public void setCorequisitos(List<String> corequisitos) {
		this.corequisitos = corequisitos;
	}

	@Override
    public int hashCode() {
    	return codigo.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
    	String comp = ((MateriaFiltro) obj).getCodigo();
    	return codigo.equalsIgnoreCase(comp);
    }

	@Override
	public String toString() {
		return "MateriaFiltro [codigo=" + codigo + ", nombre=" + nombre + ", prerequisito=" + prerequisito
				+ ", corequisitos=" + corequisitos + ", promedioEstimado=" + promedioEstimado + ", dificultadEstimada="
				+ dificultadEstimada + ", obligatoria=" + obligatoria + ", creditos=" + creditos + ", nivel=" + nivel
				+ ", sucesores=" + sucesores + "]";
	}    
}
