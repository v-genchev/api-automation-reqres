package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Data
public class Support {

    @JsonProperty("url")
    private String url;

    @JsonProperty("text")
    private String text;
}
