package com.ew.ajbackend.artifact.converter;

import com.ew.ajbackend.artifact.Artifact;
import com.ew.ajbackend.artifact.dto.ArtifactDto;
import com.ew.ajbackend.wizard.converter.WizardToWizardDtoConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtifactToArtifactDtoConverter implements Converter<Artifact, ArtifactDto> {

    private final WizardToWizardDtoConverter wizardToWizardDtoConverter;

    public ArtifactToArtifactDtoConverter(WizardToWizardDtoConverter wizardToWizardDtoConverter) {
        this.wizardToWizardDtoConverter = wizardToWizardDtoConverter;
    }


    @Override
    public ArtifactDto convert(Artifact source) {
       ArtifactDto artifactDto = new ArtifactDto(source.getId(), source.getName(), source.getDescription(),
               source.getImageUrl(), source.getOwner() != null ? this.wizardToWizardDtoConverter
               .convert(source.getOwner()): null);
        return artifactDto;
    }
}
