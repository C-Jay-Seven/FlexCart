package com.potragis.FlexCart.mapper.payment;

import com.potragis.FlexCart.dto.payment.PaymentRequest;
import com.potragis.FlexCart.dto.payment.PaymentResponse;
import com.potragis.FlexCart.model.entity.Orders;
import com.potragis.FlexCart.model.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentDTOMapper {

    public Payment toEntity(PaymentRequest request, Orders order) {
        Payment payment = new Payment();
        payment.setMethod(request.getMethod());
        payment.setStatus(request.getStatus());
        payment.setTransactionReference(request.getTransactionReference());
        payment.setAmount(request.getAmount());
        payment.setPaidAt(request.getPaidAt());
        payment.setOrder(order);
        return payment;
    }

    public PaymentResponse toDTO(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getMethod(),
                payment.getStatus(),
                payment.getTransactionReference(),
                payment.getAmount(),
                payment.getPaidAt(),
                payment.getOrder() != null ? payment.getOrder().getId() : null
        );
    }

    public void updateEntity(Payment payment, PaymentRequest request, Orders order) {
        payment.setMethod(request.getMethod());
        payment.setStatus(request.getStatus());
        payment.setTransactionReference(request.getTransactionReference());
        payment.setAmount(request.getAmount());
        payment.setPaidAt(request.getPaidAt());
        payment.setOrder(order);
    }
}
