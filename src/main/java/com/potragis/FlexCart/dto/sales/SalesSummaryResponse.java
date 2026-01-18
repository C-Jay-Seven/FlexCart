package com.potragis.FlexCart.dto.sales;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesSummaryResponse {

    private Double totalSales;
    private Long totalOrders;

}
