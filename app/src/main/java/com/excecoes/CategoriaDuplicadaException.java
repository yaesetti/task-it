package com.excecoes;

public class CategoriaDuplicadaException extends Exception {
    public CategoriaDuplicadaException() {
        super("Categoria Duplicada: Ja existe uma categoria com este nome!");
    }
}
