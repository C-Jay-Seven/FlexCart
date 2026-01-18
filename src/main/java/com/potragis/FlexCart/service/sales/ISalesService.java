package com.potragis.FlexCart.service.sales;

import com.potragis.FlexCart.dto.sales.SalesSummaryResponse;

public interface ISalesService {
    SalesSummaryResponse getSellerSalesSummary(Long sellerId);
}
