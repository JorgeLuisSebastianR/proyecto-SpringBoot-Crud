package com.tcna.gestioncursos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "cursos")
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@ToString
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(length = 128,nullable = false)
    private String titulo;

    @Column(length = 256)
    private String descripcion;

    @Column(nullable = false)
    private int nivel;

    @Column(name = "estado_publicacion")
    private boolean isPublicado;

    public Curso(String descripcion, boolean isPublicado, int nivel, String titulo, Integer id) {
        this.descripcion = descripcion;
        this.isPublicado = isPublicado;
        this.nivel = nivel;
        this.titulo = titulo;
        this.id = id;
    }
    public Curso() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public boolean isPublicado() {
        return isPublicado;
    }

    public void setPublicado(boolean publicado) {
        isPublicado = publicado;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
