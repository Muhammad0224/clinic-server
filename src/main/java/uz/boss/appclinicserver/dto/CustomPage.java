package uz.boss.appclinicserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author Muhammad Mo'minov
 * 06.11.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomPage<T> {
    private Collection<T> content; // Elementlar
    private int numberOfElements;  // Current page dagi elementlar soni
    private int number;            // Current page number
    private long totalElements;    // Barcha elementlar soni
    private int totalPages;        // Barcha page lar soni
    private int size;              // Nechta so'ragani
}
