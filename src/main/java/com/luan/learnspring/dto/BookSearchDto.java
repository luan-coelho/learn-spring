package com.luan.learnspring.dto;

import java.time.LocalDate;

/**
 * DTO para busca de livros.
 * Obsersação: Não utilizar tipos primitidos, pois não aceitam null por padrão.
 */
public record BookSearchDto(String title, String author, Integer year, LocalDate publishedAt) {

}
