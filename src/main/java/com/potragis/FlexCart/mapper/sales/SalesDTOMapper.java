package com.potragis.FlexCart.mapper.sales;

import com.potragis.FlexCart.dto.sales.SalesSummaryResponse;
import org.springframework.stereotype.Component;

@Component
public class SalesDTOMapper {

    public SalesSummaryResponse toSummary(Double totalSales, Long totalOrders) {
        return new SalesSummaryResponse(totalSales, totalOrders);
    }

}
