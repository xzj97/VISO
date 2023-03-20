///////////////////////////////////////////////////////////////////////
//                       Paquete fundamentos                         //
//  Conjunto de clases para hacer entrada/salida sencilla en Java    //
//                                                                   //
//                     Copyright (C) 2005-2016                       //
//                 Universidad de Cantabria, SPAIN                   //
//                                                                   //
// Authors: Michael Gonzalez   <mgh@unican.es>                       //
//          Mariano Benito Hoz <mbenitohoz at gmail dot com>         //
//                                                                   //
// This program is free software; you can redistribute it and/or     //
// modify it under the terms of the GNU General Public               //
// License as published by the Free Software Foundation; either      //
// version 3 of the License, or (at your option) any later version.  //
//                                                                   //
// This program is distributed in the hope that it will be useful,   //
// but WITHOUT ANY WARRANTY; without even the implied warranty of    //
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU //
// General Public License for more details.                          //
///////////////////////////////////////////////////////////////////////

import java.awt.Color;

/**
 * @author sofia
 * @version 1.0
 * 
 *          Crear una figura que puede ajustar su color
 * 
 */
public class ColorFig {

	private Color col;

	// Constructor privado, que obtiene el color
	// a partir de un objeto de la clase Color
	/**
	 * @param el color que quieres tener el figura creado
	 */
	private ColorFig(Color col) {
		this.col = new Color(col.getRed(), col.getGreen(), col.getBlue());
	}

	// Retorna el color en un objeto de la clase Color
	/**
	 * @return el color de la figura
	 */
	Color colorOf() {
		return col;
	}

	public ColorFig(int r, int g, int b) {
		this.col = new Color(r, g, b);
	}

	public static final ColorFig negro = new ColorFig(Color.black);
	public static final ColorFig azul = new ColorFig(Color.blue);
	public static final ColorFig grisOscuro = new ColorFig(Color.darkGray);
	public static final ColorFig gris = new ColorFig(Color.gray);
	public static final ColorFig verde = new ColorFig(Color.green);
	public static final ColorFig grisClaro = new ColorFig(Color.lightGray);
	public static final ColorFig magenta = new ColorFig(Color.magenta);
	public static final ColorFig naranja = new ColorFig(Color.orange);
	public static final ColorFig rosa = new ColorFig(Color.pink);
	public static final ColorFig rojo = new ColorFig(Color.red);
	public static final ColorFig blanco = new ColorFig(Color.white);
	public static final ColorFig amarillo = new ColorFig(Color.yellow);

	public ColorFig masOscuro() {
		return new ColorFig(col.darker());
	}

	public ColorFig masClaro() {
		return new ColorFig(col.brighter());
	}

}
