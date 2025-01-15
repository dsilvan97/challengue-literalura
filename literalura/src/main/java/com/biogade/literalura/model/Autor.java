package com.biogade.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombreAutor;
    private Integer anoNacimiento;
    private Integer anoMuerte;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){}

    public Autor(String nombreAutor, int anoNacimiento, int anoMuerte) {
        this.nombreAutor = nombreAutor;
        this.anoNacimiento = anoNacimiento;
        this.anoMuerte = anoMuerte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public int getAnoNacimiento() {
        return anoNacimiento;
    }

    public void setAnoNacimiento(Integer anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    public int getAnoMuerte() {
        return anoMuerte;
    }

    public void setAnoMuerte(Integer anoMuerte) {
        this.anoMuerte = anoMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "********************AUTOR********************\n" +
                "Nombre del Autor = " + nombreAutor + "\n" +
                "Año de Nacimiento = " + anoNacimiento + "\n" +
                "Año de Fallecimiento =" + anoMuerte + "\n" +
                "Cantidad Total de Libro Guardados = " + libros.stream().count() + "\n" +
                "********************************************\n";
    }
}
