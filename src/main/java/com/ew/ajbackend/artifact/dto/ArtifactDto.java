package com.ew.ajbackend.artifact.dto;

import com.ew.ajbackend.wizard.dto.WizardDto;
import jakarta.validation.constraints.NotEmpty;

public record ArtifactDto(String id,
                          @NotEmpty(message = "Name is required")
                          String name,

                          @NotEmpty(message = "Description is required")
                          String description,

                          @NotEmpty(message = "Image URL is required")
                          String imageUrl,

                          WizardDto owner){
}
