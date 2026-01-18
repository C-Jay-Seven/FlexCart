package com.potragis.FlexCart.service.sales;

import com.potragis.FlexCart.dto.sales.SalesSummaryResponse;
import com.potragis.FlexCart.mapper.sales.SalesDTOMapper;
import com.potragis.FlexCart.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalesService implements ISalesService {

    private final IOrderRepository orderRepository;
    private final SalesDTOMapper mapper;

    @Override
    public SalesSummaryResponse getSellerSalesSummary(Long sellerId) {

        Double totalSales = orderRepository.getSellerTotalSales(sellerId);
        Long totalOrders = orderRepository.getSellerTotalOrders(sellerId);

        return mapper.toSummary(totalSales, totalOrders);
    }

}
