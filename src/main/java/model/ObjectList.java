package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder(toBuilder = true)
@Data
public abstract class ObjectList<T> {

    @JsonProperty("page")
    private int page;

    @JsonProperty("per_page")
    private int perPage;

    @JsonProperty("total")
    private int total;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("data")
    private List<T> data;

    @JsonProperty("support")
    private Support support;
}
