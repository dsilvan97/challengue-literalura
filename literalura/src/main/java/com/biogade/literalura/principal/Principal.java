package com.biogade.literalura.principal;

import com.biogade.literalura.model.Autor;
import com.biogade.literalura.model.DatosLibreria;
import com.biogade.literalura.model.DatosLibro;
import com.biogade.literalura.model.Libro;
import com.biogade.literalura.repository.AutorRepository;
import com.biogade.literalura.service.ConsumoAPI;
import com.biogade.literalura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    Scanner consola = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();

    private final String URL_BASE = "https://gutendex.com/books/";

    private ConvierteDatos conversor = new ConvierteDatos();

    private String json = consumoAPI.obtenerDatos(URL_BASE);

    private DatosLibreria datos = conversor.obtenerDatos(json, DatosLibreria.class);

    private AutorRepository repositorio;

    public Principal(AutorRepository repository) {
        this.repositorio = repository;
    }

    public void mostrarMenu() {
        //Buscar los datos generales de GUTENDEX:

        System.out.println("Actualmente GUTENDEX cuenta con un repositorio de más de " + datos.cantidadLibrosTotal() + " libros.");

        var opcion = -1;

        while (opcion != 0) {

            System.out.println("""
                    -----------------------------------
                    Elija la opción a través de un número:
                    1- Buscar Libro por Título.
                    2- Listar Libros Registrados.
                    3- Listar Autores Registrados.
                    4- Listar Autores Vivos en un Determinado Año.
                    5- Listar Libros por Idioma.
                    6- Ver Estadísticas de Descargas de Base de Datos.
                    7- Top 10 Libros Más Descargados.
                    8- Buscar Autor por Nombre.
                    
                    0- Salir.
                    """);

            try {
                opcion = consola.nextInt();
                consola.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Ingrese un número decimal:");
                consola.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;

                case 2:
                    listarLibrosRegistrados();
                    break;

                case 3:
                    listarAutoresRegistrados();
                    break;

                case 4:
                    listarAutoresVivosEnAño();
                    break;

                case 5:
                    listarLibrosPorIdioma();
                    break;

                case 6:
                    mostrarEstadisticasBD();
                    break;

                case 7:
                    top10MasDescargados();
                    break;

                case 8:
                    buscarAutorPorNombre();
                    break;

                case 0:
                    System.out.println("Saliendo de aplicación...");
                    break;

                default:
                    System.out.println("Debes Ingresar una Opción Válida...");
                    opcion = -1;
                    break;
            }

        }

        System.exit(0);

    }

    public void buscarLibroPorTitulo() {

        //Pedir el nombre del libro a buscar:
        System.out.println("Ingrese el libro del cual desees buscar información: (Recuerda que debe ser de  repositorio público.)");
        var nombreLibro = consola.nextLine();

        //Realizar búsqueda de información en API Gutendex:
        var jsonLibro = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        var datosLibro = conversor.obtenerDatos(jsonLibro, DatosLibreria.class);

        //Realizar búsqueda para confirmar se encuentre un libro con un título que contenga lo escrito por el usuario:
        Optional<DatosLibro> libroBuscado = datosLibro.datosLibros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();

        //Si encontró algo entonces entra en el If:
        if (libroBuscado.isPresent()) {

            //Objeto con los datos del libro encontrado para un mejor manejo:
            var libroEncontrado = libroBuscado.get();

            //Generar lista de autores con el fin de seleccionar solo el primer autor, si es que un libro tiene 2 autores o más:
            List<Autor> autores = libroEncontrado.datosAutor().stream()
                    .map(e -> new Autor(e.nombreAutor(), e.anoNacimiento(), e.anoMuerte()))
                    .collect(Collectors.toList());

            Autor autorSeleccionado = new Autor();

            if (autores.stream().count() == 0) {
                autorSeleccionado.setNombreAutor("Desconocido");
                autorSeleccionado.setAnoMuerte(0);
                autorSeleccionado.setAnoNacimiento(0);
            } else {
                autorSeleccionado = autores.get(0);
            }

            //Se crea objeto libro con los datos encontrados y se le añade el autor seleccionado anteriormente:
            Libro libro = new Libro(libroEncontrado.identificador(), libroEncontrado.titulo(), libroEncontrado.cantidadDescargas(), libroEncontrado.idioma().get(0), autorSeleccionado);
            System.out.println("libro Encontrado!!!");
            System.out.println(libro);

            //Se verifica si el autor ya existe en la base de datos para no crear duplicados:
            Optional<Autor> verificarAutorExistente = repositorio.findByNombreAutorEqualsIgnoreCase(autorSeleccionado.getNombreAutor());

            //Esta lista se crea para agregar libros al autor, ya que la clase Autor solo recibe LISTAS de LIBROS:
            List<Libro> listaLibros = new ArrayList<>();
            if (verificarAutorExistente.isPresent()) {

                //Verificar si el libro ya existe en la BD para no crear duplicados, buscando por título del libro y su respectivo autor:
                String titulo = libro.getTitulo();
                Autor autorExistente = verificarAutorExistente.get();
                Optional<Libro> libroExistente = repositorio.obtenerLibroPorTituloConAutor(titulo, autorExistente);

                if (!libroExistente.isPresent()) {

                    //Si ya existe el autor en la BD pero el libro no, se indica al objeto libro que cambie su autor por el encontrado
                    // y se añade a la lista para agregar a la BD:
                    libro.setAutor(autorExistente);
                    listaLibros.add(libro);

                    //En el autor existente se agrega el libro nuevo y se guarda en la base de datos:
                    autorExistente.setLibros(listaLibros);
                    System.out.println("El Autor Ya Se Encuentra en la Base de Datos.");
                    repositorio.save(verificarAutorExistente.get());
                    System.out.println("Libro Guardado con Éxito...");

                } else {
                    System.out.println("Libro ya registrado en la BD...");
                }

            } else {

                //Si no existe el autor en la base de datos se crea el mismo junto con su respectivo libro:
                listaLibros.add(libro);
                autorSeleccionado.setLibros(listaLibros);

                repositorio.save(autorSeleccionado);
                System.out.println("Libro y Autor guardado con éxito...");

            }


        }//Si no encontró nada entra en el else:
        else {
            System.out.println("Libro No Encontrado.");
        }

    }

    public void listarLibrosRegistrados() {
        repositorio.findAll().stream().
                forEach(e -> e.getLibros().stream()
                        .forEach(l -> System.out.println(l)));
    }

    public void listarAutoresRegistrados() {
        repositorio.findAll().stream()
                .forEach(System.out::println);
    }

    public void listarAutoresVivosEnAño() {

        System.out.println("Ingresa el año en el cual quieres ver qué autores estaban vivos:");

        try {

            var anoBuscado = consola.nextInt();
            consola.nextLine();
            repositorio.buscarAutoresVivosPorAno(anoBuscado).stream()
                    .forEach(System.out::println);

        } catch (InputMismatchException e) {
            System.out.println("Ingrese un número decimal...");
            consola.nextLine();
        }
    }

    public void listarLibrosPorIdioma() {

        System.out.println("""
                ------------------------------------
                Selecciona en que idioma deseas buscar los libros:
                es- Español
                en- Ingles
                fr- Frances
                pt- Portugues
                """);

        var idiomaSeleccionado = consola.nextLine();
        idiomaSeleccionado = idiomaSeleccionado.toLowerCase();

        String idiomaLibro = "";

        var opcion2 = -1;
        switch (idiomaSeleccionado) {
            case "es":
                idiomaLibro = "Español";
                break;

            case "en":
                idiomaLibro = "Inglés";
                break;

            case "fr":
                idiomaLibro = "Frances";
                break;

            case "pt":
                idiomaLibro = "Portugues";
                break;

            default:
                System.out.println("Opción Inválida...");
                opcion2 = 0;
                break;
        }

        if (opcion2 != 0) {
            System.out.println("Lista de libros en " + idiomaLibro + ":");
            repositorio.buscarLibroPorIdioma(idiomaSeleccionado).stream()
                    .forEach(System.out::println);
        }
    }

    public void mostrarEstadisticasBD() {

        List<Libro> libros = repositorio.findAll().stream()
                .flatMap(e -> e.getLibros().stream()
                        .map(l -> new Libro(l.getIdentificador(), l.getTitulo(), l.getCantidadDescargas(), l.getIdioma(), l.getAutor())))
                .collect(Collectors.toList());


        DoubleSummaryStatistics stats = libros.stream()
                .collect(Collectors.summarizingDouble(value -> value.getCantidadDescargas()));

        System.out.printf("""
                ********************ESTADÍSTICAS LIBRERÍA BIOGADE-GUTENDEX********************
                
                Cantidad de descargas máximas en un libro: %.2f
                Cantidad de descargas mínimas en un libro %.2f
                Cantidad media de descargas de la librería: %.2f
                ******************************************************************************
                """, stats.getMax(), stats.getMin(), stats.getAverage());
    }

    public void top10MasDescargados() {

        System.out.println("************************TOP 10 LIBROS MÁS DESCARGADOS************************\n");

        repositorio.mostrarTop10MasDescargados().stream().forEach(System.out::println);

        System.out.println("*****************************************************************************");
    }

    public void buscarAutorPorNombre(){

        System.out.println("Ingrese el nombre del autor que desea buscar:");
        String nombreBuscar = consola.nextLine();

        System.out.println("*************************Lista Autores que contengan en su nombre '" + nombreBuscar + "' *************************\n");

        repositorio.findByNombreAutorContainsIgnoreCase(nombreBuscar).stream()
                .forEach(System.out::println);

        System.out.println("*****************************************************************");
    }

}