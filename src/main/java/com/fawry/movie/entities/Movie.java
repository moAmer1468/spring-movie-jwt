package com.fawry.movie.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String year;

    @Column(unique = true, nullable = false)
    private String imdbId;

    private String type;   // movie, series, episode...

    private String posterUrl;

}
