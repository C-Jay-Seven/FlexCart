package com.potragis.FlexCart.service.discount;

import com.potragis.FlexCart.dto.discount.DiscountRequest;
import com.potragis.FlexCart.dto.discount.DiscountResponse;

import java.util.List;

public interface IDiscountService {

    DiscountResponse createDiscount(DiscountRequest request);

    DiscountResponse getDiscountById(Long id);

    List<DiscountResponse> getAllDiscounts();

    DiscountResponse updateDiscount(Long id, DiscountRequest request);

    void deleteDiscount(Long id);

}
