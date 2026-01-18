package com.potragis.FlexCart.service.refund;

import com.potragis.FlexCart.dto.refund.RefundRequest;
import com.potragis.FlexCart.dto.refund.RefundResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRefundService {

    RefundResponse createRefund(RefundRequest request);

    RefundResponse getRefundById(Long id);

    List<RefundResponse> getRefundsByOrderId(Long orderId);

    RefundResponse updateRefund(Long id, RefundRequest request);

    void deleteRefund(Long id);

}
