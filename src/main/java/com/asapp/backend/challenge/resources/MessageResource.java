package com.asapp.backend.challenge.resources;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "messages", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class MessageResource {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long sender;
    @Column
    private Long recipient;
    @Column
    private String type;
    @Column
    private String text;
    @Column
    private Date timestamp = Date.from(Instant.now());
    @Column
    private Boolean hasCrawlerPassed;

    //The orphanRemoval attribute is going to instruct the JPA provider to trigger a remove entity state transition when a Metadata entity is no longer referenced by its parent Message entity.
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "message_id")
    private List<MetadataResource> metadataResourceList;

    public MessageResource addMetadataResource(MetadataResource metadataResource) {
        metadataResourceList.add(metadataResource);
        metadataResource.setMessageResource(this);
        return this;
    }

    public boolean hasMetadata() {
        return !type.equals("text") && metadataResourceList != null && metadataResourceList.size() > 0;
    }

    public boolean isMetadataSupported() {
        return type.equals("image") || type.equals("video");
    }
}
