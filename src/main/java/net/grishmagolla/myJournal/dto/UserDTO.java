package net.grishmagolla.myJournal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotEmpty
    @Schema(description = "User UserName")
    public String userName;
    @NotEmpty
    public String userPassword;
    private String userEmail;
    private boolean sentimentAnalysis;
}
