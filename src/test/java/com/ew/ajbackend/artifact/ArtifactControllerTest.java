package com.ew.ajbackend.artifact;

import com.ew.ajbackend.artifact.dto.ArtifactDto;
import com.ew.ajbackend.system.StatusCode;
import com.ew.ajbackend.system.exception.ObjectNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.ew.ajbackend.system.StatusCode.SUCCESS;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class ArtifactControllerTest {

    // for Controller level Methods testing we are targeting both the When and Then based test -
    // -Implementation pattern together

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ArtifactService artifactService;

    List<Artifact> artifacts;

    @BeforeEach
    void setUp() {
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

    @AfterEach
    void tearDown() {
    }

    @Test
    void findArtifactByIdSuccess() throws Exception {
        // Given
        given(this.artifactService.findById("3213")).willReturn(this.artifacts.get(0));

        //when And then combined together
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/artifacts/3213").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value(3213))
                .andExpect(jsonPath("$.data.name").value("Pen"));


    }
    @Test
    void testFindAllArtifactsSuccess() throws Exception {
        //Given
        given(this.artifactService.findAllArtifacts()).willReturn(this.artifacts);
        // When and then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/artifacts").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.artifacts.size())))
                .andExpect(jsonPath("$.data[0].id").value("3213"))
                .andExpect(jsonPath("$.data[0].name").value("Pen"))
                .andExpect(jsonPath("$.data[1].id").value("3214"))
                .andExpect(jsonPath("$.data[1].name").value("Pencil"));


    }
    @Test
    void testAddArtifactSuccess() throws Exception {
        // Given
        ArtifactDto artifactDto = new ArtifactDto(null,
                "Remembrall",
                "A Remembrall was a magical large marble-sized glass ball that contained smoke which turned red when its owner or user had forgotten something. It turned clear once whatever was forgotten was remembered.",
                "ImageUrl",
                null);
        String json = this.objectMapper.writeValueAsString(artifactDto);

        Artifact savedArtifact = new Artifact();
        savedArtifact.setId("1250808601744904197");
        savedArtifact.setName("Java Course Material");
        savedArtifact.setDescription("A Learning Kit");
        savedArtifact.setImageUrl("ImageUrl");

        given(this.artifactService.save(Mockito.any(Artifact.class))).willReturn(savedArtifact);


        // When and then
        this.mockMvc.perform(post("/api/v1/artifacts").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.name").value(savedArtifact.getName()))
                .andExpect(jsonPath("$.data.description").value(savedArtifact.getDescription()))
                .andExpect(jsonPath("$.data.imageUrl").value(savedArtifact.getImageUrl()));
    }
        @Test
        void testUpdateArtifactSuccess() throws Exception {
            // Given
            ArtifactDto artifactDto = new ArtifactDto("1250808601744904192",
                    "Invisibility Cloak",
                    "A new description.",
                    "ImageUrl",
                    null);
            String json = this.objectMapper.writeValueAsString(artifactDto);

            Artifact updatedArtifact = new Artifact();
            updatedArtifact.setId("1250808601744904192");
            updatedArtifact.setName("Invisibility Cloak");
            updatedArtifact.setDescription("A new description.");
            updatedArtifact.setImageUrl("ImageUrl");

            given(this.artifactService.update(eq("1250808601744904192"), Mockito.any(Artifact.class))).willReturn(updatedArtifact);

            // When and then
            this.mockMvc.perform(put("/api/v1/artifacts/1250808601744904192").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.flag").value(true))
                    .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                    .andExpect(jsonPath("$.message").value("Update Success"))
                    .andExpect(jsonPath("$.data.id").value("1250808601744904192"))
                    .andExpect(jsonPath("$.data.name").value(updatedArtifact.getName()))
                    .andExpect(jsonPath("$.data.description").value(updatedArtifact.getDescription()))
                    .andExpect(jsonPath("$.data.imageUrl").value(updatedArtifact.getImageUrl()));
        }

        @Test
        void testUpdateArtifactErrorWithNonExistentId() throws Exception {
            // Given
            ArtifactDto artifactDto = new ArtifactDto("1250808601744904192",
                    "Invisibility Cloak",
                    "A new description.",
                    "ImageUrl",
                    null);
            String json = this.objectMapper.writeValueAsString(artifactDto);

            given(this.artifactService.update(eq("1250808601744904192"), Mockito.any(Artifact.class))).willThrow(new ObjectNotFoundException("artifact","1250808601744904192"));

            // When and then
            this.mockMvc.perform(put("/api/v1/artifacts/1250808601744904192").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.flag").value(false))
                    .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                    .andExpect(jsonPath("$.message").value("Could not find artifact with Id 1250808601744904192 :("))
                    .andExpect(jsonPath("$.data").isEmpty());
        }

    @Test
    void testDeleteArtifactSuccess() throws Exception {
        // Given
        doNothing().when(this.artifactService).delete("1250808601744904191");

        // When and then
        this.mockMvc.perform(delete("/api/v1/artifacts/1250808601744904191").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteArtifactErrorWithNonExistentId() throws Exception {
        // Given
        doThrow(new ObjectNotFoundException("artifact","1250808601744904191")).when(this.artifactService).delete("1250808601744904191");

        // When and then
        this.mockMvc.perform(delete("/api/v1/artifacts/1250808601744904191").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find artifact with Id 1250808601744904191 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}