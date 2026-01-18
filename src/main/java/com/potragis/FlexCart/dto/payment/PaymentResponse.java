package com.potragis.FlexCart.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private Long id;
    private String method;
    private String status;
    private String transactionReference;
    private BigDecimal amount;
    private LocalDateTime paidAt;
    private Long orderId;
}
