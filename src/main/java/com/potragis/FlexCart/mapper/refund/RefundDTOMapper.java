package com.potragis.FlexCart.mapper.refund;

import com.potragis.FlexCart.dto.refund.RefundRequest;
import com.potragis.FlexCart.dto.refund.RefundResponse;
import com.potragis.FlexCart.model.entity.Orders;
import com.potragis.FlexCart.model.entity.Refunds;
import org.springframework.stereotype.Component;

@Component
public class RefundDTOMapper {

    public Refunds toEntity(RefundRequest request, Orders order) {
        Refunds refund = new Refunds();
        refund.setReason(request.getReason());
        refund.setAmount(request.getAmount());
        refund.setStatus(request.getStatus());
        refund.setRequestedAt(request.getRequestedAt());
        refund.setProcessedAt(request.getProcessedAt());
        refund.setOrder(order);
        return refund;
    }

    public RefundResponse toDTO(Refunds refund) {
        return new RefundResponse(
                refund.getId(),
                refund.getReason(),
                refund.getAmount(),
                refund.getStatus(),
                refund.getRequestedAt(),
                refund.getProcessedAt(),
                refund.getOrder() != null ? refund.getOrder().getId() : null
        );
    }

    public void updateEntity(Refunds refund, RefundRequest request, Orders order) {
        refund.setReason(request.getReason());
        refund.setAmount(request.getAmount());
        refund.setStatus(request.getStatus());
        refund.setRequestedAt(request.getRequestedAt());
        refund.setProcessedAt(request.getProcessedAt());
        refund.setOrder(order);
    }
}
