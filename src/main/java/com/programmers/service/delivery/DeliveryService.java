package com.programmers.service.delivery;

import com.programmers.domain.delivery.Delivery;
import com.programmers.dto.delivery.DeliveryResponseDto;
import com.programmers.repository.delivery.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    public Delivery save(DeliveryRequestDto deliveryRequestDto) {
        return deliveryRepository.save(deliveryRequestDto.toEntity());
    }

    public void deleteById(Long deliveryId) {
        deliveryRepository.deleteById(deliveryId);
    }
}
