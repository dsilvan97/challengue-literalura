package com.biogade.literalura.repository;

import com.biogade.literalura.model.Autor;
import com.biogade.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {


    Optional<Autor> findByNombreAutorEqualsIgnoreCase(String nombreAutor);

    @Query("SELECT l FROM Autor a JOIN a.libros l WHERE l.titulo = :titulo AND l.autor = autor")
    Optional<Libro> obtenerLibroPorTituloConAutor(String titulo, Autor autor);

    @Query("SELECT a FROM Autor a WHERE :ano BETWEEN a.anoNacimiento AND a.anoMuerte")
    List<Autor> buscarAutoresVivosPorAno(Integer ano);

    @Query("SELECT l FROM Autor a JOIN a.libros l WHERE l.idioma = :idioma")
    List<Libro> buscarLibroPorIdioma(String idioma);

    @Query("SELECT l FROM Autor a JOIN a.libros l ORDER BY l.cantidadDescargas DESC LIMIT 10")
    List<Libro> mostrarTop10MasDescargados();

    List<Autor> findByNombreAutorContainsIgnoreCase(String nombreAutor);
}
