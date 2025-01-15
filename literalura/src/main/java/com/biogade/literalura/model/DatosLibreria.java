package com.biogade.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibreria(
        @JsonAlias("count") int cantidadLibrosTotal,
        @JsonAlias("results") List<DatosLibro> datosLibros
) {

    @Override
    public String toString() {
        return "Cantidad de Libros Total=" + cantidadLibrosTotal +
                ", Datos Libros = " + datosLibros +
                '}';
    }
}


