package com.alura.pix_consumer.entrypoints.dto;

import com.alura.pix_consumer.enums.PixStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class PixDto {
  private String identifier;
  private String sourceKey;
  private String destinationKey;
  private Double value;
  private LocalDateTime transferDate;
  private PixStatus status;
}
