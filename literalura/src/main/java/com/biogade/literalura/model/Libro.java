package com.biogade.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int identificador;
    private String titulo;
    private Double cantidadDescargas;
    private String idioma;
    @ManyToOne
    private Autor autor;

    public Libro(){}

    public Libro(int identificador, String titulo, Double cantidadDescargas, String idioma, Autor autor) {
        this.identificador = identificador;
        this.titulo = titulo;
        this.cantidadDescargas = cantidadDescargas;
        this.idioma = idioma;
        this.autor = autor;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(Double cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "********************LIBRO*********************\n" +
                "Identificador = " + identificador + "\n" +
                "Titulo = " + titulo + "\n" +
                "Cantida De Descargas = " + cantidadDescargas + "\n" +
                "Idioma = " + idioma + "\n" +
                "Autor = " + autor.getNombreAutor() + "\n" +
                "********************************************\n";
    }
}
