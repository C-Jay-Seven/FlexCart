package com.potragis.FlexCart.service.discount;

import com.potragis.FlexCart.dto.discount.DiscountRequest;
import com.potragis.FlexCart.dto.discount.DiscountResponse;
import com.potragis.FlexCart.model.entity.Discounts;
import com.potragis.FlexCart.repository.IDiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountService implements IDiscountService{

    private final IDiscountRepository discountRepository;

    private DiscountResponse mapToResponse(Discounts discount) {
        return new DiscountResponse(
                discount.getId(),
                discount.getDescription(),
                discount.getDiscountType(),
                discount.getDiscountValue(),
                discount.getStartDate(),
                discount.getEndDate(),
                discount.getExpirationDate(),
                discount.isActive()
        );
    }

    @Override
    public DiscountResponse createDiscount(DiscountRequest request) {
        Discounts discount = new Discounts();
        discount.setDescription(request.getDescription());
        discount.setDiscountType(request.getDiscountType());
        discount.setDiscountValue(request.getDiscountValue());
        discount.setStartDate(request.getStartDate());
        discount.setEndDate(request.getEndDate());
        discount.setExpirationDate(request.getExpirationDate());
        discount.setActive(request.isActive());

        Discounts saved = discountRepository.save(discount);
        return mapToResponse(saved);
    }

    @Override
    public DiscountResponse getDiscountById(Long id) {
        Discounts discount = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found with ID: " + id));
        return mapToResponse(discount);
    }

    @Override
    public List<DiscountResponse> getAllDiscounts() {
        return discountRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public DiscountResponse updateDiscount(Long id, DiscountRequest request) {
        Discounts discount = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found with ID: " + id));

        discount.setDescription(request.getDescription());
        discount.setDiscountType(request.getDiscountType());
        discount.setDiscountValue(request.getDiscountValue());
        discount.setStartDate(request.getStartDate());
        discount.setEndDate(request.getEndDate());
        discount.setExpirationDate(request.getExpirationDate());
        discount.setActive(request.isActive());

        Discounts updated = discountRepository.save(discount);
        return mapToResponse(updated);
    }

    @Override
    public void deleteDiscount(Long id) {
        Discounts discount = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found with ID: " + id));
        discountRepository.delete(discount);
    }

}
