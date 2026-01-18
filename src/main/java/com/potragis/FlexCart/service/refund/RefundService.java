package com.potragis.FlexCart.service.refund;

import com.potragis.FlexCart.dto.refund.RefundRequest;
import com.potragis.FlexCart.dto.refund.RefundResponse;
import com.potragis.FlexCart.mapper.refund.RefundDTOMapper;
import com.potragis.FlexCart.model.entity.Orders;
import com.potragis.FlexCart.model.entity.Refunds;
import com.potragis.FlexCart.repository.IOrderRepository;
import com.potragis.FlexCart.repository.IRefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RefundService implements IRefundService{

    private final IRefundRepository refundRepository;
    private final IOrderRepository ordersRepository;
    private final RefundDTOMapper mapper;

    @Override
    public RefundResponse createRefund(RefundRequest request) {
        Orders order = ordersRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + request.getOrderId()));

        Refunds refund = mapper.toEntity(request, order);
        Refunds saved = refundRepository.save(refund);

        return mapper.toDTO(saved);
    }

    @Override
    public RefundResponse getRefundById(Long id) {
        Refunds refund = refundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Refund not found with ID: " + id));
        return mapper.toDTO(refund);
    }

    @Override
    public List<RefundResponse> getRefundsByOrderId(Long orderId) {
        List<Refunds> refunds = refundRepository.findByOrderId(orderId);
        return refunds.stream().map(mapper::toDTO).toList();
    }

    @Override
    public RefundResponse updateRefund(Long id, RefundRequest request) {
        Refunds refund = refundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Refund not found with ID: " + id));

        Orders order = ordersRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + request.getOrderId()));

        mapper.updateEntity(refund, request, order);
        Refunds updated = refundRepository.save(refund);

        return mapper.toDTO(updated);
    }

    @Override
    public void deleteRefund(Long id) {
        Refunds refund = refundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Refund not found with ID: " + id));
        refundRepository.delete(refund);
    }

}
