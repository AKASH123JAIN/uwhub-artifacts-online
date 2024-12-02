package com.ew.ajbackend.artifact;


import com.ew.ajbackend.artifact.utils.IdWorker;
import com.ew.ajbackend.system.exception.ObjectNotFoundException;
import com.ew.ajbackend.wizard.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class ArtifactServiceTest {

    @Mock
    ArtifactRepository artifactRepository;

    @Mock
    IdWorker idWorker;

    @InjectMocks
    ArtifactService artifactService;

    List<Artifact> artifacts;



    @BeforeEach
    public void setUp() throws Exception {
        this.artifacts  = new ArrayList<>();
        Artifact a1 = new Artifact();
        a1.setDescription("Helps to write");
        a1.setImageUrl("/pen.jpg");
        a1.setId("3213");
        a1.setName("Pen");
        this.artifacts.add(a1);
        Artifact a2 = new Artifact();
        a2.setDescription("Helps to write");
        a2.setImageUrl("/pencil.jpg");
        a2.setId("3214");
        a2.setName("Pencil");
        this.artifacts.add(a2);
        Artifact a3 = new Artifact();
        a3.setDescription("Helps to shoot");
        a3.setImageUrl("/gun.jpg");
        a3.setId("3263");
        a3.setName("Bazooka");
        this.artifacts.add(a3);
        Artifact a4 = new Artifact();
        a4.setDescription("Helps to Defend");
        a4.setImageUrl("/shield.pg");
        a4.setId("3273");
        a4.setName("Shield");
        this.artifacts.add(a4);
        Artifact a5 = new Artifact();
        a5.setDescription("Helps to Drink");
        a5.setImageUrl("/bottle.jpg");
        a5.setId("3283");
        a5.setName("Bottle");
        this.artifacts.add(a5);
        Artifact a6 = new Artifact();
        a6.setDescription("Helps to Cover");
        a6.setImageUrl("/jacket.jpg");
        a6.setId("3233");
        a6.setName("Jacket");
        this.artifacts.add(a6);
    }

    @Test
    public void testFindByIdSuccess(){

        // Given. Arrange Inputs and targets. Define the behaviour of Mock object artifactRepository
        Artifact artifact = new Artifact();
        artifact.setId("1234");
        artifact.setDescription("test123");
        artifact.setImageUrl("ImageTest");

        Wizard wiz = new Wizard();
        wiz.setId(2);
        wiz.setName("Ananya Soni");

        artifact.setOwner(wiz);

        given(artifactRepository.findById("1234")).willReturn(Optional.of(artifact));// Defines the behaviour of the mock object

        Artifact returnedArtifact = artifactService.findById("1234");
        assertThat(returnedArtifact.getId()).isEqualTo(artifact.getId());
        assertThat(returnedArtifact.getDescription()).isEqualTo(artifact.getDescription());
        assertThat(returnedArtifact.getImageUrl()).isEqualTo(artifact.getImageUrl());
        assertThat(returnedArtifact.getOwner()).isEqualTo(artifact.getOwner());

        // When Act on the target behaviour. When steps should cover the method to be tested

        // Then Assert the expected outcomes
    }

    @Test
    void testFindByIdNotFound(){
        // Given
        given(artifactRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> {
            Artifact returnedArtifact = artifactService.findById("1234");
        });

        // Then
        assertThat(thrown).isInstanceOf(ObjectNotFoundException.class).
                hasMessage("Could not find artifact with Id 1234 :(");

    }

    @Test
    void testFindAllSuccess(){
        // Given
        given(artifactRepository.findAll()).willReturn(this.artifacts);

        // When
        List<Artifact> actualArtifacts = artifactService.findAllArtifacts();

        // Then
        assertThat(actualArtifacts.size()).isEqualTo(this.artifacts.size());
    }

    @Test
    void testSaveSuccess(){
        // Given

        Artifact newArtifact = new Artifact();
        newArtifact.setName(" Artifact 3");
        newArtifact.setDescription("Description");
        newArtifact.setImageUrl("/imageUrl");

        given(idWorker.nextId()).willReturn(123456L);
        given(artifactRepository.save(newArtifact)).willReturn(newArtifact);
        // When

        Artifact savedArtifact = artifactService.save(newArtifact);

        // Then
        assertThat(savedArtifact.getId()).isEqualTo("123456");
        assertThat(savedArtifact.getName()).isEqualTo(newArtifact.getName());
        assertThat(savedArtifact.getDescription()).isEqualTo(newArtifact.getDescription());
        assertThat(savedArtifact.getImageUrl()).isEqualTo(newArtifact.getImageUrl());
    }

    @Test
    void testUpdateSuccess(){
        // Given
        Artifact oldArtifact = new Artifact();
        oldArtifact.setName("Pen");
        oldArtifact.setId("1234");
        oldArtifact.setDescription("test123");
        oldArtifact.setImageUrl("ImageTest");

        Artifact update = new Artifact();
        update.setName("Pen");
        update.setId("1234");
        update.setDescription("test updated description");
        update.setImageUrl("ImageTest");

        given(artifactRepository.findById("1234")).willReturn(Optional.of(oldArtifact));
        given(artifactRepository.save(oldArtifact)).willReturn(oldArtifact);
        // When
        Artifact updatedArtifact = artifactService.update("1234", update);
        // Then
        assertThat(updatedArtifact.getId()).isEqualTo(update.getId());
        assertThat(updatedArtifact.getDescription()).isEqualTo(update.getDescription());

    }

    @Test
    void testUpdateNotFound() {
        // Given
        Artifact update = new Artifact();
        update.setName("Random Object");
        update.setDescription("A new description.");
        update.setImageUrl("ImageUrl");

        given(artifactRepository.findById("1250808601744904192")).willReturn(Optional.empty());

        // When & Then
        assertThrows(ObjectNotFoundException.class, () -> {
            artifactService.update("1250808601744904192", update);
        });


    }

    @Test
    void testDeleteSuccess(){
        // Given
        Artifact artifact = new Artifact();
        artifact.setId("1250808601744904192");
        artifact.setName("Invisibility Cloak");
        artifact.setDescription("An invisibility cloak is used to make the wearer invisible.");
        artifact.setImageUrl("ImageUrl");

        given(artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(artifact));
        doNothing().when(artifactRepository).deleteById("1250808601744904192");

        // When
        artifactService.delete("1250808601744904192");


    }

    @Test
    void testDeleteNotFound(){
        // Given
        given(artifactRepository.findById("1250808601744904192")).willReturn(Optional.empty());

        // When
        assertThrows(ObjectNotFoundException.class, () -> {
            artifactService.delete("1250808601744904192");
        });


    }

}