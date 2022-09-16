package org.cegielka.periodicals.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse {

    private int pageNo;
    private int pageSize;
    private String query;
    private String sortField;
    private String sortDirection;
    private Long groupValue;
}
