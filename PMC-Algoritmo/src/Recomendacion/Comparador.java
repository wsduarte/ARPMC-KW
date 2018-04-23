/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recomendacion;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

/**
 *
 * @author ws.duarte
 */
public class Comparador {

	public static enum Criterio {
		PROMEDIO("promedioEstimado", "PROMEDIO", -1), 
		DIFICULTAD("dificultadEstimada", "DIFICULTAD", 1), 
		CREDITOS("creditos", "CREDITOS", 1),
//		ADELANTAR
		;

		private final String atributo;
		private final String nombre;
		private final int direccion;

		Criterio(String atributo, String nombre, int direccion) {
			this.atributo = atributo;
			this.nombre = nombre;
			this.direccion = direccion;
		}

		public String getAtributo() {
			return atributo;
		}

		public String getNombre() {
			return nombre;
		}

		public int getDireccion() {
			return direccion;
		}

		public static Criterio buscar(String nombre) {
			for (Criterio c : Criterio.values()) {
				if (c.getNombre().equalsIgnoreCase(nombre))
					return c;
			}
			return null;
		}
	}

	public static Comparator<MateriaFiltro> filtrar(Criterio... criterios) {
		return (MateriaFiltro o1, MateriaFiltro o2) -> {
			try {
				int ret = 0;
				Field crit = null;
				for (Criterio criterio : criterios) {
					crit = MateriaFiltro.class.getField(criterio.getAtributo());
					ret = criterio.getDireccion() * ((int) crit.getType().getMethod("compareTo", crit.getType()).invoke(crit.get(o1), crit.get(o2)));
					if (ret != 0) break;
				}
				return ret;
			} catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NoSuchMethodException
					| SecurityException | InvocationTargetException e) {
				throw new IllegalArgumentException("No se especifico adecuadamente el atributo de filtro");
			}
		};
	}

}
