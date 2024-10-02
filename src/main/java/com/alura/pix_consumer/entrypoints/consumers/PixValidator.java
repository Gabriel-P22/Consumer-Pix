package com.alura.pix_consumer.entrypoints.consumers;

import com.alura.pix_consumer.entrypoints.dto.PixDto;
import com.alura.pix_consumer.enums.PixStatus;
import com.alura.pix_consumer.infrastructure.database.model.Key;
import com.alura.pix_consumer.infrastructure.database.model.Pix;
import com.alura.pix_consumer.infrastructure.database.repository.KeyRepository;
import com.alura.pix_consumer.infrastructure.database.repository.PixRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PixValidator {

    @Autowired private final KeyRepository keyRepository;

    @Autowired private final PixRepository pixRepository;

    @KafkaListener(topics = "pix-topic", groupId = "grupo")
    public void pixProcessing(PixDto pixDto) {
        System.out.println("Pix recebido: " + pixDto.getIdentifier());

        Pix pix = pixRepository.findByIdentifier(pixDto.getIdentifier());
        Key sourceKey = keyRepository.findByKey(pixDto.getSourceKey());
        Key destinationKey = keyRepository.findByKey(pixDto.getDestinationKey());

        if (sourceKey != null && destinationKey != null) {
            pix.setStatus(PixStatus.PROCESSED);
        } else {
            pix.setStatus(PixStatus.ERROR);
        }

        pixRepository.save(pix);
    }

}
