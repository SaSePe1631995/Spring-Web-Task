package web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class UserUpdateDto {

    private Long id;

    @NotNull
    @Size(min = 3, max = 20, message = "Firstname must be {min} - {max} characters long")
    @NotBlank(message = "Firstname mustn't be empty.")
    @Pattern(regexp = "^[^0-9]*$", message = "The firstname can't contain numbers.")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 20, message = "Lastname must be {min} - {max} characters long")
    @NotBlank(message = "Lastname mustn't be empty.")
    @Pattern(regexp = "^[^0-9]*$", message = "The lastname can't contain numbers.")
    private String lastName;

    @NotNull
    @Positive(message = "The number must be natural.")
    private short age;

    public UserUpdateDto() {}

    public UserUpdateDto(Long id,
                         String firstName,
                         String lastName,
                         @NotNull @Positive(message = "The number must be natural.") short age) {
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public  Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @Positive(message = "The number must be natural.") short getAge() {
        return age;
    }

    public void setAge(@NotNull @Positive(message = "The number must be natural.") short age) {
        this.age = age;
    }
}
