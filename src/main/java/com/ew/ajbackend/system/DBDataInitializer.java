package com.ew.ajbackend.system;

import com.ew.ajbackend.artifact.Artifact;
import com.ew.ajbackend.artifact.ArtifactRepository;
import com.ew.ajbackend.uwhubuser.UWHubUser;
import com.ew.ajbackend.uwhubuser.UserRepository;
import com.ew.ajbackend.wizard.Wizard;
import com.ew.ajbackend.wizard.WizardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;


@Component
public class DBDataInitializer implements CommandLineRunner {

    private final ArtifactRepository artifactRepository;
    private final WizardRepository wizardRepository;
    private final UserRepository userRepository;

    public DBDataInitializer(ArtifactRepository artifactRepository, WizardRepository wizardRepository, UserRepository userRepository) {
        this.artifactRepository = artifactRepository;
        this.wizardRepository = wizardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Artifact a1 = new Artifact();
        a1.setDescription("Helps to write");
        a1.setImageUrl("/pen.jpg");
        a1.setId("3213");
        a1.setName("Pen");

        Artifact a2 = new Artifact();
        a2.setDescription("Helps to write");
        a2.setImageUrl("/pencil.jpg");
        a2.setId("3214");
        a2.setName("Pencil");

        Artifact a3 = new Artifact();
        a3.setDescription("Helps to shoot");
        a3.setImageUrl("/gun.jpg");
        a3.setId("3263");
        a3.setName("Bazooka");

        Artifact a4 = new Artifact();
        a4.setDescription("Helps to Defend");
        a4.setImageUrl("/shield.pg");
        a4.setId("3273");
        a4.setName("Shield");

        Artifact a5 = new Artifact();
        a5.setDescription("Helps to Drink");
        a5.setImageUrl("/bottle.jpg");
        a5.setId("3283");
        a5.setName("Bottle");

        Artifact a6 = new Artifact();
        a6.setDescription("Helps to Cover");
        a6.setImageUrl("/jacket.jpg");
        a6.setId("3233");
        a6.setName("Jacket");

        Wizard w1 = new Wizard();
        w1.setName("Akash");
        w1.setId(1);
        w1.addArtifact(a1);
        w1.addArtifact(a2);
        Wizard w2 = new Wizard();
        w2.setName("Ananya");
        w2.setId(1);
        w2.addArtifact(a3);
        w2.addArtifact(a4);

        Wizard w3 = new Wizard();
        w3.setName("Ajay");
        w3.setId(1);
        w3.addArtifact(a5);


        wizardRepository.save(w1);
        wizardRepository.save(w2);
        wizardRepository.save(w3);
        // Saving wizards Alone willl save all the associated artifacts

        artifactRepository.save(a6);

        // Create some users.
        UWHubUser u1 = new UWHubUser();
        u1.setId(1);
        u1.setUsername("Amith");
        u1.setPassword("123456");
        u1.setEnabled(true);
        u1.setRoles("admin user");

        UWHubUser u2 = new UWHubUser();
        u2.setId(2);
        u2.setUsername("Terrie");
        u2.setPassword("654321");
        u2.setEnabled(true);
        u2.setRoles("user");

        UWHubUser u3 = new UWHubUser();
        u3.setId(3);
        u3.setUsername("Michael Beer");
        u3.setPassword("qwerty");
        u3.setEnabled(false);
        u3.setRoles("user");

        this.userRepository.save(u1);
        this.userRepository.save(u2);
        this.userRepository.save(u3);





    }
}
