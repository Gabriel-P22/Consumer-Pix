package com.alura.pix_consumer.entrypoints.consumers;

import com.alura.pix_consumer.entrypoints.dto.PixDto;
import com.alura.pix_consumer.enums.PixStatus;
import com.alura.pix_consumer.infrastructure.adapters.LocalDateTimeAdapter;
import com.alura.pix_consumer.infrastructure.database.model.Key;
import com.alura.pix_consumer.infrastructure.database.model.Pix;
import com.alura.pix_consumer.infrastructure.database.repository.KeyRepository;
import com.alura.pix_consumer.infrastructure.database.repository.PixRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class PixValidator {

    @Autowired private KeyRepository keyRepository;

    @Autowired private PixRepository pixRepository;

    @KafkaListener(topics = "pix-topic", groupId = "grupo")
    public void pixProcessing(String message) {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        PixDto pixDto = gson.fromJson(message, PixDto.class);

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
