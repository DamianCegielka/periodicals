package org.cegielka.periodicals.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRequest {

    private int pageNo;
    private int pageSize;
    private String query;
    private String sortField;
    private String sortDirection;
    private Long groupValue;
}
