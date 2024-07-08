package com.tcna.gestioncursos.reports;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.tcna.gestioncursos.entity.Curso;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.util.List;

public class CursoExporterPDF {

    private List<Curso> cursos;

    public CursoExporterPDF(List<Curso> cursos) {
        this.cursos = cursos;
    }
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Titulo",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Descripcion",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Nivel",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Publicado",font));
        table.addCell(cell);
    }
    private void writeTableData(PdfPTable table) {
        for (Curso curso : cursos) {
            table.addCell(String.valueOf(curso.getId()));
            table.addCell(String.valueOf(curso.getTitulo()));
            table.addCell(String.valueOf(curso.getDescripcion()));
            table.addCell(String.valueOf(curso.getNivel()));
            table.addCell(String.valueOf(curso.isPublicado()));
        }
    }

    public void export(HttpServletResponse response) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.blue);
        font.setSize(18);
        Paragraph p = new Paragraph("Lista de Cursos", font);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.3f, 3.5f, 3.5f, 2.0f, 1.5f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table);
        document.add(table);
        document.close();
    }
}
