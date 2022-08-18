package org.cegielka.periodicals.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggedUserIdAndRoleResponse {

    private Long id;
    private String role;
}

