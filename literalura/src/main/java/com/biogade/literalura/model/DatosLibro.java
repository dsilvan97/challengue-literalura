package com.biogade.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("id") int identificador,
        @JsonAlias("title") String titulo,
        @JsonAlias("download_count") Double cantidadDescargas,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("authors") List<DatosAutor> datosAutor
) {

    @Override
    public String toString(){

        return "\n*********LIBRO*********" +
                "\nTítulo: " + this.titulo() +
                "\nAutores: " + this.datosAutor() +
                "\nCantidad de Descargas: " + this.cantidadDescargas() +
                "\nIdioma: " + this.idioma + "\n" +
                "**********************";
    }
}
