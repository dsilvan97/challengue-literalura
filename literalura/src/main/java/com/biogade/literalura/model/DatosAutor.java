package com.biogade.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombreAutor,
        @JsonAlias("birth_year") int anoNacimiento,
        @JsonAlias("death_year") int anoMuerte
) {

    @Override
    public String toString(){
        return "Nombre: " + this.nombreAutor +
                "\nAño de Nacimiento: " + this.anoNacimiento +
                "\nAño de Fallecimiento: " + this.anoMuerte;
    }
}