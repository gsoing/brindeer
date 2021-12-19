package org.gso.brinder.match.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gso.brinder.match.model.Address;
import org.gso.brinder.match.model.ProfileModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProfileDto {

    private String id;
    @NotEmpty
    private String userId;
    @Email
    private String mail;
    @Min(13)
    private int age;
    private String firstName;
    private String lastName;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDateTime created;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDateTime modified;
    private Address address;

    public ProfileModel toModel() {
        return ProfileModel.builder()
                .id(this.id)
                .userId(this.userId)
                .age(this.age)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .address(this.address)
                .build();
    }
}
