package com.project.java_backend.model;

import jakarta.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "movies")
public class Movie {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Movie title is required")
    @Size(max = 200, message = "Movie title cannot exceed 200 characters")
    private String title;

    @Lob
    private String description;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer duration; // in minutes

    @Size(max = 10, message = "Rating cannot exceed 10 characters")
    private String rating; // e.g., "PG-13", "R"

    @NotNull
    private boolean isPublic = false; // Controls visibility; defaults to false for early access

    private String imageFileName;

    // Relationships
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("movie-showtimes")
    @JsonIgnore
    private List<Showtime> showtimes;

    // Constructors
    public Movie() {
        // Default constructor
    }

    public Movie(String title, String description, Integer duration, String rating, String imageFileName) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.rating = rating;
        this.imageFileName = imageFileName;
        this.isPublic = false; // Default to early access
    }

    // ID
    public Long getId() {
        return id;
    }

    // Title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Duration
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    // Rating
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    // isPublic
    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    // Showtimes
    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<Showtime> showtimes) {
        this.showtimes = showtimes;
    }

    // image filename
    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
