package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.*;
import com.viju.andaluciaskills.entity.*;
import com.viju.andaluciaskills.exceptions.prueba.PruebaNotFoundException;
import com.viju.andaluciaskills.repository.*;
import com.viju.andaluciaskills.mapper.EvaluacionMapper;
import com.viju.andaluciaskills.mapper.ItemMapper;
import com.viju.andaluciaskills.mapper.PruebaMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Imports para el PDF
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PruebaService {

    @Autowired
    private PruebaRepository pruebaRepository; 

    @Autowired
    private PruebaMapper pruebaMapper;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private EvaluacionItemRepository evaluacionItemRepository;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private EvaluacionMapper evaluacionMapper;

    @Autowired
    private EvaluacionService evaluacionService;

    // SAVE
    // Metodo para guardar una Prueba
    public PruebaDTO save(PruebaDTO dto) {
        Prueba prueba = pruebaMapper.toEntity(dto); // Convertimos el DTO a Entity
        Prueba savedPrueba = pruebaRepository.save(prueba); // Guardamos en la BD
        return pruebaMapper.toDto(savedPrueba); // Convertimos el Entity a DTO y lo devolvemos
    }


    // FIND BY ID
    // Metodo para buscar una Prueba por su ID
    public Optional<PruebaDTO> findById(Integer id) {
        return pruebaRepository.findById(id) // Buscamos en la BD por ID
                .map(pruebaMapper::toDto); // Convertimos el Entity a DTO mediante el Mapper y lo devolvemos
    }


    // FIND BY ID ESPECIALIDAD
    // Metodo para buscar una Prueba por su ID de Especialidad
    public List<PruebaDTO> findByEspecialidadId(Integer idEspecialidad) {
        List<Prueba> pruebas = pruebaRepository.findByEspecialidadId(idEspecialidad);
        return pruebaMapper.toDtoList(pruebas); // Convertimos la lista de Entity a DTO mediante el Mapper y lo
                                                // devolvemos
    }


    // FIND ALL
    // Metodo para buscar todas las Pruebas
    public List<PruebaDTO> findAll() {
        return pruebaMapper.toDtoList(pruebaRepository.findAll()); // Convertimos la lista de Entity a DTO mediante el
                                                                   // Mapper y lo devolvemos
    }


    // UPDATE
    // Metodo para actualizar una Prueba existente
    public PruebaDTO update(PruebaDTO dto) {
        Prueba prueba = pruebaMapper.toEntity(dto);
        Prueba updatedPrueba = pruebaRepository.save(prueba);
        return pruebaMapper.toDto(updatedPrueba);
    }


    // DELETE
    // Metodo para eliminar una Prueba por su ID
    public void delete(Integer id) {
        pruebaRepository.deleteById(id);
    }


    // CREAR PRUEBA CON ITEMS
    // Metodo para crear una Prueba con Items asociados
    public PruebaDTO crearPruebaItem(PruebaDTO PruebaDTO, List<ItemDTO> ItemDTO) {
        try {
            // Creamos la prueba
            PruebaDTO prueba = save(PruebaDTO);
            System.out.println("Prueba guardada con ID: " + prueba.getIdPrueba());

            // Asignamos el ID de la prueba a los items
            ItemDTO.forEach(item -> {
                item.setPrueba_id_prueba(prueba.getIdPrueba());
            });

            // Guardamos los items
            List<ItemDTO> items = itemRepository.saveAll(ItemDTO.stream() // Convertimos la lista de ItemDTO en un flujo
                                                                          // de datos para su procesamiento
                    .map(itemMapper::toEntity) // Convertimos los DTOs a Entities mediante el Mapper
                    .collect(Collectors.toList())) // Guardamos las entidades en la Lista
                    .stream() // Stream con los Items
                    .map(itemMapper::toDto) // Convertimos las Entities a DTOs mediante el Mapper
                    .collect(Collectors.toList()); // Guardamos los DTOs en la Lista

            // Chekeamos los IDs creados
            System.out.println("Items creados con IDs: " +
                    items.stream()
                            .map(item -> item.getIdItem().toString()) // Obtiene IDs de los Items
                            .collect(Collectors.joining(", "))); // Separa por comas ","

            return prueba;

        } catch (Exception e) {
            System.err.println("Error en crearPruebaConItems: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    // COMPROBAR QUE LA EVALUACION EXISTA
    public boolean existeEvaluacion(Integer idPrueba, Integer idParticipante) {
        return evaluacionRepository.existsByPruebaIdAndParticipanteId(idPrueba, idParticipante);
    }


    // CREAR EVALUACION PARA LA PRUEBA
    // Metodo para crear una Evaluacion para una Prueba existente
    public EvaluacionDTO crearEvaluacionParaPrueba(Integer idPrueba, Integer idParticipante, Integer idUser) {
        try {
            // Log para debugging y control 
            System.out.println("Creando evaluación con: pruebaId=" + idPrueba +
                    ", participanteId=" + idParticipante +
                    ", userId=" + idUser);

            // Creamos la evaluacion con los datos recibidos
            EvaluacionDTO evaluacion = new EvaluacionDTO();
            evaluacion.setPrueba_idPrueba(idPrueba); // Asignamos el ID de la prueba
            evaluacion.setParticipante_idParticipante(idParticipante); // Asignamos el ID del participante
            evaluacion.setUser_idUser(idUser); // Asignamos el ID del usuario
            evaluacion.setNotaFinal(0.0); // Inicializamos la nota final a 0

            // Guardamos la evaluación
            EvaluacionDTO evaluacionGuardada = evaluacionService.save(evaluacion);
            System.out.println("Evaluación guardada: " + evaluacionGuardada);

            // Devolvemos la evaluación guardada
            return evaluacionGuardada;

        } catch (Exception e) {
            System.err.println("Error en crearEvaluacionParaPrueba: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    // ACTUALIZAR NOTAS DE LA EVALUACION
    public EvaluacionDTO actualizarNotasEvaluacion(Integer idEvaluacion, List<EvaluacionItemDTO> evaluacionItems) {
        try {
            // Log para debugging y control
            System.out.println("Procesando evaluacionItems: " + evaluacionItems);

            // Verificamos si existe la evaluación y la obtenemos
            Evaluacion evaluacion = evaluacionRepository.findById(idEvaluacion)
                    .orElseThrow(() -> new RuntimeException("Evaluación no encontrada")); // Si no existe, lanzamos una excepción

            // Verificamos que no tenga items ya evaluados
            if (evaluacionItemRepository.existsById(idEvaluacion)) {
                throw new RuntimeException("Esta evaluación ya tiene items evaluados"); // Si ya tiene items evaluados, lanzamos una excepción
            }

            // Guardamos cada item de evaluación
            List<EvaluacionItem> evaluacionesGuardadas = evaluacionItems.stream()
                    .map(dto -> {
                        EvaluacionItem entity = new EvaluacionItem(); // Creamos una nueva entidad
                        entity.setValoracion(dto.getValoracion()); // Asignamos la valoración
                        entity.setEvaluacion_idEvaluacion(idEvaluacion); // Asignamos el ID de la evaluación
                        entity.setItem_idItem(dto.getItem_idItem()); // Asignamos el ID del item

                        return evaluacionItemRepository.save(entity); // Guardamos la entidad en la BD
                    })
                    .collect(Collectors.toList()); // Guardamos las entidades en la Lista

            // Calculamos la nota final !!!
            double notaFinal = evaluacionesGuardadas.stream() // Flujo de datos de EvaluacionItem
                    .mapToDouble(ei -> { // Convertimos cada EvaluacionItem a un valor double
                        
                        // Obtenemos el Item por su repositorio
                        Item item = itemRepository.findById(ei.getItem_idItem())
                                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

                        // Calculamos la nota ponderada
                        return (ei.getValoracion() * item.getPeso()) / 100.0; // valor x peso / 100
                    })
                    .sum(); // Sumamos todos los valores calculados

            // Actualizamos y guardamos la nota final en la evaluación
            evaluacion.setNotaFinal(notaFinal);
            evaluacion = evaluacionRepository.save(evaluacion);

            // Convertimos a DTO y devolvemos el resultado
            return evaluacionMapper.toDto(evaluacion);

        } catch (Exception e) {
            System.err.println("Error al actualizar notas: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    // GENERAR PLANTILLA DE EVALUACION

    /*
     * Vamos a seguir los siguientes pasos:
     * 1: Recogemos los datos de la prueba y sus items
     * 2: Creamos un documento PDF, lo almacenamos en memoria, lo configuramos y lo abrimos
     * 3: Agregamos el título de la plantilla
     * 4: Agregamos la información de la prueba (Enunciado y Puntuación Máxima)
     * 5: Creamos la tabla con 4 columnas (Criterio de Evaluación, Peso (en %), Grados de Consecución y Valoración)
     * 6: Procesamos los items (si los hay) y los agregamos a la tabla
     * 7: Agregamos la tabla al PDF
     * 8: Agregamos la puntuación total
    */ 
    public byte[] generarPlantillaEvaluacion(Integer idPrueba) throws IOException, com.itextpdf.text.DocumentException {
        
        // RECOGEMOS DATOS
        // Buscamos la prueba por ID y si no la encontramos lanzamos excepción
        Prueba prueba = pruebaRepository.findById(idPrueba)
                .orElseThrow(() -> new PruebaNotFoundException(idPrueba));

        // Obtenemos todos los items asociados a la prueba
        List<Item> items = itemRepository.findByPruebaIdPrueba(idPrueba);

        // GESTION DEL PDF
        // Creamos un nuevo documento
        Document document = new Document();

        // Stream para almacenar el PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        // Configuramos el escritor PDF
        PdfWriter.getInstance(document, baos);
        
        // Abrimos el documento para escribir
        document.open();


        // Agregamos el título centrado
        Paragraph titulo = new Paragraph("Plantilla para Evaluación");

        titulo.setAlignment(Element.ALIGN_CENTER); // Alinear al centro
        document.add(titulo); // Agregamos el titulo al documento
        document.add(new Paragraph("\n")); // Espaciamos


        // Agregamos información de la prueba
        document.add(new Paragraph("Enunciado: " + prueba.getEnunciado()));
        document.add(new Paragraph("Puntuación Máxima: " + prueba.getPuntuacionMaxima()));
        document.add(new Paragraph("\n")); // Espaciamos

        // Creamos una tabla con 4 columnas
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100); // Decimos que ocupe todo el ancho

        // Configuramos el estilo de las cabeceras
        PdfPCell headerCell = new PdfPCell();
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell.setPadding(5);


        // Definimos y agregamos las cabeceras
        String[] headers = { "Criterio de Evaluación", "Peso (%)",
                "Grados de Consecución", "Valoración" };

        for (String header : headers) {
            headerCell.setPhrase(new Phrase(header)); // Agregamos el texto a la celda
            table.addCell(headerCell); // Agregamos la celda a la tabla
        }

        // Si hay items, los procesamos uno a uno
        if (items != null && !items.isEmpty()) {
            for (Item item : items) {
                System.out.println("Procesando item: " + item.getDescripcion());

                // Configuramos el estilo de las celdas
                PdfPCell cell = new PdfPCell();
                cell.setPadding(5);

                // Agregamos la descripción del item
                cell.setPhrase(new Phrase(item.getDescripcion()));
                table.addCell(cell);

                // Agregamos el peso del item
                cell.setPhrase(new Phrase(String.valueOf(item.getPeso())));
                table.addCell(cell);

                // Agregamos los grados de consecución
                cell.setPhrase(new Phrase(String.valueOf(item.getGradosConsecucion())));
                table.addCell(cell);

                // Agregamos el hueco para valoración
                cell.setPhrase(new Phrase("_______"));
                table.addCell(cell);
            }
        } else {
            System.out.println("No se encontraron items para la prueba"); // Debug
        }

        // Agregamos la tabla al documento
        document.add(table);
        document.add(new Paragraph("\n")); // Espaciamos el documento

        // Agregamos la Puntuación Total
        Paragraph total = new Paragraph("Puntuación Total: _____________");
        total.setAlignment(Element.ALIGN_RIGHT); // Alineamos a la derecha
        document.add(total); // Agregamos al documento

        // Cerramos el documento
        document.close();

        // Devolvemos el PDF como array de bytes para su descarga o visualización en el navegador
        return baos.toByteArray();
    }
}