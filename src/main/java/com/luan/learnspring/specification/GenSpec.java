package com.luan.learnspring.specification;

import com.luan.learnspring.util.StringUtils;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

/**
 * Classe utilitária para criar especificações JPA.
 */
public final class GenSpec {

    /**
     * Cria uma especificação para verificar a igualdade de um campo com um valor dado.
     *
     * @param fieldName o nome do campo
     * @param value     o valor a ser comparado
     * @param <T>       o tipo da entidade
     * @return uma especificação para verificação de igualdade
     */
    public static <T> Specification<T> equals(String fieldName, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (value == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(getPath(root, fieldName), value);
        };
    }

    /**
     * Cria uma especificação para verificar se um campo é semelhante a um valor dado.
     *
     * @param fieldName o nome do campo
     * @param value     o valor a ser comparado
     * @param <T>       o tipo da entidade
     * @return uma especificação para verificação de semelhança
     */
    public static <T> Specification<T> like(String fieldName, String value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (value == null) {
                return criteriaBuilder.conjunction();
            }

            String processedValue = StringUtils.removeAccents(value);
            Expression<String> expression = getPath(root, fieldName).as(String.class);

            return criteriaBuilder.like(expression, processedValue);
        };
    }

    /**
     * Cria uma especificação para verificar se um campo contém um valor dado.
     *
     * @param fieldName o nome do campo
     * @param value     o valor a ser comparado
     * @param <T>       o tipo da entidade
     * @return uma especificação para verificação de conteúdo
     */
    public static <T> Specification<T> contains(String fieldName, String value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (value == null) {
                return criteriaBuilder.conjunction();
            }

            String processedValue = StringUtils.removeAccents(value);
            Expression<String> expression = getPath(root, fieldName).as(String.class);

            return criteriaBuilder.like(expression, "%" + processedValue + "%");
        };
    }

    /**
     * Cria uma especificação para verificar se um campo contém um valor dado, ignorando maiúsculas e minúsculas.
     *
     * @param fieldName o nome do campo
     * @param value     o valor a ser comparado
     * @param <T>       o tipo da entidade
     * @return uma especificação para verificação de conteúdo ignorando maiúsculas e minúsculas
     */
    public static <T> Specification<T> containsIgnoreCase(String fieldName, String value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (value == null) {
                return criteriaBuilder.conjunction();
            }

            String processedValue = StringUtils.removeAccents(value.toLowerCase());
            Expression<String> expression = criteriaBuilder.lower(getPath(root, fieldName).as(String.class));

            return criteriaBuilder.like(expression, "%" + processedValue + "%");
        };
    }

    /**
     * Método auxiliar para obter o caminho de um campo, suportando campos aninhados.
     *
     * @param root      o tipo raiz na cláusula from
     * @param fieldName o nome do campo
     * @param <T>       o tipo da entidade
     * @return o caminho para o campo
     */
    private static <T> Path<?> getPath(Root<T> root, String fieldName) {
        String[] parts = fieldName.split("\\.");
        Path<?> path = root;
        for (String part : parts) {
            path = path.get(part);
        }
        return path;
    }

}