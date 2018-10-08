package testeSpark;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class Person implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "fname")
    private String fname;

    @JsonProperty(value = "lname")
    private String lname;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "gender")
    private String gender;

    //getters and setters
}
