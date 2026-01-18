package com.potragis.FlexCart.service.payment;

import com.potragis.FlexCart.dto.payment.PaymentRequest;
import com.potragis.FlexCart.dto.payment.PaymentResponse;
import com.potragis.FlexCart.mapper.payment.PaymentDTOMapper;
import com.potragis.FlexCart.model.entity.Orders;
import com.potragis.FlexCart.model.entity.Payment;
import com.potragis.FlexCart.model.enums.PaymentStatus;
import com.potragis.FlexCart.repository.IOrderRepository;
import com.potragis.FlexCart.repository.IPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {

    private final IPaymentRepository paymentRepository;
    private final IOrderRepository ordersRepository;
    private final PaymentDTOMapper mapper;

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {

        Orders order = findOrderById(request.getOrderId());

        Payment payment = mapper.toEntity(request, order);

        payment.setStatus(String.valueOf(PaymentStatus.PENDING));

        Payment saved = paymentRepository.save(payment);
        return mapper.toDTO(saved);
    }

    @Override
    public PaymentResponse getPaymentById(Long id) {
        return mapper.toDTO(findPaymentById(id));
    }

    @Override
    public List<PaymentResponse> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PaymentResponse updatePayment(Long id, PaymentRequest request) {

        Payment payment = findPaymentById(id);

        if (!payment.getOrder().getId().equals(request.getOrderId())) {
            Orders newOrder = findOrderById(request.getOrderId());
            payment.setOrder(newOrder);
        }

        mapper.updateEntity(payment, request, payment.getOrder());

        Payment updated = paymentRepository.save(payment);
        return mapper.toDTO(updated);
    }

    public PaymentResponse updatePaymentStatus(Long id, PaymentStatus status) {
        Payment payment = findPaymentById(id);
        payment.setStatus(String.valueOf(status));
        return mapper.toDTO(paymentRepository.save(payment));
    }


    @Override
    public void deletePayment(Long id) {
        paymentRepository.delete(findPaymentById(id));
    }

    private Payment findPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found with ID: " + id));
    }

    private Orders findOrderById(Long id) {
        return ordersRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found with ID: " + id));
    }
}
