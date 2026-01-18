package com.potragis.FlexCart.service.payment;

import com.potragis.FlexCart.dto.payment.PaymentRequest;
import com.potragis.FlexCart.dto.payment.PaymentResponse;
import com.potragis.FlexCart.model.enums.PaymentStatus;

import java.util.List;

public interface IPaymentService {

    PaymentResponse createPayment(PaymentRequest request);

    PaymentResponse getPaymentById(Long id);

    List<PaymentResponse> getPaymentsByOrderId(Long orderId);

    PaymentResponse updatePayment(Long id, PaymentRequest request);

    PaymentResponse updatePaymentStatus(Long id, PaymentStatus status);

    void deletePayment(Long id);

}
