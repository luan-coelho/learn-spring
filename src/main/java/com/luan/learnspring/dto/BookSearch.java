package com.luan.learnspring.dto;

import java.time.LocalDate;

public record BookSearch(String title, String author, int year, LocalDate publishedAt) {

}
