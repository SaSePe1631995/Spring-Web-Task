package web.dto;

import javax.validation.constraints.*;

public class UserCreateDto {

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

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }
}
